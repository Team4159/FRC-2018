package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.Robot;
import frc.team4159.robot.subsystems.Drivetrain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class RunGhostAuto extends Command implements Runnable {

    private Drivetrain drivetrain;
    private Notifier notifier;
    private String csvFile, line, csvSplitBy;
    private boolean running;

    public RunGhostAuto(String fileName) {
        drivetrain = Robot.getDrivetrain();
        requires(drivetrain);
        csvFile = fileName;
        line = "";
        csvSplitBy = ",";
        running = true;
    }

    @Override
    protected void initialize() {
        double period = 0.01;
        notifier = new Notifier(this);
        notifier.startPeriodic(period);
    }

    @Override
    public void run() {

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] values = line.split(csvSplitBy);
                double left = Double.parseDouble(values[0]);
                double right = Double.parseDouble(values[1]);
                drivetrain.setRawOutput(left, right);

            }

            running = false;

        } catch (IOException e) {
            e.printStackTrace();
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
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
