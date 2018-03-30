package frc.team4159.robot.commands.led;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team4159.robot.subsystems.Superstructure;

class TurnOnLED extends TimedCommand {

    TurnOnLED(double timeout) {
        super(timeout);
    }

    @Override
    protected void initialize() {
        Superstructure.getInstance().getLED().enableLEDRings();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

}
