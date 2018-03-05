package frc.team4159.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.commands.auto.*;
import frc.team4159.robot.commands.drive.TestMotionProfile;
import frc.team4159.robot.subsystems.Drivetrain;
import frc.team4159.robot.subsystems.Superstructure;
import openrio.powerup.MatchData;

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

	/* Auto match data */
	private MatchData.OwnedSide switchNear;
	private StartingConfiguration autoPosition;

	/* Auto choosers */
	private Command actionCommand;
    private Command setPositionCommand;
    private SendableChooser<Command> positionChooser = new SendableChooser<>();
    private SendableChooser<Command> actionChooser = new SendableChooser<>();
    private final double defaultAutoDelay = 0.0;

    /* This function is called when the robot is first started up */
	@Override
	public void robotInit() {

		drivetrain = Drivetrain.getInstance();
		superstructure = Superstructure.getInstance();
		oi = OI.getInstance();

		/* Adds options to Shuffleboard/Smartdashboard to choose from */

		actionChooser.addDefault("Drive Straight (Default)", new TestMotionProfile());
		actionChooser.addObject("One Cube",                  new Auto(AutoAction.ONE_CUBE));
		actionChooser.addObject("Two Cube",                  new Auto(AutoAction.TWO_CUBE));
		actionChooser.addObject("One Cube / One Vault",      new Auto(AutoAction.ONE_CUBE_ONE_VAULT));
		actionChooser.addObject("Two Vault",                 new Auto(AutoAction.TWO_VAULT));
		SmartDashboard.putData("!!! CHOOSE AUTO COMMAND !!!", actionChooser);

		positionChooser.addDefault("Left (Default)",  new SetPosition(StartingConfiguration.LEFT));
		positionChooser.addObject("Middle",           new SetPosition(StartingConfiguration.MIDDLE));
		positionChooser.addObject("Right",            new SetPosition(StartingConfiguration.RIGHT));
		SmartDashboard.putData("!!! CHOOSE STARTING CONFIGURATION !!!", positionChooser);

		SmartDashboard.putNumber("Auto Delay", defaultAutoDelay);
	}

    /**
     * Called once each time robot enters Disabled mode. Use to reset subsystem info you want cleared when robot is disabled.
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


	public void setAutoPosition(StartingConfiguration position) {
        autoPosition = position;
    }

    public StartingConfiguration getStartingConfiguration() {
	    return autoPosition;
    }

    public double getAutoDelay() {
		return SmartDashboard.getNumber("Auto Delay", defaultAutoDelay);
	}

    public MatchData.OwnedSide getSwitchNear() {
        return switchNear;
    }

	public static Drivetrain getDrivetrain() {
		return drivetrain;
	}

}
