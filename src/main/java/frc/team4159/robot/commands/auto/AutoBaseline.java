package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4159.robot.commands.drive.DriveStraight;

/* Autonomous command to cross the baseline */

class AutoBaseline extends CommandGroup {

    AutoBaseline() {

        // Distance in feet from alliance wall to baseline + 5
        final double distance = 15;
        addSequential(new DriveStraight(distance));

    }
}
