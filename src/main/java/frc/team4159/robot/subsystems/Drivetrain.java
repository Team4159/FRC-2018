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
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.Robot;
import frc.team4159.robot.RobotMap;
import frc.team4159.robot.commands.drive.Drive;
import static frc.team4159.robot.Constants.*;

import edu.wpi.first.wpilibj.command.Subsystem;

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

    /* Constants for PID control */
    private final int MAX_SPEED = 5200; // Native units per 100ms
    private final int SLOTIDX = 0;
    private final int PIDIDX = 0;
    private final double kF_left = 0.196730769230769; // 1023 / 5200 where 5200 is our max speed
    private final double kP_left = 0.4092; // (10% * 1023) / 250 where 250 is our max error
    private final double kI_left = 0;
    private final double kD_left = 4.092; // kP * 10

    /* Stores state if controls should be reversed or not */
    private boolean reverse;

    private PIDController turnController;
    private final double kP_turn = 0.1;
    private final double kI_turn = 0.0;
    private final double kD_turn = 0.0;
    private final double kF_turn = 0.0;
    private final double kToleranceDegrees = 2.0f;

    private double rotateToAngleRate;

    private Drivetrain() {

        /* Inverts left talon and followed by victor */
        leftTalon = new TalonSRX(RobotMap.LEFT_TALON);
        leftTalon.setInverted(true);
        leftVictor = new VictorSPX(RobotMap.LEFT_DRIVE_VICTOR);
        leftVictor.follow(leftTalon);

        /* Right victor follow right talon */
        rightTalon = new TalonSRX(RobotMap.RIGHT_TALON);
        rightVictor = new VictorSPX(RobotMap.RIGHT_DRIVE_VICTOR);
        rightVictor.follow(rightTalon);

        /* The NavX is a 9-axis inertial/magnetic sensor and motion processor, plugged into the RoboRio's MXP port */
        try {
            navx = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
        }

        reverse = false;

        limitCurrent();
        configureSensors();

        turnController = new PIDController(kP_turn,kI_turn,kD_turn, kF_turn, navx, this);
        turnController.setInputRange(-180.0f, 180.0f);
        turnController.setOutputRange(-1, 1);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
        turnController.disable();

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

        rightTalon.config_kF(SLOTIDX, 0, TIMEOUT_MS);
        rightTalon.config_kP(SLOTIDX, 0, TIMEOUT_MS);
        rightTalon.config_kI(SLOTIDX, 0, TIMEOUT_MS);
        rightTalon.config_kD(SLOTIDX, 0, TIMEOUT_MS);

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

    public void turnToAngle(double angle) {
        if(!turnController.isEnabled()) {
            turnController.setSetpoint( /*Robot.drivetrain.getHeadingDegrees() + */ angle); // TODO: Write function that keeps angle (-180, 180)
            rotateToAngleRate = 0;
            turnController.enable();
        }
        setRawOutput(-rotateToAngleRate, rotateToAngleRate);
    }

    public void driveStraight(double magnitude) {
        if(!turnController.isEnabled()) {
            turnController.setSetpoint(navx.getYaw());
            rotateToAngleRate = 0;
            turnController.enable();
        }
        setRawOutput(magnitude + rotateToAngleRate, magnitude - rotateToAngleRate);
    }

    public boolean turnOnTarget() {
        return turnController.onTarget();
    }

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
    public void setVelocity(double leftPercent, double rightPercent) {
        double leftTarget = leftPercent * MAX_SPEED;
        double rightTarget = rightPercent * MAX_SPEED;
        leftTalon.set(ControlMode.Velocity, leftTarget);
        rightTalon.set(ControlMode.Velocity, rightTarget);
    }

    public void driveStraight(double leftValue, double rightValue) {
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

    public AHRS getNavx() {
        return navx;
    }


    public void logDashboard() {

        double leftOutput = leftTalon.getMotorOutputPercent();
        double leftSpeed = leftTalon.getSelectedSensorVelocity(PIDIDX);
        double rightSpeed = rightTalon.getSelectedSensorVelocity(PIDIDX);
        double leftError = leftTalon.getClosedLoopError(PIDIDX);
        double acceleration = navx.getRawAccelX();
        double velocity = navx.getVelocityX();

        SmartDashboard.putNumber("left output", leftOutput);
        SmartDashboard.putNumber("left speed", leftSpeed);
        SmartDashboard.putNumber("right speed", rightSpeed);
        SmartDashboard.putNumber("left error", leftError);
        SmartDashboard.putNumber("heading", getHeadingDegrees());
        SmartDashboard.putNumber("acc", acceleration);
        SmartDashboard.putNumber("vel", velocity);

    }

    private void limitCurrent() {

        /* Sets and limits the peak and continuous current for both sides of motors to prevent brownouts */

        final int PEAK_CURRENT = 30; // Amps
        final int CONTINUOUS_CURRENT = 25; // Amps
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

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }

    @Override
    public void pidWrite(double output) {
        rotateToAngleRate = output;
    }

}
