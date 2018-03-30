package frc.team4159.robot.commands.led;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team4159.robot.subsystems.Superstructure;

class TurnOffLED extends TimedCommand {

    TurnOffLED(double timeout) {
        super(timeout);
    }

    @Override
    protected void initialize() {
        Superstructure.getInstance().getLED().disableLEDRings();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
