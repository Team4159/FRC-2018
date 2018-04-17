package frc.team4159.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
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
import frc.team4159.robot.util.AutoAction;
import frc.team4159.robot.subsystems.Drivetrain;
import frc.team4159.robot.subsystems.Superstructure;
import org.opencv.imgproc.*;
import org.opencv.core.*;

import java.util.ArrayList;
import java.util.List;

import static frc.team4159.robot.util.AutoAction.*;

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

    /* Commands */
    private Command autoCommand;
    private Command endGameCommand;

    /* Auto choosers */
    private SendableChooser<AutoAction> leftAutoAction;
    private SendableChooser<AutoAction> rightAutoAction;

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

        /*
         * Put auto action choosers to SmartDashboard
         */
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
        UsbCamera driverCam = CameraServer.getInstance().startAutomaticCapture(0);
//        CvSink cvSink = new CvSink("driverCam");
//        cvSink.setSource(driverCam);
//        cvSink.setEnabled(true);

//        Command visualServo = new VisualServo();
//        visualServo.start();

        new Thread(() -> {

            CvSink cvSink = new CvSink("driverCam");
            cvSink.setSource(driverCam);
            cvSink.setEnabled(true);

            CvSource outputStream = CameraServer.getInstance().putVideo("VisualServo", 256, 144);

            Mat source = new Mat();
            Mat output = new Mat();

            //Outputs
            Mat resizeImageOutput = new Mat();
            Mat hsvThresholdOutput = new Mat();
            ArrayList<MatOfPoint> findContoursOutput = new ArrayList<>();
            ArrayList<MatOfPoint> filterContoursOutput = new ArrayList<>();

            while(!Thread.interrupted()) {

                if(cvSink.grabFrame(source)!=0){

                    //Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);

                    // Step HSV_Threshold0:
                    Mat hsvThresholdInput = source;
                    double[] hsvThresholdHue = {0.0, 180.0};
                    double[] hsvThresholdSaturation = {0.0, 25.543293718166378};
                    double[] hsvThresholdValue = {233.90287769784172, 255.0};
                    hsvThreshold(hsvThresholdInput, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue, output);

                    // Step Find_Contours0:
                    Mat findContoursInput = output;
                    boolean findContoursExternalOnly = false;
                    findContours(findContoursInput, findContoursExternalOnly, findContoursOutput);

                    // Step Filter_Contours0:
                    ArrayList<MatOfPoint> filterContoursContours = findContoursOutput;
                    double filterContoursMinArea = 50.0;
                    double filterContoursMinPerimeter = 0.0;
                    double filterContoursMinWidth = 0.0;
                    double filterContoursMaxWidth = 1000.0;
                    double filterContoursMinHeight = 0.0;
                    double filterContoursMaxHeight = 1000.0;
                    double[] filterContoursSolidity = {75.53956834532373, 100.0};
                    double filterContoursMaxVertices = 1000000.0;
                    double filterContoursMinVertices = 4.0;
                    double filterContoursMinRatio = 0.0;
                    double filterContoursMaxRatio = 1000.0;
                    filterContours(filterContoursContours, filterContoursMinArea, filterContoursMinPerimeter, filterContoursMinWidth, filterContoursMaxWidth, filterContoursMinHeight, filterContoursMaxHeight, filterContoursSolidity, filterContoursMaxVertices, filterContoursMinVertices, filterContoursMinRatio, filterContoursMaxRatio, filterContoursOutput);

                    Imgproc.drawContours(source, filterContoursOutput, 0, new Scalar(255,0,0), 10);

                    outputStream.putFrame(source);

                    Moments m = Imgproc.moments(filterContoursOutput.get(0));
                    int x = (int)(m.m01/m.m00);

                    drivetrain.setRawOutput(-0.01 * x, 0.01 * x);
                }

            }
        }).start();

    }

    /**
     * Scales and image to an exact size.
     * @param input The image on which to perform the Resize.
     * @param width The width of the output in pixels.
     * @param height The height of the output in pixels.
     * @param interpolation The type of interpolation.
     * @param output The image in which to store the output.
     */
    private void resizeImage(Mat input, double width, double height,
                             int interpolation, Mat output) {
        Imgproc.resize(input, output, new Size(width, height), 0.0, 0.0, interpolation);
    }

    /**
     * Segment an image based on hue, saturation, and value ranges.
     *
     * @param input The image on which to perform the HSL threshold.
     * @param hue The min and max hue
     * @param sat The min and max saturation
     * @param val The min and max value
     */
    private void hsvThreshold(Mat input, double[] hue, double[] sat, double[] val,
                              Mat out) {
        Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HSV);
        Core.inRange(out, new Scalar(hue[0], sat[0], val[0]),
                new Scalar(hue[1], sat[1], val[1]), out);
    }

    /**
     * Sets the values of pixels in a binary image to their distance to the nearest black pixel.
     * @param input The image on which to perform the Distance Transform.
     */
    private void findContours(Mat input, boolean externalOnly,
                              List<MatOfPoint> contours) {
        Mat hierarchy = new Mat();
        contours.clear();
        int mode;
        if (externalOnly) {
            mode = Imgproc.RETR_EXTERNAL;
        }
        else {
            mode = Imgproc.RETR_LIST;
        }
        int method = Imgproc.CHAIN_APPROX_SIMPLE;
        Imgproc.findContours(input, contours, hierarchy, mode, method);
    }


    /**
     * Filters out contours that do not meet certain criteria.
     * @param inputContours is the input list of contours
     * @param output is the the output list of contours
     * @param minArea is the minimum area of a contour that will be kept
     * @param minPerimeter is the minimum perimeter of a contour that will be kept
     * @param minWidth minimum width of a contour
     * @param maxWidth maximum width
     * @param minHeight minimum height
     * @param maxHeight maximimum height
     * @param minVertexCount minimum vertex Count of the contours
     * @param maxVertexCount maximum vertex Count
     * @param minRatio minimum ratio of width to height
     * @param maxRatio maximum ratio of width to height
     */
    private void filterContours(List<MatOfPoint> inputContours, double minArea,
                                double minPerimeter, double minWidth, double maxWidth, double minHeight, double
                                        maxHeight, double[] solidity, double maxVertexCount, double minVertexCount, double
                                        minRatio, double maxRatio, List<MatOfPoint> output) {
        final MatOfInt hull = new MatOfInt();
        output.clear();
        //operation
        for (final MatOfPoint contour : inputContours) {
            final Rect bb = Imgproc.boundingRect(contour);
            if (bb.width < minWidth || bb.width > maxWidth) continue;
            if (bb.height < minHeight || bb.height > maxHeight) continue;
            final double area = Imgproc.contourArea(contour);
            if (area < minArea) continue;
            if (Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) < minPerimeter) continue;
            Imgproc.convexHull(contour, hull);
            MatOfPoint mopHull = new MatOfPoint();
            mopHull.create((int) hull.size().height, 1, CvType.CV_32SC2);
            for (int j = 0; j < hull.size().height; j++) {
                int index = (int) hull.get(j, 0)[0];
                double[] point = new double[]{contour.get(index, 0)[0], contour.get(index, 0)[1]};
                mopHull.put(j, 0, point);
            }
            final double solid = 100 * area / Imgproc.contourArea(mopHull);
            if (solid < solidity[0] || solid > solidity[1]) continue;
            if (contour.rows() < minVertexCount || contour.rows() > maxVertexCount) continue;
            final double ratio = bb.width / (double) bb.height;
            if (ratio < minRatio || ratio > maxRatio) continue;
            output.add(contour);
        }
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

        /*
        if(oi.getAutoSelectionButton()) {
            autoSelector.nextSelection();
            printAutoOptions();
        }

        if(oi.getAutoOptionButton()) {
            autoSelector.nextOption();
            printAutoOptions();
        }
        */

        Scheduler.getInstance().run();
    }

    /**
     * Runs once at the start of autonomous
     */
    @Override
    public void autonomousInit() {

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

        Superstructure.getInstance().enableCompressor();

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
    public AutoAction getLeftAction() {
        return leftAutoAction.getSelected();
    }

    /**
     * @return Auto action if right side is near switch
     */
    public AutoAction getRightAction() {
        return rightAutoAction.getSelected();
    }

    /*
     * Print auto options, along with a bunch of new lines
     * Currently unused.
     */
    private void printAutoOptions() {
        /*
        System.out.println("SELECTION: " + autoSelector.getSelection());
        System.out.println("POSITION: " + autoSelector.getPosition());
        System.out.println("LEFT ACTION: " + autoSelector.getLeftAction());
        System.out.println("RIGHT ACTION: " + autoSelector.getRightAction());
        for(int i = 0; i < 20; i++) {
            System.out.println("\n");
        }
        */
    }

}
