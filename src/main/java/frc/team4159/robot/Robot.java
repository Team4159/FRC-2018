package frc.team4159.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import frc.team4159.robot.logging.RobotLogger;
import java.io.IOException;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.Timer;
import frc.team4159.robot.subsystems.Drivetrain;

public class Robot extends TimedRobot {
    private Logger logger;

    private DriverStation driverStation;

    private Drivetrain drivetrain;
    private OI oi;

    @Override
    public void robotInit() {
        drivetrain = Drivetrain.getInstance();
        oi = OI.getInstance();

        driverStation = DriverStation.getInstance();

        initLogger();
    }

    @Override
    public void robotPeriodic() {
        logCycle();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    private void initLogger() {
        if (logger == null) {
            try {
                RobotLogger.setup(
                        driverStation.getEventName(),
                        driverStation.getAlliance().toString(),
                        driverStation.getMatchType().toString(),
                        driverStation.getMatchNumber()
                );
                logger = Logger.getLogger("team4159");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Problems with creating the log files, check if the USB is plugged in.");
            }
        }
    }

    private void logCycle() {
        if (logger != null && !isDisabled() && 0.02 * Math.round(Timer.getFPGATimestamp() / 0.02) % 5 == 0) {
            logger.info((isAutonomous() ? "Autonomous": "Teleoperated") + "," + RobotController.getBatteryVoltage());
        }
    }
}