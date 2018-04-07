package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import static frc.team4159.robot.RobotMap.ANTENNA_FORWARD;
import static frc.team4159.robot.RobotMap.ANTENNA_REVERSE;

public class Antenna extends Subsystem {

    private static Antenna instance;

    public static Antenna getInstance() {
        if (instance == null)
            instance = new Antenna();
        return instance;
    }

    private DoubleSolenoid antennaSolenoid;

    private Antenna() {
        antennaSolenoid = new DoubleSolenoid(ANTENNA_FORWARD, ANTENNA_REVERSE);
    }

    public void deployAntenna() {
        antennaSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void retractAntenna() {
        antennaSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Intentionally left blank
     */
    public void initDefaultCommand() {
    }
}

