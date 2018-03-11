package utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//TODO: Transform logger into Singleton
public class Logger {
    private static Logger logger;
    private static Path logPath;

    private Logger() {
        try {
            logPath = Paths.get("logs/server-log-" + getCurrentDate() + ".txt");
            if (!Files.exists(Paths.get("logs")))
                Files.createDirectory(Paths.get("logs"));

            Files.createFile(logPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger initialize() {
        if (logger == null) {
            logger = new Logger();
            return logger;
        } else return logger;
    }

    public static void write(String header, String message, String type) {
        String line = "[" + type + "] [" + getCurrentDate() + "] " + header + ": " + message + "\n";

        System.out.println(line);

        ArrayList<String> lines = new ArrayList<>();
        lines.add(line);
        try {
            Files.write(logPath, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void error(String header, String message) {
        write(header, message, "ERROR");
    }

    public static void info(String header, String message) {
        write(header, message, "INFO");
    }

    private static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD HH-mm-ss");
        Date date = new Date();

        return dateFormat.format(date);
    }
}
