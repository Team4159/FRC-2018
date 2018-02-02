package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.RobotMap;
import frc.team4159.robot.commands.drive.TankDrive;
import static frc.team4159.robot.Constants.*;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

    private static Drivetrain instance;

    private TalonSRX leftTalon, rightTalon;

    /* The NavX is a 9-axis inertial/magnetic sensor and motion processor, plugged into the RoboRio's MXP port */
    private AHRS navx;

    private final int MAX_SPEED = 5200; // Native units per 100ms
    private final int PARAM_SLOT = 0;
    private final int PIDIDX = 0;
    private final double kF = 0.196730769230769; // 1023 / 5200 where 5200 is our max speed
    private final double kP = 0.4092; // (10% * 1023) / 250 where 250 is our max error
    private final double kI = 0;
    private final double kD = 4.092; // kP * 10

    public static Drivetrain getInstance() {
        if(instance == null)
            instance = new Drivetrain();
        return instance;
    }

    private Drivetrain() {

        leftTalon = new TalonSRX(RobotMap.LEFT_TALON);
        leftTalon.setInverted(false);
        VictorSPX leftVictor = new VictorSPX(RobotMap.LEFT_VICTOR);
        leftVictor.setInverted(false);
        leftVictor.follow(leftTalon);

        rightTalon = new TalonSRX(RobotMap.RIGHT_TALON);
        rightTalon.setInverted(true);
        VictorSPX rightVictor = new VictorSPX(RobotMap.RIGHT_VICTOR);
        rightVictor.setInverted(true);
        rightVictor.follow(rightTalon);

        final int PEAK_CURRENT = 35; // Amps
        final int CONTINUOUS_CURRENT = 30; // Amps
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

        try {
            navx = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
        }

        configureSensors();

    }

    private void configureSensors() {

        // Mag encoder attached to its respective talon via the srx data cable
        leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PIDIDX, TIMEOUT_MS);
        leftTalon.setSensorPhase(false);

        leftTalon.configNominalOutputForward(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        leftTalon.configNominalOutputReverse(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        leftTalon.configPeakOutputForward(PEAK_OUT_PERCENT, TIMEOUT_MS);
        leftTalon.configPeakOutputReverse(-PEAK_OUT_PERCENT, TIMEOUT_MS);
//
//        leftTalon.config_kP(PARAM_SLOT, kP, TIMEOUT_MS);
//        leftTalon.config_kI(PARAM_SLOT, kI, TIMEOUT_MS);
//        leftTalon.config_kD(PARAM_SLOT, kD, TIMEOUT_MS);
//        leftTalon.config_kF(PARAM_SLOT, kF, TIMEOUT_MS);
//
        rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PIDIDX, TIMEOUT_MS);
        rightTalon.setSensorPhase(true);

        rightTalon.configNominalOutputForward(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        rightTalon.configNominalOutputReverse(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        rightTalon.configPeakOutputForward(PEAK_OUT_PERCENT, TIMEOUT_MS);
        rightTalon.configPeakOutputReverse(-PEAK_OUT_PERCENT, TIMEOUT_MS);

//        rightTalon.config_kP(PARAM_SLOT, 0.0, TIMEOUT_MS);
//        rightTalon.config_kI(PARAM_SLOT, 0.0, TIMEOUT_MS);
//        rightTalon.config_kD(PARAM_SLOT, 0.0, TIMEOUT_MS);
//        rightTalon.config_kF(PARAM_SLOT, 0.0, TIMEOUT_MS);

        zeroNavX();

    }

    public void setRawOutput(double leftPercent, double rightPercent){
        leftTalon.set(ControlMode.PercentOutput, leftPercent);
        rightTalon.set(ControlMode.PercentOutput, rightPercent);
    }

//    public void setVelocity(double leftPercent, double rightPercent) {
//        double leftTarget = leftPercent * MAX_SPEED;
//        double rightTarget = rightPercent * MAX_SPEED;
//        leftTalon.set(ControlMode.Velocity, leftTarget);
//        rightTalon.set(ControlMode.Velocity, rightTarget);
//    }

    public int getLeftEncoderPosition() {
        return leftTalon.getSelectedSensorPosition(PIDIDX);
    }

    public int getRightEncoderPosition() {
        return rightTalon.getSelectedSensorPosition(PIDIDX);
    }

    public double getHeadingDegrees() {
        return navx.getYaw();
    }

    private void zeroNavX() {
        navx.zeroYaw();
    }

    public void logDashboard() {

        double leftOutput = leftTalon.getMotorOutputPercent();
        double leftSpeed = leftTalon.getSelectedSensorVelocity(PIDIDX);
        double leftError = leftTalon.getClosedLoopError(PIDIDX);

        SmartDashboard.putNumber("left output", leftOutput);
        SmartDashboard.putNumber("left speed", leftSpeed);
        SmartDashboard.putNumber("left error", leftError);
        SmartDashboard.putNumber("heading", getHeadingDegrees());

    }

    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }

}
