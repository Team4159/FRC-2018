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

    private static CubeHolder cubeHolder;
    public static LED led;
    private static Compressor compressor;

    private Superstructure() {

        cubeHolder = CubeHolder.getInstance();
        led = LED.getInstance();

        compressor = new Compressor(PCM);
        compressor.start();

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

    public void disableCompressor() {
        compressor.stop();
    }

    public void enableCompressor() {
        compressor.start();
    }

}
