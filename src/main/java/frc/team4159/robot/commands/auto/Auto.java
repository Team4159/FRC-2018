package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team4159.robot.Robot;
import openrio.powerup.MatchData;

import java.util.HashMap;

public class Auto extends TimedCommand {

    private Robot robot;
    private HashMap<String, Command> autoList;

    public Auto() {

        super(15);
        robot = Robot.getInstance();
        autoList = new HashMap<>();

        autoList.put("BASELINE",         new BaselineAuto());
        autoList.put("BASELINE_DROP",    new BaselineDropAuto());
        autoList.put("MID_TO_LEFT",      new MiddleLeftAuto());
        autoList.put("MID_TO_RIGHT",     new MiddleRightAuto());
        autoList.put("LEFT_TO_LEFT",     new LeftToLeftAuto());
        autoList.put("RIGHT_TO_RIGHT",   new RightToRightAuto());

    }

    @Override
    protected void initialize() {

        Command command;

        MatchData.OwnedSide nearSwitch = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        String leftAction = robot.getLeftAction();
        String rightAction = robot.getRightAction();

        if(nearSwitch == MatchData.OwnedSide.LEFT) {
            command = autoList.get(leftAction);

        } else if(nearSwitch == MatchData.OwnedSide.RIGHT) {
            command = autoList.get(rightAction);

        } else {
            command = new BaselineAuto();
        }

        command.start();

    }

    /**
     * @return True after 15 seconds has passed
     */
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
