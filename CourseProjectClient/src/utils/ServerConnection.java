package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        String message = response.readLine();

        Logger.logInfo("Response from server:", message);
    }
}
