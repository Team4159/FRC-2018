package frc.team4159.robot.commands;


import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.RobotMap;

public class MPcodeRunner extends Command {
    private TalonSRX leftTalon = new TalonSRX(RobotMap.LEFT_TALON);
    private TalonSRX rightTalon = new TalonSRX(RobotMap.RIGHT_TALON);
    private MPcode mp;

    public MPcodeRunner(Trajectory path){
        mp = new MPcode(leftTalon,rightTalon,path);
        mp.reset();
        mp.control();

        SetValueMotionProfile setOutput = mp.getSetValue();
        leftTalon.set(ControlMode.MotionProfile, setOutput.value);
        rightTalon.set(ControlMode.MotionProfile, setOutput.value);

        mp.startMotionProfile();

    }

    public boolean isFinished(){
        // Shut off everything
        rightTalon.set(ControlMode.Disabled, 0);
        leftTalon.set(ControlMode.Disabled, 0);
        return true;
    }

}
