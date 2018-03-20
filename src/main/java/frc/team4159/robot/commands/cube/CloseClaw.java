package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.subsystems.Superstructure;


public class CloseClaw extends InstantCommand {

    public CloseClaw() {
        Superstructure.getInstance().getCubeHolder().close();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
