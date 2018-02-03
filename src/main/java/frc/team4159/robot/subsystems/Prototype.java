package frc.team4159.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.sun.corba.se.impl.ior.iiop.MaxStreamFormatVersionComponentImpl;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4159.robot.commands.test.TestPrototype;

import static frc.team4159.robot.RobotMap.*;

public class Prototype extends Subsystem {

    private static Prototype instance;

    private DoubleSolenoid piston = new DoubleSolenoid(SOLENOID_A, SOLENOID_B);
    private VictorSPX leftMotor = new VictorSPX(PROTOTYPE_MOTOR_LEFT);
    private VictorSPX rightMotor = new VictorSPX(PROTOTYPE_MOTOR_RIGHT);
    private VictorSPX lifterMotor = new VictorSPX(PROTOTYPE_LIFTER);

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

    public void setIntake(double direction){
        leftMotor.set(ControlMode.PercentOutput, direction);
        rightMotor.set(ControlMode.PercentOutput,direction);
    }

    public void setLifter(double input){
        lifterMotor.set(ControlMode.PercentOutput,input*.5);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new TestPrototype());
    }
}

