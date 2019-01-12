package frc.team4159.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import frc.team4159.robot.Logger.logging.RobotLogger;
import java.io.IOException;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.RobotController;

public class Robot extends TimedRobot {
    private Logger LOGGER;

    @Override
    public void robotInit() {
        try {
            RobotLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }

        LOGGER = Logger.getLogger("team4159");
        LOGGER.info("Starting up!");
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        if (0.02 * Math.round(Timer.getFPGATimestamp() / 0.02) % 5 == 0) {
            LOGGER.info(Double.toString(RobotController.getBatteryVoltage()));
        }
        
        Scheduler.getInstance().run();
    }

    public Logger getLogger() { return LOGGER; }
}