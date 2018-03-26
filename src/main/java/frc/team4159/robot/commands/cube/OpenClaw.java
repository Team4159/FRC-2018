package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.subsystems.Superstructure;

public class OpenClaw extends InstantCommand {

    public OpenClaw() {
        Superstructure.getInstance().getCubeHolder().open();
    }

}
