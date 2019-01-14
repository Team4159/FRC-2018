package frc.team4159.robot.logging;

import edu.wpi.first.wpilibj.RobotBase;

import java.io.IOException;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RobotLogger {
    static public void setup(String eventName, String alliance, String matchType) throws IOException {
        LogFormatter formatter = new LogFormatter();

        Logger logger = Logger.getLogger("team4159");

        FileHandler logFile = new FileHandler("team4159_logs_" + LogFormatter.calcDate(System.currentTimeMillis()));

        logFile.setFormatter(formatter);
        logger.setUseParentHandlers(false);

        logger.addHandler(logFile);

        logger.setLevel(Level.FINEST);
        logger.config(eventName + "," + alliance + "," + matchType);
        logger.config("level,timestamp,voltage");
    }
}
