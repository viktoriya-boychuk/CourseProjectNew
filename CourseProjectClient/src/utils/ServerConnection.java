package utils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServerConnection {
    private Socket socket;

    public ServerConnection(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
    }

    public Socket getSocket() {
        return socket;
    }

    public void start() throws IOException {
        BufferedReader response = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        PrintWriter requestWriter = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
//        Request request = new Request();


        String message = response.readLine();

        Logger.logInfo("Response from server:", message);
    }
}
