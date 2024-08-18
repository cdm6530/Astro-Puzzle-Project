package puzzles.clock;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * author: Chiemelie Momah
 * this is used to solve the clock puzzle for different configurations of the clock puzzle.
 */
public class Clock{


    public Clock(){

    }


    /**
     * this is used to call all the methods and classes needed to solve the puzzle.
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Clock  start end");
        }
        ClockConfig clock  = new ClockConfig(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
        Solver solve = new Solver(clock);
        Collection<Configuration> path = null;
        try {
            path = solve.PuzzleSolve();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int configs = solve.getConfigs();
        System.out.println("Hours: " + clock.getHours() + " Start: " + clock.getStart() + " End: " + clock.getEnd());
        System.out.println("Total Configs: " +  configs);
        System.out.println("Unique Configs: " + path.size()*2);
        if (clock.getEnd() > clock.getHours()){
            System.out.println("No solution");
        }int num = 0;

        for (Configuration config: path){
            String[] start = config.toString().split("\\s+");
            System.out.println("Step " + num + ": " + start[3]);
            num ++;
        }
    }
}
