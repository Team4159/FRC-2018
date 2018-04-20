package frc.team4159.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team4159.robot.commands.drive.RunMotionProfile;

import static frc.team4159.robot.ControlMap.*;
import static frc.team4159.robot.util.TrajectoryCSV.*;

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

    /**
     *  Logitech Attack 3 joysticks, plugged in via USB to the driver station laptop
     */
    private Joystick leftJoy, rightJoy, secondaryJoy, testJoy;
    private JoystickButton base, midToRight, midToLeft;

    private OI() {

        leftJoy = new Joystick(LEFT_STICK);
        rightJoy = new Joystick(RIGHT_STICK);
        secondaryJoy = new Joystick(SECONDARY_STICK);
        testJoy = new Joystick(TEST_STICK);

        base = new JoystickButton(testJoy, 3);
        midToLeft = new JoystickButton(testJoy, 4);
        midToRight = new JoystickButton(testJoy, 5);

        base.whenReleased(new RunMotionProfile(BASELINE_L, BASELINE_R));
        midToLeft.whenReleased(new RunMotionProfile(MID_TO_LEFT_L, MID_TO_LEFT_R));
        midToRight.whenReleased(new RunMotionProfile(MID_TO_RIGHT_L, MID_TO_RIGHT_R));

    }

    /*
     * DRIVETRAIN CONTROLS
     */

    /**
     * @return Left y-axis joystick value squared and inverted sign
     */

    public double getLeftY() {
        double leftY = leftJoy.getY();
        return -Math.copySign(Math.pow(leftY, 2), leftY);
    }

    /**
     * @return Right y-axis joystick value squared and inverted sign
     */
    public double getRightY() {
        double rightY = rightJoy.getY();
        return -Math.copySign(Math.pow(rightY, 2), rightY);
	}

    /**
     * @return Secondary y-axis joystick value squared
     */
    public double getSecondaryY() {
        double secondaryY = secondaryJoy.getY();
        return Math.copySign(Math.pow(secondaryY, 2), secondaryY);
    }

    /**
     * @return True if reverse button pressed.
     */
	public boolean reverseControls() {
        return (leftJoy.getRawButtonPressed(REVERSE_CONTROLS));
    }

    /*

    public boolean left90Button() {
        return rightJoy.getRawButton(LEFT_90);
    }

    public boolean right90Button() {
        return rightJoy.getRawButton(RIGHT_90);
    }

    public boolean front0Button() {
        return rightJoy.getRawButton(FRONT_0);
    }

    public boolean back180Button() {
        return rightJoy.getRawButton(BACK_180);
    }

    */

    /**
     * @return True if right trigger held.
     */
    public boolean driveStraightButton() {
        return rightJoy.getTrigger();
    }

    /*
     * INTAKE CONTROLS
     */

    /**
     * @return True if intake button held
     */
    public boolean intakeButton() {
        return secondaryJoy.getRawButton(INTAKE);
    }

    /**
     * @return True if outtake button held
     */
    public boolean outtakeButton() {
        return secondaryJoy.getRawButton(OUTTAKE);
    }

    /**
     * @return True if secondary trigger held
     */
    public boolean openClaw() {
        return secondaryJoy.getTrigger();
    }

    /**
     * @return True if switch height state button pressed
     */
    public boolean setSwitchHeight(){
        return secondaryJoy.getRawButtonPressed(SWITCH);
    }

    /**
     * @return True if set lifter down button pressed
     */
    public boolean setLiftTargetZero() {
        return secondaryJoy.getRawButtonPressed(GROUND);
    }

    /**
     * @return True if lifter raw mode button pressed
     */
    public boolean toggleLifterRawMode() {
	    return secondaryJoy.getRawButtonPressed(TOGGLE_RAW_LIFT);
    }

    /**
     * @return True if reset lift encoder button pressed
     */
    public boolean resetLiftEncoder(){
        return secondaryJoy.getRawButtonPressed(RESET_LIFT_ENCODER);
    }

    /*
     * CLIMBER CONTROLS
     */

    /**
     * @return True if set enable climb button held
     */
    public boolean climbEnable() {
        return secondaryJoy.getRawButton(CLIMB_ENABLE);
    }

    /**
     * @return True if winch button held
     */
    public boolean climbWinch() {
        return secondaryJoy.getRawButton(WINCH);
    }

    /**
     * @return True if set toggle raw climb button pressed
     */
    public boolean toggleRawClimb() {
        return secondaryJoy.getRawButtonPressed(TOGGLE_RAW_CLIMB);
    }

    /*
     * TEST JOYSTICK
     */

    boolean getLeftSelectionButton(){
        return testJoy.getRawButtonReleased(SELECTOR);
    }

    boolean getRightSelectionButton(){
        return testJoy.getRawButtonReleased(OPTION);
    }

    boolean getTestTriggerPressed() {
        return testJoy.getTriggerPressed();
    }

    boolean getTestTriggerReleased() {
        return testJoy.getTriggerReleased();
    }

}