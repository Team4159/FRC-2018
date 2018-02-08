package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.RobotMap;
import frc.team4159.robot.commands.climb.Climb;
import static frc.team4159.robot.Constants.*;


public class Climber extends Subsystem {

    private static Climber instance;
    private double Kf = 0;
    private double Kp = .1;
    private double Ki = 0;
    private double Kd = 0;
    private final int PARAM_SLOT = 0;
    private final int PIDIDX = 0;
    private double target = 0;
    private double climberPosition = 0;

    public static Climber getInstance() {
        if(instance == null)
            instance = new Climber();
        return instance;
    }

    private TalonSRX climbTalon;

    private Climber() {
        climbTalon = new TalonSRX(RobotMap.CLIMB_TALON);

        climbTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT_MS);
        configureSensors();
    }
    private void configureSensors(){
        climbTalon.config_kF(PARAM_SLOT,Kf, TIMEOUT_MS);
        climbTalon.config_kP(PARAM_SLOT,Kp, TIMEOUT_MS);
        climbTalon.config_kI(PARAM_SLOT,Ki, TIMEOUT_MS);
        climbTalon.config_kD(PARAM_SLOT,Kd, TIMEOUT_MS);
        climbTalon.setSelectedSensorPosition(0,PIDIDX,TIMEOUT_MS);

        SmartDashboard.putNumber("kP_climb", Kp);
        SmartDashboard.putNumber("kI_climb", Ki);
        SmartDashboard.putNumber("kD_climb", Kd);
        SmartDashboard.putNumber("kF_climb", Kf);
        SmartDashboard.putNumber("target", target);

    }

    public void climberRun(){
        climbTalon.set(ControlMode.Position, climberPosition);//36840
    }

    public void climbUp() {
        System.out.println(climberPosition);
        climberPosition += 100.0;
        System.out.println(climberPosition + "after");
    }

    public void climbDown() {
        System.out.println(climberPosition);
        climberPosition -= 100.0;
        System.out.println(climberPosition + "after");
    }

    public void climbTop(){
        climberPosition = target;
        System.out.println("Climb");
    }

    public void holdPosition() {
    }

    public void logSmartDashboard() {

        Kp = SmartDashboard.getNumber("kP_climb", 0.0);
        Ki = SmartDashboard.getNumber("kI_climb", 0.0);
        Kd = SmartDashboard.getNumber("kD_climb", 0.0);
        Kf = SmartDashboard.getNumber("kF_climb", 0.0);
        target = SmartDashboard.getNumber("target", 0.0);

        SmartDashboard.putNumber("position", climbTalon.getSelectedSensorPosition(PIDIDX));
        System.out.println(target);

        //System.out.println("out: " + output + " spd: " + speed + " err: " + error);
    }

    public void initDefaultCommand() {
        new Climb();
    }
}
