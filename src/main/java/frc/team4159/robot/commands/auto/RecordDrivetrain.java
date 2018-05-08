package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.OI;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RecordDrivetrain extends Command {

    private OI oi;
    private List<String> lines;

    public RecordDrivetrain() {
        SmartDashboard.putString("Record Name", "/home/lvuser/baseline.csv");
    }

    @Override
    protected void initialize() {
        System.out.println("Recording initialized.");

        oi = OI.getInstance();
        lines = new ArrayList<>();

    }

    @Override
    public void execute() {
        String left  = String.valueOf(oi.getLeftY());
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

        String fileName = SmartDashboard.getString("Record Name", "/home/lvuser/baseline.csv");
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
