package frc.team4159.robot.commands.led;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.LED;
import frc.team4159.robot.subsystems.Superstructure;

public class SolidLED extends TimedCommand {

    private LED led = Superstructure.getInstance().getLED();

    public SolidLED() {
        super(15);
        requires(led);
    }

    @Override
    protected void initialize() {
        led.enableLEDRings();
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        led.disableLEDRings();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
