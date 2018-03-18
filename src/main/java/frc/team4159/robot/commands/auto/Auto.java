package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team4159.robot.Robot;
import frc.team4159.robot.commands.cube.LiftUp;
import frc.team4159.robot.commands.cube.OuttakeWheels;
import frc.team4159.robot.commands.cube.RunLift;
import frc.team4159.robot.commands.drive.RunCSVProfile;

import frc.team4159.robot.commands.led.SolidLED;
import openrio.powerup.MatchData;

import static frc.team4159.robot.commands.auto.TrajectoryCSV.*;

/*
 * This CommandGroup handles the initial delay and switching between auto actions
 */

public class Auto extends CommandGroup {

    public Auto(AutoAction action) {

        Robot robot = Robot.getInstance();

        double delay = robot.getAutoDelay();
        String startingPosition = robot.getStartingPosition();

        MatchData.OwnedSide switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        while(switchNear == MatchData.OwnedSide.UNKNOWN) {
            addSequential(new WaitCommand(0.1));
            switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        }

        System.out.println(switchNear + " IS NEAR SWITCH");

        addParallel(new SolidLED());
        addSequential(new WaitCommand(delay));

        /* Goes through all auto action cases */
        switch (action) {

            case BASELINE:
                System.out.println("BASELINE ACTION");
                addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                break;

            case ONE_CUBE:

                System.out.println("ONE CUBE ACTION");

                /* Always runs the lifter */
                addParallel(new RunLift());

                /* Updates the lifter to switch height position */
                addSequential(new LiftUp());

                switch(startingPosition) {

                    case "LEFT":
                        System.out.println("LEFT STARTING POSITION");

                        if(switchNear == MatchData.OwnedSide.LEFT) {
                            addSequential(new RunCSVProfile(LEFT_TO_LEFT_L, LEFT_TO_LEFT_R));
                            addSequential(new OuttakeWheels(1));

                        } else if (switchNear == MatchData.OwnedSide.RIGHT) {
                            addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));

                        } else {
                            System.out.println("Don't know which side");
                            addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                        }

                        break;

                    case "MIDDLE":
                        System.out.println("MIDDLE STARTING POSITION");

                        if(switchNear == MatchData.OwnedSide.LEFT) {
                            addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));

//                            addSequential(new RunCSVProfile(MID_TO_LEFT_L, MID_TO_LEFT_R));
//                            addSequential(new OuttakeWheels(1));

                        } else if (switchNear == MatchData.OwnedSide.RIGHT) {
//                            addSequential(new RunCSVProfile(MID_TO_RIGHT_L, MID_TO_RIGHT_R));
                            addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                            addSequential(new OuttakeWheels(1));

                        } else {
                            addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                        }

                        break;

                    case "RIGHT":
                        System.out.println("RIGHT STARTING POSITION");

                        if(switchNear == MatchData.OwnedSide.RIGHT) {
                            addSequential(new RunCSVProfile(RIGHT_TO_RIGHT_L, RIGHT_TO_RIGHT_R));
                            addSequential(new OuttakeWheels(1));

                        } else {
                            addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                        }
                        break;

                }

                break;

            case TWO_CUBE:
                addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                break;

            default:
                addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                break;
        }
    }
}
