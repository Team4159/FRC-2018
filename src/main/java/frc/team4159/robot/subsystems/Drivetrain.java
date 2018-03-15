package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.Constants;
import frc.team4159.robot.commands.drive.Drive;

import static frc.team4159.robot.Constants.*;
import static frc.team4159.robot.RobotMap.*;

public class Drivetrain extends Subsystem implements PIDOutput {

    private static Drivetrain instance;

    public static Drivetrain getInstance() {
        if(instance == null)
            instance = new Drivetrain();
        return instance;
    }

    /* Motor controllers and sensors */
    private TalonSRX leftTalon, rightTalon;
    private VictorSPX leftVictor, rightVictor;
    private AHRS navx;

    private PIDController turnController;
    private double angleSetpoint = 0;

    /* Drivetrain encoder PID constants */
    private final int MAX_SPEED = 5200; // Native units per 100ms
    private final int SLOTIDX = 0;
    private final int PIDIDX = 0;
    private final double kF_left = 0.196730769230769; // 1023 / 5200 where 5200 is our max speed
    private final double kP_left = 0.4092; // (10% * 1023) / 250 where 250 is our max error
    private final double kI_left = 0;
    private final double kD_left = 4.092; // kP * 10
    private final double kF_right = 0.0;
    private final double kP_right = 0.0;
    private final double kI_right = 0.0;
    private final double kD_right = 0.0;

    /* Stores state if controls should be reversed or not */
    private boolean reverse;

    /* NavX turning PID constants */
    private final double kP_turn = 5 * 0.01;
    private final double kI_turn = 0;
    private final double kD_turn = 0.05;
    private final double kF_turn = 0;
    private final double kToleranceDegrees = 2.0f;

    private final int MOTOR_OUTPUT_RANGE = 1;
    private final double NAVX_YAW_RANGE = 180.0f;

    private double rotateToAngleRate;

    private Drivetrain() {

        /* Invert left motors and set victors to follow talons */

        leftTalon = new TalonSRX(LEFT_TALON);
        leftVictor = new VictorSPX(LEFT_DRIVE_VICTOR);
        leftTalon.setInverted(true);
        leftVictor.setInverted(true);
        leftVictor.follow(leftTalon);

        rightTalon = new TalonSRX(RIGHT_TALON);
        rightVictor = new VictorSPX(RIGHT_DRIVE_VICTOR);
        rightTalon.setInverted(false);
        rightVictor.setInverted(false);
        rightVictor.follow(rightTalon);

        /* NavX is a 9-axis inertial/magnetic sensor and motion processor, plugged into the RoboRio's MXP port */
        try {
            navx = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
        }

        reverse = false;

        limitCurrent();
        configureSensors();

        LiveWindow.add(this);
    }

