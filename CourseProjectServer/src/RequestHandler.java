import connection.SQLHelper;
import dao.BaseDAO;
import utils.Logger;
import utils.Protocol;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class RequestHandler implements Callable<ArrayList<BaseDAO>> {
    private Protocol mRequest;
    private Socket mSocket;
    private Future<ArrayList<BaseDAO>> mFuture;

    public RequestHandler(Protocol request, Socket socket) {
        this.mRequest = request;
        this.mSocket = socket;
    }

    public void setFuture(Future<ArrayList<BaseDAO>> mFuture) {
        this.mFuture = mFuture;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<BaseDAO> call() {
        switch (mRequest.getRequestType()) {
            case GET:
                Task task = new Task(mFuture, mSocket, mRequest);
                Server.addToWaitingList(task);

                try {
                    return SQLHelper.getInstance().getArrayListFor((Class<? extends BaseDAO>) Class.forName(mRequest.getDataType()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case POST:
                Logger.logInfo("POST", "This is post request");
                break;
            case DELETE:
                Logger.logInfo("DELETE", "This is delete request");
                break;
            case UPDATE:
                Logger.logInfo("UPDATE", "This is update request");
                break;
            default:
                Logger.logInfo("NO-TYPE", "This request got no type");
                break;
        }
        return new ArrayList<>();
    }
}