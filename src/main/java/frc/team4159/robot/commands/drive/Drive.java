package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.team4159.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Drive extends Command{

    public Drive() {
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        if(Robot.oi.reverseControls()) {
            Robot.drivetrain.reverseControls();
        }

        if(Robot.oi.left90Button()) {
            Robot.drivetrain.turnToAngle(-90);

        } else if(Robot.oi.right90Button()) {
            Robot.drivetrain.turnToAngle(90);

        } else if(Robot.oi.cw180Button()) {
            Robot.drivetrain.turnToAngle(180);

        } else if(Robot.oi.ccw180Buton()) {
            Robot.drivetrain.turnToAngle(-180);

        } else if(Robot.oi.driveStraightButton()) {
            double magnitude = (Robot.oi.getLeftY() + Robot.oi.getRightY()) /2;
            Robot.drivetrain.driveStraight(magnitude);

        } else {
            Robot.drivetrain.disableTurnControl();
            Robot.drivetrain.setRawOutput(Robot.oi.getLeftY(), Robot.oi.getRightY());

        }

        Robot.drivetrain.logDashboard();

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
