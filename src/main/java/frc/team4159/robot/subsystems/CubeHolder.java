package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.commands.cube.LiftCube;

import static frc.team4159.robot.Constants.*;
import static frc.team4159.robot.RobotMap.*;

public class CubeHolder extends Subsystem {

    private static CubeHolder instance;

    public static CubeHolder getInstance() {
        if(instance == null)
            instance = new CubeHolder();
        return instance;
    }

    private TalonSRX liftTalon;
    private VictorSP intakeVictor;
    private DoubleSolenoid pistons;
    private DigitalInput limitSwitch;

    private final int PIDIDX = 0;
    private final double kF = 0.0;
    private final double kP = 0.5;
    private final double kI = 0.0;
    private final double kD = 0.0;

    private double targetPosition; // In encoder units. 4096 per revolution.
    private boolean rawMode;

    private CubeHolder() {

        intakeVictor = new VictorSP(INTAKE_VICTOR);
        liftTalon = new TalonSRX(LIFT_TALON);
        pistons = new DoubleSolenoid(FORWARD_CHANNEL, REVERSE_CHANNEL);
        limitSwitch = new DigitalInput(LIMIT_SWITCH);

        rawMode = true;

        targetPosition = UPPER_LIFTER_LIMIT; // Initial target value in starting configuration (raised)

        configureSensors();
        limitCurrent();
    }

    public void setLiftEncoderValue(int value) {
        liftTalon.setSelectedSensorPosition(value, PIDIDX, TIMEOUT_MS);
    }

    private void configureSensors() {

        final int SLOTIDX = 0;

        liftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, PIDIDX, TIMEOUT_MS);
        liftTalon.setSensorPhase(true);
        liftTalon.configNominalOutputForward(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        liftTalon.configNominalOutputReverse(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        liftTalon.configPeakOutputForward(PEAK_OUT_PERCENT, TIMEOUT_MS);
        liftTalon.configPeakOutputReverse(-PEAK_OUT_PERCENT, TIMEOUT_MS);

        // TODO: Figure out allowable closed loop error units and value
        liftTalon.configAllowableClosedloopError(SLOTIDX, 0, TIMEOUT_MS);

        liftTalon.config_kF(SLOTIDX, kF, TIMEOUT_MS);
        liftTalon.config_kP(SLOTIDX, kP, TIMEOUT_MS);
        liftTalon.config_kI(SLOTIDX, kI, TIMEOUT_MS);
        liftTalon.config_kD(SLOTIDX, kD, TIMEOUT_MS);

        // Sets initial encoder value in AUTONOMOUS starting configuration (raised)
        liftTalon.setSelectedSensorPosition(UPPER_LIFTER_LIMIT, PIDIDX, TIMEOUT_MS);
    }

    private void limitCurrent() {

        /* Sets and limits the peak and continuous current for both sides of motors to prevent brownouts */

        final int PEAK_CURRENT = 7; // Amps
        final int CONTINUOUS_CURRENT = 5; // Amps
        final int PEAK_CURRENT_DURATION = 200; // ms
        final int PEAK_CURRENT_TIMEOUT = 20; // ms

        liftTalon.configPeakCurrentLimit(PEAK_CURRENT,TIMEOUT_MS);
        liftTalon.configPeakCurrentDuration(PEAK_CURRENT_DURATION, PEAK_CURRENT_TIMEOUT);
        liftTalon.configContinuousCurrentLimit(CONTINUOUS_CURRENT, TIMEOUT_MS);
        liftTalon.enableCurrentLimit(true);

    }

    /* Runs wheels inwards to intake the cube */
    public void intake() {
        intakeVictor.set(-1);
    }

    /* Runs wheels outwards to outtake the cube */
    public void outtake() {
        intakeVictor.set(1);
    }

    /* Stops running the wheels */
    public void stopFlywheels() {
        intakeVictor.set(0);
    }

    /* Opens the claw */
    public void open() {
        pistons.set(DoubleSolenoid.Value.kReverse);
    }

    /* Closes the claw */
    public void close() {
        pistons.set(DoubleSolenoid.Value.kForward);
    }

    public void setRawLift(double value) {
        if(limitSwitchPressed() && value < 0) {
            liftTalon.set(ControlMode.PercentOutput, 0);
        } else {
            liftTalon.set(ControlMode.PercentOutput, value);
        }
    }

    /**
     * Set lift talon to desired target
     */
    public void move() {

        // If limit switch is pressed, reset encoder and target pos to 0
        if(limitSwitchPressed()) {
            resetLiftEncoder();
        }

        // Limits to avoid hitting into hardstop
        if(targetPosition < LOWER_LIFTER_LIMIT)
            targetPosition = LOWER_LIFTER_LIMIT;
        if(targetPosition > UPPER_LIFTER_LIMIT)
            targetPosition = UPPER_LIFTER_LIMIT;

        liftTalon.set(ControlMode.Position, targetPosition);
    }

    /**
     * @return True if raw control mode is enabled
     */
    public boolean getRawMode() {
        return rawMode;
    }

    /**
     * Set raw mode to true or false
     */
    public void toggleLifterRawMode(){
        rawMode = !rawMode;
    }

    /**
     * Reset lift encoder to 0 and sets target position to 0
     */
    public void resetLiftEncoder() {
        liftTalon.setSelectedSensorPosition(LOWER_LIFTER_LIMIT, PIDIDX, TIMEOUT_MS);
        targetPosition = 0;
    }

    /**
     * @param target Setpoint in native encoder units (1 rev = 4096 units)
     */
    public void setTargetPosition(double target) {
        targetPosition = target;
    }

    /**
     *  Update target position to a value from -MAX_SPEED to +MAX_SPEED according to value
     *  @param value Joystick y-axis value from -1 to 1
     */
    public void updatePosition(double value) {

        double MAX_SPEED = 200.0;
        value *= MAX_SPEED;
        targetPosition += value;
    }

    /**
     * Set cube lifter to switch height
     */
    public void setToSwitch() {
        targetPosition = SWITCH_HEIGHT;
    }

    /**
     * Set cube lifter to ground height
     */
    public void setToBottom() {
        targetPosition = LOWER_LIFTER_LIMIT;
    }

    private boolean limitSwitchPressed() {
        return !limitSwitch.get();
    }

    /**
     * Log values to SmartDashboard
     */
    public void logDashboard() {

//        SmartDashboard.putNumber("lift position", liftTalon.getSelectedSensorPosition(0));
//        SmartDashboard.putNumber("lift target", targetPosition);
        if(rawMode) {
            SmartDashboard.putString("Lift mode", "RAW");
        } else {
            SmartDashboard.putString("Lift mode", "PID");
        }

        SmartDashboard.putBoolean("Limit Switch", limitSwitchPressed());

    }

    /**
     * Set default command
     */
    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new LiftCube());
    }
}
