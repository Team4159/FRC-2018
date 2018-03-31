package frc.team4159.robot.commands.led;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class BlinkLED extends CommandGroup {

    public BlinkLED() {

        /* Wait 1 min and 45 seconds */
        addSequential(new WaitCommand(105));

        /* Blinky magic! */
        for(int i = 0; i < 150; i++) {
            addSequential(new TurnOnLED(0.1));
            addSequential(new TurnOffLED(0.1));
        }
    }
}
