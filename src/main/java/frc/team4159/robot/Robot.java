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
    private enum Mode {
        None,
        Autonomous,
        Teleoperated
    }

    private Logger logger;

    private DriverStation driverStation;

    private Drivetrain drivetrain;
    private OI oi;

    private Mode mode = Mode.None;

    @Override
    public void robotInit() {
        drivetrain = Drivetrain.getInstance();
        oi = OI.getInstance();

        driverStation = DriverStation.getInstance();
    }

    @Override
    public void autonomousInit() {
        initLogger();
    }

    @Override
    public void autonomousPeriodic() {
        mode = Mode.Autonomous;
        logCycle();
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        initLogger();
    }

    @Override
    public void teleopPeriodic() {
        mode = Mode.Teleoperated;
        logCycle();
        Scheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        if (driverStation.isFMSAttached()) {
            if (mode == Mode.Teleoperated) {
                logger = null;
            }
        } else {
            logger = null;
        }
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
        if (logger != null) {
            if (0.02 * Math.round(Timer.getFPGATimestamp() / 0.02) % 5 == 0) {
                logger.info(mode.toString() + "," + Double.toString(RobotController.getBatteryVoltage()));
            }
        }
    }
}