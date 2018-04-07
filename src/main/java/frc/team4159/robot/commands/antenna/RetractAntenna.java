package frc.team4159.robot.commands.antenna;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.subsystems.Superstructure;


public class RetractAntenna extends InstantCommand {

    public RetractAntenna() {
        Superstructure.getInstance().getAntenna().retractAntenna();
    }
}
