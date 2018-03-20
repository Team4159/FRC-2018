package frc.team4159.robot.commands.led;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class BlinkLED extends CommandGroup {

    public BlinkLED() {

        for(int i = 0; i < 20; i++) {
            addSequential(new TurnOnLED());
            addSequential(new WaitCommand(0.5));
            addSequential(new TurnOffLED());
            addSequential(new WaitCommand(0.5));
        }
    }

}
