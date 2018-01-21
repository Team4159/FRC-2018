package frc.team4159.robot.commands;

import frc.team4159.robot.Constants;
import frc.team4159.robot.RobotMap;


public class TestMPcode {

    //Running PID on left and right
    private PIDcode pidLeft;
    private PIDcode pidRight;

    //chooses which motor to apply PID to
    public TestMPcode(){
        pidLeft = new PIDcode(RobotMap.leftMotorMaster,RobotMap.leftMotorSlave);
        pidRight = new PIDcode(RobotMap.rightMotorMaster, RobotMap.rightMotorSlave);
    }

    //Resets each target value with the values in trajectory
    public void Run(Trajectory trajectory){
        for(int i =0; i < Constants.numTraPts; i++){
            pidLeft.setSetPoint(trajectory.leftWheelPoints[i][1]);
            pidRight.setSetPoint(trajectory.rightWheelPoints[i][1]);
        }
    }
}
