package utils;

import java.io.BufferedReader;
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
    private static TaskHandler mTaskHandler;

    private Logger() {
        try {
            logPath = Paths.get("logs/server-log-" + getCurrentDate() + ".txt");
            if (!Files.exists(Paths.get("logs")))
                Files.createDirectory(Paths.get("logs"));

            Files.createFile(logPath);
            mTaskHandler = new TaskHandler("Logger");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger initialize() {
        if (logger == null) {
            logger = new Logger();
            mTaskHandler.startInBackgroundThread();
            return logger;
        } else {

            return logger;
        }
    }

    private static void write(String header, String message, String type) throws IOException {
        String line = composeLine(header, message, type);

        System.out.println(line);

        ArrayList<String> lines = new ArrayList<>();
        lines.add(line);

        Files.write(logPath, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
    }

    private static void write(String header, BufferedReader bufferedReader, String type) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        Integer iterator = 0;
        String line;
        String lineToWrite;

        while ((line = bufferedReader.readLine()) != null) {
            if (iterator == 0) {
                lineToWrite = composeLine(header, line, type);
                iterator++;
            } else
                lineToWrite = composeHeadlessLine(line);
            lines.add(lineToWrite);
        }

        Files.write(logPath, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
    }

    public static void logError(String header, String message) {
        try {
            write(header, message, "ERROR");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logInfo(String header, String message) {
        try {
            write(header, message, "INFO");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logError(String header, BufferedReader bufferedReader) {
        try {
            write(header, bufferedReader, "INFO");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logInfo(String header, BufferedReader bufferedReader) {
        try {
            write(header, bufferedReader, "INFO");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD HH-mm-ss");
        Date date = new Date();

        return dateFormat.format(date);
    }

    private static String composeLine(String header, String message, String type) {
        return "[" + type + "]\t[" + getCurrentDate() + "]\t" + header + ":\t" + message + "\n";
    }

    private static String composeHeadlessLine(String message) {
        return "\t\t\t" + message + "\n";
    }
}
