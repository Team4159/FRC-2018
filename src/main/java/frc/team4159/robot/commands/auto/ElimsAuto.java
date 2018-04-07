package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import openrio.powerup.MatchData;

/**
 * Unused. Kept for historic purposes.
 * We used this in SVR 2018 elimination rounds for simple auto logic that would never go wrong ;)
 * Finalists!!! With Spartan Robotics and Funky Monkeys
 */

public class ElimsAuto extends TimedCommand {

    public ElimsAuto() {
        super(15);
    }

    @Override
    protected void initialize() {
        Command command;

        MatchData.OwnedSide switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);

        if(switchNear == MatchData.OwnedSide.LEFT) {
            command = new BaselineAuto();

        } else if(switchNear == MatchData.OwnedSide.RIGHT) {
            command = new BaselineDropAuto();

        } else {
            command = new BaselineAuto();
        }

        command.start();
    }
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
