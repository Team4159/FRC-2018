package frc.team4159.robot.util;

public class AutoSelector {

    private static AutoSelector instance;

    public static AutoSelector getInstance() {
        if(instance == null)
            instance = new AutoSelector();
        return instance;
    }

    private String[] leftOptions, rightOptions;
    private int leftIndex, rightIndex;

    private AutoSelector() {
        leftOptions = new String[]{"BASELINE", "BASELINE_DROP", "MID_TO_LEFT", "MID_TO_RIGHT", "LEFT_TO_LEFT", "RIGHT_TO_RIGHT"};
        rightOptions = new String[]{ "BASELINE", "BASELINE_DROP", "MID_TO_LEFT", "MID_TO_RIGHT", "LEFT_TO_LEFT", "RIGHT_TO_RIGHT"};
        leftIndex = rightIndex = 0;
    }

    /**
     * Increments left index. If greater than or equal to left array length, set to 0
     */
    public void nextLeftSelection() {
        leftIndex++;
        if(leftIndex >= leftOptions.length) {
            leftIndex = 0;
        }
    }

    public void nextRightSelection() {
        rightIndex++;
        if(rightIndex >= rightOptions.length) {
            rightIndex = 0;
        }
    }

    /**
     * @return Left auto action based on left index
     */
    public String getLeftAuto() {
        return leftOptions[leftIndex];
    }

    /**
     * @return Right auto action based on right index
     */
    public String getRightAuto() {
        return rightOptions[rightIndex];
    }

}
