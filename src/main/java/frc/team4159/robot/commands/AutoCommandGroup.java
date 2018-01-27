package frc.team4159.robot.commands;

//importing things form other packages etc
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4159.robot.Robot;


public class AutoCommandGroup extends CommandGroup  {
	
	public AutoCommandGroup(Mode m) {
		//using drivetrain in auto
		requires(Robot.drivetrain);
		
		//tells what each path does
		switch(m) {
			case LEFT_SWITCH: //not sure if this will work
                MPcode trailL = new MPcode(new TalonSRX(3), new TalonSRX(4), new LeftSwitchTrajectory());
                addSequential(trailL);// what does this really do?
                trailL.reset();
                trailL.control();
				System.out.println("left switch path activated");
				break;
			case RIGHT_SWITCH:
                MPcode trailR = new MPcode(new TalonSRX(3), new TalonSRX(4), new LeftSwitchTrajectory());
                addSequential(trailR);
                trailR.reset();
                trailR.control();
				System.out.println("right switch path activated");
				break;
			case DO_NOTHING:
				System.out.println("Standing still");
				break;
		}
	}
	
	
	
	//naming different types of Autos
	public enum Mode{
		LEFT_SWITCH, RIGHT_SWITCH, DO_NOTHING
	}
	

}
