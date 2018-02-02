package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Constants;
import frc.team4159.robot.Robot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class DriveStraight extends Command {

    // TODO: Check frequency this pid loop gets called

    private double distance;
    private final double WHEELBASE_WIDTH = 0.6566535; // 25.8525 inches to meters
    private final double MAX_VELOCITY = 4; // meters per second
    private final double WHEEL_DIAMETER = 0.1016; // 4 inches to meters
    private EncoderFollower left;
    private EncoderFollower right;
    public DriveStraight(double distance) {
        requires(Robot.drivetrain);
        this.distance = distance;
    }

    // See https://github.com/JacisNonsense/Pathfinder/wiki/Pathfinder-for-FRC---Java
    @Override
    protected void initialize() {

        System.out.println("drive straight init");

        Waypoint[] points = new Waypoint[] {
                new Waypoint(distance, 0, 0)
        };
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, MAX_VELOCITY, 5, 60.0);
        Trajectory trajectory = Pathfinder.generate(points, config);
        TankModifier modifier = new TankModifier(trajectory).modify(WHEELBASE_WIDTH);
        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());
        left.configureEncoder(Robot.drivetrain.getLeftEncoderPosition(), Constants.UNITS_PER_REV, WHEEL_DIAMETER);
        left.configurePIDVA(0.8, 0.0, 0.0, 1 / MAX_VELOCITY, 0);
        right.configureEncoder(Robot.drivetrain.getRightEncoderPosition(), Constants.UNITS_PER_REV, WHEEL_DIAMETER);
        right.configurePIDVA(0.8, 0.0, 0.0, 1 / MAX_VELOCITY, 0);
    }

    @Override
    protected void execute() {

        System.out.println("drive straight execute");

        double l = left.calculate(Robot.drivetrain.getLeftEncoderPosition());
        double r = right.calculate(Robot.drivetrain.getRightEncoderPosition());

        double gyro_heading = Robot.drivetrain.getHeadingDegrees();  // Assuming the gyro is giving a value in degrees
        double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        double turn = 0.8 * (-1.0/80.0) * angleDifference;

        Robot.drivetrain.setRawOutput(l + turn, r - turn);
        Robot.drivetrain.getHeadingDegrees();
    }

    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
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
