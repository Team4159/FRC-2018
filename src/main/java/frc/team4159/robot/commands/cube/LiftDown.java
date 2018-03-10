package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.subsystems.CubeHolder;
import frc.team4159.robot.subsystems.Superstructure;

public class LiftDown extends Command {

    private CubeHolder cubeHolder = Superstructure.getInstance().getCubeHolder();

    public LiftDown() {
       requires(Superstructure.cubeHolder);
    }

    @Override
    protected void initialize() {
        cubeHolder.updatePosition(0);
    }


    @Override
    protected void execute() {
        cubeHolder.move();
    }

    @Override
    protected boolean isFinished() {
        return cubeHolder.getLiftPosition() > -200 && cubeHolder.getLiftPosition() < 200;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
