package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team4159.robot.Constants;
import frc.team4159.robot.Robot;


public class DriveRadius extends TimedCommand {

    private double mTurnRadius;
    private double mBaseSpeed;
    private TurnDirection mTurnDirection;
    private double mWheelbase = Constants.WHEELBASE_WIDTH_INCHES;

    public DriveRadius(double seconds, double radius, double baseSpeed, TurnDirection turnDirection) {
        super(seconds); //Runs this command for a certain duration
        mTurnRadius = radius;
        mBaseSpeed = baseSpeed;
        mTurnDirection = turnDirection;
    }

    @Override
    protected void execute() {
        Robot.getDrivetrain().setVelocity(mBaseSpeed*radiusToRatio(mTurnRadius,mWheelbase,mTurnDirection),
                                          mBaseSpeed/radiusToRatio(mTurnRadius,mWheelbase,mTurnDirection));
    }

    private double radiusToRatio(double radius, double wheelbase, TurnDirection direction){
        //Converts a given radius to a ratio of speeds
        //Ratio is left over right
        if(direction == TurnDirection.CLOCKWISE)
            return (radius+wheelbase)/(radius-wheelbase);

        return (radius-wheelbase)/(radius+wheelbase);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
