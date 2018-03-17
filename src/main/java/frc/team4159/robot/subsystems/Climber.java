package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.commands.climb.Climb;

import static frc.team4159.robot.Constants.TIMEOUT_MS;
import static frc.team4159.robot.RobotMap.CLIMB_TALON;
import static frc.team4159.robot.RobotMap.CLIMB_VICTOR;

public class Climber extends Subsystem {

    private static Climber instance;

    public static Climber getInstance() {
        if(instance == null)
            instance = new Climber();
        return instance;
    }

    private TalonSRX climbTalon; // hook
    private VictorSP climbVictor; // winch

    private double kF = 0;
    private double kP = 0.4;
    private double kI = 0;
    private double kD = 0;
    private final int SLOTIDX = 0;
    private final int PIDIDX = 0;
    private double targetPosition = 0;

    private Climber() {

        climbTalon = new TalonSRX(CLIMB_TALON);
        climbVictor = new VictorSP(CLIMB_VICTOR);

        climbTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT_MS);
        configureSensors();
    }

    private void configureSensors(){
        climbTalon.config_kF(SLOTIDX, kF, TIMEOUT_MS);
        climbTalon.config_kP(SLOTIDX, kP, TIMEOUT_MS);
        climbTalon.config_kI(SLOTIDX, kI, TIMEOUT_MS);
        climbTalon.config_kD(SLOTIDX, kD, TIMEOUT_MS);
        climbTalon.setSelectedSensorPosition(0,PIDIDX, TIMEOUT_MS);

        SmartDashboard.putNumber("kP_climb", kP);
        SmartDashboard.putNumber("kI_climb", kI);
        SmartDashboard.putNumber("kD_climb", kD);
        SmartDashboard.putNumber("kF_climb", kF);

    }

    public void move(){
        climbTalon.set(ControlMode.Position, targetPosition); //36840
    }

    public void updatePosition(double value) {
        value *= 200;
        targetPosition += value;
    }

    public void fast1() {
        targetPosition += 1500;
    }

    public void fast2() {
        targetPosition -= 1500;
    }

    public void winch() {
        climbVictor.set(-1);
    }

    public void stopWinch() {
        climbVictor.set(0);
    }

    public void logSmartDashboard() {

//        kP = SmartDashboard.getNumber("kP_climb", 0.0);
//        kI = SmartDashboard.getNumber("kI_climb", 0.0);
//        kD = SmartDashboard.getNumber("kD_climb", 0.0);
////      kF = SmartDashboard.getNumber("kF_climb", 0.0);
//        target = SmartDashboard.getNumber("target", 0.0);

//        SmartDashboard.putNumber("position", climbTalon.getSelectedSensorPosition(PIDIDX));
//        SmartDashboard.putNumber("targetPosition", targetPosition);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new Climb());
    }
}
