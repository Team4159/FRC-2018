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
        /* Controls intake/outtake */
        if(Robot.oi.intakeButton() && Robot.oi.outtakeButton()) {
            cubeHolder.stopFlywheels();

        } else if(Robot.oi.intakeButton()) {
            cubeHolder.intake();

        } else if(Robot.oi.outtakeButton()) {
            cubeHolder.outtake();

        } else {
            cubeHolder.stopFlywheels();
        }


        if(Robot.oi.openClaw()) {
            cubeHolder.open();
        } else {
            cubeHolder.close();
        }

        if(!Robot.oi.climbEnable()) {
            if (cubeHolder.getRawMode()) {
                cubeHolder.setRawLift(Robot.oi.getSecondaryY());
            } else {
                if (Robot.oi.setSwitchHeight())
                    cubeHolder.setToSwitch();
                else if (Math.abs(Robot.oi.getSecondaryY()) > .1)
                    cubeHolder.updatePosition(Robot.oi.getSecondaryY());
                else {
                    cubeHolder.updatePosition(0);
                }
                cubeHolder.move();
            }
        }

        if(Robot.oi.toggleLifterRawMode()){
            cubeHolder.toggleLifterRawMode();
        }
        if(Robot.oi.resetLiftEncoder()){
            cubeHolder.resetLiftEncoder();
        }

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
