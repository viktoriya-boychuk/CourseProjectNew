import connection.SQLHelper;
import dao.Program;
import utils.Logger;
import utils.Request;
import utils.TaskHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    public static final Integer DEFAULT_SOCKET_PORT = 28365;
    private static Server mServer;
    private SQLHelper mSQLHelper;
    private static TaskHandler mTaskHandler;
    private CustomServerSocket mServerSocket;

    public Server() {

        try {
            mServerSocket = new CustomServerSocket(DEFAULT_SOCKET_PORT);
            mSQLHelper = SQLHelper.getInstance();
        } catch (IOException e) {
            Logger.logError("Error creating socket", "Most likely the socket has been already occupied. Try starting the application with a different one");
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        mServer = this;
        mTaskHandler = new TaskHandler("Logger");
        mTaskHandler.startTask(new ClientRequestsListener());

        mTaskHandler.startInCurrentThread();
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

    public class ClientRequestsListener implements Runnable {

        @Override
        public void run() {
            while (!mTaskHandler.status()) {
                try {
                    Socket client = Server.getServerSocketInstance().accept();
                    Logger.logInfo("Client connected", client.getInetAddress().toString());

                    PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);


                    String test = mSQLHelper.getJSONArrayFor(Program.class).toString();
                    Logger.logInfo("Program table JSON is: ", mSQLHelper.getJSONArrayFor(Program.class).toString());
                    Request request = new Request(Request.RequestType.POST, test);
                    out.println(request.toString());
                } catch (IOException e) {
                    Logger.logError("Server Socket", "Socket " + Server.getCurrentPort());
                    e.printStackTrace();
                } catch (IllegalAccessException | InstantiationException | SQLException e) {
                    e.printStackTrace();
                }
            }
            Logger.logInfo("Server Thread", "The server doesn't listen anymore and has stopped");
        }
    }

    public static class RequestHandler implements Runnable {
        private Request mRequest;

        public RequestHandler(Request mRequest) {
            this.mRequest = mRequest;
        }

        @Override
        public void run() {

        }
    }
}
