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

        if(Robot.oi.climbEnable()){
            if(Math.abs(Robot.oi.getSecondaryY()) > .1) {
                climber.updatePosition(Robot.oi.getSecondaryY());
            }
        }

        if(Robot.oi.climbWinch()) {
            climber.winch();
        } else {
            climber.stopWinch();
        }

        if(Robot.oi.fastDownButton1()) {
            climber.fast1();
        }

        if(Robot.oi.fastDownButton2()) {
            climber.fast2();
        }

        climber.move();

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