    private void configureSensors() {

        /* Configure SRX Mag encoder sensors on both sides, connected to the talon via the SRX data cable */

        leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PIDIDX, TIMEOUT_MS);
        leftTalon.setSensorPhase(false);
        leftTalon.configNominalOutputForward(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        leftTalon.configNominalOutputReverse(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        leftTalon.configPeakOutputForward(PEAK_OUT_PERCENT, TIMEOUT_MS);
        leftTalon.configPeakOutputReverse(-PEAK_OUT_PERCENT, TIMEOUT_MS);

        rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PIDIDX, TIMEOUT_MS);
        rightTalon.setSensorPhase(true); // Reverses encoder output to match with motor output
        rightTalon.configNominalOutputForward(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        rightTalon.configNominalOutputReverse(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        rightTalon.configPeakOutputForward(PEAK_OUT_PERCENT, TIMEOUT_MS);
        rightTalon.configPeakOutputReverse(-PEAK_OUT_PERCENT, TIMEOUT_MS);

        // TODO: Retune PIDF values for both sides of drivetrain

        leftTalon.config_kF(SLOTIDX, kF_left, TIMEOUT_MS);
        leftTalon.config_kP(SLOTIDX, kP_left, TIMEOUT_MS);
        leftTalon.config_kI(SLOTIDX, kI_left, TIMEOUT_MS);
        leftTalon.config_kD(SLOTIDX, kD_left, TIMEOUT_MS);

        rightTalon.config_kF(SLOTIDX, kF_right, TIMEOUT_MS);
        rightTalon.config_kP(SLOTIDX, kP_right, TIMEOUT_MS);
        rightTalon.config_kI(SLOTIDX, kI_right, TIMEOUT_MS);
        rightTalon.config_kD(SLOTIDX, kD_right, TIMEOUT_MS);

        // TODO: figure out the correct cruise velocity and acceleration
        leftTalon.configMotionAcceleration(0, TIMEOUT_MS);
        leftTalon.configMotionCruiseVelocity(0, TIMEOUT_MS);

        rightTalon.configMotionAcceleration(0, TIMEOUT_MS);
        rightTalon.configMotionAcceleration(0, TIMEOUT_MS);

        turnController = new PIDController(kP_turn, kI_turn, kD_turn, kF_turn, navx, this);
        turnController.setInputRange(-NAVX_YAW_RANGE, NAVX_YAW_RANGE);
        turnController.setOutputRange(-MOTOR_OUTPUT_RANGE, MOTOR_OUTPUT_RANGE);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
        turnController.disable();

        // Zero sensors
        leftTalon.setSelectedSensorPosition(0, PIDIDX, TIMEOUT_MS);
        rightTalon.setSelectedSensorPosition(0, PIDIDX, TIMEOUT_MS);
        zeroNavX();

    }

    public void setRawOutput(double leftPercent, double rightPercent){

        if(reverse) {
            leftTalon.set(ControlMode.PercentOutput, -rightPercent);
            rightTalon.set(ControlMode.PercentOutput, -leftPercent);
        } else {
            leftTalon.set(ControlMode.PercentOutput, leftPercent);
            rightTalon.set(ControlMode.PercentOutput, rightPercent);
        }

    }

    /* Changes state of reversed controls */
    public void reverseControls() {
        reverse = !reverse;
    }

    /* Turns to a specific angle relative to robot heading when first powered on */
    public void turnToAngle(double angle) {
        if(!turnController.isEnabled()) {
            turnController.setSetpoint(angle);
            angleSetpoint = angle;
            rotateToAngleRate = 0;
            turnController.enable();
        }
        setRawOutput(-rotateToAngleRate, rotateToAngleRate);
    }

    /* Drives straight on current heading given a speed */
    public void driveStraight(double magnitude) {
        if(!turnController.isEnabled()) {
            turnController.setSetpoint(navx.getYaw());
            rotateToAngleRate = 0;
            turnController.enable();
        }
        setRawOutput(magnitude + rotateToAngleRate, magnitude - rotateToAngleRate);
    }

    /* Returns true if error is less than tolerance */
    public boolean turnOnTarget() {
        return turnController.onTarget();
    }

    /* Stops running turning PID controller */
    public void disableTurnControl() {
        if(turnController.isEnabled())
            turnController.disable();
    }

    /* Stops running drive motors */
    public void stop() {
        leftTalon.set(ControlMode.PercentOutput, 0);
        rightTalon.set(ControlMode.PercentOutput, 0);
    }

    /* Sets left and right motors to a target velocity */
    public void setVelocity(double leftTarget, double rightTarget) { //Input inches in seconds
        //TODO: double check conversions
        double leftNativeVelocity = leftTarget*UNITS_PER_REV/(1000*WHEEL_CIRCUMFERENCE); //1000 milliseconds in a second
        double rightNativeVelocity = leftTarget*UNITS_PER_REV/(1000*WHEEL_CIRCUMFERENCE); //1000 milliseconds in a second
        leftTalon.set(ControlMode.Velocity, leftTarget); //Encoder units/ms
        rightTalon.set(ControlMode.Velocity, rightTarget);
    }

    /* Drives straight a certain amount of distance for auto */
    public void driveDistance(double distance) {
        //TODO convert distance to number of rotations
        double targetPos = distance * 10.0 * 4096;
        rightTalon.set(ControlMode.MotionMagic, targetPos);
        leftTalon.set(ControlMode.MotionMagic, targetPos);
    }

    public void driveStraightMotionMagic(double distance) {

    }

    public int getLeftEncoderPosition() {
        return leftTalon.getSelectedSensorPosition(PIDIDX);
    }

    public int getRightEncoderPosition() {
        return rightTalon.getSelectedSensorPosition(PIDIDX);
    }

    public double getHeadingDegrees() {
        return navx.getYaw();
    }

    public void zeroNavX() {
        navx.zeroYaw();
    }

    public void logDashboard() {
        SmartDashboard.putNumber("Current Angle", navx.getYaw());
        SmartDashboard.putNumber("Angle Error",turnController.getError());
        SmartDashboard.putNumber("Setpoint Angle", angleSetpoint);
    }

    private void limitCurrent() {

        /* Sets and limits the peak and continuous current for both sides of motors to prevent brownouts */

        final int PEAK_CURRENT = 20; // Amps
        final int CONTINUOUS_CURRENT = 15; // Amps
        final int PEAK_CURRENT_DURATION = 200; // ms
        final int PEAK_CURRENT_TIMEOUT = 20; // ms

        leftTalon.configPeakCurrentLimit(PEAK_CURRENT,TIMEOUT_MS);
        leftTalon.configPeakCurrentDuration(PEAK_CURRENT_DURATION, PEAK_CURRENT_TIMEOUT);
        leftTalon.configContinuousCurrentLimit(CONTINUOUS_CURRENT, TIMEOUT_MS);
        leftTalon.enableCurrentLimit(true);

        rightTalon.configPeakCurrentLimit(PEAK_CURRENT,TIMEOUT_MS);
        rightTalon.configPeakCurrentDuration(PEAK_CURRENT_DURATION, PEAK_CURRENT_TIMEOUT);
        rightTalon.configContinuousCurrentLimit(CONTINUOUS_CURRENT, TIMEOUT_MS);
        rightTalon.enableCurrentLimit(true);

    }

    /* Updates rotateToAngleRate from output given by turnController */
    @Override
    public void pidWrite(double output) {
        rotateToAngleRate = output;
    }

    /* Bounds an angle to a value between -180 to 180 degrees */
    private double boundAngle(double angle) {
        if(angle > 180) {
            return (angle - 360);
        } else if (angle < -180) {
            return (angle + 360);
        } else {
            return angle;
        }
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }

}
