package frc.team4159.robot;

import edu.wpi.first.wpilibj.Joystick;
import static frc.team4159.robot.ControlMap.*;

/*
* The OI (Operator Interface) class binds the controls on the physical operator interface to the commands and command
* groups that allow control of the robot.
*/

public class OI {

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

    /*
    * getLeftY() and getRightY() squares the joystick y-axis value and changes its sign
     */
    public double getLeftY() {
        double leftY = leftJoy.getY();
        return -Math.copySign(Math.pow(leftY, 2), leftY);

    }

    public double getRightY() {
        double rightY = rightJoy.getY();
        return -Math.copySign(Math.pow(rightY, 2), rightY);

	}

    public double getSecondaryY() {
        return secondaryJoy.getY();
    }

	/* Boolean methods called from commands. Constants can be changed in Constants.java */

	public boolean reverseControls() {
        return (leftJoy.getRawButtonPressed(2));
    }

    public boolean left90Button() {
        return rightJoy.getRawButton(LEFT_90);
    }

    public boolean right90Button() {
        return rightJoy.getRawButton(RIGHT_90);
    }

    public boolean cw180Button() {
        return rightJoy.getRawButton(CW_180);
    }

    public boolean ccw180Buton() {
        return rightJoy.getRawButton(CCW_180);
    }

    public boolean driveStraightButton() {
        return rightJoy.getTrigger();
    }

    public boolean climbTopButton() { return secondaryJoy.getRawButton(CLIMB_TOP);}
    public boolean climbUpButton() { return secondaryJoy.getRawButton(CLIMB_UP);}
    public boolean climbDownButton() { return secondaryJoy.getRawButton(CLIMB_DOWN); }

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