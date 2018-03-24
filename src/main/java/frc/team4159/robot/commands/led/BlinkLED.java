package frc.team4159.robot.commands.led;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class BlinkLED extends CommandGroup {

    public BlinkLED() {

        /* Wait 1 min and 45 seconds */
        addSequential(new WaitCommand(105));

        /* Blinky stuff! */
        for(int i = 0; i < 30; i++) {
            addSequential(new TurnOnLED());
            addSequential(new WaitCommand(0.5));
            addSequential(new TurnOffLED());
            addSequential(new WaitCommand(0.5));
        }
    }

}
