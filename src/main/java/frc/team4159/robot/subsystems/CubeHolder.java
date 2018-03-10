package frc.team4159.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.commands.cube.LiftCube;

import static frc.team4159.robot.Constants.NOMINAL_OUT_PERCENT;
import static frc.team4159.robot.Constants.PEAK_OUT_PERCENT;
import static frc.team4159.robot.Constants.TIMEOUT_MS;
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

    private final int PIDIDX = 0;
    private final double MAX_SPEED = 50.0; // encoder units per cycle TODO: Test and change as necessary
    private final double kF = 0.0;
    private final double kP = 1.5;
    private final double kI = 0.0;
    private final double kD = 0.0;

    private double targetPosition; // In encoder units. 4096 per revolution.
    private final int upperEncoderLimit = 3500-200; // Lifter is up
    private final int lowerEncoderLimit = 0; // Lifter is down
    private final int switchHeight = 0;
    //TODO: this is a random number; determine switch height
    private boolean rawMode = true; //Switches between raw input (true) and position controlled (false)

    private CubeHolder() {

        intakeVictor = new VictorSP(INTAKE_VICTOR);
        liftTalon = new TalonSRX(LIFT_TALON);
        pistons = new DoubleSolenoid(FORWARD_CHANNEL, REVERSE_CHANNEL);

        targetPosition = lowerEncoderLimit; // Initial target value in starting configuration (raised)

        configureSensors();
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
        liftTalon.setSelectedSensorPosition(lowerEncoderLimit, PIDIDX, TIMEOUT_MS);
    }

    private void limitCurrent() {

        /* Sets and limits the peak and continuous current for both sides of motors to prevent brownouts */

        final int PEAK_CURRENT = 10; // Amps
        final int CONTINUOUS_CURRENT = 7; // Amps
        final int PEAK_CURRENT_DURATION = 200; // ms
        final int PEAK_CURRENT_TIMEOUT = 20; // ms

        liftTalon.configPeakCurrentLimit(PEAK_CURRENT,TIMEOUT_MS);
        liftTalon.configPeakCurrentDuration(PEAK_CURRENT_DURATION, PEAK_CURRENT_TIMEOUT);
        liftTalon.configContinuousCurrentLimit(CONTINUOUS_CURRENT, TIMEOUT_MS);
        liftTalon.enableCurrentLimit(true);

    }

    //FLYWHEELS

    /* Runs wheels inwards to intake the cube */
    public void intake() {
        intakeVictor.set(1);
    }

    /* Runs wheels outwards to outtake the cube */
    public void outtake() {
        intakeVictor.set(-1);
    }

    /* Stops running the wheels */
    public void stopFlywheels() {
        intakeVictor.set(0);
    }

    /* Opens the claw */
    public void open() {
        pistons.set(DoubleSolenoid.Value.kForward);
    }

    /* Closes the claw */
    public void close() {
        pistons.set(DoubleSolenoid.Value.kReverse);
    }

    public void setRawLift(double value) {
        liftTalon.set(ControlMode.PercentOutput, value);
    }

    public void move() {

        // Limits to avoid hitting into hardstop
        if(targetPosition < lowerEncoderLimit)
            targetPosition = lowerEncoderLimit;
        if(targetPosition > upperEncoderLimit)
            targetPosition = upperEncoderLimit;

        liftTalon.set(ControlMode.Position, targetPosition);
    }

    public int getLiftPosition() {
        return liftTalon.getSelectedSensorPosition(PIDIDX);
    }

    public boolean getRawMode(){
        return rawMode;
    }

    public void toggleLifterRawMode(){
        rawMode = !rawMode;

    }

    public void resetLiftEncoder(){
        liftTalon.setSelectedSensorPosition(lowerEncoderLimit, PIDIDX, TIMEOUT_MS);
    }

    /* Updates target position to a value from -MAX_SPEED to +MAX_SPEED according to the joystick value */
    public void updatePosition(double value) {
        value *= MAX_SPEED;
        targetPosition += value;
    }

    //H: made these separate functions to keep constants at top of class, feel free to change?
    public void setToSwitch(){
        targetPosition = switchHeight;
    }
    public void setToBottom(){
        targetPosition = lowerEncoderLimit;
    }

    public void logDashboard() {
        SmartDashboard.putNumber("lift position", liftTalon.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("lift target", targetPosition);
        SmartDashboard.putBoolean("Lift Mode", rawMode);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new LiftCube());
    }
}
