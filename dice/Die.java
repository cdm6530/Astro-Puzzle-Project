package puzzles.dice;

import java.io.*;
import java.util.List;
import java.util.*;

/**
 * author : Chiemelie Momah
 * this class is used to represent a single die
 */
public class Die {

    private String filename;
    private int faces;

    private LinkedHashMap<String, ArrayList<String> > neighbours;

    /**
     * this reads the given file and stores the faces and neighbours of the die.
     * @param filename the die file
     * @throws IOException
     */

    public Die(String filename)throws IOException {
        this.neighbours = new LinkedHashMap<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filename))){
            String faces = in.readLine();
            this.faces = Integer.parseInt(faces);
            String line = in.readLine();
            String[] sides = line.split("\\s+");
            for(String side : sides) {
                this.neighbours.put(side,new ArrayList<>());
            }

            while((line = in.readLine()) != null){
                String[] list = line.split("\\s+");
                String face = list[0];
                for (String str : list){
                    if (!str.equals(face)){
                        this.neighbours.get(face).add(str);
                    }
                }
            }

        }
    }

    public LinkedHashMap<String,ArrayList<String>> getNeighbours(){
        return this.neighbours;
    }

    public int getFaces(){
        return this.faces;
    }


    public String toString(){
        return  "faces" + getFaces();
    }
}
