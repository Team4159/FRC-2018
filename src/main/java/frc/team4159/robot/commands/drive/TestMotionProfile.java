package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import static frc.team4159.robot.Constants.*;
import frc.team4159.robot.Robot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import java.io.File;

public class TestMotionProfile extends Command {

    private final double MAX_VELOCITY = 1.7; // meters per second
    private final double MAX_ACCELERATION = 2.0; // meters per second squared
    private final double MAX_JERK = 60.0; // meters per second cubed
    private final double DELTA_TIME = 0.05; // rate at which control loop on roborio runs (sec)
    private final double kV = 1 / MAX_VELOCITY;
    private final double kA = 0;
    private final double kP_TURN = 0.3;

    private EncoderFollower left;
    private EncoderFollower right;

    public TestMotionProfile() {
        requires(Robot.drivetrain);
    }

    // See https://github.com/JacisNonsense/Pathfinder/wiki/Pathfinder-for-FRC---Java
    @Override
    protected void initialize() {

        Robot.drivetrain.zeroNavX();

        Waypoint[] points = new Waypoint[] {
                new Waypoint(0, 0, 0),
                new Waypoint(3.75, 1, 90)
        };

        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
                Trajectory.Config.SAMPLES_HIGH, DELTA_TIME, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
        Trajectory trajectory = Pathfinder.generate(points, config);

        TankModifier modifier = new TankModifier(trajectory).modify(WHEELBASE_WIDTH);

        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());

        left.configureEncoder(Robot.drivetrain.getLeftEncoderPosition(), UNITS_PER_REV, WHEEL_DIAMETER);
        right.configureEncoder(Robot.drivetrain.getRightEncoderPosition(), UNITS_PER_REV, WHEEL_DIAMETER);
        left.configurePIDVA(0,0,0, kV, kA);
        right.configurePIDVA(0,0,0, kV, kA);

//        File file1 = new File("realLeftBaseline.csv");
//        Pathfinder.writeToCSV(file1, modifier.getLeftTrajectory());
//        File file2 = new File("realRightBaseline.csv");
//        Pathfinder.writeToCSV(file2, modifier.getRightTrajectory());

    }

    @Override
    protected void execute() {

        double l = left.calculate(Robot.drivetrain.getLeftEncoderPosition());
        double r = right.calculate(Robot.drivetrain.getRightEncoderPosition());

        double gyro_heading = Robot.drivetrain.getHeadingDegrees();
        double desired_heading = Pathfinder.r2d(left.getHeading());

        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        double turn = kP_TURN * (-1.0/80.0) * angleDifference;

        Robot.drivetrain.setRawOutput(l + turn, r - turn);
        Robot.drivetrain.logDashboard();

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
