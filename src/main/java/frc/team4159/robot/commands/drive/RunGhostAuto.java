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

    private String file;
    private boolean running;

    public RunGhostAuto(String fileName) {

        file = fileName;

        drivetrain = Robot.getDrivetrain();
        requires(drivetrain);

    }

    @Override
    protected void initialize() {

        try {
            scanner = new Scanner(new File("/home/lvuser/ghost/" + file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        scanner.useDelimiter(",|\\n");

        running = true;

        System.out.println("Ghost init: " + file);

        notifier = new Notifier(this);
        notifier.startPeriodic(0.01);

    }

    @Override
    public void run() {

        if (scanner.hasNext()) {
            double left = scanner.nextDouble();
            double right = scanner.nextDouble();
            drivetrain.setRawOutput(left, right);
            System.out.println("Running: " + left + "," + right);

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
        notifier.stop();
        System.out.println("Ghost " + file + " ended.");
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}