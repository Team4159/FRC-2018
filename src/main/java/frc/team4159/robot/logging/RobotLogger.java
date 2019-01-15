package frc.team4159.robot.logging;

import java.io.IOException;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class RobotLogger {
    static public void setup(String eventName, String alliance, String matchType, int matchNumber, String period) throws IOException {
        LogManager.getLogManager().reset();

        eventName = eventName.isEmpty() ? "Practice" : eventName;

        Logger logger = Logger.getLogger("team4159");

        FileHandler logFile = new FileHandler("/media/sda1/" + LogFormatter.calcDate(System.currentTimeMillis()) + "_" + period + "_" + eventName.replaceAll( "[^a-zA-Z0-9\\.\\-]", "_") + "_" + matchNumber + ".csv");
        logFile.setFormatter(new LogFormatter());
        logger.addHandler(logFile);

        logger.setLevel(Level.FINEST);

        logger.config( eventName + "," + alliance + "," + matchType + "," + matchNumber);
        logger.config("level,timestamp,period,voltage");
    }
}
