package frc.team4159.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.commands.auto.Auto;
import frc.team4159.robot.commands.drive.RecordDrivetrain;
import frc.team4159.robot.commands.led.BlinkLED;
import frc.team4159.robot.subsystems.Drivetrain;
import frc.team4159.robot.subsystems.Superstructure;
import frc.team4159.robot.util.AutoSelector;

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

    private Command recordCommand = new RecordDrivetrain();

    /* Commands */
    private Command autoCommand;
    private Command endGameCommand;

    /* Auto choosers */
//    private SendableChooser<AutoAction> leftAutoAction;
//    private SendableChooser<AutoAction> rightAutoAction;

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
         * Put auto action choosers to SmartDashboard
         */
        /*
        leftAutoAction = new SendableChooser<>();
        leftAutoAction.addDefault("Baseline", BASELINE);
        leftAutoAction.addObject("Baseline ONE", BASELINE_DROP);
        leftAutoAction.addObject("Mid to Left ONE", MID_TO_LEFT_ONE);
        leftAutoAction.addObject("Mid to Right ONE", MID_TO_RIGHT_ONE);
        SmartDashboard.putData("Left Auto Chooser", leftAutoAction);
        SmartDashboard.putString("Left Action: ", leftAutoAction.getName());

        rightAutoAction = new SendableChooser<>();
        rightAutoAction.addDefault("Baseline", BASELINE);
        rightAutoAction.addObject("Baseline ONE", BASELINE_DROP);
        rightAutoAction.addObject("Mid to Left ONE", MID_TO_LEFT_ONE);
        rightAutoAction.addObject("Mid to Right ONE", MID_TO_RIGHT_ONE);
        SmartDashboard.putData("Right Auto Chooser", leftAutoAction);
        SmartDashboard.putString("Right Action: ", rightAutoAction.getName());
        */

        /*
         * Start networktables for rPi to read
         */
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("datatable");
        ledModeEntry = table.getEntry("LED Mode");

        /*
         * Variables that can change in SmartDashboard to tune auto during teleop
         */
        SmartDashboard.putNumber("MAX_VELOCITY", 4.05);
        SmartDashboard.putNumber("kP_TURN", 0.05);

        /*
         * Automatically stream driver camera and keep it open
         * View stream on http://roborio-4159-frc.local:1181
         */
        UsbCamera driverCam = CameraServer.getInstance().startAutomaticCapture();
        CvSink cvSink = new CvSink("driverCam");
        cvSink.setSource(driverCam);
        cvSink.setEnabled(true);

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
        if (endGameCommand != null) {
            endGameCommand.cancel();
        }
    }

    /* Called periodically when robot is disabled */
    @Override
    public void disabledPeriodic() {


        if(oi.getLeftSelectionButton()) {
            autoSelector.nextLeftSelection();
            printAutoOptions();
        }

        if(oi.getRightSelectionButton()) {
            autoSelector.nextRightSelection();
            printAutoOptions();
        }


        Scheduler.getInstance().run();
    }

    /**
     * Runs once at the start of autonomous
     */
    @Override
    public void autonomousInit() {

        // Disable compressor to prevent vibration error
        Superstructure.getInstance().disableCompressor();

        /* Start auto command */
        autoCommand = new Auto();
        autoCommand.start();

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
         * Start end game command (waits until endgame first)
         */
        endGameCommand = new BlinkLED();
        endGameCommand.start();

        // Enable compressor
        Superstructure.getInstance().enableCompressor();

    }

    /**
     * Periodically called during teleoperatated control.
     */
    @Override
    public void teleopPeriodic() {

        if(oi.getTestTriggerPressed()) {
            recordCommand.start();
        }

        if(oi.getTestTriggerReleased()) {
            recordCommand.cancel();
        }

        Scheduler.getInstance().run();
    }

    /**
     * Periodically called during test mode.
     */
    @Override
    public void testPeriodic() {}

    /**
     * @return Drivetrain subsystem
     */
    public static Drivetrain getDrivetrain() {
        return drivetrain;
    }

    /**
     * @return Auto action if left side is near switch
     */
    public String getLeftAction() {
        return autoSelector.getLeftAuto();
    }

    /**
     * @return Auto action if right side is near switch
     */
    public String getRightAction() {
        return autoSelector.getRightAuto();
    }

    /*
     * Print auto options, along with a bunch of new lines
     * Currently unused.
     */
    private void printAutoOptions() {

        System.out.println("LEFT ACTION: " + autoSelector.getLeftAuto());
        System.out.println("RIGHT ACTION: " + autoSelector.getRightAuto());
        for(int i = 0; i < 20; i++) {
            System.out.println("\n");
        }

    }

}
