import connection.SQLHelper;
import utils.Logger;

public class Debug {

    public static void main(String[] args) {
        Logger.initialize();
        Logger.info("Initialization", "Application started and log has been created in \"logs\" folder");

        SQLHelper sqlHelper;
        try {
            sqlHelper = SQLHelper.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
