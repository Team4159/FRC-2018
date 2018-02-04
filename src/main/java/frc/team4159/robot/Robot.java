package frc.team4159.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.commands.auto.SetPosition;
import frc.team4159.robot.commands.auto.TestMotionProfile;
import frc.team4159.robot.subsystems.Drivetrain;
import frc.team4159.robot.subsystems.Superstructure;
import openrio.powerup.MatchData;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation.
 */

public class Robot extends TimedRobot {

    /* All the hardware */
	public static Drivetrain drivetrain;
	public static Superstructure superstructure;
	public static OI oi;

	/* Auto match data */
	private MatchData.OwnedSide switchNear;
	private static StartingConfiguration autoPosition;

	/* Auto command choosers */
	private Command actionCommand;
    private Command setPositionCommand;
    private SendableChooser<Command> positionChooser = new SendableChooser<>();
    private SendableChooser<Command> actionChooser = new SendableChooser<>();

    /* This function is run when the robot is first started up */
	@Override
	public void robotInit() {

        // TODO: Add option for auto delay using Preferences class.
		// See https://wpilib.screenstepslive.com/s/currentCS/m/smartdashboard/l/255423-setting-robot-preferences-from-smartdashboard

		drivetrain = Drivetrain.getInstance();
		superstructure = Superstructure.getInstance();
		oi = OI.getInstance();

		/* Adds options to Shuffleboard/Smartdashboard to choose from */

		actionChooser.addDefault("Drive Straight (Default)", new TestMotionProfile());
		SmartDashboard.putData("!!! CHOOSE AUTO COMMAND !!!", actionChooser);

		positionChooser.addDefault("Left", new SetPosition(StartingConfiguration.LEFT));
		positionChooser.addObject("Middle", new SetPosition(StartingConfiguration.MIDDLE));
		positionChooser.addObject("Right", new SetPosition(StartingConfiguration.RIGHT));
		SmartDashboard.putData("!!! CHOOSE STARTING CONFIGURATION !!!", positionChooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	    // TODO: Reset subsystem info e.g. sensors (maybe)
	}

	/* Called periodically when robot is disabled */
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/* Runs once at the start of autonomous */
	@Override
	public void autonomousInit() {

	    /* Gets match data of the closest switch from FMS */
		switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);

		/* Gets user determined auto settings */
		setPositionCommand = positionChooser.getSelected();
		actionCommand = actionChooser.getSelected();

		/* First, starts the command to get user determined starting configuration */
        if (setPositionCommand != null) {
            setPositionCommand.start();
        }

        /* Then, starts the command to run an action based on starting configuration and match data */
        if (actionCommand != null) {
            actionCommand.start();
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

    /* Periodically called during operator control. */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/* This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {
	}

	public static void setAutoPosition(StartingConfiguration position) {
        autoPosition = position;
    }

    public StartingConfiguration getAutoPosition() {
	    return autoPosition;
    }

    public MatchData.OwnedSide getSwitchNear() {
        return switchNear;
    }

	public enum StartingConfiguration {
        LEFT, MIDDLE, RIGHT
    }

}
