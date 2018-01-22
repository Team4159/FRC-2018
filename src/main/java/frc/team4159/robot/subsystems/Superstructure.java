package frc.team4159.robot.subsystems;

/*
 * Previously known as a "CommandBase class"
 * Initialize all subsystems except drive here
 * Initialize all hardware not part of subsystems e.g. compressor, pressure switch, etc.
 * Robot.java <--> Superstructure <--> Individual subsystems
 */

public class Superstructure {

    private static Superstructure instance;

    public static Superstructure getInstance() {
        if(instance == null)
            instance = new Superstructure();
        return instance;
    }

}
