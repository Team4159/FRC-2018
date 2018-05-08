package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Drivetrain;

import java.io.*;
import java.util.Scanner;

public class RunGhostAuto extends Command {

    private Drivetrain drivetrain;
    private Scanner scanner;

    private boolean running;

    public RunGhostAuto(String fileName) {
        drivetrain = Robot.getDrivetrain();
        requires(drivetrain);
        try {
            String csv = SmartDashboard.getString("Record Name", "/home/lvuser/baseline.csv");
            scanner = new Scanner(new File("home/lvuser/" + fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner.useDelimiter(",|\\n");

        running = true;
    }

    @Override
    protected void initialize() {
        System.out.println("Ghost auto initialized.");

    }

    @Override
    protected void execute() {
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
        System.out.println("Run ghost auto ended");
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
