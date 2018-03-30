package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team4159.robot.commands.drive.RunCSVProfile;
import frc.team4159.robot.util.AutoSelector;
import openrio.powerup.MatchData;

import static frc.team4159.robot.util.TrajectoryCSV.*;


public class Auto extends TimedCommand {

    public Auto() {
        super(15);
    }

    @Override
    protected void initialize() {

        MatchData.OwnedSide nearSwitch = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        String position = AutoSelector.getInstance().getPosition();
        Command command;

        System.out.println("Near switch: " + nearSwitch);
        System.out.println("Position: " + position);

        switch(position) {
            case "LEFT":
                if(nearSwitch == MatchData.OwnedSide.LEFT) {
                    command = new LeftToLeftAuto();
                } else if(nearSwitch == MatchData.OwnedSide.RIGHT) {
                    command = new RunCSVProfile(BASELINE_L, BASELINE_R);
                } else {
                    command = new RunCSVProfile(BASELINE_L, BASELINE_R);
                }
                break;
            case "MIDDLE":
                if(nearSwitch == MatchData.OwnedSide.LEFT) {
                    command = new MiddleLeftAuto();
                } else if(nearSwitch == MatchData.OwnedSide.RIGHT) {
                    command = new MiddleRightAuto();
                } else {
                    command = new RunCSVProfile(BASELINE_L, BASELINE_R);
                }
                break;
            case "RIGHT":
                if(nearSwitch == MatchData.OwnedSide.LEFT) {
                    command = new RunCSVProfile(BASELINE_L, BASELINE_R);
                } else if(nearSwitch == MatchData.OwnedSide.RIGHT) {
                    command = new RightToRightAuto();
                } else {
                    command = new RunCSVProfile(BASELINE_L, BASELINE_R);
                }
                break;
            default:
                command = new RunCSVProfile(BASELINE_L, BASELINE_R);
                break;
        }

        command.start();

    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
