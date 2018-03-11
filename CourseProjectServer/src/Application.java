import utils.Logger;

import java.io.IOException;

public class Application {
    //TODO: Transform logger into Singleton
    private static Logger logger;

    public static void main(String[] args) {
        logger = new Logger();
        try {
            Process p = Runtime.getRuntime()
                    .exec("cmd /c start cmd.exe /k \"java -jar CourseProjectServer.jar\"");

        } catch (IOException e) {
            e.printStackTrace();
        }

//        Attaching a small portion of code to the quitting of the application for it to log the time it got terminated
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Exit", "Application terminated");
        }));
    }

    public static Logger getLogger() {
        return logger;
    }
}
