package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Drivetrain;

public class DriveDistance extends Command {

    private Drivetrain drivetrain;
    private double leftDistance, rightDistance;

    /**
     * @param distance applies to both sides of drivetrain. Basically drive straight.
     */
    public DriveDistance(double distance) {
        requires(Robot.drivetrain);
        drivetrain = Robot.getDrivetrain();
        leftDistance = distance;
        rightDistance = distance;
    }

    /**
     * @param leftDistance left drivetrain distance in feet
     * @param rightDistance right drivetrain distance in feet
     */
    public DriveDistance(double leftDistance, double rightDistance) {
        requires(Robot.drivetrain);
        drivetrain = Robot.getDrivetrain();
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
    }

    @Override
    protected void initialize() {
        drivetrain.driveDistance(leftDistance, rightDistance);
    }

    /**
     * @return true if driving distance using motion magic is finished
     */
    @Override
    protected boolean isFinished() {
        return drivetrain.motionMagicFinished();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
