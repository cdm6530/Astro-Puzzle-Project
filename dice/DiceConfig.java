package puzzles.dice;

import puzzles.common.solver.Configuration;

import java.util.*;

/**
 * author : Chiemelie Momah
 * DiceConfiguration is used to represent a single Die Configuration, containing its current start, end goal,
 * and a representation of the  Die that stores its neighbours
 */
public class DiceConfig implements Configuration {
    private String start;
    private String end;

    private Die die;

    /**
     *
     * @param start the current face of the die
     * @param end the die we want to get to
     * @param die representation of the current die.
     */
    public DiceConfig(String start, String end,Die die){
        this.die = die;
        this.end = end;
        this.start= start;
    }

    /**
     *
     * @return the face the die is currently at.
     */
    public String getStart(){
        return this.start;
    }

    /**
     *
     * @return a representation of the current Die.
     */
    public Die getDie() {
        return die;
    }

    /**
     *
     * @return the face we want to end at.
     */
    public String getEnd() {
        return end;
    }

    /**
     *
     * @return whether the die is a solution or not.
     */
    @Override
    public boolean isSolution() {
        return this.start.equals(this.end);
    }

    /**
     *
     * @return a collection of the neighbours of a face of a die.
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        Set<Configuration> neighbours = new LinkedHashSet<>();
        for (String things : this.die.getNeighbours().get(this.start)){
            DiceConfig CONFIG = new DiceConfig(things,end,die);
            neighbours.add(CONFIG);
        }
        return  neighbours;
    }

    /**
     * used to check if an instance of DiceConfig is the same as another.
     * @param other the DiceConfig being compared.
     * @return whether they are equal or not.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DiceConfig)){
            return false;
        }else{
            DiceConfig otherdice = (DiceConfig) other;
            if (this.start.equals(otherdice.start) && this.end.equals(otherdice.end)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return hashcode of the start value.
     */
    @Override
    public int hashCode() {
        return this.start.hashCode();
    }

    /**
     *
     * @return String representation of the DiceConfig.
     */
    @Override
    public String toString() {
        return "Start: " + this.start + " End: " + this.end + " Die: " + this.die;
    }
}
