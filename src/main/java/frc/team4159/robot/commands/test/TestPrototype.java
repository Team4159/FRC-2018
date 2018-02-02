package frc.team4159.robot.commands.test;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import frc.team4159.robot.OI;
import frc.team4159.robot.subsystems.Superstructure;


public class TestPrototype extends Command {

    public TestPrototype() {
        requires(Robot.superstructure.prototype);
    }

    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {

    }

    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        Robot.superstructure.prototype.operatePiston(Robot.oi.intakeOpenPiston());

        if(Robot.oi.intakeButton()){
            Robot.superstructure.prototype.setIntake(1.0);
        }else if(Robot.oi.outtakeButton()){
            Robot.superstructure.prototype.setIntake(-1.0);
        }else{
            Robot.superstructure.prototype.setIntake(0.0);
        }

        Robot.superstructure.prototype.setLifter(Robot.oi.getSecondaryY());
    }

    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
