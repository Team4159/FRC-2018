package frc.team4159.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.OI;
import frc.team4159.robot.subsystems.Climber;
import frc.team4159.robot.subsystems.Superstructure;

/*
 * The climber subsystem consists of a motor controlling a hook-delivering telescoping arm and another motor to winch
 * the robot up.
 */

public class Climb extends Command {

    private Climber climber;
    private OI oi;

    public Climb() {
        climber = Superstructure.getInstance().getClimber();
        oi = OI.getInstance();
        requires(climber);
    }

    @Override
    protected void execute() {

        if(oi.climbEnable() && Math.abs(oi.getSecondaryY()) > 0.1) {
            climber.rawClimb(oi.getSecondaryY());
        } else {
            climber.stopClimb();
        }

        if(oi.climbWinch()) {
            climber.winch();
        } else {
            climber.stopWinch();
        }

        if(oi.toggleRawClimb()) {
            climber.toggleRawClimb();
        }

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
