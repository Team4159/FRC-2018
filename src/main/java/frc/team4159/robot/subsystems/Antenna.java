package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4159.robot.commands.antenna.AntennaControl;

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
    private boolean rawMode;
    private boolean deployed;

    private Antenna() {
        antennaSolenoid = new DoubleSolenoid(ANTENNA_FORWARD, ANTENNA_REVERSE);
        rawMode = false;
        deployed = false;
    }

    public void deployAntenna() {
        deployed = true;
        antennaSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void retractAntenna() {
        deployed = false;
        antennaSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void setRawMode() {
        rawMode = false;
    }

    public void setAutoMode() {
        rawMode = true;
    }

    public boolean getRawMode() {
        return rawMode;
    }

    /**
     * Intentionally left blank
     */
    public void initDefaultCommand() {
        setDefaultCommand(new AntennaControl());
    }
}

