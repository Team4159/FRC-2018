package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.team4159.robot.Robot;


public class TurnToAngle extends Command implements PIDOutput {

    private PIDController turnController;

    private final double kP = 0.1;
    private final double kI = 0.0;
    private final double kD = 0.0;
    private final double kF = 0.0;
    private final double kToleranceDegrees = 2.0f;

    private double angle;
    private double rotateToAngleRate;

    public TurnToAngle(double angle) {
        requires(Robot.drivetrain);
        this.angle = angle;
    }

    @Override
    protected void initialize() {
        turnController = new PIDController(kP,kI,kD, kF, Robot.drivetrain.getNavx(), this);
        turnController.setInputRange(-180.0f, 180.0f);
        turnController.setOutputRange(-1, 1);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
        turnController.disable();
        LiveWindow.add(this);
    }

    @Override
    protected void execute() {

        if (!turnController.isEnabled()) {
            turnController.setSetpoint(angle);
            rotateToAngleRate = 0;
            turnController.enable();
        }
        double leftValue = -rotateToAngleRate;
        double rightValue = rotateToAngleRate;
        Robot.drivetrain.setRawOutput(leftValue, rightValue);

    }

    @Override
    protected boolean isFinished() {
        return turnController.onTarget();
    }

    @Override
    public void pidWrite(double output) {
        rotateToAngleRate = output;
    }

    @Override
    protected void end() {
        turnController.disable();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
