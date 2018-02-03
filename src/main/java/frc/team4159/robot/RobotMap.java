package frc.team4159.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap {


    // CAN Talon SRX
    public static final int LEFT_TALON = 4;
    public static final int RIGHT_TALON = 3;

    // CAN Victor SPX
    public static final int LEFT_VICTOR = 3;
    public static final int RIGHT_VICTOR = 4;
    public static final int CLIMB_VICTOR = 10;

    public static final int PROTOTYPE_MOTOR_LEFT = 5;
    public static final int PROTOTYPE_MOTOR_RIGHT = 6;
    public static final int PROTOTYPE_LIFTER = 1;

    // Double Solenoids
    public static final int SOLENOID_A = 0;
    public static final int SOLENOID_B = 1;

}
