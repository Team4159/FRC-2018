package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class DriveStraight extends Command {

    private double distance;
    private final double WHEELBASE_WIDTH = 0.5;
    private final double MAX_VELOCITY = 1.7;
    private final double WHEEL_DIAMETER = 1;
    private EncoderFollower left;
    private EncoderFollower right;
    public DriveStraight(double distance) {
        requires(Robot.drivetrain);
        this.distance = distance;
    }

    // See https://github.com/JacisNonsense/Pathfinder/wiki/Pathfinder-for-FRC---Java
    @Override
    protected void initialize() {
        Waypoint[] points = new Waypoint[] {
                new Waypoint(0, distance, 0)
        };
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, MAX_VELOCITY, 2.0, 60.0);
        Trajectory trajectory = Pathfinder.generate(points, config);
        TankModifier modifier = new TankModifier(trajectory).modify(WHEELBASE_WIDTH);
        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());
        left.configureEncoder(Robot.drivetrain.getLeftEncoderPosition(), 4096, WHEEL_DIAMETER);
        left.configurePIDVA(1.0, 0.0, 0.0, 1 / MAX_VELOCITY, 0);
        right.configureEncoder(Robot.drivetrain.getRightEncoderPosition(), 4096, WHEEL_DIAMETER);
        right.configurePIDVA(1.0, 0.0, 0.0, 1 / MAX_VELOCITY, 0);
    }

    @Override
    protected void execute() {

        double l = left.calculate(Robot.drivetrain.getLeftEncoderPosition());
        double r = right.calculate(Robot.drivetrain.getRightEncoderPosition());

        double gyro_heading = Robot.drivetrain.getHeadingDegrees();  // Assuming the gyro is giving a value in degrees
        double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        double turn = 0.8 * (-1.0/80.0) * angleDifference;

        Robot.drivetrain.setRawOutput(l + turn, r - turn);
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
