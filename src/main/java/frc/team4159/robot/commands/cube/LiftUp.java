package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team4159.robot.subsystems.CubeHolder;
import frc.team4159.robot.subsystems.Superstructure;


public class LiftUp extends InstantCommand {

    public LiftUp() {

        CubeHolder cubeHolder = Superstructure.getInstance().getCubeHolder();

        cubeHolder.setLiftEncoderValue(3300);
        cubeHolder.setTargetPosition(2700);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
