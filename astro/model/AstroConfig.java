package puzzles.astro.model;

import puzzles.common.Coordinates;
import puzzles.common.solver.Configuration;
import puzzles.hoppers.model.HoppersConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * author : Chiemelie Momah
 * Configuration for the astro game.
 */
// TODO: implement your HoppersConfig for the common solver

public class AstroConfig implements Configuration{
    private static int row;
    private static int col;
    private final  static String EMPTY = ".";
    private Coordinates goalLocation;
    private Coordinates astro;
    private Map<String,Coordinates> ships;

    protected static int configs;
    private String[][] board;

    private Set<Configuration> unique_configs;

    /**
     * reads in the configuration from the file.
     * @param filename
     * @throws IOException
     */

    public AstroConfig(String filename) throws IOException {
        try(BufferedReader in = new BufferedReader(new FileReader(filename))){
            this.unique_configs = new HashSet<>();
            this.ships = new TreeMap<>();
            String line = in.readLine();
            String[] rowcol = line.split("\\s++");
            row = Integer.parseInt(rowcol[0]);
            col = Integer.parseInt(rowcol[1]);
            this.board = new String[row][col];


            String[] goal = in.readLine().split("\\s++");
            this.goalLocation = new Coordinates(goal[1]);
            this.board[this.goalLocation.row()][this.goalLocation.col()] = "*" ;  //storing the goal location on the board
            String[] astronaut = in.readLine().split("\\s++");
            this.astro = new Coordinates(astronaut[1]);
            ships.put(astronaut[0],this.astro);
            this.board[this.astro.row()][this.astro.col()] = "A";
            int num_robots = Integer.parseInt(in.readLine());
            while((line = in.readLine()) != null){
                //STORING THE LOCATIONS OF THE SHIPS
                if (line.contains(",")){
                    String[] robot = line.split("\\s+");
                    String name = robot[0];
                    String[] coordinates = robot[1].split(",");
                    int s_row = Integer.parseInt(coordinates[0]);
                    int s_col = Integer.parseInt(coordinates[1]);
                    Coordinates location = new Coordinates(s_row,s_col);
                    this.board[s_row][s_col] = name;
                    ships.put(name,location);
                }
            }
            for (int i = 0; i < row; i++){
                for (int j = 0; j < col ; j++){
                    if (this.board[i][j] == null){
                        this.board[i][j] = EMPTY;
                    }
                }
            }

        }
    }

    /**
     * copy constructor for astro config when making neighbours
     * @param other
     */
    public AstroConfig(AstroConfig other){
        this.board = new String[row][col];
        this.ships = other.ships;
        this.goalLocation = other.goalLocation;
        this.astro = other.astro;
        this.unique_configs = other.unique_configs;
        for (int r = 0; r < row; ++r) {
            System.arraycopy(other.board[r], 0, this.board[r], 0, col);
        }
    }

    public Map<String,Coordinates> getShips(){
        return this.ships;
    }

    public String[][] getBoard(){
        return this.board;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    @Override
    public boolean isSolution() {
        return this.astro.row() == goalLocation.row() && this.astro.col() == goalLocation.col();
    }

    public Coordinates getAstro(){
       return this.astro;
    }

    public Coordinates getGoalLocation(){
        return this.goalLocation;
    }

    /**
     * return the possible movements for certain positions in a grid.
     * @return
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        Set<Configuration> neighbours = new LinkedHashSet<>();
        LinkedList<String> map = new LinkedList<>(ships.keySet());
        LinkedList<Coordinates> coords = new LinkedList<>();
        for (int i = 0;i < row; i++){
            for (int j = 0; j< col;j++){
                if (!(this.board[i][j].equals(".")|| this.board[i][j].equals("*"))){
                    coords.add(new Coordinates(i,j));
                }
            }
        }

        for (Coordinates positions : coords){
            int right = positions.col() + 1;
            int down = positions.row() + 1;
            int left = positions.col() -1;
            int up = positions.row() - 1;
            while (right < col){
                if (map.contains(this.board[positions.row()][right])) {
                    //if (this.board[positions.row()][right-1].equals(".") || this.board[positions.row()][right-1].equals("*")) {
                        AstroConfig neighbour = new AstroConfig(this);
                        if (this.board[positions.row()][positions.col()].equals("A")) {
                            neighbour.astro = new Coordinates(positions.row(), right - 1);
                            ships.replace("A", new Coordinates(positions.row(), right - 1));
                        }
                        neighbour.board[positions.row()][positions.col()] = ".";
                        neighbour.board[positions.row()][right - 1] = this.board[positions.row()][positions.col()];
                        neighbours.add(neighbour);
                        unique_configs.add(neighbour);
                        configs++;
                        break;
                   // }
                }
                right ++;
            }
            while (down < row){
                if (map.contains(this.board[down][positions.col()])) {
                   // if (this.board[down-1][positions.col()].equals(".") || this.board[down-1][positions.col()].equals("*")) {
                        AstroConfig neighbour = new AstroConfig(this);
                        if (this.board[positions.row()][positions.col()].equals("A")) {
                            neighbour.astro = new Coordinates(down - 1, positions.col());
                            ships.replace("A", new Coordinates(down - 1, positions.col()));
                        }
                        neighbour.board[positions.row()][positions.col()] = ".";
                        neighbour.board[down - 1][positions.col()] = this.board[positions.row()][positions.col()];
                        neighbours.add(neighbour);
                        unique_configs.add(neighbour);
                        configs++;
                        break;
                   // }
                }
                down ++;
            }
            while(left >= 0){
                if(map.contains(this.board[positions.row()][left])) {
                    //if (this.board[positions.row()][left + 1].equals(".")||this.board[positions.row()][left + 1].equals("*")) {
                        AstroConfig neighbour = new AstroConfig(this);
                        if (this.board[positions.row()][positions.col()].equals("A")) {
                            neighbour.astro = new Coordinates(positions.row(), left + 1);
                            ships.replace("A", new Coordinates(positions.row(), left + 1));
                        }
                        neighbour.board[positions.row()][positions.col()] = ".";
                        neighbour.board[positions.row()][left + 1] = this.board[positions.row()][positions.col()];
                        neighbours.add(neighbour);
                        unique_configs.add(neighbour);
                        configs++;
                        break;

                }
                left--;
            }
            while(up >= 0){
                if (map.contains(this.board[up][positions.col()])) {
                    AstroConfig neighbour = new AstroConfig(this);
                        if (this.board[positions.row()][positions.col()].equals("A")) {
                            neighbour.astro = new Coordinates(up + 1, positions.col());
                            ships.replace("A", new Coordinates(up + 1, positions.col()));
                        }
                        neighbour.board[positions.row()][positions.col()] = ".";
                        neighbour.board[up + 1][positions.col()] = this.board[positions.row()][positions.col()];
                        neighbours.add(neighbour);
                        unique_configs.add(neighbour);
                        configs++;
                        break;

                }
                up--;
            }
        }
        return neighbours;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        AstroConfig that = (AstroConfig) other;
        return Arrays.deepEquals(board, that.board);
    }

    public int getConfigs(){
        return configs;
    }

    public Set<Configuration> getUnique_configs(){
        return this.unique_configs;
    }
    @Override
    public int hashCode() { return Arrays.deepHashCode(board); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String[] row : board) {
            for (String cell : row) {
                sb.append(cell).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
