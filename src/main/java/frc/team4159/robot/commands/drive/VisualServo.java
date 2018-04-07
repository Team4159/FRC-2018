package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Drivetrain;

public class VisualServo extends Command implements Runnable {

    private Drivetrain drivetrain;
    private Notifier notifier;

    public VisualServo() {
        drivetrain = Robot.getDrivetrain();
        notifier = new Notifier(this);
        requires(drivetrain);

    }

    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
        notifier.startPeriodic(0.01);
    }

    @Override
    public void run() {

    }

    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    protected void end() {
        notifier.stop();
        drivetrain.stop();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
