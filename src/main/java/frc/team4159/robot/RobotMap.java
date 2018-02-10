package frc.team4159.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public interface RobotMap {

    // CAN Talon SRX
    int LEFT_TALON = 4;
    int RIGHT_TALON = 3;
    int LIFT_TALON = 2;
    int CLIMB_TALON = 1;

    // CAN Victor SPX
    int LEFT_DRIVE_VICTOR = 3;
    int RIGHT_DRIVE_VICTOR = 4;
    int LEFT_CUBE_VICTOR = 1;
    int RIGHT_CUBE_VICTOR = 2;

    // Double Solenoid PCM IDs
    int LEFT_FORWARD = 0;
    int LEFT_REVERSE = 1;
    int RIGHT_FORWARD = 2;
    int RIGHT_REVERSE = 3;

    // Pneumatics Control Module CAN ID
    int PCM = 0;

}
