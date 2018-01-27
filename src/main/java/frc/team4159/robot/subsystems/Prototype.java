package frc.team4159.robot.subsystems;


import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4159.robot.commands.test.TestPrototype;

public class Prototype extends Subsystem {

    private static Prototype instance;

    public static Prototype getInstance() {
        if(instance == null)
            instance = new Prototype();
        return instance;
    }

    public void initDefaultCommand() {
        setDefaultCommand(new TestPrototype());
    }
}

