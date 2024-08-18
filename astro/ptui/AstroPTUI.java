package puzzles.astro.ptui;

import puzzles.common.Direction;
import puzzles.common.Observer;
import puzzles.astro.model.AstroModel;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;

/**
 * author: Chiemelie Momah
 * PTUI Implementation of the Astro Game, contains view and controller
 */
public class AstroPTUI implements Observer<AstroModel, String> {
    private AstroModel model;

    public void init(String filename) throws IOException {
        this.model = new AstroModel(filename);
        this.model.addObserver(this);
        displayHelp();
    }

    @Override
    public void update(AstroModel model, String data) {
        // for demonstration purposes
        System.out.println(data);
        System.out.println(model);
    }

    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "m(ove) n|s|e|w      -- move selected piece in direction" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
    }

    public void run() throws IOException {
        Scanner in = new Scanner( System.in );
        for ( ; ; ) {
            System.out.print( "> " );
            String line = in.nextLine();
            String[] words = line.split( "\\s+" );
            if (words.length > 0) {
                if (words[0].startsWith( "q" )) {
                    model.quit();
                }
                else if (words[0].startsWith("l")){
                    model.load(words[1]);
                }
                else if ( words[0].startsWith( "h" )) {
                    model.hint();
                    if (model.isSolved()){
                        System.out.println("Already solved");
                    }
                    System.out.println(model.toString());
                }
                else if (words[0].startsWith("s")){
                    model.select(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                    System.out.println(model.toString());
                }
                else if (words[0].startsWith("r")){
                    model.reset(model.getFilename());
                }
                else if(words[0].startsWith("m")){
                    if (!model.isSolved()) {
                        if (Objects.equals(words[1], "s")) {
                            model.move(Direction.SOUTH);
                        } else if (Objects.equals(words[1], "e")) {
                            model.move(Direction.EAST);

                        } else if (Objects.equals(words[1], "n")) {
                            model.move(Direction.NORTH);
                        } else {
                            model.move(Direction.WEST);

                        }
                    }
                    else{
                        System.out.println("Already solved");
                    }
                }
                else {
                    displayHelp();
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java AstroPTUI filename");
        } else {
            try {
                AstroPTUI ptui = new AstroPTUI();
                ptui.init(args[0]);
                ptui.run();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}
