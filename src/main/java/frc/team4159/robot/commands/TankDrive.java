package frc.team4159.robot.commands;

import frc.team4159.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {

    public TankDrive() {
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.drivetrain.setLeftRaw(Robot.oi.getLeftY());
        Robot.drivetrain.setRightRaw(Robot.oi.getRightY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		end();
    }
}
