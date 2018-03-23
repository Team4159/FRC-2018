package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.commands.climb.Climb;

import static frc.team4159.robot.Constants.TIMEOUT_MS;
import static frc.team4159.robot.RobotMap.*;

public class Climber extends Subsystem {

    private static Climber instance;

    public static Climber getInstance() {
        if(instance == null)
            instance = new Climber();
        return instance;
    }

    /* Controls hook delivery */
    private TalonSRX climbTalon;

    /* Controls winching */
    private VictorSP climbVictor;

    private DigitalInput hallSensor;

    private boolean rawMode;
    private boolean hasStartedClimb;

    private final int PIDIDX = 0;
    private double targetPosition = 0;

    private Climber() {

        climbTalon = new TalonSRX(CLIMB_TALON);
        climbVictor = new VictorSP(CLIMB_VICTOR);
        hallSensor = new DigitalInput(HALL_SENSOR);

        rawMode = false;
        hasStartedClimb = false;

        configureSensors();
        limitCurrent();

    }

    /**
     * Configure mag encoder as sensor plugged into Talon. Set PIDF values. Set encoder position to 0.
     */
    private void configureSensors() {

        final int SLOTIDX = 0;

        final double kF = 0;
        final double kP = 0.4;
        final double kI = 0;
        final double kD = 0;

        climbTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PIDIDX, TIMEOUT_MS);

        climbTalon.config_kF(SLOTIDX, kF, TIMEOUT_MS);
        climbTalon.config_kP(SLOTIDX, kP, TIMEOUT_MS);
        climbTalon.config_kI(SLOTIDX, kI, TIMEOUT_MS);
        climbTalon.config_kD(SLOTIDX, kD, TIMEOUT_MS);

        climbTalon.setSelectedSensorPosition(0, PIDIDX, TIMEOUT_MS);

    }

    /**
     * Update the setpoint for position PID if raw mode is false and hasStartedClimb is true.
     * If hasStartedClimb is false, use raw voltage control
     *
     * @param joyAxisValue -1 to 1 from secondary joystick y-axis
     */
    public void updateSetpoint(double joyAxisValue) {

        if(!rawMode && hasStartedClimb) {
            final int MAX_SPEED = 200;
            targetPosition = climbTalon.getSelectedSensorPosition(PIDIDX) + joyAxisValue * MAX_SPEED;
        } else {
            climbTalon.set(ControlMode.PercentOutput, joyAxisValue);
        }

    }

    /**
     * If hall effect sensor value is true, set hasStartedClimb to false and set encoder position to 0
     * If false, set hasStartedClimb to true. If raw mode is false, set climber to target position using position PID
     */
    public void update() {

        if(hallSensorPresent()) {
            hasStartedClimb = false;
            climbTalon.setSelectedSensorPosition(0, PIDIDX, TIMEOUT_MS);

        } else {
            hasStartedClimb = true;
            if(!rawMode) {
                climbTalon.set(ControlMode.Position, targetPosition);
            }
        }
    }

    /**
     * Starting winching at full speed if telescoping arm is down and hook already started to deploy
     * OR if rawMode is true
     */
    public void winch() {
        if((hallSensorPresent() && hasStartedClimb) || rawMode) {
            climbVictor.set(-1);
        } else {
            climbVictor.set(0);
        }
    }

    /**
     * @return True if hall effect sensor is present
     */
    private boolean hallSensorPresent() {
        return hallSensor.get();
    }

    /**
     * Reverse the state of rawMode
     */
    public void toggleRawClimb() {
        rawMode = !rawMode;
    }

    /**
     * Limit current of the climber motors to prevent breakage
     */
    private void limitCurrent() {
        // TODO
    }

    /**
     * Put climber values to SmartDashboard
     */
    public void logSmartDashboard() {
        SmartDashboard.putBoolean("Climber Raw Mode", rawMode);
        SmartDashboard.putBoolean("Has Started Climb", hasStartedClimb);
        SmartDashboard.putNumber("Climber Encoder Position", climbTalon.getSelectedSensorPosition(PIDIDX));
    }

    /**
     * Initialize default command
     */
    public void initDefaultCommand() {
        setDefaultCommand(new Climb());
    }
}
