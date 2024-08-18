package puzzles.clock;


import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * author : Chiemelie Momah
 * ClockConfig is used to represent a single clock configuration.
 */
public class ClockConfig implements Configuration {

    private int start;
    private int end;
    private int hours;


    /**
     *
     * @param hours the number of hours the clock has
     * @param start the hand the clock is currently at
     * @param end the hand the clock wants to reach.
     */
    public ClockConfig(int hours,int start,int end){
        this.hours = hours;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean isSolution() {
        return this.start == this.end;
    }

    public int getStart(){
        return this.start;
    }

    public int getEnd(){
        return this.end;
    }

    public int getHours(){
        return this.hours;
    }

    /**
     * gets the neighbours of a given Clock Config
     * @return: a collection of the Neighbours.
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        Collection<Configuration> neighbours = new ArrayList<>();
        if (hours  == 12) {
            if (start == 1) {
                neighbours.add(new ClockConfig(this.hours, 12, end));
                neighbours.add(new ClockConfig(this.hours, start + 1, end));
            } else if (start == 12) {
                neighbours.add(new ClockConfig(this.hours, 1, end));
                neighbours.add(new ClockConfig(this.hours, start - 1, end));
            }
            else{
                neighbours.add(new ClockConfig(this.hours,start+1,end));
                neighbours.add(new ClockConfig(this.hours,start-1,end));
            }
        } else if (hours == 24) {
            if (start == 1) {
                neighbours.add(new ClockConfig(this.hours, 24, end));
                neighbours.add(new ClockConfig(this.hours, start + 1, end));
            } else if (start == 24) {
                neighbours.add(new ClockConfig(this.hours, 1, end));
                neighbours.add(new ClockConfig(this.hours, start - 1, end));
            }
            else{
                neighbours.add(new ClockConfig(this.hours,start+1,end));
                neighbours.add(new ClockConfig(this.hours,start-1,end));
            }

        }
        else if (hours == 100) {
            if (start == 1) {
                neighbours.add(new ClockConfig(this.hours, 100, end));
                neighbours.add(new ClockConfig(this.hours, start + 1, end));
            } else if (start == 100) {
                neighbours.add(new ClockConfig(this.hours, 1, end));
                neighbours.add(new ClockConfig(this.hours, start - 1, end));
            } else {
                neighbours.add(new ClockConfig(this.hours, start + 1, end));
                neighbours.add(new ClockConfig(this.hours, start - 1, end));
            }
        }
        else if(hours == 1000) {
            if (start == 1) {
                neighbours.add(new ClockConfig(this.hours, 1000, end));
                neighbours.add(new ClockConfig(this.hours, start + 1, end));
            } else if (start == 1000) {
                neighbours.add(new ClockConfig(this.hours, 1, end));
                neighbours.add(new ClockConfig(this.hours, start - 1, end));
            }
            else{
                neighbours.add(new ClockConfig(this.hours,start+1,end));
                neighbours.add(new ClockConfig(this.hours,start-1,end));
            }
        }

        return neighbours;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ClockConfig)){
            return false;
        }else{
            ClockConfig otherclock  = (ClockConfig) other;
            if ((otherclock.start == this.start)&&(otherclock.end == this.end)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.start*(this.hours-8)/this.end;
    }

    @Override
    public String toString() {
        return "Hours: " + getHours() + " Start: " +  getStart() + " End: " +  getEnd();
    }
}
