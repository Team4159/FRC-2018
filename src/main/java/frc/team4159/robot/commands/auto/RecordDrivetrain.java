package frc.team4159.robot.commands.auto;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4159.robot.OI;

import java.io.*;


public class RecordDrivetrain extends Command implements Runnable {

    private Notifier notifier;
    private OI oi;
    private String fileName;
    private PrintWriter writer;

    public RecordDrivetrain() {
        SmartDashboard.putString("Record Name", "baseline.csv");
    }

    @Override
    protected void initialize() {
        notifier = new Notifier(this);
        oi = OI.getInstance();
        double period = 0.01;
        fileName = SmartDashboard.getString("Record Name", "baseline.csv");
        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        notifier.startPeriodic(period);
        System.out.println("Recording initialized.");
    }

    @Override
    public void run() {
        double left  = oi.getLeftY();
        double right = oi.getRightY();
        writer.println(left + "," + right);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        notifier.stop();
        writer.close();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
