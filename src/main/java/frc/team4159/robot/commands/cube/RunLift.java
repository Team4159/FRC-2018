package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team4159.robot.subsystems.Superstructure;

public class RunLift extends TimedCommand {

    public RunLift() {
        /* Run for 15 seconds, the duration of autonomous */
        super(15);
    }

    @Override
    protected void execute() {
        Superstructure.getInstance().getCubeHolder().move();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
