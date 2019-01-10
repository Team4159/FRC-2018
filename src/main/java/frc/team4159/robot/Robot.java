package frc.team4159.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team4159.robot.subsystems.Drivetrain;

public class Robot extends TimedRobot {

    //robot code starts!! (like public void string args in coding)
    private Drivetrain drivetrain;

    private OI oi;

    private static Command auto;

    @Override
    public void robotInit(){
        drivetrain = Drivetrain.getInstance();
        oi = OI.getInstance();
    }

    @Override
    public void disabledInit(){

    }

    @Override
    public void disabledPeriodic(){

    }

    //@Override
    //public void autonomousInit()

    //}

    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();

    }
    // loops during antonomous

    @Override
    public void teleopInit(){
        if (auto != null) {
            auto.cancel();
        }

    }
    // when u control

    @Override
    public void teleopPeriodic(){

        Scheduler.getInstance().run();
    }
    // when it repeats the controls

    public Drivetrain getDrivetrain(){

        return drivetrain;
    }
}