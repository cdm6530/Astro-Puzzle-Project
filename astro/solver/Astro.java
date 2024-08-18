package puzzles.astro.solver;

import puzzles.astro.model.AstroConfig;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Astro {

    public static void main(String[] args) {
        //DON'T FORGET TO PULL!!!
        if (args.length != 1) {
            System.out.println("Usage: java Astro filename");
        }
        try{
            System.out.println("File: data/astro/" + args[0]);
            AstroConfig config = new AstroConfig(args[0]);
            Solver solver = new Solver(config);
            Collection<Configuration> path = solver.PuzzleSolve();
            System.out.print(config.toString());

            int configs = solver.getConfigs();
            System.out.println(STR."Total configs: \{configs}");
            System.out.println(STR."Unique configs: \{config.getUnique_configs().size()}");
            if(path.isEmpty()){
                System.out.println("No solution");
            }
            else{
            int count = 0;
            for ( Configuration configu : path){
                System.out.println(STR."Step \{count}:");
                System.out.println(configu);
                count++;
            }}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
