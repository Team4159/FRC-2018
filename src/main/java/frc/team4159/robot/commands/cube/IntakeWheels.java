package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team4159.robot.subsystems.CubeHolder;
import frc.team4159.robot.subsystems.Superstructure;


public class IntakeWheels extends TimedCommand {

    CubeHolder cubeHolder = Superstructure.getInstance().getCubeHolder();

    public IntakeWheels(double duration) {
        super(duration);
    }

    @Override
    protected void execute() {
        cubeHolder.intake();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        cubeHolder.stopFlywheels();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
