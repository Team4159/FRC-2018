package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.team4159.robot.Robot;
import frc.team4159.robot.commands.cube.LiftUp;
import frc.team4159.robot.commands.cube.OuttakeWheels;
import frc.team4159.robot.commands.cube.RunLift;
import frc.team4159.robot.commands.drive.RunCSVProfile;

import openrio.powerup.MatchData;

import static frc.team4159.robot.commands.auto.TrajectoryCSV.*;

/*
 * This CommandGroup handles the initial delay and switching between auto actions
 */

public class Auto extends CommandGroup {

    public Auto(AutoAction action) {

        Robot robot = Robot.getInstance();
        double delay = robot.getAutoDelay();
        StartingConfiguration position = robot.getStartingConfiguration();
        MatchData.OwnedSide switchNear = MatchData.getOwnedSide(MatchData.GameFeature.SWITCH_NEAR);

        System.out.println(switchNear + " sdfdsfs");

        /* Run auto delay */
        addSequential(new WaitCommand(delay));

        /* Goes through all auto action cases */
        switch (action) {

            case BASELINE:
                System.out.println("BASELINE");
                addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                break;

            case ONE_CUBE:

                System.out.println("ONE CUBE");

                /* Always runs the lifter */
                addParallel(new RunLift());

                /* Updates the lifter to switch height position */
                addSequential(new LiftUp());

                switch(position) {

                    case LEFT:
                        System.out.println("LEFT STARTING CONFIGURATION");

                        if(switchNear == MatchData.OwnedSide.LEFT) {
                            System.out.println("LEFT SWITCH");
                            addSequential(new RunCSVProfile(LEFT_TO_LEFT_L, LEFT_TO_LEFT_R));
                        }
                        else if(switchNear == MatchData.OwnedSide.RIGHT) {
                            System.out.println("RIGHT SWITCH");
                            addSequential(new RunCSVProfile(LEFT_TO_RIGHT_L, LEFT_TO_RIGHT_R));

                        } else {
                            System.out.println("Don't know which side");
                            addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                        }
                        break;

                    case MIDDLE:
                        System.out.println("MIDDLE STARTING CONFIGURATION");
                        //if(switchNear == MatchData.)
                        break;

                    case RIGHT:
                        System.out.println("RIGHT STARTING CONFIGURATION");

                        if(switchNear == MatchData.OwnedSide.RIGHT) {
                            addSequential(new RunCSVProfile(RIGHT_TO_RIGHT_L, RIGHT_TO_RIGHT_R));
                        } else {
                            addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                        }
                        break;
                }

                /* After reaching destination, outtake the cube! :) */
                addSequential(new OuttakeWheels(1));

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
