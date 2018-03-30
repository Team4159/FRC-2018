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


class LeftAuto extends CommandGroup {

    LeftAuto(MatchData.OwnedSide switchNear, String leftAction, String rightAction) {

        System.out.println("Left position auto started");

        addParallel(new SolidLED());
        addParallel(new RunLift());
        addSequential(new ResetLiftTopPosition());
        addSequential(new LiftUp());

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
