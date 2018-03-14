import utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    public static final long DEFAULT_THREAD_DELAY = 10;
    public static final Integer DEFAULT_SOCKET_PORT = 28365;
    private static long threadDelay;
    private static ExecutorService mExecutorService;
    private static Server mServer;
    private CustomServerSocket mServerSocket;
    private ArrayList<Runnable> mTaskPool;
    private Boolean exit = false;

    public Server() {
        mExecutorService = Executors.newCachedThreadPool();
        mTaskPool = new ArrayList<>();
        threadDelay = DEFAULT_THREAD_DELAY;
        try {
            mServerSocket = new CustomServerSocket(DEFAULT_SOCKET_PORT);
        } catch (IOException e) {
            Logger.logError("Error creating socket", "Most likely the socket has been already occupied. Try starting the application with a different one");
            e.printStackTrace();
        }
        addToTaskPool(new ClientRequestsListener());
        mServer = this;
        mExecutorService.submit(mServer);
    }

    @Override
    public void run() {
        Logger.logInfo("Server started", "Socket is " + Server.getCurrentPort());
        while (!exit) {
            //TODO: Thread buffer for TaskPool
            ArrayList<Runnable> toRemove = new ArrayList<>();
            if (!mTaskPool.isEmpty()) {
                for (Runnable runnable : mTaskPool) {
                    addToTaskPool(runnable);
                    toRemove.add(runnable);
                }
                mTaskPool.removeAll(toRemove);
            }
            try {
                Thread.sleep(threadDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Logger.logInfo("Server", "Server stopped");
    }

    public static void start() {
        new Server();
    }

    public static void finish() {
        mServer.stopServer();
    }

    private void stopServer() {
        this.exit = true;
    }

    public static void addToTaskPool(Runnable runnable) {
        mExecutorService.submit(runnable);
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

    public static class ClientRequestsListener implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Socket client = Server.getServerSocketInstance().accept();

                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                    String buffer;
                    StringBuilder message = null;
                    while ((buffer = in.readLine()) != "\0") {
                        message.append(buffer);
                    }

                    Logger.logInfo("Received info:", message.toString());
                } catch (IOException e) {
                    Logger.logError("Server Socket", "Socket " + Server.getCurrentPort());
                    e.printStackTrace();
                }
            }
        }
    }

    public static class RequestHandler implements Runnable {

        @Override
        public void run() {

        }
    }
}
