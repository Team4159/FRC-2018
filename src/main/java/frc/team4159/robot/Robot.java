package frc.team4159.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.team4159.robot.commands.auto.DriveStraight;
import frc.team4159.robot.commands.drive.TankDrive;
import frc.team4159.robot.subsystems.Drivetrain;
import frc.team4159.robot.subsystems.Superstructure;
import openrio.powerup.MatchData;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation.
 */

public class Robot extends TimedRobot {

	public static Drivetrain drivetrain;
	public static Superstructure superstructure;
	public static OI oi;

	private MatchData.OwnedSide switchNear;
	private MatchData.OwnedSide scale;
	private MatchData.OwnedSide switchFar;

	private Command autoCommand;
    //private SendableChooser<Command> configurationChooser = new SendableChooser<>();
    private SendableChooser<Command> actionChooser = new SendableChooser<>();

    /* This function is run when the robot is first started up */
	@Override
	public void robotInit() {

        System.out.println("robot init");


        // TODO: Add option for auto delay using Preferences class. See https://wpilib.screenstepslive.com/s/currentCS/m/smartdashboard/l/255423-setting-robot-preferences-from-smartdashboard

		drivetrain = Drivetrain.getInstance();
		superstructure = Superstructure.getInstance();
		oi = OI.getInstance();

		actionChooser.addDefault("Drive Straight", new DriveStraight(3)); // in meters
//		configurationChooser.addObject("Left", new MyAutoCommand());
//		configurationChooser.addObject("Middle", new MyAutoCommand());
//		configurationChooser.addObject("Right", new MyAutoCommand());
//		SmartDashboard.putData("!!! CHOOSE STARTING CONFIGURATION !!!", configurationChooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {

		System.out.println("auto init");

		switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
		scale = MatchData.getOwnedSide(MatchData.GameFeature.SCALE);
		switchFar = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_FAR);

		autoCommand = actionChooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autoCommand != null) {
			System.out.println("auto started");
			autoCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		/* Makes sure autonomous stops running when telop starts running */
		if (autoCommand != null) {
            autoCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
