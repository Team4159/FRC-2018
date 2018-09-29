package frc.team4159.robot.commands.drive;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4159.robot.OI;
import frc.team4159.robot.util.Constants;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RecordDrivetrain extends Command implements Runnable {

    private Notifier notifier;
    private Constants constants;
    private OI oi;
    private List<String> lines;

    @Override
    protected void initialize() {

        constants = Constants.getInstance();
        oi = OI.getInstance();
        lines = new ArrayList<>();

        System.out.println("Recording init: " + constants.getString("ghostFile"));

        notifier = new Notifier(this);
        notifier.startPeriodic(0.01);

    }

    @Override
    public void run() {
        String left   = String.valueOf(oi.getLeftY());
        String right  = String.valueOf(oi.getRightY());
        lines.add(left + "," + right);
        System.out.println("Recorded: " + left + ", " + right);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

        notifier.stop();

        String fileName = "/home/lvuser/ghost/" + constants.getString("ghostFile");
        Path file = Paths.get(fileName);

        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
            System.out.println("Recording ended. Saved at " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}