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
			case LEFT_SWITCH: //not sure if this will work
                //MPcodeRunner trailL = new MPcodeRunner(new LeftSwitchTrajectory());
                addSequential(new MPcodeRunner(new LeftSwitchTrajectory()));// what does this really do?
				System.out.println("left switch path activated");
				break;
			case RIGHT_SWITCH:
                //MPcodeRunner trailR = new MPcodeRunner(new RightSwitchTrajectory());
                addSequential(new MPcodeRunner(new RightSwitchTrajectory()));
				System.out.println("right switch path activated");
				break;
			case DRIVE_STRAIGHT:
				//MPcodeRunner trailS = new MPcodeRunner(new StraightTrajectory());
				addSequential(new MPcodeRunner(new StraightTrajectory()));
				System.out.println(" Straight path activated");
				break;
			case DO_NOTHING:
				System.out.println("Standing still");
				break;
		}
	}
	
	
	
	//naming different types of Autos
	public enum Mode{
		LEFT_SWITCH, RIGHT_SWITCH, DO_NOTHING, DRIVE_STRAIGHT
	}
	

}
