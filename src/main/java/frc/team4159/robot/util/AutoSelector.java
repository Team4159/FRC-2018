package frc.team4159.robot.util;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.Arrays;

public class AutoSelector extends Subsystem {

    private static AutoSelector instance;

    public static AutoSelector getInstance() {
        if(instance == null)
            instance = new AutoSelector();
        return instance;
    }

    private ArrayList<String> selections;
    private ArrayList<String> positionOptions;
    private ArrayList<String> autoOptions;

    private int selectionsIndex = 0;
    private int positionIndex = 0;
    private int leftIndex = 0;
    private int rightIndex = 0;

    private AutoSelector() {
        selections = new ArrayList<>();
        positionOptions = new ArrayList<>();
        autoOptions = new ArrayList<>();

        selections.addAll(Arrays.asList("POSITION", "LEFT ACTION", "RIGHT ACTION"));
        positionOptions.addAll(Arrays.asList("LEFT", "MIDDLE", "RIGHT"));
        autoOptions.addAll(Arrays.asList("BASE", "ONE", "TWO"));
    }

//    public String getSelection() {
//        return selections.get(selectionsIndex);
//    }

    public void nextSelection() {

        selectionsIndex++;

        if(selectionsIndex >= selections.size()) {
            selectionsIndex = 0;
        }

        System.out.println(selections.get(selectionsIndex));
    }

    public void nextOption() {
        switch (selections.get(selectionsIndex)) {
            case "POSITION":
                positionIndex++;
                if (positionIndex >= positionOptions.size())
                    positionIndex = 0;
                System.out.println(positionOptions.get(positionIndex));

                break;
            case "LEFT ACTION":
                leftIndex++;
                if (leftIndex >= autoOptions.size())
                    leftIndex = 0;
                System.out.println(autoOptions.get(leftIndex));

                break;
            case "RIGHT ACTION":
                rightIndex++;
                if (rightIndex >= autoOptions.size())
                    rightIndex = 0;
                System.out.println(autoOptions.get(rightIndex));

                break;
        }

    }

//    public String getOption() {
//        switch (selections.get(selectionsIndex)) {
//            case "POSITION":
//                return positionOptions.get(positionIndex);
//            case "LEFT ACTION":
//                return autoOptions.get(leftIndex);
//            case "RIGHT ACTION":
//                return autoOptions.get(rightIndex);
//            default:
//                return "";
//        }
//    }

    public String getSelection() {
        return selections.get(selectionsIndex);
    }

    public String getPosition() {
        return positionOptions.get(positionIndex);
    }

    public String getLeftAction() {
        return autoOptions.get(leftIndex);
    }
    public String getRightAction() {
        return autoOptions.get(rightIndex);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new SelectAuto());
    }
}

