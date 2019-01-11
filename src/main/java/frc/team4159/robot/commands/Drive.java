package frc.team4159.robot.command;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.OI;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Drivetrain;

public class Drive extends Command {

    private OI oi;
    private Drivetrain drivetrain = Drivetrain.getInstance();

    public Drive(){
        oi = OI.getInstance();
        requires(drivetrain);
    }

    public void execute(){
        drivetrain.setSpeed(oi.getLeftJoy(), oi.getRightJoy());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}