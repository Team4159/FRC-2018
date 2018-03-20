package frc.team4159.robot.subsystems;

/*
* All subsystems and other hardware other than the Drivetrain are initialized here.
*/

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

import static frc.team4159.robot.RobotMap.PCM;

public class Superstructure {

    private static Superstructure instance;

    public static Superstructure getInstance() {
        if (instance == null)
            instance = new Superstructure();
        return instance;
    }

    public static Climber climber;
    public static CubeHolder cubeHolder;
    public static LED led;

    private PowerDistributionPanel pdp;
    private Compressor compressor;

    private Superstructure() {
        climber = Climber.getInstance();
        cubeHolder = CubeHolder.getInstance();
        led = LED.getInstance();

        pdp = new PowerDistributionPanel();
        compressor = new Compressor(PCM);
        compressor.setClosedLoopControl(true);
    }

    public Climber getClimber() {
        return climber;
    }

    public CubeHolder getCubeHolder() {
        return cubeHolder;
    }

    public LED getLED() {
        return led;
    }

    public double getPDPCurrent(int channel) {
        return pdp.getCurrent(channel);
    }

    public double getTotalCurrent() {
        return pdp.getTotalCurrent();
    }

    public double getVoltage() {
        return pdp.getVoltage();
    }

    public double compressorCurrent() {
        return compressor.getCompressorCurrent();
    }

    public boolean compressorEnabled() {
        return compressor.enabled();
    }

    public boolean pressureSwitch() {
        return compressor.getPressureSwitchValue();
    }

}
