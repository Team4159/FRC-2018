package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4159.robot.commands.TestPiston;
import static frc.team4159.robot.RobotMap.WHATEVER;
import static frc.team4159.robot.RobotMap.WHATEVER2;


public class TestPneumatics extends Subsystem{
    DoubleSolenoid exampleDouble = new DoubleSolenoid(WHATEVER, WHATEVER2);
    public void initDefaultCommand() {
        setDefaultCommand(new TestPiston());
    }

    public void pistonIn(){
        exampleDouble.set(DoubleSolenoid.Value.kReverse);
    }

    public void pistonOut(){
        exampleDouble.set(DoubleSolenoid.Value.kForward);
    }
}
