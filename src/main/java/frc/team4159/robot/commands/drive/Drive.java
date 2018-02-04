package frc.team4159.robot.commands.drive;

import frc.team4159.robot.Robot;
import frc.team4159.robot.OI;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.commands.auto.TurnToAngle;

public class Drive extends Command {

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
            new TurnToAngle(-90);

        } else if(Robot.oi.right90Button()) {
            new TurnToAngle(90);

        } else if(Robot.oi.cw180Button()) {
            new TurnToAngle(180);

        } else if(Robot.oi.ccw180Buton()) {
            new TurnToAngle(-180);

        } else if(Robot.oi.driveStraightButton()) {
            //Robot.drivetrain.driveStraight();
        } else {
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
