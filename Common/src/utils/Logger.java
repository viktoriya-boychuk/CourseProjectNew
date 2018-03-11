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

public class Logger {
    private Path logPath;

    public Logger() {
        try {
            logPath = Paths.get("logs/server-log-" + getCurrentDate() + ".txt");
            if (!Files.exists(Paths.get("logs")))
                Files.createDirectory(Paths.get("logs"));

            Files.createFile(logPath);
            this.info("Log created", "Log has been created with a name \"server-log-"
                    + getCurrentDate() + "\" in \"logs\" folder");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String header, String message, String type) {
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

    public void error(String header, String message) {
        write(header, message, "ERROR");
    }

    public void info(String header, String message) {
        write(header, message, "INFO");
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD HH-mm-ss");
        Date date = new Date();

        return dateFormat.format(date);
    }
}
