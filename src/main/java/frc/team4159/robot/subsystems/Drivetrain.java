package frc.team4159.robot.subsystems;


import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4159.robot.command.Drive;

public class Drivetrain extends Subsystem {
    //to control the drivetrain; the wheels on the robot

    private static Drivetrain instance;

    public static Drivetrain getInstance(){
        if(instance == null)
            instance = new Drivetrain();
        return instance;

    }

    private VictorSP leftVictorOne;
    private VictorSP leftVictorTwo;
    private VictorSP rightVictorOne;
    private VictorSP rightVictorTwo;


    private Drivetrain(){

        leftVictorOne = new VictorSP(2);
        rightVictorTwo = new VictorSP(3);
        rightVictorOne = new VictorSP(4);
        leftVictorTwo = new VictorSP(1);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }

    public void setSpeed (double left, double right){
        leftVictorOne.set(left);
        leftVictorTwo.set(left);
        rightVictorOne.set(right);
        rightVictorTwo.set(right);
    }
}