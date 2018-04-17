package frc.team4159.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import static frc.team4159.robot.RobotMap.PCM;

/*
 * All subsystems and electrical hardware other than the Drivetrain are initialized here.
 */

public class Superstructure {

    private static Superstructure instance;

    public static Superstructure getInstance() {
        if (instance == null)
            instance = new Superstructure();
        return instance;
    }

    private static Climber climber;
    private static CubeHolder cubeHolder;
    public static LED led;
    private static Antenna antenna;
    private static Compressor compressor;

    private Superstructure() {

        climber = Climber.getInstance();
        cubeHolder = CubeHolder.getInstance();
        led = LED.getInstance();
        antenna = Antenna.getInstance();

        compressor = new Compressor(PCM);
        compressor.start();

    }

    /**
     * @return Climber Subsystem
     */
    public Climber getClimber() {
        return climber;
    }

    /**
     * @return CubeHolder Subsystem
     */
    public CubeHolder getCubeHolder() {
        return cubeHolder;
    }

    /**
     * @return LED Subsystem
     */
    public LED getLED() {
        return led;
    }

    public Antenna getAntenna() {
        return antenna;
    }

    public void disableCompressor() {
        compressor.stop();
    }

    public void enableCompressor() {
        compressor.start();
    }

}
