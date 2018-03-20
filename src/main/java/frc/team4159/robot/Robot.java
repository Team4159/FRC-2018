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

    /* Declare subsystems */
	public static Drivetrain drivetrain;
	private static Superstructure superstructure;

	public static OI oi;

	/* Auto choosers */
	private Command autoCommand;
	private SendableChooser<Command> autoChooser;
    private final double defaultAutoDelay = 0.0;
    private final String defaultStartingPosition = "LEFT";
    private final String defaultLeftAction = "BASE";
    private final String defaultRightAction = "BASE";

    private double autoDelay = defaultAutoDelay;
    private String startingPosition = defaultStartingPosition;
    private String leftAction = defaultLeftAction;
    private String rightAction = defaultRightAction;

    private NetworkTableEntry ledModeEntry;

    /* This function is called when the robot is first started up */
	@Override
	public void robotInit() {

	    /* Initialize subsystems */
		drivetrain = Drivetrain.getInstance();
		superstructure = Superstructure.getInstance();

		/* Initialize operator control bindings */
		oi = OI.getInstance();

		/* Auto command */
		autoChooser = new SendableChooser<Command>();
        autoChooser.addDefault("Auto!", new Auto());

        /* Auto options */
		SmartDashboard.putString("Starting Position", defaultStartingPosition);
        SmartDashboard.putString("Left Action", defaultLeftAction);
        SmartDashboard.putString("Right Action", defaultRightAction);
        SmartDashboard.putNumber("Auto Delay", defaultAutoDelay);

        /* Stream webcamera on default port */
        CameraServer.getInstance().startAutomaticCapture();

        /* Start networktables for rPi to read */
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

        /* Starts auto command */
        autoCommand = autoChooser.getSelected();
        if (autoCommand != null) {
            autoCommand.start();
        }

        /* Put alliance color to NetworkTables to be used by rPi to control LED strips */
        if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
			ledModeEntry.setString("RED");
		} else {
			ledModeEntry.setString("BLUE");
		}

	}

    /* Periodically called during autonomous */
	@Override
	public void autonomousPeriodic() {

        startingPosition = SmartDashboard.getString("Starting Position", defaultStartingPosition);
        autoDelay = SmartDashboard.getNumber("Starting Position", defaultAutoDelay);
        leftAction = SmartDashboard.getString("Left Action", defaultLeftAction);
        rightAction = SmartDashboard.getString("Right Action", defaultRightAction);

        Scheduler.getInstance().run();
	}

	/* Runs once at the start of teleop */
	@Override
	public void teleopInit() {

        /* Makes sure autonomous action stops running when teleop starts running */
		if (autoCommand != null) {
            autoCommand.cancel();
		}

	}

	//private boolean blinkMode = false;

    /* Periodically called during operator control. */
	@Override
	public void teleopPeriodic() {

//		if(DriverStation.getInstance().getMatchTime() <= 30 && !blinkMode) {
//			ledModeEntry.setString("END GAME");
//			Command blinkCommand = new BlinkLED();
//			blinkCommand.start();
//			blinkMode = true;
//		}

		Scheduler.getInstance().run();
	}

	/* This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {
	}

	public String getStartingPosition() {
	    return startingPosition;
    }

    public String getLeftAction() {
	    return leftAction;
    }

    public String getRightAction() {
	    return rightAction;
    }

    public double getAutoDelay() {
		return autoDelay;
	}

	public static Drivetrain getDrivetrain() {
		return drivetrain;
	}

}
