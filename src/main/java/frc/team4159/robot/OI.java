package frc.team4159.robot;

import edu.wpi.first.wpilibj.Joystick;

/*
* The OI (Operator Interface) class binds the controls on the physical operator interface to the commands and command
* groups that allow control of the robot.
*/

public class OI implements ControlMap {

    private static OI instance;

    public static OI getInstance() {
        if(instance == null)
            instance = new OI();
        return instance;
    }

    /* Logitech Attack 3 joysticks, plugged in via USB to the driver station laptop */
    private Joystick leftJoy, rightJoy, secondaryJoy;

    private OI() {
        leftJoy = new Joystick(LEFT_STICK);
        rightJoy = new Joystick(RIGHT_STICK);
        secondaryJoy = new Joystick(SECONDARY_STICK);
    }

    public double getLeftY() {

        double leftY = leftJoy.getY();
        if(leftY < 0)
            return 1 * Math.pow(leftY, 2);
        return -Math.pow(leftY, 2);

    }

    public double getRightY() {

        double rightY = rightJoy.getY();
        if(rightY < 0)
            return 1 * Math.pow(rightY, 2);
        return -Math.pow(rightY, 2);

	}

	public boolean reverseControls() {
        return (leftJoy.getRawButtonPressed(2));
    }

    public double getSecondaryY() {
        return secondaryJoy.getY();
    }

    public boolean left90Button() {
        return rightJoy.getRawButtonPressed(LEFT_90);
    }

    public boolean right90Button() {
        return rightJoy.getRawButtonPressed(RIGHT_90);
    }

    public boolean cw180Button() {
        return rightJoy.getRawButtonPressed(CW_180);
    }

    public boolean ccw180Buton() {
        return rightJoy.getRawButtonPressed(CCW_180);
    }

    public boolean driveStraightButton() {
        return rightJoy.getTrigger();
    }

    public boolean climbUpButton() {
        return secondaryJoy.getRawButton(CLIMB_UP);
    }

    public boolean climbDownButton() {
        return secondaryJoy.getRawButton(CLIMB_DOWN);
    }

    public boolean intakeButton() {
        return secondaryJoy.getRawButton(INTAKE);
    }
    public boolean outtakeButton() {
        return secondaryJoy.getRawButton(OUTTAKE);
    }

    public boolean openClaw() {
        return secondaryJoy.getTrigger();
    }

}