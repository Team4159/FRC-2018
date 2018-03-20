package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team4159.robot.subsystems.CubeHolder;
import frc.team4159.robot.subsystems.Superstructure;


public class OuttakeWheels extends TimedCommand {

    private CubeHolder cubeHolder = Superstructure.getInstance().getCubeHolder();

    public OuttakeWheels(double duration) {
        super(duration);
    }

    @Override
    protected void execute() {
        cubeHolder.outtake();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }

}
