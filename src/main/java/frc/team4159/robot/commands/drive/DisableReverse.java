package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.Robot;

public class DisableReverse extends InstantCommand {

    public DisableReverse() {
        Robot.getDrivetrain().disableReverse();
    }

}
