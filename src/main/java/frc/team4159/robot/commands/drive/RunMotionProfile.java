package frc.team4159.robot.commands.drive;

import java.io.File;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Drivetrain;
import frc.team4159.robot.util.Constants;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

// TODO: Test Notifier and change time step on paths to 0.01

public class RunMotionProfile extends Command implements Runnable {

    private Notifier notifier;
    private Constants constants;

    private Drivetrain drivetrain;
    private double kP_TURN;

    private EncoderFollower left;
    private EncoderFollower right;

    private String leftCSV;
    private String rightCSV;

    public RunMotionProfile(String leftCSV, String rightCSV) {
        this.leftCSV = leftCSV;
        this.rightCSV = rightCSV;

        constants = Constants.getInstance();
        kP_TURN = constants.getDouble("kP_turn");

        drivetrain = Robot.getDrivetrain();
        requires(drivetrain);
    }

    @Override
    protected void initialize() {

        System.out.println("Running: " + leftCSV + ", " + rightCSV);

        double MAX_VELOCITY = constants.getDouble("maxVelocity");
        double kA = 0;
        double kV = 1 / MAX_VELOCITY;
        int UNITS_PER_REV = constants.getInt("unitsPerRev");
        double WHEEL_DIAMETER = constants.getDouble("wheelDiameter");

        drivetrain.zeroNavX();

        File left_csv_trajectory  = new File(leftCSV);
        File right_csv_trajectory = new File(rightCSV);

        Trajectory left_trajectory  = Pathfinder.readFromCSV(left_csv_trajectory);
        Trajectory right_trajectory = Pathfinder.readFromCSV(right_csv_trajectory);

        left = new EncoderFollower(left_trajectory);
        right = new EncoderFollower(right_trajectory);

        left.configureEncoder(drivetrain.getLeftEncoderPosition(), UNITS_PER_REV, WHEEL_DIAMETER);
        left.configurePIDVA(0.0, 0.0, 0.0, kV, kA);

        right.configureEncoder(drivetrain.getRightEncoderPosition(), UNITS_PER_REV, WHEEL_DIAMETER);
        right.configurePIDVA(0.1, 0.0, 0.0, kV, kA);

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
     * Loops in separate thread running at constant rate
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
     * Calls end() if interrupted by another command
     */
    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
