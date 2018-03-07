package frc.team4159.robot;

/* Constants for joystick ports and button mappings to be implemented by OI.java */

public interface ControlMap {

    /* Joystick ports */
    int LEFT_STICK = 0;
    int RIGHT_STICK = 1;
    int SECONDARY_STICK = 2;

    /* Button mappings */
    //Drivetrain
    int LEFT_90 = 4;
    int RIGHT_90 = 5;
    int CW_180 = 3;
    int CCW_180 = 2;

    int REVERSE_CONTROLS = 2;

    //Climber
    int CLIMB_TOP = 10;
    int CLIMB_UP = 8;
    int CLIMB_DOWN = 9;
    int WINCH = 7;

    //Intake
    int INTAKE = 3;
    int OUTTAKE = 2;
    int TOGGLE_RAW_LIFTER = 6; //Switched lifter to raw power input
}
