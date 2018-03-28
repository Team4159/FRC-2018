package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;


public class GetAutoOptions extends Command {

    GetAutoOptions() {

    }

    @Override
    protected void initialize() {
        Auto.setPosition(Robot.getAutoSelector().getPosition());
        Auto.setLeftAction(Robot.getAutoSelector().getLeftAction());
        Auto.setRightAction(Robot.getAutoSelector().getRightAction());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
