package frc.team4159.robot.commands.drive;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.VisionThread;
import frc.team4159.robot.Robot;
import frc.team4159.robot.SwitchVisionPipeline;
import frc.team4159.robot.subsystems.Drivetrain;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class VisionScreenStepsExample extends Command implements Runnable {

    private Drivetrain drivetrain;
    private Notifier notifier;

    /* Image rec stuff */
    private static final int IMG_WIDTH = 160;
    private static final int IMG_HEIGHT = 90;
    private VisionThread visionThread;
    private double centerX = 0.0;
    private final Object imgLock = new Object();

    public VisionScreenStepsExample() {
        drivetrain = Robot.getDrivetrain();
        notifier = new Notifier(this);
        requires(drivetrain);
    }

    @Override
    protected void initialize() {

        /*
         * Set up vision thread
         */
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(1);
        camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
        visionThread = new VisionThread(camera, new SwitchVisionPipeline(), pipeline -> {
            if (!pipeline.filterContoursOutput().isEmpty()) {
                Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                synchronized (imgLock) {
                    centerX = r.x + (r.width / 2);
                }
            }
        });
        visionThread.start();

        notifier.startPeriodic(0.01);
    }

    @Override
    public void run() {

        double centerX;
        double kP_TURN = 0.005;
        double BASE_SPEED = 0.5;

        synchronized (imgLock) {
            centerX = this.centerX;
        }

        double angleDifference = centerX - (IMG_WIDTH / 2);
        double turn = angleDifference * kP_TURN;
        drivetrain.setRawOutput(BASE_SPEED + turn, BASE_SPEED - turn);
    }

    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    protected void end() {
        notifier.stop();
        visionThread.interrupt();
        drivetrain.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
