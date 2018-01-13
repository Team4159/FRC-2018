package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team4159.robot.commands.TankDrive;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {
	
	/* add motor controllers and encoders and drive functions */
	private TalonSRX leftTalon = new TalonSRX(0);
    private TalonSRX rightTalon = new TalonSRX(1);


    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }
}
