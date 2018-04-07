package frc.team4159.robot.commands.antenna;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.subsystems.Superstructure;

public class DeployAntenna extends InstantCommand {

    public DeployAntenna() {
        Superstructure.getInstance().getAntenna().deployAntenna();
    }
}
