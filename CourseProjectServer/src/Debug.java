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
}