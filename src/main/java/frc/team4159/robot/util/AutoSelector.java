package frc.team4159.robot.util;

import java.util.ArrayList;
import java.util.Arrays;

public class AutoSelector {

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

    public void nextSelection() {

        selectionsIndex++;

        if(selectionsIndex >= selections.size()) {
            selectionsIndex = 0;
        }

    }

    public void nextOption() {
        switch (selections.get(selectionsIndex)) {
            case "POSITION":
                positionIndex++;
                if (positionIndex >= positionOptions.size())
                    positionIndex = 0;

                break;
            case "LEFT ACTION":
                leftIndex++;
                if (leftIndex >= autoOptions.size())
                    leftIndex = 0;

                break;
            case "RIGHT ACTION":
                rightIndex++;
                if (rightIndex >= autoOptions.size())
                    rightIndex = 0;

                break;
        }

    }

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

}
