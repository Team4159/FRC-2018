package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import frc.team4159.robot.Robot;
import frc.team4159.robot.commands.cube.RunLift;
import frc.team4159.robot.commands.drive.RunCSVProfile;
import frc.team4159.robot.commands.led.SolidLED;
import static frc.team4159.robot.commands.auto.TrajectoryCSV.*;

/*
 * This CommandGroup handles the initial delay and switching between auto actions
 */

public class Auto extends CommandGroup {

    public Auto() {

        Robot robot = Robot.getInstance();

        /* Get SmartDashboard values from Robot */
        double delay = robot.getAutoDelay();
        String startingPosition = robot.getStartingPosition();

        addParallel(new SolidLED());
        addParallel(new RunLift());
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
