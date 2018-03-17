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

        /*These should have gone in the subsystem itself, but later*/

        /* If climb hasn't already started and the joystick is pointed towards positive,
           the set the hasStartetdClimb flag to true.*/
        if(!climber.getClimbStarted()&&Robot.oi.getSecondaryY()>.5){
            climber.setStartedClimb(true);
        }

        /* If the climber has already started going up and the joystick is pointed towards negative,
           then set the hasGoneDownFlag to false*/
        if(climber.getClimbStarted()&&Robot.oi.getSecondaryY()<-.5){
           climber.setGoneDown(true);
        }

        if(Robot.oi.climbEnable()){
            if(Math.abs(Robot.oi.getSecondaryY()) > .1)
                climber.updatePosition(Robot.oi.getSecondaryY());
            else
                climber.stopIncrement();

        }

        if(Robot.oi.climbWinch()) {
            climber.winch();
        } else {
            climber.stopWinch();
        }

        if(Robot.oi.fastDownButton()) {
            climber.fastDown();
        }

        if(Robot.oi.toggleClimbTalonMode()){
            climber.toggleClimbTalonMode();
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
