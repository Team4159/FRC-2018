package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4159.robot.commands.cube.LiftUp;
import frc.team4159.robot.commands.cube.OuttakeWheels;
import frc.team4159.robot.commands.cube.ResetLiftTopPosition;
import frc.team4159.robot.commands.cube.RunLift;
import frc.team4159.robot.commands.drive.RunMotionProfile;
import frc.team4159.robot.commands.drive.TimeDrive;
import frc.team4159.robot.commands.led.SolidLED;

import static frc.team4159.robot.util.TrajectoryCSV.BASELINE_SHORT_L;
import static frc.team4159.robot.util.TrajectoryCSV.BASELINE_SHORT_R;

class BaselineDropAuto extends CommandGroup {

    BaselineDropAuto() {
        addParallel(new SolidLED());
        addParallel(new RunLift());
        addSequential(new ResetLiftTopPosition());
        addSequential(new LiftUp());
        //addSequential(new RunMotionProfile(BASELINE_SHORT_L, BASELINE_SHORT_R));
        addSequential(new TimeDrive());
        addSequential(new OuttakeWheels(3));
    }
}
