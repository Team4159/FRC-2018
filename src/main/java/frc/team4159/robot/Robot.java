package frc.team4159.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import frc.team4159.robot.Logger.logging.RobotLogger;
import java.io.IOException;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.RobotController;
import frc.team4159.robot.subsystems.Drivetrain;

public class Robot extends TimedRobot {
    private Logger batteryLogger;
    private Drivetrain drivetrain;
    private OI oi;

    @Override
    public void robotInit() {
        try {
            RobotLogger.setup();
            batteryLogger = Logger.getLogger("team4159.battery");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problems with creating the log files");
        }

        drivetrain = Drivetrain.getInstance();
        oi = OI.getInstance();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        if (batteryLogger != null) {
            if (0.02 * Math.round(Timer.getFPGATimestamp() / 0.02) % 5 == 0) {
                batteryLogger.info(Double.toString(RobotController.getBatteryVoltage()));
            }
        }
        
        Scheduler.getInstance().run();
    }
}