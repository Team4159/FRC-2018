package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team4159.robot.Robot;
import openrio.powerup.MatchData;

class RightAuto extends CommandGroup {

    RightAuto() {

        Robot robot = Robot.getInstance();

        /* Get game data from FMS. If UNKNOWN, wait for 0.1 seconds and try again */
        MatchData.OwnedSide switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        while(switchNear == MatchData.OwnedSide.UNKNOWN) {
            addSequential(new WaitCommand(0.1));
            switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        }

        String leftAction = robot.getLeftAction();
        String rightAction = robot.getRightAction();

        switch (switchNear) {
            case LEFT:
                break;
            case RIGHT:
                break;
            case UNKNOWN:
                break;
            default:
                break;
        }
    }
}
