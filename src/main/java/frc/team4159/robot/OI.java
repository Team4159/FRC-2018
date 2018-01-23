package frc.team4159.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI implements ControlMap {

    private static OI instance;

    public static OI getInstance() {
        if(instance == null)
            instance = new OI();
        return instance;
    }

    private static Joystick leftJoy;
    private static Joystick rightJoy;
    private static Joystick secondaryJoy;

    public OI() {
        leftJoy = new Joystick(leftStick);
        rightJoy = new Joystick(rightStick);
        secondaryJoy = new Joystick(secondaryStick);
        // TODO: Bind joystick buttons to commands
    }

    public static double getLeftY() {

        double leftY = leftJoy.getY();

        if(leftY < 0)
            return -1 * Math.pow(leftY, 2);
        return Math.pow(leftY, 2);

    }

    public static double getRightY() {

        double rightY = rightJoy.getY();

        if(rightY < 0)
            return -1 * Math.pow(rightY, 2);
        return Math.pow(rightY, 2);

	}

	public  boolean testButton(){
		if(secondaryJoy.getRawButton(2))
			return true;
		return false;
	}
}

