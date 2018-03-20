import connection.SQLHelper;
import dao.Program;
import org.json.JSONArray;
import utils.Logger;
import utils.TaskHandler;

import java.sql.ResultSet;
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
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.logInfo("Exit", "Application terminated");
        }));

        Server.start();
        mTaskHandler.addToTaskPool(Debug::testSQLConnection);

        mTaskHandler.startInCurrentThread();
    }

    private static void testSQLConnection() {
        SQLHelper sqlHelper = null;
        Server.finish();
        try {
            sqlHelper = SQLHelper.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Program> programs = new ArrayList<>();

        try {
            JSONArray jsonArray = sqlHelper.getJSONArrayFor(Program.class);

            Logger.logInfo("AAAAAAAAA", jsonArray.toString());
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        for (Program program : programs) {
            System.out.println(program.toString());
        }
    }
}