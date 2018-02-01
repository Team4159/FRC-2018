package frc.team4159.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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

    private VictorSPX climbVictor;

    public Climber() {
        climbVictor = new VictorSPX(RobotMap.CLIMB_VICTOR);
    }

    public void climbUp() {
        climbVictor.set(ControlMode.PercentOutput, 0.75);
    }

    public void climbDown() {
        climbVictor.set(ControlMode.PercentOutput, -0.25);
    }

    public void stopClimb() {
        climbVictor.set(ControlMode.PercentOutput, 0);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new Climb());
    }
}
