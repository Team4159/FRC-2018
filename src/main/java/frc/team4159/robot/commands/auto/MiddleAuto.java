package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4159.robot.commands.cube.LiftUp;
import frc.team4159.robot.commands.cube.OuttakeWheels;
import frc.team4159.robot.commands.cube.ResetLiftTopPosition;
import frc.team4159.robot.commands.cube.RunLift;
import frc.team4159.robot.commands.drive.RunCSVProfile;
import frc.team4159.robot.commands.led.SolidLED;
import openrio.powerup.MatchData;

import static frc.team4159.robot.util.TrajectoryCSV.*;
import static frc.team4159.robot.util.TrajectoryCSV.BASELINE_L;
import static frc.team4159.robot.util.TrajectoryCSV.BASELINE_R;


class MiddleAuto extends CommandGroup {

    MiddleAuto(MatchData.OwnedSide switchNear, String leftAction, String rightAction) {

        addParallel(new SolidLED());
        addParallel(new RunLift());
        addSequential(new ResetLiftTopPosition());
        addSequential(new LiftUp());


        switch (switchNear) {
            case LEFT:
                switch (leftAction) {
                    case "ONE":
                        System.out.println("Yay One ");
                        addSequential(new RunCSVProfile(MID_TO_LEFT_L, MID_TO_LEFT_R));
                        addSequential(new OuttakeWheels(1));
                        break;
                    case "TWO":
                        addSequential(new RunCSVProfile(MID_TO_LEFT_L, MID_TO_LEFT_R));
                        addSequential(new OuttakeWheels(1));
                        //turn, down lifter, open lifter, intake wheels, drive forward, close lifter, drive back, lift, turn, outtake
                        break;
                    case "BASE":
                        addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                        break;
                    default:
                        addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                        break;
                }
                break;

            case RIGHT:
                switch (rightAction) {
                    case "ONE":
                        addSequential(new RunCSVProfile(MID_TO_RIGHT_L, MID_TO_RIGHT_R));
                        addSequential(new OuttakeWheels(1));
                        break;
                    case "TWO":
                        addSequential(new RunCSVProfile(MID_TO_RIGHT_L, MID_TO_RIGHT_R));
                        addSequential(new OuttakeWheels(1));
                        //turn, down lifter, open lifter, intake wheels, drive forward, close lifter, drive back, lift, turn, outtake
                        break;
                    case "BASE":
                        addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                        addSequential(new OuttakeWheels(1));
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
