package frc.team4159.robot.commands.drive;

import java.io.File;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Drivetrain;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

import static frc.team4159.robot.Constants.UNITS_PER_REV;
import static frc.team4159.robot.Constants.WHEEL_DIAMETER;

public class RunCSVProfile extends Command {

    private Drivetrain drivetrain;

    private double MAX_VELOCITY = 4.05; // meters per second ?!?!?!?!?!?!
    private double kA = 0;
    private double kP_TURN = 0.05;

    private EncoderFollower left;
    private EncoderFollower right;

    private String leftCSV;
    private String rightCSV;

    public RunCSVProfile(String leftCSV, String rightCSV) {
        requires(Robot.drivetrain);
        drivetrain = Robot.getDrivetrain();
        this.leftCSV = leftCSV;
        this.rightCSV = rightCSV;
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

    }

    @Override
    protected void execute() {
        double l = left.calculate(drivetrain.getLeftEncoderPosition());
        double r = right.calculate(drivetrain.getRightEncoderPosition());

        double gyro_heading = drivetrain.getHeadingDegrees();
        double desired_heading = Pathfinder.r2d(left.getHeading());

        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        double kG = kP_TURN * (-1.0/80.0);
        double turn = kG * angleDifference;

        drivetrain.setRawOutput(l + turn, r - turn);
        drivetrain.logDashboard();
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
