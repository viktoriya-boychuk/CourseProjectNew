import utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final Integer DEFAULT_SOCKET = 28365;
    private static CustomServerSocket mServerSocket;
    private ExecutorService mExecutorService;
    private static Server mServer;

    public static ArrayList<Socket> mSocketList = new ArrayList<>();

    private Server() {
        mExecutorService = Executors.newCachedThreadPool();
        attachTask(new ClientRequestsListener());
        mServer = this;
    }

    public static void start() {
        new Server();
    }

    private static Server getServerInstance() {
        return mServer;
    }

    static ServerSocket getServerSocketInstance() {
        if (mServerSocket == null) {
            try {
                mServerSocket = new CustomServerSocket(Server.DEFAULT_SOCKET);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mServerSocket;
    }

    public static Integer getCurrentPort() {
        return getServerSocketInstance().getLocalPort();
    }

    private void attachTask(Runnable runnable) {
        mExecutorService.submit(runnable);
    }

    public static void spawnHandler(String data) {
        Logger.logInfo("Client Message", data);

        // Обробка інфи тут
    }

    protected static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }


    public static class ClientRequestsListener implements Runnable {

        public ClientRequestsListener() {
        }

        @Override
        public void run() {
            try {
                Server.getServerInstance().attachTask(new ClientRequestsListener());
                Socket client = Server.getServerSocketInstance().accept();
                String message = getStringFromInputStream(client.getInputStream());

                Server.spawnHandler(message);
            } catch (IOException e) {
                Logger.logError("Server Socket", "Socket" + Server.getCurrentPort());
                e.printStackTrace();
            }
        }
    }
}
