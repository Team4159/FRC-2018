package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4159.robot.Robot;
import frc.team4159.robot.commands.drive.RunCSVProfile;

import static frc.team4159.robot.commands.auto.TrajectoryCSV.*;


public class Backwards extends CommandGroup {

    public Backwards() {
        //Robot.getDrivetrain().reverseControls();
        addSequential(new RunCSVProfile(MID_TO_LEFT_L, MID_TO_LEFT_R));
    }
}
