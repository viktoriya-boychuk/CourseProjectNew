package utils;

import java.io.IOException;
import java.io.PrintWriter;
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
        String input = "SIMPLE STRING TO TEST SERVER\n NEXT LINE \n ONE MORE LINE HERE";

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println(input);
        out.flush();
        out.close();
    }
}
