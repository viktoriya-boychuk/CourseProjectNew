import utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CustomServerSocket extends ServerSocket {


    public CustomServerSocket(int port) throws IOException {
        super(port);
    }

    @Override
    public Socket accept() throws IOException {
        Logger.logInfo("CustomServerSocket", "Listening for connections at " + getLocalSocketAddress().toString());
        return super.accept();
    }
}