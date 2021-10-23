package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;

public class KeyboardInteract {
    private int width;
    private int height;
    private boolean colonWarning;

    /**Construct keybaord interact with given dimensions.
     * @param width
     * @param height
     * */
    public KeyboardInteract(int width, int height) {
        this.width = width;
        this.height = height;
        colonWarning = false;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        Font small = new Font("Monaco", Font.PLAIN, 15);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
        StdDraw.text(width / 2, 0.75 * height, "CS61B: The Game");
        StdDraw.setFont(small);
        StdDraw.text(width / 2, 2 * height / 5, "New Game (N)");
        StdDraw.text(width / 2, height / 3, "Load Game (L)");
        StdDraw.text(width / 2, height / 4, "Quit Game (Q)");
        StdDraw.text(width / 2, height / 5 - 2, "Replay Save (R)");
        StdDraw.show();
    }

    public char nextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                return c;
            }
        }
    }

    public void movePlayer(char c, Player p, TETile[][] grid) {
        if (c == 'W') {
            p.moveUp(grid);
        } else if (c == 'A') {
            p.moveLeft(grid);
        } else if (c == 'S') {
            p.moveDown(grid);
        } else if (c == 'D') {
            p.moveRight(grid);
        }
    }

    public Long getSeed() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2, height / 2, "Please Enter a Seed (#).");
        StdDraw.text(width / 2, height / 2 - 2.5, "Hit 'S' to Submit the Number");
        StdDraw.show();
        String s = "";
        char next = nextKey();
        System.out.println(next);
        while (next != 'S') {
            s = s + next;
            next = nextKey();
            System.out.println(next);
        }
        return Long.parseLong(s);
    }

    public void replay(String steps, int w, int h, TERenderer ter) {
        String seed = "";
        int index = 0;
        Character c = steps.charAt(index);
        if (c == 'N' || c == 'n') {
            index++;
            while (c != 'S' && c != 's') {
                c = steps.charAt(index);
                if (c != 'S' && c != 's') {
                    seed = seed + c;
                }
                index++;
            }
        }
        Long inputLong = Long.parseLong(seed);
        WorldGenerator world = new WorldGenerator(inputLong, w, h);
        TETile[][] finalWorldFrame = world.generate();
        Player p = world.getPlayer();
        ter.renderFrame(finalWorldFrame);
        StdDraw.pause(750);
        while (index < steps.length()) {
            c = steps.charAt(index);
            if (c == 'D' || c == 'd') {
                p.moveRight(finalWorldFrame);
            }
            if (c == 'W' || c == 'w') {
                p.moveUp(finalWorldFrame);
            }
            if (c == 'S' || c == 's') {
                p.moveDown(finalWorldFrame);
            }
            if (c == 'A' || c == 'a') {
                p.moveLeft(finalWorldFrame);
            }
            ter.renderFrame(finalWorldFrame);
            StdDraw.pause(750);
            index++;
        }
    }

    public boolean isHovering() {
        //MouseEvent.
        return true;
    }

    public void colonWarningTrigger() {
        colonWarning = true;
    }

    public void turnOffColonWarning() {
        colonWarning = false;
    }

    public boolean getColonWarning() {
        return colonWarning;
    }

    public boolean possibleInput() {
        return true;
    }

    public void replayBar() {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2, height - 7, "REPLAY MODE");
        StdDraw.line(0, height - 10, width, height - 10);
        StdDraw.show();
    }
}
