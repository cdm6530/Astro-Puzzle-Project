package puzzles.astro.model;

import puzzles.common.Coordinates;
import puzzles.common.Direction;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.DataInput;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * author : Chiemelie Momah
 * Astromodel is the model for the Astro game and contains all the logic it uses.
 */

public class AstroModel {
    /** the collection of observers of this model */
    private final List<Observer<AstroModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private AstroConfig currentConfig;

    private int movesMade;

    private boolean isSolved;

    private int row;

    private int col;

    private int current_row;

    private int current_col;

    private Coordinates astro;

    private Coordinates goallocate;

    private boolean valid_sel;

    private String filename;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<AstroModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void notifyObservers(String msg) {
        for (var observer : observers) {
            observer.update(this, msg);
        }
    }


    /**
     *
     * @param filename
     * @throws IOException
     */
    public AstroModel(String filename) throws IOException {
        load(filename);
        this.filename = filename;
        this.movesMade = 0;
        this.current_row = 0;
        this.current_col = 0;
        this.valid_sel = false;
        this.astro = new Coordinates(0,0);
        this.goallocate = new Coordinates(0,0);
        this.isSolved = false;
    }

    /**
     * loading the file into the model.
     * @param filename
     * @throws IOException
     */
    public void load(String filename) throws IOException {
        AstroConfig config = new AstroConfig(filename);
        this.currentConfig = config;
        this.row = this.currentConfig.getRow();
        this.col = this.currentConfig.getCol();
        this.astro = this.currentConfig.getAstro();
        this.goallocate = this.currentConfig.getGoalLocation();
        String loaded = "Loaded " + filename;
        this.notifyObservers(loaded);
    }

    public int getRow(){
        return this.currentConfig.getRow();
    }

    public int getCol(){
        return this.currentConfig.getCol();
    }

    public String[][] getGrid(){
        return this.currentConfig.getBoard();
    }

    public void setAstro(Coordinates coordinates){
        this.astro = coordinates;
    }

    public boolean isSolved(){
        return this.isSolved;
    }
    public String getFilename(){
        return this.filename;
    }

    /**
     * selecting an item in the grid.
     * @param row the row of the item
     * @param col column of the item.
     */
    public void select(int row,int col){
        if (this.currentConfig.getBoard()[row][col].equals(".") ){
            System.out.println("No Piece at" + " (" + row + ","  + col + ")" );
            this.valid_sel = false;
        }
        else{
            this.current_row = row;
            this.current_col = col;
            this.valid_sel = true;
            System.out.println(STR."Selected  (\{row},\{col})");
        }
    }

    /**
     * gives the player a hint for the next step.
     * @throws IOException
     */
    public void hint() throws IOException {
        Solver solver = new Solver(this.currentConfig);
        Collection<Configuration> path = solver.PuzzleSolve();
        boolean found = false;
        if (this.currentConfig.isSolution()){
            this.isSolved = true;
            //notifyObservers("Already Solved");
        }
        else if (path.contains(this.currentConfig)){
            for (Configuration config : path) {
                if (found) {
                    System.out.println("Succesful");
                    this.currentConfig = (AstroConfig) config;
                    break;
                }
                if (config.equals(this.currentConfig)) {
                    found = true;
                }
            }
        }
        else{
            System.out.println("No solution");
        }

    }

    public boolean isValid_sel(){
        return this.valid_sel;
    }

    public void reset(String filename) throws IOException {
        load(filename);
        notifyObservers("Puzzle: reset!");
    }

    public void quit(){
        System.exit(0);
    }

    /**
     * allows the user to move in a certain direction after selcting an item.
     * @param direction
     */
    public void move(Direction direction){
        int up  = current_row - 1;
        int right = current_col + 1;
        int down = current_row + 1;
        int left = current_col - 1;
        if (this.currentConfig.isSolution()){
            this.isSolved = true;
            notifyObservers("Already Solved");
        }
        else if (this.valid_sel) {
            if (direction == Direction.NORTH) {
                while (up >= 0) {
                    if (!(this.currentConfig.getBoard()[up][current_col].equals(".") ||
                            this.currentConfig.getBoard()[up][current_col].equals("*"))) {
                        this.currentConfig.getBoard()[up + 1][current_col] = this.currentConfig.getBoard()[current_row][current_col];
                        this.currentConfig.getBoard()[current_row][current_col] = ".";
                        int val = up + 1;
                        notifyObservers("Moved  (" + current_row + "," + current_col + ")  to " + "(" + val + "," + current_col + ")");
                        break;
                    }
                    up--;
                }
            }
            if (direction == Direction.EAST) {
                while (right < this.currentConfig.getCol()) {
                    if (!(this.currentConfig.getBoard()[current_row][right].equals(".") || this.currentConfig.getBoard()[current_row][right].equals("*"))) {
                        this.currentConfig.getBoard()[current_row][right - 1] = this.currentConfig.getBoard()[current_row][current_col];
                        this.currentConfig.getBoard()[current_row][current_col] = ".";
                        int val = right - 1;
                        notifyObservers("Moved  (" + current_row + "," + current_col + ")  to " + "(" + val + "," + current_col + ")");
                        break;
                    }
                    right++;
                }
            }
            if (direction == Direction.SOUTH) {
                while (down < this.currentConfig.getRow()) {
                    if (!(this.currentConfig.getBoard()[down][current_col].equals(".") || this.currentConfig.getBoard()[down][current_col].equals("*"))) {
                        this.currentConfig.getBoard()[down - 1][current_col] = this.currentConfig.getBoard()[current_row][current_col];
                        this.currentConfig.getBoard()[current_row][current_col] = ".";
                        int val = down - 1;
                        notifyObservers("Moved  (" + current_row + "," + current_col + ")  to " + "(" + val + "," + current_col + ")");
                        break;
                    }
                    down++;
                }
            }
            if (direction == Direction.WEST) {
                while (left >= 0) {
                    if (!(this.currentConfig.getBoard()[current_row][left].equals(".") || this.currentConfig.getBoard()[current_row][left].equals("*"))) {
                        this.currentConfig.getBoard()[current_row][left + 1] = this.currentConfig.getBoard()[current_row][current_col];
                        this.currentConfig.getBoard()[current_row][current_col] = ".";
                        int val = left + 1;
                        notifyObservers("Moved  (" + current_row + "," + current_col + ")  to " + "(" + val + "," + current_col + ")");
                        break;
                    }
                    left--;
                }
            }

        }
        else if (!this.valid_sel){
            System.out.println("> No piece selected");
        }
        else{
            if (direction == Direction.NORTH){
                int val = up + 1 ;
            System.out.println("Cant move from (" +current_row +"," + current_col + ") to (" + val + "," + current_col+ ")" );}
        }


    }

    public String toString(){
        int numRows = this.currentConfig.getRow();
        int numCols = this.currentConfig.getCol();

        StringBuilder sb = new StringBuilder();

        sb.append("  ");
        for (int i = 0; i < numCols; i++) {
            sb.append(i).append(" ");
        }
        sb.append("\n");
        sb.append("  ");
        for (int i = 0; i < numCols; i++) {
            sb.append("--");
        }
        sb.append("\n");
        for (int i = 0; i < numRows; i++) {
            sb.append(i).append("| ");
            for (int j = 0; j < numCols; j++) {
                sb.append(this.currentConfig.getBoard()[i][j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
