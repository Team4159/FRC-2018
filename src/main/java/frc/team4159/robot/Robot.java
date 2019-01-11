package frc.team4159.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team4159.robot.subsystems.Drivetrain;

import frc.team4159.robot.Logger.logging.RobotLogger;
import java.io.IOException;
import java.util.logging.Logger;

public class Robot extends TimedRobot {

    private Drivetrain drivetrain;

    private OI oi;

    private static Logger LOGGER;

    /**
     * Called when the robot is first powered on
     */
    @Override
    public void robotInit() {
        drivetrain = Drivetrain.getInstance();
        oi = OI.getInstance();

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
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }
    // loops during antonomous

    @Override
    public void teleopInit() {

    }
    // when u control

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    // when it repeats the controls

    public Drivetrain getDrivetrain() {
        return drivetrain;
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}