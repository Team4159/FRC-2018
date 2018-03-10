package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.subsystems.CubeHolder;
import frc.team4159.robot.subsystems.Superstructure;


public class LiftUp extends Command {

    CubeHolder cubeHolder = Superstructure.getInstance().getCubeHolder();

    public LiftUp() {
        requires(Superstructure.cubeHolder);
    }

    @Override
    protected void initialize() {
        cubeHolder.updatePosition(3200);
    }

    @Override
    protected void execute() {
        cubeHolder.move();
    }


    @Override
    protected boolean isFinished() {
        return cubeHolder.getLiftPosition() >= 3000 && cubeHolder.getLiftPosition() <= 3400;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
