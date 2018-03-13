import utils.Logger;

public class Application {
    //TODO: Transform logger into Singleton

    public static void main(String[] args) {
        Logger.initialize();

        System.console().readLine();

//        Attaching a small portion of code to the quitting of the application for it to log the time it got terminated
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Logger.logInfo("Exit", "Application terminated");
        }));

        Server.start();
    }
}
