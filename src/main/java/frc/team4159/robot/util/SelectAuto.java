package frc.team4159.robot.util;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;


public class SelectAuto {

    private AutoSelector autoSelector = Robot.getAutoSelector();

    SelectAuto() {
        while(true) {
            if(Robot.oi.getAutoOptionButton()){
                autoSelector.nextOption();
            }

            if(Robot.oi.getAutoSelectionButton()){
                autoSelector.nextSelection();
            }
        }

    }

}
