package frc.team4159.robot;

/*
 * Robot-wide constants that are not roborio or joystick related i.e. configurations, scale factors
 * */

public interface Constants {

    // TODO: Test the effects of lowering the pid timeout

    // Timeout value in ms. If nonzero, function will wait for config success and report an error if it times out. If zero, no blocking or checking is performed.
    int TIMEOUT_MS = 3;

    // Nominal (minimal) percent output
    int NOMINAL_OUT_PERCENT = 0;

    // Desired peak percent output
    int PEAK_OUT_PERCENT = 1;

    // For SRX mag encoder. AKA ticks per revolution or pulses per revolution
    int UNITS_PER_REV = 4096;

    // Distance between wheels of two sides
    double WHEELBASE_WIDTH = .6566535; // 25.8525 inches to meters.

    // The diameter of our robot wheels
    double WHEEL_DIAMETER = 0.1016; // 4 inches to meters

}
