package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team4159.robot.RobotMap;
import frc.team4159.robot.commands.TankDrive;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {
	
	/* add motor controllers and encoders and drive functions */
    private TalonSRX leftTalon = new TalonSRX(RobotMap.DT_LEFT);
    private TalonSRX leftTalonF = new TalonSRX(RobotMap.DT_LEFT_FOLLOWER);
    private TalonSRX rightTalon = new TalonSRX(RobotMap.DT_RIGHT);
    private TalonSRX rightTalonF = new TalonSRX(RobotMap.DT_RIGHT_FOLLOWER);

    public void setLeftRaw(double speed){
        if(speed<0)
            leftTalon.set(ControlMode.PercentOutput, -speed*speed);
        else
            leftTalon.set(ControlMode.PercentOutput,speed*speed);
        leftTalonF.set(ControlMode.Follower, RobotMap.DT_LEFT);
    }
    public void setRightRaw(double speed){
        if(speed<0)
            rightTalon.set(ControlMode.PercentOutput, speed*speed);
        else
            rightTalon.set(ControlMode.PercentOutput, -speed*speed);
        rightTalonF.set(ControlMode.Follower, RobotMap.DT_RIGHT);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }


}
