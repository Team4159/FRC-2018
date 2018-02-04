package frc.team4159.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4159.robot.RobotMap;
import frc.team4159.robot.commands.cube.LiftCube;

import static frc.team4159.robot.Constants.*;

public class CubeHolder extends Subsystem {

    private static CubeHolder instance;

    public static CubeHolder getInstance() {
        if(instance == null)
            instance = new CubeHolder();
        return instance;
    }

    private VictorSPX leftVictor, rightVictor;
    private DoubleSolenoid leftPiston, rightPiston;
    private TalonSRX liftTalon;
    private final int PIDIDX = 0;
    private final int MAX_SPEED = 5; // encoder units per cycle TODO: Test and change as necessary
    private int targetPosition; // In encoder units. 4096 per revolution.

    private CubeHolder() {

        leftVictor = new VictorSPX(RobotMap.LEFT_CUBE_VICTOR);
        rightVictor = new VictorSPX(RobotMap.RIGHT_CUBE_VICTOR);
        liftTalon = new TalonSRX(RobotMap.LIFT_TALON);
        leftPiston = new DoubleSolenoid(RobotMap.LEFT_FORWARD,RobotMap.LEFT_REVERSE);
        rightPiston = new DoubleSolenoid(RobotMap.RIGHT_FORWARD,RobotMap.RIGHT_REVERSE);

        targetPosition = 0; // Initial encoder value when lifter is down

        configureSensors();
    }

    private void configureSensors() {

        // TODO: Add a limit switch, hall effect, talon tech, etc. to zero out encoder

        final int SLOTIDX = 0;

        liftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, PIDIDX, TIMEOUT_MS);
        liftTalon.setSensorPhase(false);
        liftTalon.configNominalOutputForward(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        liftTalon.configNominalOutputReverse(NOMINAL_OUT_PERCENT, TIMEOUT_MS);
        liftTalon.configPeakOutputForward(PEAK_OUT_PERCENT, TIMEOUT_MS);
        liftTalon.configPeakOutputReverse(-PEAK_OUT_PERCENT, TIMEOUT_MS);
        liftTalon.configAllowableClosedloopError(SLOTIDX, 0, TIMEOUT_MS);

        liftTalon.config_kF(SLOTIDX, 0, TIMEOUT_MS);
        liftTalon.config_kP(SLOTIDX, 0.1, TIMEOUT_MS);
        liftTalon.config_kI(SLOTIDX, 0, TIMEOUT_MS);
        liftTalon.config_kD(SLOTIDX, 0, TIMEOUT_MS);

    }

    public void intake() {
        leftVictor.set(ControlMode.PercentOutput, -1);
        rightVictor.set(ControlMode.PercentOutput, 1);
    }

    public void outtake() {
        leftVictor.set(ControlMode.PercentOutput, 1);
        rightVictor.set(ControlMode.PercentOutput, -1);
    }

    public void stopFlywheels() {
        leftVictor.set(ControlMode.PercentOutput, 0);
        rightVictor.set(ControlMode.PercentOutput, 0);
    }

    public void open() {
        leftPiston.set(DoubleSolenoid.Value.kForward);
        rightPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void close() {
        leftPiston.set(DoubleSolenoid.Value.kReverse);
        rightPiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void setRawLift(double value) {
        liftTalon.set(ControlMode.PercentOutput, value);
    }

    public void move() {

//        if(limit switch triggered) {
//            liftTalon.setSelectedSensorPosition(0, PIDIDX, TIMEOUT_MS);
//            targetPosition = 0;
//        }

        if(targetPosition < 0)
            targetPosition = 0;
        if(targetPosition > 1050) // 90 degrees is 1024
            targetPosition = 1024;

        liftTalon.set(ControlMode.Position, targetPosition);
    }

    public void updatePosition(double value) {
        value *= MAX_SPEED;
        targetPosition += (int)value;
    }

    public void initDefaultCommand() {
        setDefaultCommand(new LiftCube());
    }
}

