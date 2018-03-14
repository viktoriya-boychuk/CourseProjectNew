import dao.Program;
import org.json.JSONObject;
import utils.Logger;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Debug {
    public static ArrayList<Runnable> mTaskPool;
    public static ExecutorService mExecutorService;

    public static void main(String[] args) throws InterruptedException {
        mTaskPool = new ArrayList<>();
        mExecutorService = Executors.newCachedThreadPool();
        Logger.initialize();
        Logger.logInfo("Initialization", "Application started and log has been created in \"logs\" folder");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            Logger.logInfo("Exit", "Application terminated");
        }));

        Server.start();

        handleTasks();

//        SQLHelper sqlHelper = null;
//        Server.finish();
//        try {
//            sqlHelper = SQLHelper.getInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ArrayList<Program> programs = new ArrayList<>();
//
//        try {
//            ResultSet resultSet = sqlHelper.getDataFor(Program.class);
//
//            while (resultSet.next()) {
//                programs.add(Program.parse(resultSet));
//            }
//        } catch (SQLException | IllegalAccessException | InstantiationException e) {
//            e.printStackTrace();
//        }
//
//        for (Program program : programs) {
//            System.out.println(program.toString());
//        }
    }

    private static void handleTasks() throws InterruptedException {
        while (true) {
            ArrayList<Runnable> toRemove = new ArrayList<>();
            if (!mTaskPool.isEmpty()) {
                for (Runnable runnable : mTaskPool) {
                    addToTaskPool(runnable);
                    toRemove.add(runnable);
                    Thread.sleep(Server.DEFAULT_THREAD_DELAY);
                }
                mTaskPool.removeAll(toRemove);
            }
        }
    }

    public static void addToTaskPool(Runnable runnable) {
        mTaskPool.add(runnable);
    }
}
