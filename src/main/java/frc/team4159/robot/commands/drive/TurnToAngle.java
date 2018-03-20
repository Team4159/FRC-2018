package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Drivetrain;


public class TurnToAngle extends Command{

    private Drivetrain drivetrain = Robot.getDrivetrain();
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
        drivetrain.turnToAngle(angle);
    }

    @Override
    protected boolean isFinished() {
        return drivetrain.turnOnTarget();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted(){
        end();
    }
}
