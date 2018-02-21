package frc.team4159.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public interface RobotMap {

    /* COMPETITION BOT */

    // CAN Talon SRX
    int LEFT_TALON = 7;
    int RIGHT_TALON = 9;
    int LIFT_TALON = 8;
    int CLIMB_TALON = 10;

    // CAN Victor SPX
    int LEFT_DRIVE_VICTOR = 1;
    int RIGHT_DRIVE_VICTOR = 3;

    // Victor SP PWM ports
    int INTAKE_VICTOR = 0;
    int CLIMB_VICTOR = 1;

    // Double Solenoid PCM IDs
    int FORWARD_CHANNEL = 6;
    int REVERSE_CHANNEL = 7;

    // Pneumatics Control Module CAN ID
    int PCM = 0;

    /* PRACTICE BOT */
    /*
        // CAN Talon SRX
    int LEFT_TALON = 4;
    int RIGHT_TALON = 3;
    int LIFT_TALON = 2;
    int CLIMB_TALON = 1;

    // CAN Victor SPX
    int LEFT_DRIVE_VICTOR = 3;
    int RIGHT_DRIVE_VICTOR = 4;
    int CLIMB_VICTOR = 1;

    // Victor SP PWM ports
    int LEFT_CUBE_VICTOR = 0;
    int RIGHT_CUBE_VICTOR = 1;

    // Double Solenoid PCM IDs
    int FORWARD_CHANNEL = 6;
    int REVERSE_CHANNEL = 7;

    // Pneumatics Control Module CAN ID
    int PCM = 0;

     */

}
