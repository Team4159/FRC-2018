package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import frc.team4159.robot.Robot;
import frc.team4159.robot.commands.cube.LiftUp;
import frc.team4159.robot.commands.cube.ResetLiftTopPosition;
import frc.team4159.robot.commands.cube.RunLift;
import frc.team4159.robot.commands.drive.RunCSVProfile;
import frc.team4159.robot.commands.led.SolidLED;
import static frc.team4159.robot.commands.auto.TrajectoryCSV.*;

/*
 * This CommandGroup handles the initial delay and switching between auto actions based on starting position
 */

public class Auto extends CommandGroup {

    public Auto() {

        Robot robot = Robot.getInstance();

        /*
         *  Get auto delay and starting position from SmartDashboard
         */
        double delay = robot.getAutoDelay();
        String startingPosition = robot.getStartingPosition();

        /*
         * Under all auto circumstances, the robot will turn on its LED rings for 15 seconds, update the lift position
         * for 15 seconds, reset the lift encoder, set the lift to switch height, and wait a delay (default 0 seconds)
         */
        addParallel(new SolidLED());
        addParallel(new RunLift());
        addSequential(new ResetLiftTopPosition());
        addSequential(new LiftUp());
        addSequential(new WaitCommand(delay));

        switch (startingPosition) {
            case "LEFT":
                addSequential(new LeftAuto());
                break;
            case "MIDDLE":
                addSequential(new MiddleAuto());
                break;
            case "RIGHT":
                addSequential(new RightAuto());
                break;
            default:
                addSequential(new RunCSVProfile(BASELINE_L, BASELINE_R));
                break;
        }
    }
}
