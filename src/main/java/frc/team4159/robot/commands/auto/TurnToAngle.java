package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;


public class TurnToAngle extends Command {

    PIDController turnController;

    public TurnToAngle() {
        requires(Robot.drivetrain);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
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
        super.interrupted();
    }
}
