package frc.team4159.robot;

/* Constants for joystick ports and button mappings to be implemented by OI.java */

public interface ControlMap {

    /* JOYSTICK PORTS */
    int LEFT_STICK = 0;
    int RIGHT_STICK = 1;
    int SECONDARY_STICK = 2;

    /* JOYSTICK BUTTON MAPPINGS */

    /* Drivetrain Buttons */
    int LEFT_90 = 4;
    int RIGHT_90 = 5;
    int FRONT_0 = 3;
    int BACK_180 = 2;

    int REVERSE_CONTROLS = 2;

    /* Climber Buttons */
    int CLIMB_ENABLE = 7;
    int WINCH = 6;

    /* Intake Buttons */
    int INTAKE = 3;
    int OUTTAKE = 2;
    int TOGGLE_RAW_LIFT = 10;
    int RESET_LIFT_ENCODER = 11;
    int SWITCH = 4;
}
