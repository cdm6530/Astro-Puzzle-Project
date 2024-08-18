package puzzles.common.solver;


import puzzles.clock.ClockConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.*;

/**
 * author: Chiemelie Momah
 * Solver uses the BFS algorithm to solve any given type of Configuration puzzle.
 */
public class Solver {


    private Configuration configuration;

    private int configs;

    /**
     *
     * @param configuration the configuration being solved.
     */
    public Solver(Configuration configuration) {
        this.configuration = configuration;
        this.configs = 0;
    }

    public int getConfigs(){
        return this.configs;
    }

    /**
     * uses BFS to solve the given configuration.
     * @return the path taken to the solution
     */
    public Collection<Configuration> PuzzleSolve() throws IOException {
        List <Configuration> path = new LinkedList<>();
//        Collection<Configuration> neighbors = configuration.getNeighbors();
        Map<Configuration,Configuration> predmap = new HashMap<>();
        List<Configuration> queue = new LinkedList<>();
        predmap.put(configuration,null);
        queue.add(configuration);
        this.configs++;
//        for (Configuration config: neighbors) {
//            configs++;
//            predmap.put(config, configuration);
//            queue.add(config);
//            queue.remove(configuration);
//        }
        Configuration current;
        while (!queue.isEmpty()) {
            current = queue.removeFirst();
            if (current.isSolution()) {
                path.add(current);
                Configuration con =predmap.get(current);
                while (con != null){
                    path.addFirst(con);
                    con = predmap.get(con);
                }
//                this.configs = (predmap.size()*2)-1;
                break;
            }
            else{
                for (Configuration neighbours : current.getNeighbors()){
                    this.configs++;
                    if (!(predmap.containsKey(neighbours))) {
//                        configs++;
                        predmap.put(neighbours, current);
                        queue.add(neighbours);
                    }
                }
            }


        }
        return path;
    }
}
