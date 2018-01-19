package frc.team4159.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI {
	
	private static Joystick leftJoy = new Joystick(ControlMap.leftStick);
	private static Joystick rightJoy = new Joystick(ControlMap.rightStick);
	
	public static double getLeftY() {
		return leftJoy.getY();
	}
	
	public static double getRightY() {
		return rightJoy.getY();
	}
	
}
