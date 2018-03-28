package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import frc.team4159.robot.commands.cube.LiftUp;
import frc.team4159.robot.commands.cube.OuttakeWheels;
import frc.team4159.robot.commands.cube.ResetLiftTopPosition;
import frc.team4159.robot.commands.cube.RunLift;
import frc.team4159.robot.commands.drive.RunCSVProfile;
import frc.team4159.robot.commands.led.SolidLED;
import frc.team4159.robot.util.AutoSelector;
import openrio.powerup.MatchData;

import static frc.team4159.robot.commands.auto.TrajectoryCSV.*;
import static frc.team4159.robot.commands.auto.TrajectoryCSV.BASELINE_L;
import static frc.team4159.robot.commands.auto.TrajectoryCSV.BASELINE_R;

/*
 * This CommandGroup handles the initial delay and switching between auto actions based on starting position
 */

public class Auto extends CommandGroup {

    private AutoSelector autoSelector;
    private static String position, leftAction, rightAction;
    private MatchData.OwnedSide switchNear;

    public Auto() {

        autoSelector = AutoSelector.getInstance();
        position = autoSelector.getPosition();
        leftAction = autoSelector.getLeftAction();
        rightAction = autoSelector.getRightAction();

        switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        while(switchNear == MatchData.OwnedSide.UNKNOWN) {
            addSequential(new WaitCommand(0.1));
            switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);
        }

        /*
         * Under all auto circumstances, the robot will turn on its LED rings for 15 seconds, update the lift position
         * for 15 seconds, reset the lift encoder, set the lift to switch height, and wait a delay (default 0 seconds)
         */
        addParallel(new SolidLED());
        addParallel(new RunLift());
        addSequential(new ResetLiftTopPosition());
        addSequential(new LiftUp());
        //addSequential(new GetAutoOptions());
        //addSequential(new WaitCommand(0));

        switch(position) {
            case "LEFT":
                leftCommand();
                break;
            case "MIDDLE":
                middleCommand();
                break;
            case "RIGHT":
                rightCommand();
                break;
        }

    }

    private void leftCommand() {

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

    private void middleCommand() {

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

    private void rightCommand() {

        switch (switchNear) {
            case LEFT:
                switch(leftAction) {
                    case("ONE"):
                        addSequential(new RunCSVProfile(RIGHT_TO_LEFT_L, RIGHT_TO_LEFT_L));
                        // turn 180, drive straight
                        //addSequential(new OuttakeWheels(1));
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
                        addSequential(new RunCSVProfile(RIGHT_TO_RIGHT_L, RIGHT_TO_RIGHT_R));
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
            default:
                addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                break;
        }
    }

    public static void setPosition(String new_position) {
        position = new_position;
    }
    public static void setLeftAction(String new_position) {
        leftAction = new_position;
    }
    public static void setRightAction(String new_position) {
        rightAction = new_position;
    }
}
