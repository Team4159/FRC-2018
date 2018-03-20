package frc.team4159.robot.commands.led;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.subsystems.Superstructure;


public class TurnOffLED extends InstantCommand {

    public TurnOffLED() {
        requires(Superstructure.led);
        Superstructure.getInstance().getLED().disableLEDRings();
    }

}
