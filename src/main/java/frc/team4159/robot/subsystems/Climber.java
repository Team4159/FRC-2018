package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4159.robot.RobotMap;
import frc.team4159.robot.commands.climb.Climb;

public class Climber extends Subsystem {

    private static Climber instance;

    public static Climber getInstance() {
        if(instance == null)
            instance = new Climber();
        return instance;
    }

    private TalonSRX climbTalon;

    private Climber() {
        climbTalon = new TalonSRX(RobotMap.CLIMB_TALON);
    }

    public void climbUp() {
        climbTalon.set(ControlMode.PercentOutput, 0.75);
    }

    /* Slower than climb up for safety reasons when lowering the robot */
    public void climbDown() {
        climbTalon.set(ControlMode.PercentOutput, -0.25);
    }

    public void stopClimb() {
        climbTalon.set(ControlMode.PercentOutput, 0);
    }

    public void initDefaultCommand() {
        new Climb();
    }
}
