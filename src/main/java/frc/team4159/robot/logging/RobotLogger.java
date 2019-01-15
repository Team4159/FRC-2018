package frc.team4159.robot.logging;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class RobotLogger {
    static public void setup(String eventName, String alliance, String matchType, int matchNumber) throws IOException {
        LogManager.getLogManager().reset();

        eventName = eventName.isEmpty() ? "Practice" : eventName;

        Logger logger = Logger.getLogger("team4159");

        String[] directories = new File("/media").list(
            (File dir, String name) -> new File(dir, name).isDirectory()
        );

        if (directories != null) {
            for (String directory : directories) {
                String[] files = new File("/media/" + directory).list(
                        (File dir, String name) -> new File(dir, name).isDirectory()
                );

                if (files != null) {
                    if (Arrays.asList(files).contains("RobotLogViewer")) {
                        FileHandler logFile = new FileHandler("/media/" + directory + "/" + System.currentTimeMillis() + "_" + eventName.replaceAll( "[^a-zA-Z0-9\\.\\-]", "_") + "_" + matchNumber + ".csv");
                        logFile.setFormatter(new LogFormatter());
                        logger.addHandler(logFile);

                        logger.setLevel(Level.FINEST);

                        logger.config( eventName + "," + alliance + "," + matchType + "," + matchNumber);
                        logger.config("level,timestamp,period,voltage");
                    }
                }
            }
        }
    }
}
