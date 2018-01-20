package frc.team4159.robot.commands;

//importing things form other packages etc
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4159.robot.Robot;


public class AutoCommandGroup extends CommandGroup  {
	
	public AutoCommandGroup(Mode m) {
		//using drivetrain in auto
		requires(Robot.drivetrain);
		
		//tells what each path does
		switch(m) {
			case LEFT_SWITCH:
				System.out.println("left switch path activated");
				break;
			case RIGHT_SWITCH:
				System.out.println("right switch path activated");
				break;
			case LEFT_SCALE_RIGHT_SWITCH:
				System.out.println("left scale right switch path activated");
				break;
			case RIGHT_SCALE_RIGHT_SWITCH:
				System.out.println("right scale right switch path activated");
				break;
			case LEFT_SCALE_LEFT_SWITCH:
				System.out.println("left scale left switch path activated");
				break;
			case RIGHT_SCALE_LEFT_SWITCH:
				System.out.println("right scale left switch path activated");
				break;
			case DO_NOTHING:
				System.out.println("Standing still");
				break;
		}
	}
	
	
	
	//naming different types of Autos
	public enum Mode{
		LEFT_SWITCH, RIGHT_SWITCH, RIGHT_SCALE_LEFT_SWITCH, RIGHT_SCALE_RIGHT_SWITCH, LEFT_SCALE_RIGHT_SWITCH, LEFT_SCALE_LEFT_SWITCH, DO_NOTHING
	}
	

}
