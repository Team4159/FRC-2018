package frc.team4159.robot.commands;



/*Written by Helen Zhang on Sol*/
/*Altered by Andrew Sue*/

//import org.usfirst.frc.team9159.robot.RobotMap;
//import org.usfirst.frc.team9159.robot.commands.Shoot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;


/**
 *
 */
public class PIDcode extends PIDSubsystem {
    //Not tuned. Not tested with a load, so I don't know if it actually works
    private static final double PID_P=0.1;
    private static final double PID_I=0.0;
    private static final double PID_D=0.0;
    private static final double PID_F=0.0;
    private static final double PID_TOLERANCE_PERCENT=5.0;//Input of 15.0 == 15 percent


    private static final double COUNTS_PER_REV = 4096;//Pulse/revolution; for vp integrated encoder


    private TalonSRX motorMaster;
    private VictorSPX motorSlave;

    private double motorInput;

    // Initialize your subsystem here
    public PIDcode(int canIDForMotorMaster, int canIDForMotorSlave) {
        super("PIDDrivetrain", PID_P, PID_I, PID_D,PID_F);
        motorMaster = new TalonSRX(canIDForMotorMaster);
        motorSlave = new VictorSPX(canIDForMotorSlave);
        motorSlave.follow(motorMaster);

        //Configuring sensors
        motorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        motorMaster.setSensorPhase(true);

        motorMaster.configNominalOutputForward(0, 10);
        motorMaster.configNominalOutputReverse(0, 10);
        motorMaster.configPeakOutputForward(1, 10);
        motorMaster.configPeakOutputReverse(-1, 10);

        motorMaster.config_kP(0,0,10);
        motorMaster.config_kI(0,0,10);
        motorMaster.config_kD(0,0,10);
        motorMaster.config_kF(0,0,10);

        //shooterEncoder=new Encoder(RobotMap.SHOOTER_ENCODER_A,RobotMap.SHOOTER_ENCODER_B);
        //shooterEncoder.setPIDSourceType(PIDSourceType.kRate);
        //shooterEncoder.setDistancePerPulse(1.0/COUNTS_PER_REV);//Revolutions/pulse

        // Use these to get going:
        enable();//Enables the PID controller.
        getPIDController().setContinuous(true);
        setPercentTolerance(PID_TOLERANCE_PERCENT);
        setOutputRange(0.0,1.0);//Make sure they don't try to run backward
    }

    public void setRaw(double speed){
        motorMaster.set(ControlMode.PercentOutput,speed);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new TankDrive());
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return motorMaster.getSelectedSensorVelocity(0);//Distance/sec (Distance as defined by setDistancePerPulse())
    }

    protected void usePIDOutput(double output) {
        motorInput=output;
        // Use output to drive your system, like a motor
        motorMaster.set(ControlMode.PercentOutput,output);
    }

    public double getEncoderRate(){
        return motorMaster.getSelectedSensorVelocity(0);
    }

    public double getPIDOutput(){
        return motorInput;
    }

    public void setSetPoint(double speed){
        super.setSetpoint(speed);
    }
}