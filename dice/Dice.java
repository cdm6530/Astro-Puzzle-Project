package puzzles.dice;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.*;

/**
 * author: Chiemelie Momah
 * This class is used to run the puzzles for all the configs of die configs.
 */
public class Dice {
    /**
     *
     * @param args provided in the form : java Dice start end die1 die2...
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Usage: java Dice start end die1 die2...");
        } else {
            String start = args[0];
            String end = args[1];
            int count = 0;
            ArrayList<Die> dieArrayList = new ArrayList<>();
            for (int i = 2; i < args.length; i++) {
                String flnm = "die-"+args[i]+".txt";
                Die h = new Die(flnm);
                dieArrayList.add(h);
                System.out.println("Die #" + count +": File: " + flnm + ", Faces: " + h.getFaces());
                LinkedHashMap<String, ArrayList<String>> map = h.getNeighbours();
                for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                    System.out.println("\t" + entry.getKey() + "=" + entry.getValue());
                }
                count++;
            }
            ArrayList<DiceConfig> diceConfigs = new ArrayList<>();
            for (int i = 0; i < args[0].length(); i ++){
                DiceConfig con = new DiceConfig(String.valueOf(start.charAt(i)),
                      String.valueOf(end.charAt(i)),dieArrayList.get(i));
                diceConfigs.add(con);
            }
            System.out.println("Start: " + start + " End: " + end);
            System.out.println("Total Configs:");
            System.out.println("Unique Configs:");
            int count3 = 0;
            int confignum = 0;
            int count4 = 0;
            StringBuilder sb = new StringBuilder(start);
            for (DiceConfig config : diceConfigs){
                Solver solver = new Solver(config);
                Collection<Configuration> path = solver.PuzzleSolve();
                for (Configuration configuration: path){
                    String oldsb = String.valueOf(sb);
                    sb.setCharAt(count4,configuration.toString().charAt(7));
                    String newsb = sb.toString();
                    if (!oldsb.equals(newsb)){
                        System.out.println("Step " + count3 +  ": " + sb);
                        count3++;
                    } else if (newsb.equals(start)){
                        System.out.println("Step " + count3 +  ": " + sb);
                        count3++;
                    }
                }
                if (start.length() > 1){
                    count4++;
                }
            }
        }
    }
}