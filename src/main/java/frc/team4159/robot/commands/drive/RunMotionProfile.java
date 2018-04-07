package frc.team4159.robot.commands.drive;

import java.io.File;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Drivetrain;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

import static frc.team4159.robot.Constants.UNITS_PER_REV;
import static frc.team4159.robot.Constants.WHEEL_DIAMETER;

// TODO: Test Notifier and change time step on paths to 0.01

public class RunMotionProfile extends Command implements Runnable {

    private Notifier notifier;

    private Drivetrain drivetrain;

    private double MAX_VELOCITY = 4.05; // meters per second ?!?!?!?!?!?!
    private double kA = 0;
    private double kP_TURN = 0.05;

    private EncoderFollower left;
    private EncoderFollower right;

    private String leftCSV;
    private String rightCSV;

    public RunMotionProfile(String leftCSV, String rightCSV) {
        this.leftCSV = leftCSV;
        this.rightCSV = rightCSV;

        drivetrain = Robot.getDrivetrain();
        requires(drivetrain);
    }

    @Override
    protected void initialize() {

        System.out.println("Running: " + leftCSV + ", " + rightCSV);

        MAX_VELOCITY = SmartDashboard.getNumber("MAX_VELOCITY", 4.05);
        kP_TURN = SmartDashboard.getNumber("kP_TURN", 0.05);

        double kV = 1 / MAX_VELOCITY;

        drivetrain.zeroNavX();

        File left_csv_trajectory = new File(leftCSV);
        File right_csv_trajectory = new File(rightCSV);

        Trajectory left_trajectory = Pathfinder.readFromCSV(left_csv_trajectory);
        Trajectory right_trajectory = Pathfinder.readFromCSV(right_csv_trajectory);

        left = new EncoderFollower(left_trajectory);
        right = new EncoderFollower(right_trajectory);

        left.configureEncoder(drivetrain.getLeftEncoderPosition(), UNITS_PER_REV, WHEEL_DIAMETER);
        left.configurePIDVA(0.0, 0.0, 0.0, kV, kA);

        right.configureEncoder(drivetrain.getRightEncoderPosition(), UNITS_PER_REV, WHEEL_DIAMETER);
        right.configurePIDVA(0.0, 0.0, 0.0, kV, kA);

        /*
         * Period of the loop. Consistent with the time step on motion profile csv
         */
        final double period = 0.01;

        /*
         * Start a separate thread with given period
         */
        notifier = new Notifier(this);
        notifier.startPeriodic(period);

    }

    /**
     * Loops in separate thread
     */
    @Override
    public void run() {

        double l = left.calculate(drivetrain.getLeftEncoderPosition());
        double r = right.calculate(drivetrain.getRightEncoderPosition());

        double gyro_heading = drivetrain.getHeadingDegrees();
        double desired_heading = Pathfinder.r2d(left.getHeading());

        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        double kG = kP_TURN * (-1.0/80.0); // TODO: What if this was positive?
        double turn = kG * angleDifference;

        drivetrain.setRawOutput(l + turn, r - turn);
        //drivetrain.logDashboard();

    }

    /**
     * @return True if finished tracking trajectory
     */
    @Override
    protected boolean isFinished() {
        return left.isFinished() && right.isFinished();
    }

    /**
     * Stop drivetrain from moving when command ends
     */
    @Override
    protected void end() {
        notifier.stop();
        drivetrain.stop();
    }

    /**
     * Calls end()
     */
    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
