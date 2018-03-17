package frc.team4159.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.commands.auto.*;
import frc.team4159.robot.commands.led.BlinkLED;
import frc.team4159.robot.commands.led.TurnOffLED;
import frc.team4159.robot.commands.led.TurnOnLED;
import frc.team4159.robot.subsystems.Drivetrain;
import frc.team4159.robot.subsystems.Superstructure;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation.
 */

public class Robot extends TimedRobot {

    private static Robot instance;

    public static Robot getInstance() {
        if(instance == null)
            instance = new Robot();
        return instance;
    }

    /* All the hardware */
	public static Drivetrain drivetrain;
	private static Superstructure superstructure;
	public static OI oi;

	/* Auto choosers */
	private Command actionCommand;
    private SendableChooser<Command> actionChooser = new SendableChooser<>();
    private final double defaultAutoDelay = 0.0;
    private final String defaultStartingPosition = "LEFT";

    private NetworkTableEntry ledModeEntry;

    /* This function is called when the robot is first started up */
	@Override
	public void robotInit() {

		drivetrain = Drivetrain.getInstance();
		superstructure = Superstructure.getInstance();
		oi = OI.getInstance();

		/* Adds options to Shuffleboard/Smartdashboard to choose from */
        actionChooser.addDefault("One Cube (Default)", new Auto(AutoAction.ONE_CUBE));
		actionChooser.addObject("Drive Straight",      new Auto(AutoAction.BASELINE));
		actionChooser.addObject("Two Cube",            new Auto(AutoAction.TWO_CUBE));
		SmartDashboard.putData("CHOOSE AUTO ACTION!", actionChooser);

		SmartDashboard.putNumber("Auto Delay", defaultAutoDelay);
		SmartDashboard.putString("Starting Position", defaultStartingPosition);

		CameraServer.getInstance().startAutomaticCapture();

		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		NetworkTable table = inst.getTable("datatable");
		ledModeEntry = table.getEntry("LED Mode");

	}

    /**
     * Called once each time robot enters Disabled mode. Use to reset subsystem info you want cleared when robot is disabled.
     */
	@Override
	public void disabledInit() {
		ledModeEntry.setString("DISABLED");
	}

	/* Called periodically when robot is disabled */
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/* Runs once at the start of autonomous */
	@Override
	public void autonomousInit() {

        actionCommand = actionChooser.getSelected();

        /* Then, starts the command to run an action based on starting configuration and match data */
        if (actionCommand != null) {
            actionCommand.start();
        }

        if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
			ledModeEntry.setString("RED");

		} else {
			ledModeEntry.setString("BLUE");
		}

	}

    /* Periodically called during autonomous */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	/* Runs once at the start of teleop */
	@Override
	public void teleopInit() {

		/* Makes sure autonomous action stops running when teleop starts running */
		if (actionCommand != null) {
            actionCommand.cancel();
		}

	}

	private boolean blinkMode = false;

    /* Periodically called during operator control. */
	@Override
	public void teleopPeriodic() {

		if(DriverStation.getInstance().getMatchTime() <= 30 && !blinkMode) {
			ledModeEntry.setString("END GAME");
			Command blinkCommand = new BlinkLED();
			blinkCommand.start();
			blinkMode = true;
		}

		Scheduler.getInstance().run();
	}

	/* This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {
	}

	public String getStartingPosition() {
	    return SmartDashboard.getString("Starting Position", defaultStartingPosition);
    }

    public double getAutoDelay() {
		return SmartDashboard.getNumber("Auto Delay", defaultAutoDelay);
	}

	public static Drivetrain getDrivetrain() {
		return drivetrain;
	}

}
