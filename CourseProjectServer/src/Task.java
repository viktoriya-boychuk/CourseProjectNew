import dao.BaseDAO;
import utils.Protocol;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Future;

public class Task {
    private Future<ArrayList<BaseDAO>> mFuture;
    private Socket mSocket;
    private Protocol mRequest;

    public Task(Future<ArrayList<BaseDAO>> future, Socket socket, Protocol request) {
        this.mFuture = future;
        this.mSocket = socket;
        this.mRequest = request;
    }

    public Future<ArrayList<BaseDAO>> getFuture() {
        return mFuture;
    }

    public void setFuture(Future<ArrayList<BaseDAO>> mFuture) {
        this.mFuture = mFuture;
    }

    public Socket getSocket() {
        return mSocket;
    }

    public void setSocket(Socket mSocket) {
        this.mSocket = mSocket;
    }

    public Protocol getRequest() {
        return mRequest;
    }

    public void setRequest(Protocol mRequest) {
        this.mRequest = mRequest;
    }
}
