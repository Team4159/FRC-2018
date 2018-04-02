package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4159.robot.commands.cube.LiftUp;
import frc.team4159.robot.commands.cube.OuttakeWheels;
import frc.team4159.robot.commands.cube.ResetLiftTopPosition;
import frc.team4159.robot.commands.cube.RunLift;
import frc.team4159.robot.commands.drive.DriveSlow;
import frc.team4159.robot.commands.drive.RunCSVProfile;
import frc.team4159.robot.commands.led.SolidLED;

import static frc.team4159.robot.util.TrajectoryCSV.*;

class MiddleRightAuto extends CommandGroup {

    MiddleRightAuto() {
        addParallel(new SolidLED());
        addParallel(new RunLift());
        addSequential(new ResetLiftTopPosition());
        addSequential(new LiftUp());
        addSequential(new RunCSVProfile(MID_TO_RIGHT_L, MID_TO_RIGHT_R));
        addSequential(new DriveSlow());
        addSequential(new OuttakeWheels(3));
    }
}
