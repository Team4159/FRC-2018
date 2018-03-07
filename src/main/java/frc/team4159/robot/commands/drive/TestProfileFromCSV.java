package frc.team4159.robot.commands.drive;

import java.io.File;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import static frc.team4159.robot.Constants.UNITS_PER_REV;
import static frc.team4159.robot.Constants.WHEELBASE_WIDTH;
import static frc.team4159.robot.Constants.WHEEL_DIAMETER;

public class TestProfileFromCSV extends Command{
    private final double MAX_VELOCITY = 1.7; // meters per second
    private final double MAX_ACCELERATION = 2.0; // meters per second squared
    private final double MAX_JERK = 60.0; // meters per second cubed
    private final double DELTA_TIME = 0.05;
    private final double kV = 1 / MAX_VELOCITY;
    private final double kA = 0;
    private final double kP_TURN = 0.8;

    private EncoderFollower left;
    private EncoderFollower right;

    public TestProfileFromCSV() {
        requires(Robot.drivetrain);
    }

    // See https://github.com/JacisNonsense/Pathfinder/wiki/Pathfinder-for-FRC---Java
    @Override
    protected void initialize() {

        Robot.drivetrain.zeroNavX();

        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
                Trajectory.Config.SAMPLES_HIGH, DELTA_TIME, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
        File left_csv_trajectory = new File("/traj/baseline_test_left.csv");
        File right_csv_trajectory = new File("/traj/baseline_test_right.csv");
        Trajectory left_trajectory = Pathfinder.readFromCSV(left_csv_trajectory);
        Trajectory right_trajectory = Pathfinder.readFromCSV(right_csv_trajectory);

        left = new EncoderFollower(left_trajectory);
        right = new EncoderFollower(right_trajectory);

        left.configureEncoder(Robot.drivetrain.getLeftEncoderPosition(), UNITS_PER_REV, WHEEL_DIAMETER);
        left.configurePIDVA(0.0, 0.0, 0.0, kV, kA);

        right.configureEncoder(Robot.drivetrain.getRightEncoderPosition(), UNITS_PER_REV, WHEEL_DIAMETER);
        right.configurePIDVA(0.0, 0.0, 0.0, kV, kA);
    }

    @Override
    protected void execute() {

        double l = left.calculate(Robot.drivetrain.getLeftEncoderPosition());
        double r = right.calculate(Robot.drivetrain.getRightEncoderPosition());

        double gyro_heading = Robot.drivetrain.getHeadingDegrees();
        double desired_heading = Pathfinder.r2d(left.getHeading());

        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        double kG = kP_TURN * (-1.0/80.0);
        double turn = kG * angleDifference;

        Robot.drivetrain.setRawOutput(l + turn, r - turn);
        Robot.drivetrain.logDashboard();

    }

    /* Return true if EncoderFollower outputs 0 */
    @Override
    protected boolean isFinished() {
        return (left.calculate(Robot.drivetrain.getLeftEncoderPosition()) == 0 &&
                right.calculate(Robot.drivetrain.getRightEncoderPosition()) == 0);
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
