package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.subsystems.Superstructure;


public class ResetLiftTopPosition extends InstantCommand {

    public ResetLiftTopPosition() {
        Superstructure.getInstance().getCubeHolder().setLiftEncoderValue(3300);
    }

}
