package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.util.Date;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static String seedAndMoves = "";
    private static String path;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        KeyboardInteract keyboard = new KeyboardInteract(WIDTH, HEIGHT + 5);
        TETile[][] worldFrame;
        while (keyboard.possibleInput()) {
            char c = keyboard.nextKey();
            System.out.println(c);
            if (c == 'N') {
                seedAndMoves = seedAndMoves + c;
                Long seed = keyboard.getSeed();
                seedAndMoves = seedAndMoves + seed + 'S';
                WorldGenerator world = new WorldGenerator(seed, WIDTH, HEIGHT);
                worldFrame = world.generate();
                Player p = world.getPlayer();
                ter.renderFrame(worldFrame);
                playGame(keyboard, p, worldFrame, true);
            } else if (c == 'Q') {
                System.exit(0);
            } else if (c == 'L') {
                String savedString = getString();
                System.out.println(savedString);
                System.out.println(getSavedSeed(savedString));
                TETile[][] grid = interactWithInputString(savedString);

                Position pos = retrievePlayerPos(grid);
                Player p = new Player(pos.getX(), pos.getY());

                ter.renderFrame(grid);
                playGame(keyboard, p, grid, false);
            } else if (c == 'R') {
                String saved = getString();
                keyboard.replay(saved, WIDTH, HEIGHT, ter);
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        String seedMoves = "", seed = "";
        int index = 0;
        Character c = input.charAt(index);
        if (c == 'N' || c == 'n') {
            seedMoves += "n";
            index++;
            while (c != 'S' && c != 's') {
                c = input.charAt(index);
                seedMoves = seedMoves + c;
                if (c != 'S' && c != 's') {
                    seed = seed + c;
                }
                index++;
            }
            Long inputLong = Long.parseLong(seed);
            WorldGenerator world = new WorldGenerator(inputLong, WIDTH, HEIGHT);
            TETile[][] finalWorldFrame = world.generate();
            Player p = world.getPlayer();
            while (index < input.length()) {
                c = input.charAt(index);
                if (c == 'D' || c == 'd') {
                    seedMoves += c;
                    p.moveRight(finalWorldFrame);
                }
                if (c == 'W' || c == 'w') {
                    seedMoves += c;
                    p.moveUp(finalWorldFrame);
                }
                if (c == 'S' || c == 's') {
                    seedMoves += c;
                    p.moveDown(finalWorldFrame);
                }
                if (c == 'A' || c == 'a') {
                    seedMoves += c;
                    p.moveLeft(finalWorldFrame);
                }
                if (c == ':' && Character.toUpperCase(input.charAt(index + 1)) == 'Q') {
                    saveFileExit(seedMoves);
                    break;
                }
                index++;
            }
            return finalWorldFrame;
        } else if (c == 'L' || c == 'l') {
            String savedString = getString();
            System.out.println(savedString);
            TETile[][] finalWorldFrame = interactWithInputString(savedString);
            Position pos = retrievePlayerPos(finalWorldFrame);
            Player p = new Player(pos.getX(), pos.getY());
            index = 1;
            while (index < input.length()) {
                c = input.charAt(index);
                if (c == 'D' || c == 'd') {
                    savedString = savedString + c;
                    p.moveRight(finalWorldFrame);
                }
                if (c == 'W' || c == 'w') {
                    savedString = savedString + c;
                    p.moveUp(finalWorldFrame);
                }
                if (c == 'S' || c == 's') {
                    savedString = savedString + c;
                    p.moveDown(finalWorldFrame);
                }
                if (c == 'A' || c == 'a') {
                    savedString = savedString + c;
                    p.moveLeft(finalWorldFrame);
                }
                if (c == ':' && Character.toUpperCase(input.charAt(index + 1)) == 'Q') {
                    saveFileExit(savedString);
                    return finalWorldFrame;
                }
                index++;
            }
            return finalWorldFrame;
        }
        return null;
    }

    public void saveFileExit(String fileText) {
        try {
            String dir = System.getProperty("user.dir");
            File file = new File(dir + "save.txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(fileText);
            writer.flush();
            writer.close();
        } catch (IOException ioe) {
            System.out.println("Trouble reading from the file: " + ioe.getMessage());
        }
    }
    public void playGame(KeyboardInteract kb, Player p, TETile[][] worldFrame, boolean n) {
        char c;
        while (true) {
            while (!StdDraw.hasNextKeyTyped()) {
                bar(gameWon(worldFrame), worldFrame);
                ter.renderFrame(worldFrame);
            }
            c = kb.nextKey();
            if (c == ':') {
                kb.colonWarningTrigger();
            } else if (Character.toUpperCase(c) == 'Q' && kb.getColonWarning()) {
                String result;
                if (!n) {
                    String sav = getStringInput(getString());
                    result = sav + seedAndMoves;
                } else {
                    result = seedAndMoves;
                }
                System.out.println(result);
                saveFileExit(result);
                System.exit(0);
            } else if (c == 'W' || c == 'A' || c == 'S' || c == 'D') {
                kb.movePlayer(c, p, worldFrame);
                seedAndMoves += c;
                bar(gameWon(worldFrame), worldFrame);
                ter.renderFrame(worldFrame);
            } else {
                kb.turnOffColonWarning();
            }
            StdDraw.pause(20);
        }
    }
    /**Get player avatar position on tile grid.
     * @param world tile grid.
     * @return coordinates of player.
     * */
    private Position retrievePlayerPos(TETile[][] world) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                if (world[i][j] == Tileset.AVATAR) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    private Water retrieveWaterPos(TETile[][] world) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                if (world[i][j] == Tileset.AVATAR) {
                    return null;
                }
            }
        }
        return null;
    }

    private boolean gameWon(TETile[][] world) {
        int count = 0;
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                if (world[i][j] == Tileset.UNLOCKED_DOOR) {
                    count++;
                }
            }
        }
        return count == 4;
    }
    /**Get saved string from saved text file.
     * return stored string.
     * */
    public String getString() {
        String filename = "save.txt";
        String dir = System.getProperty("user.dir");
        String content = readLineByLineJava8((dir + "save.txt"));
        path = dir + "save.txt";
        return content;
    }
    private String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public long getSavedSeed(String str) {
        String ret = "";
        int i = 1;
        while (str.charAt(i) != 'S') {
            ret = ret + str.charAt(i);
            i++;
        }
        return Long.parseLong(ret);
    }
    public String getStringInput(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'x') {
                break;
            }
            result = result + str.charAt(i);
        }
        return result;
    }

    /**Create HUD bar.*/
    public void bar(boolean win, TETile[][] world) {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(4, HEIGHT + 3, "Welcome!");
        //StdDraw.text(WIDTH / 2, HEIGHT + 3, "Feel Free to Explore!");
        if (win) {
            StdDraw.text(0.75 * WIDTH, HEIGHT + 3, "YOU'VE WON");
        }
        String tileName = mouseHover(world);
        StdDraw.text(0.95 * WIDTH, HEIGHT + 3, tileName);
        StdDraw.line(0, HEIGHT + 1, WIDTH, HEIGHT + 1);
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        StdDraw.text(WIDTH / 2, HEIGHT + 3, format.format(d));
        StdDraw.show();
    }

    public String mouseHover(TETile[][] grid) {
        StdDraw.setPenColor(Color.WHITE);
        double mX = StdDraw.mouseX();
        double mY = StdDraw.mouseY();
        int tileX = (int) Math.floor(mX);
        int tileY = (int) Math.floor(mY);
        if (tileX < grid.length && tileY < grid[0].length) {
            TETile tile = grid[tileX][tileY];
            if (tile == Tileset.FLOOR) {
                return "FLOOR";
            } else if (tile == Tileset.AVATAR) {
                return "PLAYER";
            } else if (tile == Tileset.WALL) {
                return "WALL";
            } else if (tile == Tileset.UNLOCKED_DOOR) {
                return "OPEN DOOR";
            } else if (tile == Tileset.LOCKED_DOOR) {
                return "LOCKED DOOR";
            } else if (tile == Tileset.NOTHING) {
                return "NOTHING";
            }
        }
        return "NOTHING";
    }
}
