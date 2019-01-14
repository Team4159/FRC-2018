package frc.team4159.robot.logging;

import java.io.IOException;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class RobotLogger {
    static public void setup(String eventName, String alliance, String matchType) throws IOException {
        LogManager.getLogManager().reset();

        Logger logger = Logger.getLogger("team4159");

        FileHandler logFile = new FileHandler("team4159_logs_" + LogFormatter.calcDate(System.currentTimeMillis()) + ".csv");
        logFile.setFormatter(new LogFormatter());
        logger.addHandler(logFile);

        logger.setLevel(Level.FINEST);

        logger.config(eventName + "," + alliance + "," + matchType);
        logger.config("level,timestamp,period,voltage");
    }
}
