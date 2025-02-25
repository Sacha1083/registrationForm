package util;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    private static final String LOG_DIR = Paths.get("").toAbsolutePath().toString();
    private static final String LOG_FILE = Paths.get(LOG_DIR, "app.log").toString();
    private static Logger logger;

    static {
        try {
            logger = Logger.getLogger("AppLogger");
            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("Error initializing logger: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void info(String message) {
        logger.log(Level.INFO, "ℹ️ " + message + " ℹ️");
    }

    public static void success(String message) {
        logger.log(Level.INFO, "🎉 " + message + " 🎉");
    }

    public static void warn(String message) {
        logger.log(Level.WARNING, "⚠️ " + message + " ⚠️");
    }

    public static void error(String message) {
        logger.log(Level.SEVERE, "❗ " + message + " ❗");
    }

    public static void config(String message) {
        logger.log(Level.CONFIG, "⚙️ " + message + " ⚙️");
    }
}