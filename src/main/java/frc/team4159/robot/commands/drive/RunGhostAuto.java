package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Drivetrain;

import java.io.*;
import java.util.Scanner;

public class RunGhostAuto extends Command implements Runnable {

    private Drivetrain drivetrain;
    private Scanner scanner;
    private Notifier notifier;

    private String fileName;
    private boolean running;

    public RunGhostAuto(String fileName) {

        drivetrain = Robot.getDrivetrain();
        requires(drivetrain);

        fileName = this.fileName;

        try {
            scanner = new Scanner(new File("/home/lvuser/ghost/" + fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        scanner.useDelimiter(",|\\n");

        running = true;
    }

    @Override
    protected void initialize() {

        notifier = new Notifier(this);
        notifier.startPeriodic(0.05);
        System.out.println("Ghost init: " + fileName);

    }

    @Override
    public void run() {

        if (scanner.hasNext()) {
            double left = scanner.nextDouble();
            double right = scanner.nextDouble();
            drivetrain.setRawOutput(left, right);
        } else {
            running = false;
        }

    }

    @Override
    protected boolean isFinished() {
        return !running;
    }

    @Override
    protected void end() {
        System.out.println("Ghost " + fileName + " ended.");
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}