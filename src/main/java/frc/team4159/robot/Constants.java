package frc.team4159.robot;

/*
 * Robot-wide constants that are not roborio or joystick related i.e. configurations, scale factors
 */

public interface Constants {

    // Function will wait for config success and report an error if it times out
    int S_TIMEOUT = 10;  // on the fly
    int L_TIMEOUT = 100; // in constructors

    // Nominal (minimal) percent output
    int NOMINAL_OUT_PERCENT = 0;

    // Desired peak output percentage
    int PEAK_OUT_PERCENT = 1;

    // Max voltage applied to motor controllers. Lower than 12V to compensate for battery drop.
    double MAX_VOLTAGE = 10.0;

    // For SRX mag encoder. AKA ticks per revolution or pulses per revolution
    int UNITS_PER_REV = 4096;

    // Distance between wheels of two sides
    // double WHEELBASE_WIDTH = .6566535; // 25.8525 inches to meters.

    // 4 inches to feet
    double WHEEL_DIAMETER = 4/12; // 0.1016

    // Circumference = PI * diameter
    double WHEEL_CIRCUMFERANCE = WHEEL_DIAMETER * Math.PI;

    /*
     * Positions of the cube lifter in mag encoder units
     */
    int UPPER_LIFTER_LIMIT = 3000;
    int SWITCH_HEIGHT = 1800;
    int LOWER_LIFTER_LIMIT = 0;

}
