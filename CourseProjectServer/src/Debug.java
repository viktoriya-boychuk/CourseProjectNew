import utils.Logger;

public class Debug {
    private static Logger logger;

    public static void main(String[] args) {
        logger = new Logger();

    }

    public static Logger getLogger() {
        return logger;
    }
}
