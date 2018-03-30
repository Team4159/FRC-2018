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
import frc.team4159.robot.commands.auto.Auto;
import frc.team4159.robot.commands.led.BlinkLED;
import frc.team4159.robot.util.AutoSelector;
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
        if (instance == null)
            instance = new Robot();
        return instance;
    }

    public static Drivetrain drivetrain;
    public static Superstructure superstructure;
    public static OI oi;
    private static AutoSelector autoSelector;

    /* Auto choosers */
    private Command autoCommand;
    private SendableChooser<Command> autoChooser;

    /* LED stuff */
    private Command blinkLEDCommand;
    private SendableChooser<Command> endGameChooser;
    private NetworkTableEntry ledModeEntry;

    /**
     * Called when the robot is first powered on
     */
    @Override
    public void robotInit() {

        /*
         *  Initialize subsystems
         */
        drivetrain = Drivetrain.getInstance();
        superstructure = Superstructure.getInstance();

        /*
         *  Initialize helper classes
         */
        oi = OI.getInstance();
        autoSelector = AutoSelector.getInstance();

        /*
         * Put auto command into SmartDashboard
         */
        autoChooser = new SendableChooser<>();
        autoChooser.addDefault("Auto!", new Auto());

        /*
         * Stream webcamera on default port
         */
        CameraServer.getInstance().startAutomaticCapture();

        /*
         * Put end game action (blinking LEDs) into SmartDashboard
         */
        endGameChooser = new SendableChooser<>();
        endGameChooser.addDefault("Blink LED Ring", new BlinkLED());

        /*
         * Start networktables for rPi to read
         */
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("datatable");
        ledModeEntry = table.getEntry("LED Mode");

        // Used for auto testing in teleop
        SmartDashboard.putNumber("MAX_VELOCITY", 4.05);
        SmartDashboard.putNumber("kP_TURN", 0.05);

    }

    /**
     * Called once every time robot enters disabled mode
     */
    @Override
    public void disabledInit() {

        ledModeEntry.setString("DISABLED");

        /*
         * Stop blinking LED command
         */
        if (blinkLEDCommand != null) {
            blinkLEDCommand.cancel();
        }
    }

    /* Called periodically when robot is disabled */
    @Override
    public void disabledPeriodic() {

        if(oi.getAutoSelectionButton()) {
            autoSelector.nextSelection();
            printAutoOptions();
        }

        if(oi.getAutoOptionButton()) {
            autoSelector.nextOption();
            printAutoOptions();
        }

        Scheduler.getInstance().run();
    }

    /**
     * Runs once at the start of autonomous
     */
    @Override
    public void autonomousInit() {

        /* Starts auto command */
        autoCommand = autoChooser.getSelected();
        if (autoCommand != null) {
            autoCommand.start();
        }

        /* Put alliance color to NetworkTables to be used by rPi to control LED strips */
        if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
            ledModeEntry.setString("RED");
        } else {
            ledModeEntry.setString("BLUE");
        }

    }

    /**
     * Periodically called during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * Runs once at the start of teleop
     */
    @Override
    public void teleopInit() {

        /*
         * Stops autonomous action from running when teleop starts
         */
        if (autoCommand != null) {
            autoCommand.cancel();
        }

        /*
         * Start blinking LED command
         */
        blinkLEDCommand = endGameChooser.getSelected();
        if(blinkLEDCommand != null) {
            blinkLEDCommand.start();
        }

    }

    /**
     * Periodically called during teleoperatated control.
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * Periodically called during test mode.
     */
    @Override
    public void testPeriodic() {
    }

    /**
     * @return Drivetrain subsystem
     */
    public static Drivetrain getDrivetrain() {
        return drivetrain;
    }

    /**
     * Print auto options, along with a bunch of new lines
     */
    private void printAutoOptions() {
        System.out.println("SELECTION: " + autoSelector.getSelection());
        System.out.println("POSITION: " + autoSelector.getPosition());
        System.out.println("LEFT ACTION: " + autoSelector.getLeftAction());
        System.out.println("RIGHT ACTION: " + autoSelector.getRightAction());
        for(int i = 0; i < 20; i++) {
            System.out.println("\n");
        }
    }

}
