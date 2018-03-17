package frc.team4159.robot.commands.drive;

import java.io.File;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

import static frc.team4159.robot.Constants.UNITS_PER_REV;
import static frc.team4159.robot.Constants.WHEEL_DIAMETER;

public class RunCSVProfile extends Command implements Runnable {

    // 4.1148 max v generator
    private final double MAX_VELOCITY = 4.25; // meters per second ?!?!?!?!?!?!
    private final double kV = 1 / MAX_VELOCITY;
    private final double kA = 0; // TUNE
    private final double kP_TURN = 0.1;

    private EncoderFollower left;
    private EncoderFollower right;

    private String leftCSV;
    private String rightCSV;

    private Notifier notifier = new Notifier(this);

    public RunCSVProfile(String leftCSV, String rightCSV) {
        requires(Robot.drivetrain);
        this.leftCSV = leftCSV;
        this.rightCSV = rightCSV;
    }

    @Override
    protected void initialize() {

        Robot.drivetrain.zeroNavX();

        File left_csv_trajectory = new File(leftCSV);
        File right_csv_trajectory = new File(rightCSV);

        Trajectory left_trajectory = Pathfinder.readFromCSV(left_csv_trajectory);
        Trajectory right_trajectory = Pathfinder.readFromCSV(right_csv_trajectory);

        left = new EncoderFollower(left_trajectory);
        right = new EncoderFollower(right_trajectory);

        left.configureEncoder(Robot.drivetrain.getLeftEncoderPosition(), UNITS_PER_REV, WHEEL_DIAMETER);
        left.configurePIDVA(0.0, 0.0, 0.0, kV, kA);

        right.configureEncoder(Robot.drivetrain.getRightEncoderPosition(), UNITS_PER_REV, WHEEL_DIAMETER);
        right.configurePIDVA(0.0, 0.0, 0.0, kV, kA);

        // In seconds
        //notifier.startPeriodic(0.01);

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

    /* Looped by Notifier */
    public void run() {

//        double l = left.calculate(Robot.drivetrain.getLeftEncoderPosition());
//        double r = right.calculate(Robot.drivetrain.getRightEncoderPosition());
//
//        double gyro_heading = Robot.drivetrain.getHeadingDegrees();
//        double desired_heading = Pathfinder.r2d(left.getHeading());
//
//        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
//        double kG = kP_TURN * (-1.0/80.0);
//        double turn = kG * angleDifference;
//
//        Robot.drivetrain.setRawOutput(l + turn, r - turn);
//        Robot.drivetrain.logDashboard();

    }

    @Override
    protected boolean isFinished() {
        return left.isFinished() && right.isFinished();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
