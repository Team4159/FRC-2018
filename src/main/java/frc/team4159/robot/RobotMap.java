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

    // CAN Victor SPX
    int LEFT_DRIVE_VICTOR = 1;
    int RIGHT_DRIVE_VICTOR = 3;

    // Victor SP PWM ports
    int INTAKE_VICTOR = 0;

    // PCM IDs
    int LED_SMALL = 4;
    int LED_BIG = 5;
    int ANTENNA_FORWARD = 3;
    int ANTENNA_REVERSE = 2;
    int CLAW_FORWARD = 6;
    int CLAW_REVERSE = 7;

    // Digital Input ports
    int LIMIT_SWITCH = 1;

    // Pneumatics Control Module CAN ID
    int PCM = 0;

}
