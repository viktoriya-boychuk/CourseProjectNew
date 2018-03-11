import connection.SQLHelper;
import utils.Logger;

public class Debug {
    private static Logger logger;

    public static void main(String[] args) {
        logger = new Logger();

        SQLHelper sqlHelper = new SQLHelper();
    }

    public static Logger getLogger() {
        return logger;
    }
}
