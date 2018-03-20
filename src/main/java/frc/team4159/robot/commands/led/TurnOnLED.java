package frc.team4159.robot.commands.led;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.subsystems.Superstructure;


public class TurnOnLED extends InstantCommand {

    public TurnOnLED() {
        requires(Superstructure.led);
        Superstructure.getInstance().getLED().enableLEDRings();
    }

}
