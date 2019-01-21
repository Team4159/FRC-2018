package frc.team4159.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.OI;
import frc.team4159.robot.subsystems.Drivetrain;
import frc.team4159.robot.subsystems.Hatch;

public class HatchControl extends Command {

    private OI oi;
    private Hatch hatch = Hatch.getInstance();

    public HatchControl(){
        oi = OI.getInstance();
        requires(hatch);
    }

    public void execute(){

        if(oi.getIn()) {
            hatch.in();
        }

        if(oi.getOut()) {
            hatch.out();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
