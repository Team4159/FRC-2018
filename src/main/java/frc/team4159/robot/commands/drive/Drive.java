package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

import frc.team4159.robot.OI;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Drivetrain;

public class Drive extends Command{

    private Drivetrain drivetrain;
    private OI oi;

    public Drive() {
        drivetrain = Robot.getDrivetrain();
        oi = OI.getInstance();
        requires(drivetrain);
    }

    /**
     * Called repeatedly when this Command is scheduled to run
     */
    @Override
    protected void execute() {

        if(oi.reverseControls() && DriverStation.getInstance().isEnabled()) {
            drivetrain.reverseControls();
        }

        /*
        if(Robot.oi.left90Button()) {
            drivetrain.turnToAngle(-90.0f);

        } else if(Robot.oi.right90Button()) {
            drivetrain.turnToAngle(90.0f);

        } else if(Robot.oi.front0Button()) {
            drivetrain.turnToAngle(0.0f);

        } else if(Robot.oi.back180Button()) {
            drivetrain.turnToAngle(180.0f);
        }
        */

        if(oi.driveStraightButton()) {
            // Take average of both joysticks
            double magnitude = (oi.getLeftY() + oi.getRightY()) / 2;
            drivetrain.driveStraight(magnitude);

        } else {
            drivetrain.disableTurnControl();
            drivetrain.setRawOutput(oi.getLeftY(), oi.getRightY());

        }

        drivetrain.logDashboard();

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
