package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.Robot;

/* This Command sets the starting configuration as determined from user input. */

public class SetPosition extends InstantCommand {

    public SetPosition(StartingConfiguration position) {
        Robot robot = Robot.getInstance();
        robot.setAutoPosition(position);
    }

}
