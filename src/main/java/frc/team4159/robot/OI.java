package frc.team4159.robot;

import edu.wpi.first.wpilibj.Joystick;

/*
 * The OI (Operator Interface) class binds the controls on the physical operator interface to the commands and command
 * groups that allow control of the robot.
 */

public class OI {

    private static OI instance;

    public static OI getInstance() {
        if (instance == null)
            instance = new OI();
        return instance;
    }

    /**
     * Logitech Attack 3 joysticks, plugged in via USB to the driver station laptop
     */
    private Joystick leftJoy, rightJoy;

    private OI() {
        leftJoy = new Joystick(0);
        rightJoy = new Joystick(1);
    }

    /*
     * DRIVETRAIN CONTROLS
     */

    /**
     * @return Left y-axis joystick value squared and inverted sign
     */

    public double getLeftJoy() {
        double leftY = leftJoy.getY();
        return Math.copySign(Math.pow(leftY, 2), leftY);
    }

    /**
     * @return Right y-axis joystick value squared and inverted sign
     */
    public double getRightJoy() {
        double rightY = rightJoy.getY();
        return -Math.copySign(Math.pow(rightY, 2), rightY);
    }

    public boolean getIn() {
        return rightJoy.getRawButtonPressed(3);
    }

    public boolean getOut() {
        return rightJoy.getRawButtonPressed(2);
    }
}