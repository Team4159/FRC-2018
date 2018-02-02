package frc.team4159.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.OI;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Superstructure;


public class Climb extends Command {

    public Climb() {
        requires(Robot.superstructure.climber);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        if(Robot.oi.getClimbUp())
            Superstructure.climber.climbUp();
        else if(Robot.oi.getClimbDown())
            Superstructure.climber.climbDown();
        else
            Superstructure.climber.stopClimb();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Superstructure.climber.stopClimb();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
