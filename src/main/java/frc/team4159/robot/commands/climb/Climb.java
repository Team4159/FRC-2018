package frc.team4159.robot.commands.climb;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Climber;
import frc.team4159.robot.subsystems.Superstructure;

/* The climber subsystem consists of a motor running a winch to move the one stage elevator up and down */

public class Climb extends Command {

    private Climber climber = Superstructure.getInstance().getClimber();

    public Climb() {
        requires(Superstructure.climber);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        climber.winch(Robot.oi.climbWinch());

        if (Robot.oi.climbTopButton()){
            climber.climbTop();
        }else if(Robot.oi.climbUpButton()){
            climber.climbUp();
        }else if(Robot.oi.climbDownButton()) {
            climber.climbDown();
        }else
            climber.holdPosition();

        climber.climberRun();

        climber.logSmartDashboard();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted(){
        end();
    }
}
