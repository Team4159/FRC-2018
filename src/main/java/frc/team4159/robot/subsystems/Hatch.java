package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4159.robot.commands.HatchControl;

public class Hatch extends Subsystem {

    private static Hatch instance;

    public static Hatch getInstance(){
        if(instance == null)
            instance = new Hatch();
        return instance;

    }

    private DoubleSolenoid solenoid = new DoubleSolenoid(6,7);

    public void in() {
        solenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void out() {
        solenoid.set(DoubleSolenoid.Value.kReverse);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new HatchControl());
    }

}
