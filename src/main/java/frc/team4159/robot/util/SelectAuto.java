package frc.team4159.robot.util;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;


public class SelectAuto extends Command {

    private AutoSelector autoSelector = Robot.getAutoSelector();

    SelectAuto() {
        requires(Robot.autoSelector);
    }

    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {

        if(Robot.oi.getAutoOptionButton()){
            autoSelector.nextOption();
        }

        if(Robot.oi.getAutoSelectionButton()){
            autoSelector.nextSelection();
        }

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
