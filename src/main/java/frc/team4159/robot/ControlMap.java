package frc.team4159.robot;

/* Constants for joystick ports and button mappings to be implemented by OI.java */

public interface ControlMap {

    /* JOYSTICK PORTS */
    int LEFT_STICK = 0;
    int RIGHT_STICK = 1;
    int SECONDARY_STICK = 2;
    int TEST_STICK = 3;

    /* JOYSTICK BUTTON MAPPINGS */

    /* Drivetrain Buttons */

    /*
    int LEFT_90 = 4;
    int RIGHT_90 = 5;
    int FRONT_0 = 3;
    int BACK_180 = 2;
    */

    /* Intake Buttons */
    int INTAKE = 2;
    int OUTTAKE = 3;
    int TOGGLE_RAW_LIFT = 10;
    int RESET_LIFT_ENCODER = 11;
    int SWITCH = 4;
    int GROUND = 5;

    /* Test joy buttons */
    int SELECTOR = 8;
    int OPTION = 9;
}
