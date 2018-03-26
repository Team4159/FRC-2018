package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.subsystems.Superstructure;

public class LiftUp extends InstantCommand {

    public LiftUp() {
        Superstructure.getInstance().getCubeHolder().setTargetPosition(2500);
    }

}
