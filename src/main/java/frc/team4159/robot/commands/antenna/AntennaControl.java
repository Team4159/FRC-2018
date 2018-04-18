package frc.team4159.robot.commands.antenna;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.OI;
import frc.team4159.robot.subsystems.Antenna;
import frc.team4159.robot.subsystems.CubeHolder;
import frc.team4159.robot.subsystems.Superstructure;

public class AntennaControl extends Command {

    private Antenna antenna;
    private CubeHolder cubeHolder;
    private OI oi;

    public AntennaControl() {
        antenna = Superstructure.getInstance().getAntenna();
        cubeHolder = Superstructure.getInstance().getCubeHolder();
        oi = OI.getInstance();
        requires(antenna);
    }

    @Override
    protected void execute() {

        if(antenna.getRawMode()) {


        } else {
            int liftPosition = cubeHolder.getLeftPosition();
            if(liftPosition > 1400 && liftPosition < 2800) {
                antenna.deployAntenna();
            } else {
                antenna.retractAntenna();
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
