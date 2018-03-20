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

    private final double fastDownDistance = 7000;
    private final double hookDeliveryIncrement = 200;

    private boolean climbTalonRawMode;

    //Flags
    private boolean hasStartedClimb;
    private boolean hasGoneDown;

    private Climber() {

        climbTalon = new TalonSRX(CLIMB_TALON);
        climbVictor = new VictorSP(CLIMB_VICTOR);

        climbTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT_MS);
        configureSensors();

        hasStartedClimb = false;
        hasGoneDown = false;
        climbTalonRawMode = false;
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

    public void movePID() {
       // if (!climbTalonRawMode) //redundant
            climbTalon.set(ControlMode.Position, targetPosition); //36840
    }

    public void moveRaw(double percentInput){
        climbTalon.set(ControlMode.PercentOutput, percentInput);
        targetPosition = climbTalon.getSelectedSensorPosition(PIDIDX);
    }

    public void setStartedClimb(boolean value){
        hasStartedClimb = value;
    }
    public void setGoneDown(boolean value){
        hasGoneDown = value;
    }

    public boolean getClimbStarted(){
        return hasStartedClimb;
    }
    public boolean getGoneDown(){
        return hasGoneDown;
    }

    public void updatePosition(double value) {
        if(climbTalon.getSelectedSensorPosition(PIDIDX)<36500){
            value *= hookDeliveryIncrement;
            targetPosition += value;
        }
    }

    public void stopIncrement(){
        targetPosition = climbTalon.getSelectedSensorPosition(PIDIDX);
    }

    public void fastDown() {
        targetPosition -= fastDownDistance;
    }

    public void winch() {
        if(hasGoneDown&&climbTalon.getSelectedSensorPosition(PIDIDX)<3000)
            climbVictor.set(-1);
    }

    public void stopWinch() {
        climbVictor.set(0);
    }

    public void toggleClimbTalonMode(){
        climbTalonRawMode = !climbTalonRawMode;
    }

    public boolean getCalimTalonMode(){
        return climbTalonRawMode;
    }

    public void logSmartDashboard() {

//        kP = SmartDashboard.getNumber("kP_climb", 0.0);
//        kI = SmartDashboard.getNumber("kI_climb", 0.0);
//        kD = SmartDashboard.getNumber("kD_climb", 0.0);
////      kF = SmartDashboard.getNumber("kF_climb", 0.0);
//        target = SmartDashboard.getNumber("target", 0.0);

        SmartDashboard.putNumber("position", climbTalon.getSelectedSensorPosition(PIDIDX));
        SmartDashboard.putNumber("targetPosition", targetPosition);
        SmartDashboard.putBoolean("hasStartedClimb", hasStartedClimb);
        SmartDashboard.putBoolean("hasGoneDown", hasGoneDown);
        SmartDashboard.putBoolean("talonRawMode", climbTalonRawMode);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new Climb());
    }
}
