package frc.team4159.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.team4159.robot.commands.drive.TankDrive;
import frc.team4159.robot.subsystems.Drivetrain;
import frc.team4159.robot.subsystems.TestPneumatics;
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
	public static TestPneumatics pneumatics;
	public static OI oi;

	private MatchData.OwnedSide switchNear;
	private MatchData.OwnedSide scale;
	private MatchData.OwnedSide switchFar;

	private Command m_autonomousCommand;
	private SendableChooser<Command> m_chooser = new SendableChooser<>();

	/* This function is run when the robot is first started up */
	@Override
	public void robotInit() {

		drivetrain = Drivetrain.getInstance();
		superstructure = Superstructure.getInstance();
		oi = OI.getInstance();

		m_chooser.addDefault("Default Auto", new TankDrive()); // TODO: Change. Just a placeholder
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
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

		switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
		scale = MatchData.getOwnedSide(MatchData.GameFeature.SCALE);
		switchFar = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_FAR);

		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
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
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
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
