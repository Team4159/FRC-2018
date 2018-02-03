package frc.team4159.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import frc.team4159.robot.commands.auto.TestMotionProfile;

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

    private Joystick leftJoy, rightJoy, secondaryJoy;

    private OI() {
        leftJoy = new Joystick(LEFT_STICK);
        rightJoy = new Joystick(RIGHT_STICK);
        secondaryJoy = new Joystick(SECONDARY_STICK);

        Button testButton = new JoystickButton(rightJoy, 2);
        //testButton.whenReleased(new TestMotionProfile());
    }

    public double getLeftY() {

        double leftY = leftJoy.getY();
        if(leftY < 0)
            return -1 * Math.pow(leftY, 2);
        return Math.pow(leftY, 2);

    }

    public double getRightY() {

        double rightY = rightJoy.getY();
        if(rightY < 0)
            return -1 * Math.pow(rightY, 2);
        return Math.pow(rightY, 2);

	}


    public boolean getClimbUp(){
        return secondaryJoy.getRawButton(CLIMB_UP);
    }
    public boolean getClimbDown(){
        return secondaryJoy.getRawButton(CLIMB_DOWN);
    }

    //Slidy Intake buttons
    public double getSecondaryY() {
        return secondaryJoy.getY();
    }
    public boolean intakeOpenPiston() {
        return secondaryJoy.getTrigger();
    }
    public boolean intakeButton() {
        return secondaryJoy.getRawButton(2);
    }
    public boolean outtakeButton() {
        return secondaryJoy.getRawButton(3);
    }

}