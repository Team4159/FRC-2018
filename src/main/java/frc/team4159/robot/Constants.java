package frc.team4159.robot;


/* Any other constants that are not roborio or joystick related i.e. scale factors, pid constants
* Store multiple bot constants here and make sure to comment them out when not in use
* */

/*
 * Robot-wide constants that are not roborio or joystick related i.e. configurations, scale factors
 * */


public class Constants {
    	/** which Talon on CANBus*/
    public static final int kTalonID = 0;

    /**
     * How many sensor units per rotation.
     * Using CTRE Magnetic Encoder.
     * @link https://github.com/CrossTheRoadElec/Phoenix-Documentation#what-are-the-units-of-my-sensor
     */
    public static final double kSensorUnitsPerRotation = 4096;

    /**
     * Which PID slot to pull gains from.  Starting 2018, you can choose
     * from 0,1,2 or 3.  Only the first two (0,1) are visible in web-based configuration.
     */
    public static final int kSlotIdx = 0;

    /**
     * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops.
     * For now we just want the primary one.
     */
    public static final int kPIDLoopIdx = 0;
    /**
     * set to zero to skip waiting for confirmation, set to nonzero to wait
     * and report to DS if action fails.
     */
    public static final int kTimeoutMs = 10;

    /**
     * Base trajectory period to add to each individual
     * trajectory point's unique duration.  This can be set
     * to any value within [0,255]ms.
     */
    public static final int kBaseTrajPeriodMs = 0;

    /**
     * Motor deadband, set to 1%.
     */
    public static final double kNeutralDeadband  = 0.01;


    public static final int numTraPts = 185;
    /* Practice bot */

    // TODO: Test the effects of lowering the pid timeout

    // Timeout value in ms. If nonzero, function will wait for config success and report an error if it times out. If zero, no blocking or checking is performed.
    public static final int TIMEOUT_MS = 10;

    // Nominal (minimal) percent output
    public static final int NOMINAL_OUT_PERCENT = 0;

    // Desired peak output percentage
    public static final int PEAK_OUT_PERCENT = 1;

    // For SRX mag encoder. Aka ticks per revolution or pulses per revolution
    public static final int UNITS_PER_REV = 4096;

    public static final double WHEELBASE_WIDTH = 0.6566535; // 25.8525 inches to meters

    public static final double WHEEL_DIAMETER = 0.1016; // 4 inches to meters

}
