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
        requires(Superstructure.climber);
        climber = Superstructure.getInstance().getClimber();
        oi = OI.getInstance();
    }

    @Override
    protected void execute() {

        /*
         * Update setpoint if enable climber button is held and absolute value of y-axis is greater than 0.1
         */
        if(oi.climbEnable() && Math.abs(oi.getSecondaryY()) > 0.1) {
            climber.updateSetpoint(oi.getSecondaryY());
        }

        if(oi.climbWinch()) {
            climber.winch();
        }

        if(oi.toggleRawClimb()) {
            climber.toggleRawClimb();
        }

        climber.update();
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
