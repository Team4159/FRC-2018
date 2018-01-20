package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import frc.team4159.robot.RobotMap;
import frc.team4159.robot.commands.TankDrive;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {
	
    private TalonSRX leftTalon;
    private VictorSPX leftVictor;
    private TalonSRX rightTalon;
    private VictorSPX rightVictor;

    private AHRS navx;

    public Drivetrain() {

        leftTalon = new TalonSRX(RobotMap.LEFT_TALON);
        leftVictor = new VictorSPX(RobotMap.LEFT_VICTOR);
        leftVictor.follow(leftTalon);

        rightTalon = new TalonSRX(RobotMap.RIGHT_TALON);
        rightVictor = new VictorSPX(RobotMap.RIGHT_VICTOR);
        rightVictor.follow(rightTalon);

        try {
            navx = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
        }

    }

    public void setLeftRaw(double speed){
        leftTalon.set(ControlMode.PercentOutput, speed);
    }
    public void setRightRaw(double speed){
        rightTalon.set(ControlMode.PercentOutput, speed);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }

}
