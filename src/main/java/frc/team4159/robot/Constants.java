package frc.team4159.robot;

/*
 * Robot-wide constants that are not roborio or joystick related i.e. configurations, scale factors
 * */

public class Constants {

    // TODO: Test the effects of lowering the pid timeout

    // Timeout value in ms. If nonzero, function will wait for config success and report an error if it times out. If zero, no blocking or checking is performed.
    public static final int TIMEOUT_MS = 10;

    // Nominal (minimal) percent output
    public static final int NOMINAL_OUT_PERCENT = 0;

    // Desired peak output percentage
    public static final int PEAK_OUT_PERCENT = 1;

    public static final int UNITS_PER_REV = 4096;

}
