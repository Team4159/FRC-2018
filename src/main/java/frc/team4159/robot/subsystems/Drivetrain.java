package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.RobotMap;
import frc.team4159.robot.commands.drive.TankDrive;
import frc.team4159.robot.Constants;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

    private static Drivetrain instance;

    private TalonSRX leftTalon, rightTalon;
    private VictorSPX leftVictor, rightVictor;
    private AHRS navx;
    private final int MAX_SPEED = 5200; // Native units per 100ms
    private final int TIMEOUT_MS = 10;
    private final int PARAM_SLOT = 0;
    private final int PIDIDX = 0;
    private final double kF = 0.196730769230769; // 1023 / 5200 where 5200 is our max speed
    private final double kP = 0.4092; // (10% * 1023) / 250 where 250 is our max error
    private final double kI = 0;
    private final double kD = 4.092; // kP * 10
    private final double NOMINAL_OUT_PERCENT = 0;
    private final double PEAK_OUT_PERCENT = 1;


    public static Drivetrain getInstance() {
        if(instance == null)
            instance = new Drivetrain();
        return instance;
    }

    public Drivetrain() {


        leftTalon = new TalonSRX(RobotMap.LEFT_TALON);
        leftVictor = new VictorSPX(RobotMap.LEFT_VICTOR);
        leftVictor.follow(leftTalon);

        rightTalon = new TalonSRX(RobotMap.RIGHT_TALON);
        rightVictor = new VictorSPX(RobotMap.RIGHT_VICTOR);
        rightVictor.follow(rightTalon);

        configureSensors();

        try {
            navx = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
        }

    }

    // TODO: Test velocity pid control on left. If it works, duplicate on right
    private void configureSensors() {
        //Combined MP sample code with drivetrain code
        leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PIDIDX, TIMEOUT_MS);
        leftTalon.setSensorPhase(false);
        leftTalon.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);


        leftTalon.configNominalOutputForward(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        leftTalon.configNominalOutputReverse(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        leftTalon.configPeakOutputForward(PEAK_OUT_PERCENT, TIMEOUT_MS);
        leftTalon.configPeakOutputReverse(-PEAK_OUT_PERCENT, TIMEOUT_MS);

        leftTalon.config_kP(PARAM_SLOT, kP, TIMEOUT_MS);
        leftTalon.config_kI(PARAM_SLOT, kI, TIMEOUT_MS);
        leftTalon.config_kD(PARAM_SLOT, kD, TIMEOUT_MS);
        leftTalon.config_kF(PARAM_SLOT, kF, TIMEOUT_MS);

        leftTalon.configMotionProfileTrajectoryPeriod(10, Constants.kTimeoutMs);
        leftTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

        rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PIDIDX, TIMEOUT_MS);
        rightTalon.setSensorPhase(true);
        leftTalon.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);

        rightTalon.configNominalOutputForward(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        rightTalon.configNominalOutputReverse(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        rightTalon.configPeakOutputForward(PEAK_OUT_PERCENT, TIMEOUT_MS);
        rightTalon.configPeakOutputReverse(-PEAK_OUT_PERCENT, TIMEOUT_MS);

        rightTalon.config_kP(PARAM_SLOT, 0, TIMEOUT_MS);
        rightTalon.config_kI(PARAM_SLOT, 0, TIMEOUT_MS);
        rightTalon.config_kD(PARAM_SLOT, 0, TIMEOUT_MS);
        rightTalon.config_kF(PARAM_SLOT, 0, TIMEOUT_MS);

        rightTalon.configMotionProfileTrajectoryPeriod(10, Constants.kTimeoutMs);
        rightTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

    }

    public void setLeftRaw(double speed){
        leftTalon.set(ControlMode.PercentOutput, speed);
    }
    public void setRightRaw(double speed){
        rightTalon.set(ControlMode.PercentOutput, speed);
    }

    // See https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/VelocityClosedLoop/src/org/usfirst/frc/team217/robot/Robot.java
    public void setLeft(double percentage) {
        double target = percentage * 500 * 4096/600;
        leftTalon.set(ControlMode.Velocity, target);
    }

    public void logSmartDashboard() {
        double speed = leftTalon.getSelectedSensorVelocity(0);
        SmartDashboard.putNumber("Left Encoder", speed);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }

}
