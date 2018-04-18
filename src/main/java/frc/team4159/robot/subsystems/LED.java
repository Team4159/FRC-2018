package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import static frc.team4159.robot.RobotMap.LED_BIG;
import static frc.team4159.robot.RobotMap.LED_SMALL;

public class LED extends Subsystem {

    private static LED instance;

    public static LED getInstance() {
        if (instance == null)
            instance = new LED();
        return instance;
    }

    private Solenoid smallRing;
    private Solenoid bigRing;

    private LED() {
        smallRing = new Solenoid(LED_SMALL);
        bigRing = new Solenoid(LED_BIG);
    }

    public void enableLEDRings() {
        smallRing.set(true);
        bigRing.set(true);
    }

    public void disableLEDRings() {
        smallRing.set(false);
        bigRing.set(false);
    }

    public void initDefaultCommand() {
        // Intentionally left blank
    }
}
