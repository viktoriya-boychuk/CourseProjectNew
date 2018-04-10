package utils;

import dao.BaseDAO;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServerConnection {
    public static final String DEFAULT_IP = "127.0.0.1";
    public static final Integer DEFAULT_PORT = 28365;
    private TaskHandler mTaskHandler;
    public Socket mSocket;

    public ServerConnection(InetAddress serverAddress, int serverPort) throws Exception {
        this.mSocket = new Socket(serverAddress, serverPort);

        mTaskHandler = new TaskHandler("Client-Connection-Manager");

        mTaskHandler.startInBackgroundThread();
    }

    public Socket getSocket() {
        return mSocket;
    }

//    public void start() throws IOException {
//        BufferedReader response = new BufferedReader(new InputStreamReader(this.mSocket.getInputStream()));
//        PrintWriter requestWriter = new PrintWriter(new OutputStreamWriter(this.mSocket.getOutputStream()), true);
//        Request request = new Request(Program.class);
//        request.setRequestType(Request.RequestType.GET);
//
//        requestWriter.println(request.toString() + "\n");
//
//        String message = response.readLine();
//        Logger.logInfo("Response is here!", "OMFG! It's :" + message);
//        Request actualResponse = new Request(message);
//        Logger.logInfo("Hey!", "Parsing's done! The result is " + actualResponse.getDataString());
//
////        Logger.logInfo("Response from server:", message);
//    }

    public void requestData(Class<? extends BaseDAO> type, Receiver caller) {
        Protocol request = new Protocol(type);

        mTaskHandler.addToTaskPool(new RequestSender(request, caller));
    }

    public class RequestSender implements Runnable {
        Protocol mRequest;
        Receiver mCaller;

        public RequestSender(Protocol request, Receiver caller) {
            this.mRequest = request;
            this.mCaller = caller;
        }

        @Override
        public void run() {
            Protocol request = null;
            try (
                    BufferedReader response = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    PrintWriter requestWriter = new PrintWriter(new OutputStreamWriter(mSocket.getOutputStream()), true)
            ) {
                requestWriter.println(mRequest.toString());

                String message = response.readLine();

                request = new Protocol(message);
                mCaller.onReceive(request);
                mSocket.close();
            } catch (IOException e) {
                Logger.logError("Exception", e.toString());
            }
        }
    }
}
