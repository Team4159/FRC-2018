package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.team4159.robot.Robot;


public class TurnToAngle extends Command{

    private double angle;

    public TurnToAngle(double angle) {
        requires(Robot.drivetrain);
        this.angle = angle;
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.drivetrain.turnToAngle(angle);
    }

    @Override
    protected boolean isFinished() {
        return Robot.drivetrain.turnOnTarget();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted(){
        end();
    }
}
