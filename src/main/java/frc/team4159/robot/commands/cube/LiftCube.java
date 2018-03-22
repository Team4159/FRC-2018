package frc.team4159.robot.commands.cube;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.CubeHolder;
import frc.team4159.robot.subsystems.Superstructure;


public class LiftCube extends Command {

    private CubeHolder cubeHolder;

    public LiftCube() {
        requires(Superstructure.cubeHolder);
        cubeHolder = Superstructure.getInstance().getCubeHolder();
    }

    @Override
    protected void execute() {

        /*
         * Intake and outtake wheel logic
         */
        if(Robot.oi.intakeButton() && Robot.oi.outtakeButton()) {
            cubeHolder.stopFlywheels();

        } else if(Robot.oi.intakeButton()) {
            cubeHolder.intake();

        } else if(Robot.oi.outtakeButton()) {
            cubeHolder.outtake();

        } else {
            cubeHolder.stopFlywheels();
        }


        /*
         * Open claw if trigger is pressed. Closed by default.
         */
        if(Robot.oi.openClaw()) {
            cubeHolder.open();
        } else {
            cubeHolder.close();
        }

        /*
         * Use secondary y-axis to control lifter if not used to control climbing
         */
        if(!Robot.oi.climbEnable()) {

            if (cubeHolder.getRawMode()) {
                cubeHolder.setRawLift(Robot.oi.getSecondaryY());

            } else {
                if (Robot.oi.setSwitchHeight())
                    cubeHolder.setToSwitch();
                else if(Robot.oi.setLiftTargetZero())
                    cubeHolder.setTargetPosition(0);
                else if (Math.abs(Robot.oi.getSecondaryY()) > .1)
                    cubeHolder.updatePosition(Robot.oi.getSecondaryY());

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
