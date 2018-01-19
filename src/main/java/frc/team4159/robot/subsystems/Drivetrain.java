package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.team4159.robot.RobotMap;
import frc.team4159.robot.commands.TankDrive;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {
	
	/* add motor controllers and encoders and drive functions */
    private TalonSRX leftTalon = new TalonSRX(RobotMap.DT_LEFT);
    private VictorSPX leftVictorF = new VictorSPX(RobotMap.DT_LEFT_FOLLOWER);
    private TalonSRX rightTalon = new TalonSRX(RobotMap.DT_RIGHT);
    private VictorSPX rightVictorF = new VictorSPX(RobotMap.DT_RIGHT_FOLLOWER);

    public Drivetrain(){
        leftVictorF.follow(leftTalon);
        rightVictorF.follow(rightTalon);
    }

    public void setLeftRaw(double speed){
        if(speed<0)
            leftTalon.set(ControlMode.PercentOutput, -speed*speed);
        else
            leftTalon.set(ControlMode.PercentOutput,speed*speed);
    }
    public void setRightRaw(double speed){
        if(speed<0)
            rightTalon.set(ControlMode.PercentOutput, speed*speed);
        else
            rightTalon.set(ControlMode.PercentOutput, -speed*speed);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }


}
