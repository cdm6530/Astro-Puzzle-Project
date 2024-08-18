package puzzles.astro.gui;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import puzzles.astro.model.AstroModel;
import puzzles.common.Coordinates;
import puzzles.common.Direction;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * author: Chiemelie Momah
 * GUI Implementation of the Astro Game, contains view and controller
 */
public class AstroGUI extends Application implements Observer<AstroModel,String> {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    private Button[][] boardgrid;

    // for demonstration purposes
    private Image bluerobot = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"robot-blue.png"));
    private Image greenrobot = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"robot-green.png"));
    private Image lightbluerobot = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"robot-lightblue.png"));
    private Image whiterobot = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"robot-white.png"));
    private Image yellowrobot = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"robot-yellow.png"));
    private Image purplerobot = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"robot-purple.png"));
    private Image orangerobot = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"robot-orange.png"));
    private Image pinkrobot = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"robot-pink.png"));
    private Image astro = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"astro.png"));
    private Image earth = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"earth.png"));
    private BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("resources/space.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    private Background background = new Background(backgroundImage);

    /** The size of all icons, in square dimension */
    private final static int ICON_SIZE = 75;

    private String filename;

    private AstroModel model;

    private GridPane gridPane;

    private  Label label;


    /**
     * initializing the GUI and making it an observer of the model.
     * @throws IOException
     */
    public void init() throws IOException {
        this.filename = getParameters().getRaw().get(0);
        this.model = new AstroModel(filename);
        this.model.addObserver(this);
    }

    /**
     *
     * Making of the primary stage and includes all the components that will be used.
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        this.label = new Label("Loaded  " + filename);
        HBox hbox1 = new HBox(label);
        hbox1.setAlignment(Pos.CENTER);
        Button button1 = new Button("Load");
        // creating the Load button
        button1.setOnAction(event -> {openFileChooser(stage);});
        Button button2 = new Button("Reset");
        button2.setOnAction(event -> {try {
            this.model.reset(filename);
            update(model,null);
            label.setText("The board has been reset");
        }
        catch (IOException e){
            e.printStackTrace();
        }}
        );
        //creating the hint button
        Button button3 = new Button("Hint");
        button3.setOnAction(event -> {try {
            this.model.hint();
            update(model,null);
            label.setText("Next Step!");
            if (model.isSolved()){
                label.setText("Already solved");
            }
        }catch (IOException e){
            e.printStackTrace();
        }}
        );
        HBox hbox2 = new HBox(button1,button2,button3);

        hbox2.setAlignment(Pos.CENTER);

        //making the grid for the ships and astronauts.
        this.gridPane = new GridPane();
        boardgrid = new Button[this.model.getRow()][this.model.getCol()];
        for (int i = 0 ; i < this.model.getRow(); i++){
            for (int j = 0 ; j < this.model.getCol(); j++) {
                Button button = new Button(this.model.getGrid()[i][j]);
                if (!(button.getText().equals(".")) && !(button.getText().equals("*"))){
                    if (this.model.getGrid()[i][j].equals("B")){
                        button.setGraphic(new ImageView(bluerobot));
                    }
                    if (button.getText().equals("C")){
                        button.setGraphic(new ImageView(greenrobot));
                    }
                    if (button.getText().equals("D")){
                        button.setGraphic(new ImageView(lightbluerobot));
                    }
                    if (button.getText().equals("E")){
                        button.setGraphic(new ImageView(orangerobot));
                    }
                    if (button.getText().equals("F")){
                        button.setGraphic(new ImageView(pinkrobot));
                    }
                    if (button.getText().equals("G")){
                        button.setGraphic(new ImageView(purplerobot));
                    }
                    if (button.getText().equals("H")){
                        button.setGraphic(new ImageView(whiterobot));
                    }
                    if (button.getText().equals("I")){
                        button.setGraphic(new ImageView(yellowrobot));
                    }
                    int row = i;
                    int col = j;
                    button.setOnAction(event -> this.model.select(row,col));
                }
                if (this.model.getGrid()[i][j].equals("A")){
                    button.setGraphic(new ImageView(astro));
                    int row = i;
                    int col = j;
                    button.setOnAction(event -> this.model.select(row,col));
                }
                if (this.model.getGrid()[i][j].equals("*")){
                    button.setGraphic(new ImageView(earth));
                }
                else{
                    int row = i;
                    int col = j;
                    button.setOnAction(event -> {try {
                        this.model.select(row, col);
                        label.setText("Selected("+ row +"," +col + " )");
                        if (!model.isValid_sel()){
                            label.setText("No piece at ("+ row +"," +col + " )");
                        }
                            }catch(Exception e){
                        e.printStackTrace();
                    }
                    });

                }
                boardgrid[i][j] = button;
                this.gridPane.setGridLinesVisible(true);
                button.setBackground(background);
                button.setMinSize(ICON_SIZE, ICON_SIZE);
                button.setMaxSize(ICON_SIZE, ICON_SIZE);
                this.gridPane.add(button, j ,i);

            }
        }
        //making the buttons for the moving directions.
        Button button6 = new Button("N");
        button6.setStyle("-fx-font-weight: bold;");
        button6.setOnAction(event ->
                    this.model.move(Direction.NORTH));
        Button button7 = new Button("E");
        button7.setStyle("-fx-font-weight: bold;");
        button7.setOnAction(event -> this.model.move(Direction.EAST));
        Button button8 = new Button("W");
        button8.setStyle("-fx-font-weight: bold;");
        button8.setOnAction(event -> this.model.move(Direction.WEST));
        HBox hbox4 = new HBox(button8,button7);
        hbox4.setSpacing(10);
        Button button9 = new Button("S");
        button9.setOnAction(event -> this.model.move(Direction.SOUTH));
        button9.setStyle("-fx-font-weight :bold");
        VBox vbox = new VBox(button6,hbox4,button9);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);


        borderPane.setTop(hbox1);
        borderPane.setCenter(this.gridPane);
        borderPane.setBottom(hbox2);
        borderPane.setRight(vbox);
        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.setTitle("Astro GUI");
        stage.sizeToScene();
        stage.show();
    }

    /**
     * * Updates the appearance of buttons on the game board based on the model's current state.
     *      * Also updates the loaded file text field and displays status messages in the GUI.
     * @param astroModel the object that wishes to inform this object
     *                about something that has happened.
     * @param msg optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(AstroModel astroModel, String msg) {
        String[][] board = model.getGrid();
        for (int i = 0 ; i < astroModel.getRow(); i++) {
            for (int j = 0; j < astroModel.getCol(); j++) {
                boardgrid[i][j].setText(board[i][j]);
                if (!(boardgrid[i][j].getText().equals(".")) && !(boardgrid[i][j].getText().equals("*"))) {
                    if (boardgrid[i][j].getText().equals("B")) {
                        boardgrid[i][j].setGraphic(new ImageView(bluerobot));
                    }
                    if (boardgrid[i][j].getText().equals("C")) {
                        boardgrid[i][j].setGraphic(new ImageView(greenrobot));
                    }
                    if (boardgrid[i][j].getText().equals("D")) {
                        boardgrid[i][j].setGraphic(new ImageView(lightbluerobot));
                    }
                    if (boardgrid[i][j].getText().equals("E")) {
                        boardgrid[i][j].setGraphic(new ImageView(orangerobot));
                    }
                    if (boardgrid[i][j].getText().equals("F")) {
                        boardgrid[i][j].setGraphic(new ImageView(pinkrobot));
                    }
                    if (boardgrid[i][j].getText().equals("G")) {
                        boardgrid[i][j].setGraphic(new ImageView(purplerobot));
                    }
                    if (boardgrid[i][j].getText().equals("H")) {
                        boardgrid[i][j].setGraphic(new ImageView(whiterobot));
                    }
                    if (boardgrid[i][j].getText().equals("I")) {
                        boardgrid[i][j].setGraphic(new ImageView(yellowrobot));
                    }
                }
                if (boardgrid[i][j].getText().equals(".")){
                    boardgrid[i][j].setGraphic(new ImageView());
                }
                if (boardgrid[i][j].getText().equals("A")){
                    boardgrid[i][j].setGraphic(new ImageView(astro));
                }
                if (boardgrid[i][j].getText().equals("*")){
                    boardgrid[i][j].setGraphic(new ImageView(earth));
                }
            }
        }

    }

    private void openFileChooser(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Astro Configuration File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
            try {
                this.model = new AstroModel(selectedFile.getName());
                clearboard();
                createButton();

                for (int i = 0; i < model.getRow(); i ++){
                    for (int j = 0; j < model.getCol(); j++){
                        this.boardgrid[i][j].setText(model.getGrid()[i][j]);
                    }
                }
                this.model.addObserver(this);
                this.filename = selectedFile.getName();
                update(this.model, null); // Update the GUI with the loaded file
                this.label.setText("Current file: " + filename);
                primaryStage.sizeToScene(); // Adjust window size to fit the contents
            } catch (IOException e) {
                e.printStackTrace();
            }


    }

    public void createButton(){
        if (gridPane != null) {
            gridPane.getChildren().clear(); // Clear all children from the gridPane
        }
        for (int i = 0 ; i < this.model.getRow(); i++){
            for (int j = 0 ; j < this.model.getCol(); j++) {
                Button button = new Button(this.model.getGrid()[i][j]);
                if (!(button.getText().equals(".")) && !(button.getText().equals("*"))){
                    if (this.model.getGrid()[i][j].equals("B")){
                        button.setGraphic(new ImageView(bluerobot));
                    }
                    if (button.getText().equals("C")){
                        button.setGraphic(new ImageView(greenrobot));
                    }
                    if (button.getText().equals("D")){
                        button.setGraphic(new ImageView(lightbluerobot));
                    }
                    if (button.getText().equals("E")){
                        button.setGraphic(new ImageView(orangerobot));
                    }
                    if (button.getText().equals("F")){
                        button.setGraphic(new ImageView(pinkrobot));
                    }
                    if (button.getText().equals("G")){
                        button.setGraphic(new ImageView(purplerobot));
                    }
                    if (button.getText().equals("H")){
                        button.setGraphic(new ImageView(whiterobot));
                    }
                    if (button.getText().equals("I")){
                        button.setGraphic(new ImageView(yellowrobot));
                    }
                    int row = i;
                    int col = j;
                    button.setOnAction(event -> this.model.select(row,col));
                }
                if (this.model.getGrid()[i][j].equals("A")){
                    button.setGraphic(new ImageView(astro));
                    int row = i;
                    int col = j;
                    button.setOnAction(event -> this.model.select(row,col));
                }
                if (this.model.getGrid()[i][j].equals("*")){
                    button.setGraphic(new ImageView(earth));
                }
                else{
                    int row = i;
                    int col = j;
                    button.setOnAction(event -> this.model.select(row,col));
                }
                boardgrid[i][j] = button;
                this.gridPane.setGridLinesVisible(true);
                button.setBackground(background);
                button.setMinSize(ICON_SIZE, ICON_SIZE);
                button.setMaxSize(ICON_SIZE, ICON_SIZE);
                this.gridPane.add(button, j ,i);

            }
        }
    }
    public void clearboard(){
        this.boardgrid = new Button[model.getRow()][model.getCol()];
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java AstroGUI filename");
        } else {
            Application.launch(args);
        }
    }
}
