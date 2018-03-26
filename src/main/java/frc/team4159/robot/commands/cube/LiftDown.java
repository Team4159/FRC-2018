package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.subsystems.Superstructure;

public class LiftDown extends InstantCommand {

    public LiftDown() {
        Superstructure.getInstance().getCubeHolder().updatePosition(0);
    }

}
