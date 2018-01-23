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

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

    private static Drivetrain instance;

    private TalonSRX leftTalon, rightTalon;
    private VictorSPX leftVictor, rightVictor;
    private AHRS navx;

    private final double MAX_RPM = 5330 / 5.5; // CIM free speed, geared down 66:12
    private final double ENCODER_UNITS_PER_REV = 4096;

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
        final int TIMEOUT_MS = 0;
        final double NOMINAL_OUT_PERCENT = 0;
        final double PEAK_OUT_PERCENT = 1;

        leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT_MS);
        leftTalon.setSensorPhase(true);

        leftTalon.configNominalOutputForward(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        leftTalon.configNominalOutputReverse(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        leftTalon.configPeakOutputForward(PEAK_OUT_PERCENT, TIMEOUT_MS);
        leftTalon.configPeakOutputReverse(-PEAK_OUT_PERCENT, TIMEOUT_MS);

        leftTalon.config_kP(0, .1, TIMEOUT_MS);
        leftTalon.config_kI(0, 0, TIMEOUT_MS);
        leftTalon.config_kD(0, 0, TIMEOUT_MS);
        leftTalon.config_kF(0, 0, TIMEOUT_MS);

    }

    public void setRaw(double leftPercent, double rightPercent){
        leftTalon.set(ControlMode.PercentOutput, leftPercent);
        rightTalon.set(ControlMode.PercentOutput, rightPercent);
    }
    // See https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java/VelocityClosedLoop/src/org/usfirst/frc/team217/robot/Robot.java
    public void setLeft(double percentage) {
        double target = percentage * MAX_RPM * ENCODER_UNITS_PER_REV/600; // Unit conversion, RPM to encoder units/rev
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
