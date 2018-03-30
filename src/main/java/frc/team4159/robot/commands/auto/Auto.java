package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team4159.robot.util.AutoSelector;
import openrio.powerup.MatchData;


/*
 * This CommandGroup handles the retrieval of auto options and the execution of auto commands based on them
 */

public class Auto extends TimedCommand {

    public Auto() {
        super(15);
    }

    @Override
    protected void initialize() {

        System.out.println("Auto.java Initialized");

        AutoSelector autoSelector = AutoSelector.getInstance();

        String position = autoSelector.getPosition();
        String leftAction = autoSelector.getLeftAction();
        String rightAction = autoSelector.getRightAction();

        MatchData.OwnedSide switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        System.out.println(switchNear + " is near switch");

        /*
         * Switch between different sets of auto commands based on starting position on the field
         */
        switch(position) {
            case "LEFT":
                Command leftCommand = new LeftAuto(switchNear, leftAction, rightAction);
                leftCommand.start();
                break;
            case "MIDDLE":
                Command middleCommand = new MiddleAuto(switchNear, leftAction, rightAction);
                middleCommand.start();
                break;
            case "RIGHT":
                Command rightCommand = new RightAuto(switchNear, leftAction, rightAction);
                rightCommand.start();
                break;
        }
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
