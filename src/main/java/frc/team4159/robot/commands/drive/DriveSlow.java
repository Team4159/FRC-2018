package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Drivetrain;

public class DriveSlow extends TimedCommand {

    private Drivetrain drivetrain;

    public DriveSlow() {
        super(8);
        requires(Robot.drivetrain);
        drivetrain = Robot.getDrivetrain();
    }

    @Override
    protected void execute() {
        drivetrain.setRawOutput(0.2, 0.2);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        drivetrain.setRawOutput(0, 0);
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
