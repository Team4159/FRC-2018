package frc.team4159.robot.util;

/**
 * Maps trajectory CSV filenames to a more easily usable variable name
 */

public interface TrajectoryCSV {

    String DIR = "/home/lvuser/traj/";

    String BASELINE_L =       DIR + "baseline_left_detailed.csv";
    String BASELINE_R =       DIR + "baseline_left_detailed.csv";
    String LEFT_TO_LEFT_L =   DIR + "leftToLeft_left_detailed.csv";
    String LEFT_TO_LEFT_R =   DIR + "leftToLeft_right_detailed.csv";
    String MID_TO_LEFT_L =    DIR + "midToLeft_left_detailed.csv";
    String MID_TO_LEFT_R =    DIR + "midToLeft_right_detailed.csv";
    String MID_TO_RIGHT_L =   DIR + "midToRight_left_detailed.csv";
    String MID_TO_RIGHT_R =   DIR + "midToRight_right_detailed.csv";
    String RIGHT_TO_RIGHT_L = DIR + "rightToRight_left_detailed.csv";
    String RIGHT_TO_RIGHT_R = DIR + "rightToRight_right_detailed.csv";

}
