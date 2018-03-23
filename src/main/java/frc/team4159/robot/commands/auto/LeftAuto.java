package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team4159.robot.Robot;
import frc.team4159.robot.commands.cube.OuttakeWheels;
import frc.team4159.robot.commands.drive.RunCSVProfile;
import openrio.powerup.MatchData;

import static frc.team4159.robot.commands.auto.TrajectoryCSV.*;

class LeftAuto extends CommandGroup {

    LeftAuto() {

        Robot robot = Robot.getInstance();

        /*
         * Get game data from FMS. If UNKNOWN, wait for 0.1 seconds and try again
         */
        MatchData.OwnedSide switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        while(switchNear == MatchData.OwnedSide.UNKNOWN) {
            addSequential(new WaitCommand(0.1));
            switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        }

        String leftAction = robot.getLeftAction();
        String rightAction = robot.getRightAction();

        switch (switchNear) {
            case LEFT:
                switch(leftAction) {
                    case("ONE"):
                        addSequential(new RunCSVProfile(LEFT_TO_LEFT_L, LEFT_TO_LEFT_R));
                        addSequential(new OuttakeWheels(1));
                        break;
                    case("BASE"):
                        addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                        break;
                    default:
                        addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                        break;
                }
                break;
            case RIGHT:
                switch(rightAction) {
                    case("ONE"):
                        addSequential(new RunCSVProfile(LEFT_TO_RIGHT_L, LEFT_TO_RIGHT_R));
                        // one eighty turn, go straight, outtake
                        break;
                    case("BASE"):
                        addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                        break;
                    default:
                        addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                        break;
                }
                break;
            default:
                addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                break;
        }
    }
}
