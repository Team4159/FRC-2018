package frc.team4159.robot.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *  Currently unused. Used throughout SVR 2018 to choose auto modes on a joystick and graphically view them on the
 *  DriverStation's console.
 */
public class AutoSelector {

    private static AutoSelector instance;

    public static AutoSelector getInstance() {
        if(instance == null)
            instance = new AutoSelector();
        return instance;
    }

    private ArrayList<String> selections, positionOptions, autoOptions;
    private int selectionsIndex, positionIndex, leftIndex, rightIndex;

    private AutoSelector() {

        selections = new ArrayList<>();
        positionOptions = new ArrayList<>();
        autoOptions = new ArrayList<>();

        selections.addAll(Arrays.asList("POSITION", "LEFT ACTION", "RIGHT ACTION"));
        positionOptions.addAll(Arrays.asList("BASELINE", "LEFT", "MIDDLE", "MID RIGHT", "RIGHT"));
        autoOptions.addAll(Arrays.asList("BASE", "ONE", "TWO"));

        selectionsIndex = positionIndex = leftIndex = rightIndex = 0;

    }

    /**
     * Increment selectionsIndex. If greater or equal to selections ArrayList size, set index to 0
     */
    public void nextSelection() {
        selectionsIndex++;
        if(selectionsIndex >= selections.size()) {
            selectionsIndex = 0;
        }
    }

    /**
     * Increment option indexes for respective ArrayList based on which selection is selected
     */
    public void nextOption() {
        switch (selections.get(selectionsIndex)) {
            case "POSITION":
                positionIndex++;
                if (positionIndex >= positionOptions.size()) {
                    positionIndex = 0;
                }
                break;

            case "LEFT ACTION":
                leftIndex++;
                if (leftIndex >= autoOptions.size()) {
                    leftIndex = 0;
                }
                break;

            case "RIGHT ACTION":
                rightIndex++;
                if (rightIndex >= autoOptions.size()) {
                    rightIndex = 0;
                }
                break;
        }

    }

    /**
     * @return Selection selected based on selection index
     */
    public String getSelection() {
        return selections.get(selectionsIndex);
    }

    /**
     * @return Position selected based on position index
     */
    public String getPosition() {
        return positionOptions.get(positionIndex);
    }

    /**
     * @return Left action selected based on left index
     */
    public String getLeftAction() {
        return autoOptions.get(leftIndex);
    }

    /**
     * @return Right action selected based on right index
     */
    public String getRightAction() {
        return autoOptions.get(rightIndex);
    }

}
