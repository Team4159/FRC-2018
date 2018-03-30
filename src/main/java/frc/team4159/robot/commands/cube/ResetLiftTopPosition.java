package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.subsystems.Superstructure;

import static frc.team4159.robot.Constants.UPPER_LIFTER_LIMIT;

public class ResetLiftTopPosition extends InstantCommand {

    public ResetLiftTopPosition() {
        Superstructure.getInstance().getCubeHolder().setLiftEncoderValue(UPPER_LIFTER_LIMIT);
    }

}
