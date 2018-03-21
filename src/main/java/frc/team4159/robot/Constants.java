package frc.team4159.robot;

/*
 * Robot-wide constants that are not roborio or joystick related i.e. configurations, scale factors
 */

public interface Constants {

    // Timeout value in ms. If nonzero, function will wait for config success and report an error if it times out. If zero, no blocking or checking is performed.
    int TIMEOUT_MS = 5;

    // Nominal (minimal) percent output
    int NOMINAL_OUT_PERCENT = 0;

    // Desired peak output percentage
    int PEAK_OUT_PERCENT = 1;

    // For SRX mag encoder. AKA ticks per revolution or pulses per revolution
    int UNITS_PER_REV = 4096;

    // Distance between wheels of two sides
    double WHEELBASE_WIDTH = .6566535; // 25.8525 inches to meters.

    // 4 inches to feet
    double WHEEL_DIAMETER = 4/12; // 0.1016

    double WHEEL_CIRCUMFERANCE = WHEEL_DIAMETER * Math.PI;

}
