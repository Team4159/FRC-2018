package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.subsystems.Superstructure;

import static frc.team4159.robot.Constants.SWITCH_HEIGHT;

public class LiftUp extends InstantCommand {

    public LiftUp() {
        Superstructure.getInstance().getCubeHolder().setTargetPosition(SWITCH_HEIGHT);
    }

}
