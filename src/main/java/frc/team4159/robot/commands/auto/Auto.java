package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team4159.robot.Robot;
import frc.team4159.robot.commands.cube.LiftUp;
import frc.team4159.robot.commands.drive.TestMotionProfile;

/*
* This CommandGroup handles the initial delay and switching between auto actions
 */

public class Auto extends CommandGroup {

    public Auto(AutoAction action) {

        Robot robot = Robot.getInstance();
        double delay = robot.getAutoDelay();

        addSequential(new WaitCommand(delay));

        switch (action) {

            case BASELINE:
                //addSequential(new AutoBaseline());
                addSequential(new TestMotionProfile());
                addParallel(new LiftUp());
                break;

            case ONE_CUBE:
//                addSequential(new AutoOneCube());
                break;

            case TWO_CUBE:
//                addSequential(new AutoTwoCube());
                break;

            case TWO_VAULT:
//                addSequential(new AutoTwoVault());
                break;

            case ONE_CUBE_ONE_VAULT:
//                addSequential(new AutoHalfHalf());
                break;

            default:
                addSequential(new AutoBaseline());
                break;
        }
    }
}
