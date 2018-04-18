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

    // PCM IDs
    int LED_SMALL = 1;
    int LED_BIG = 2;
    int ANTENNA_FORWARD = 3;
    int ANTENNA_REVERSE = 4;
    int CLAW_FORWARD = 6;
    int CLAW_REVERSE = 7;

    // Digital Input ports
    int HALL_SENSOR = 0;
    int LIMIT_SWITCH = 1;

    // Pneumatics Control Module CAN ID
    int PCM = 0;

    /* PRACTICE BOT */
    /*
    // CAN Talon SRX
    int LEFT_TALON = 6;
    int RIGHT_TALON = 1;
    int LIFT_TALON = 2;
    int CLIMB_TALON = 5;

    // CAN Victor SPX
    int LEFT_DRIVE_VICTOR = 6;
    int RIGHT_DRIVE_VICTOR = 2;

    // Victor SP PWM ports
    int INTAKE_VICTOR = 0;
    int CLIMB_VICTOR = 1;

    // PCM IDs
    int LED_SMALL = 1;
    int LED_BIG = 2;
    int ANTENNA_FORWARD = 3;
    int ANTENNA_REVERSE = 4;
    int CLAW_FORWARD = 6;
    int CLAW_REVERSE = 7;

    // Digital Input ports
    int HALL_SENSOR = 0;
    int LIMIT_SWITCH = 1;

    // Pneumatics Control Module CAN ID
    int PCM = 0;
    */

}
