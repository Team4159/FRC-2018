package frc.team4159.robot.subsystems;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4159.robot.commands.test.TestPrototype;

import static frc.team4159.robot.RobotMap.*;

public class Prototype extends Subsystem {

    private static Prototype instance;

    private DoubleSolenoid piston = new DoubleSolenoid(SOLENOID_A, SOLENOID_B);

    public static Prototype getInstance() {
        if(instance == null)
            instance = new Prototype();
        return instance;
    }

    public void operatePiston(boolean isExtended){
        if(isExtended)
            piston.set(DoubleSolenoid.Value.kForward);
        else
            piston.set(DoubleSolenoid.Value.kReverse);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new TestPrototype());
    }
}

