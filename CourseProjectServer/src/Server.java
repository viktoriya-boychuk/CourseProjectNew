import connection.SQLHelper;
import dao.BaseDAO;
import utils.Logger;
import utils.Protocol;
import utils.TaskHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Server {
    public static final Integer DEFAULT_SOCKET_PORT = 28365;
    private static Server mServer;
    public SQLHelper mSQLHelper;
    private static TaskHandler mTaskHandler;
    private CustomServerSocket mServerSocket;
    private static ConcurrentHashMap<Task, ArrayList<BaseDAO>> mWaitingList;

    public Server() {

        try {
            mServerSocket = new CustomServerSocket(DEFAULT_SOCKET_PORT);
            mSQLHelper = SQLHelper.getInstance();
        } catch (IOException e) {
            Logger.logError("Error creating socket", "Most likely the socket has been already occupied. Try starting the application with a different one");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mServer = this;
        mWaitingList = new ConcurrentHashMap<>();
        mTaskHandler = new TaskHandler("Logger");
        mTaskHandler.startTask(new ClientRequestsListener());
        mTaskHandler.startTask(new Responder());

        mTaskHandler.startInBackgroundThread();
    }

    public static void start() {
        new Server();
    }

    public static void finish() {
        mServer.stopServer();
    }

    private void stopServer() {
        mTaskHandler.stop();
    }

    public static Server getServerInstance() {
        return mServer;
    }

    public static Integer getCurrentPort() {
        return Server.getServerSocketInstance().getLocalPort();
    }

    public static CustomServerSocket getServerSocketInstance() {
        return Server.getServerInstance().mServerSocket;
    }

    public static void addToWaitingList(Task task) {
        mWaitingList.put(task, new ArrayList<>());
    }

    public void spawnHandler(Protocol request, Socket socket) {
        RequestHandler requestHandler = new RequestHandler(request, socket);
        requestHandler.setFuture(mTaskHandler.addToTaskPool(requestHandler));
    }

    public class ClientRequestsListener implements Runnable {

        @Override
        public void run() {
            while (!mTaskHandler.status()) {
                try {
                    Socket client = Server.getServerSocketInstance().accept();
                    Logger.logInfo("Client connected", client.getInetAddress().toString());

                    PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    String test = in.readLine();

                    Protocol receivedRequest = new Protocol(test);
                    Logger.logInfo("Received request is", receivedRequest.toString());
                    spawnHandler(receivedRequest, client);
                } catch (IOException e) {
                    Logger.logError("Server Socket", "Socket " + Server.getCurrentPort());
                    e.printStackTrace();
                }
            }
            Logger.logInfo("Server Thread", "The server doesn't listen anymore and has stopped");
        }
    }

    private class Responder implements Runnable {

        @Override
        public void run() {
            while (!mTaskHandler.status()) {
                    if (!mWaitingList.isEmpty()) {
                        ArrayList<Task> toRemove = new ArrayList<>();
                        for (Map.Entry<Task, ArrayList<BaseDAO>> entry : mWaitingList.entrySet()) {
                            Future<ArrayList<BaseDAO>> future = entry.getKey().getFuture();
                            Socket socket = entry.getKey().getSocket();
                            Protocol request = entry.getKey().getRequest();
                            ArrayList<BaseDAO> arrayList;

                            if (future != null && future.isDone()) {
                                Logger.logInfo("Job's done!", "Result is here!");
                                try {
                                    arrayList = future.get();
                                    entry.setValue(arrayList);

                                    request.setData(arrayList);

                                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                                    writer.println(request);
                                    toRemove.add(entry.getKey());
                                } catch (InterruptedException | ExecutionException | IOException e) {
                                    e.printStackTrace();
                                    toRemove.add(entry.getKey());
                                } finally {
                                    for (Task task : toRemove) {
                                        mWaitingList.remove(task);
                                    }
                                }
                            }
                        }
                        for (Task task : toRemove) {
                            mWaitingList.remove(task);
                        }
                    }
                    try {
                        Thread.sleep(TaskHandler.DEFAULT_THREAD_DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
