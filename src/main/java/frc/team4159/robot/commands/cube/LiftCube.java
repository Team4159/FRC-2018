package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.CubeHolder;
import frc.team4159.robot.subsystems.Superstructure;


public class LiftCube extends Command {

    private CubeHolder cubeHolder = Superstructure.getInstance().getCubeHolder();

    public LiftCube() {
        requires(Superstructure.cubeHolder);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {

        /* Control intake/outtake */
        if(Robot.oi.intakeButton() && Robot.oi.outtakeButton()) {
            cubeHolder.stopFlywheels();

        } else if(Robot.oi.intakeButton()) {
            cubeHolder.intake();

        } else if(Robot.oi.outtakeButton()) {
            cubeHolder.outtake();

        } else {
            cubeHolder.stopFlywheels();
        }

        /* Control piston claw. Closes by default. Intakes automatically when claw is open. */
        if(Robot.oi.openClaw()) {
            cubeHolder.intake();
            cubeHolder.open();
        } else {
            cubeHolder.close();
        }

        /* Updates target position to feed into position PID based on joystick values */
        if(Math.abs(Robot.oi.getSecondaryY()) > .1)
            cubeHolder.updatePosition(Robot.oi.getSecondaryY());
        else {
            cubeHolder.updatePosition(0); // Don't update current lift position
        }

        cubeHolder.move();
        cubeHolder.logDashboard();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        cubeHolder.stopFlywheels();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
