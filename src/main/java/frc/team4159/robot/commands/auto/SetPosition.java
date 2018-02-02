package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.Robot;


public class SetPosition extends InstantCommand {

    public SetPosition(Robot.StartingConfiguration position) {
        Robot.setAutoPosition(position);
    }

    public boolean isFinished() {
        return true;
    }

}
