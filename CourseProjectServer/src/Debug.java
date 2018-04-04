import connection.SQLHelper;
import dao.BaseDAO;
import dao.Program;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Logger;
import utils.Protocol;
import utils.TaskHandler;

import java.sql.SQLException;
import java.util.ArrayList;

public class Debug {
    private static TaskHandler mTaskHandler;

    public static void main(String[] args) {
        mTaskHandler = new TaskHandler("MainApplication");

        Logger.initialize();
        Logger.logInfo("Initialization", "Application started and log has been created in \"logs\" folder");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            mTaskHandler.stop();
            Server.finish();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.logInfo("Exit", "Application terminated");
        }));

        Server.start();
//        mTaskHandler.addToTaskPool(Debug::testSQLConnection);

        mTaskHandler.startInCurrentThread();
    }

    private static void testSQLConnection() {
        SQLHelper sqlHelper = null;
//        Server.finish();
        try {
            sqlHelper = SQLHelper.getInstance();

            ArrayList<BaseDAO> entries = sqlHelper.getArrayListFor(Program.class);

            try {
                JSONArray jsonArray = sqlHelper.getJSONArrayFor(Program.class);

                Protocol request1 = new Protocol(Protocol.RequestType.GET, entries);
                Protocol request2 = new Protocol(Protocol.RequestType.GET, entries);
                Protocol request3 = new Protocol(Protocol.RequestType.GET, entries);
                Protocol request4 = new Protocol(Protocol.RequestType.GET, entries);

                request1.setData(entries);
                request2.setData(new JSONObject().put(Program.class.getName(), jsonArray).toString());
                request3.setData(request1.getData());
                request4.setData(request2.getData());

                Logger.logInfo("Request1", request1.toString());
                Logger.logInfo("Request2", request2.toString());
                Logger.logInfo("Request3", request3.toString());
                Logger.logInfo("Request4", request4.toString());

                entries.clear();

                entries.addAll(request1.getData());

            } catch (SQLException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}