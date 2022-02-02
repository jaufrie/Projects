package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**@author Albert Q, Irving C.*/
public class WorldGenerator {
    /**Seed as read from input.*/
    private long seed;
    /**Random generator.*/
    private Random RANDOM;
    /**Tile array.*/
    private TETile[][] world;

    private Player player;

    /**Initialize world generator.
     * @param seed input from engine reader.
     * @param width of world.
     * @param height of world.
     * */
    public WorldGenerator(long seed, int width, int height) {
        world = new TETile[width][height];
        this.seed = seed;
        RANDOM = new Random(seed);
    }

    /**Generate world with filled tile array.
     * @return tile array of world.
     * */
    public TETile[][] generate() {
        for (int x = 0; x < world.length; x += 1) {
            for (int y = 0; y < world[0].length; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        List<Leaf> leaves = makeWorld(world.length, world[0].length);
        for (Leaf l : leaves) {

            if (l.hasHalls()) {
                for (Room hall : l.getHalls()) {
                    hall.drawRoom(world);
                }
            }
        }
        for (Leaf l : leaves) {
            if (l.hasRoom()) {
                l.accessRoom().drawRoom(world);
            }
        }
        openOverlaps(world);
        addPlayer();
        addLockedDoor();
        //addWater();
        return getWorld();
    }

    /**Create leafs & fill with rooms to add to world.
     * @param length of initial leaf world.
     * @param height or initial leaf world.
     * @return List of leaves with rooms & halls.
     * */
    private List<Leaf> makeWorld(int length, int height) {
        List<Leaf> leaves = new ArrayList<>();
        Leaf root = new Leaf(seed, 0, 0, length, height);
        leaves.add(root);
        boolean split = true;
        while (split) {
            split = false;
            int numLeafs = leaves.size();
            for (int i = 0; i < numLeafs; i++) {
                Leaf l = leaves.get(i);
                if (l.getLeftChild() == null && l.getRightChild() == null) {
                    if (l.getHeight() >= 18 || l.getWidth() >= 18) {
                        if (l.split()) {
                            leaves.add(l.getLeftChild());
                            leaves.add(l.getRightChild());
                            split = true;
                        }
                    }

                }
            }
        }
        root.addRoom();
        return leaves;
    }

    /**Get world grid.
     * @return world array.
     * */
    public TETile[][] getWorld() {
        return world;
    }

    /**Get player added to world.
     * @return Player.
     * */
    public Player getPlayer() {
        return player;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);
        WorldGenerator wg = new WorldGenerator(9223372036854775807L, 80, 30);
        wg.generate();
        TETile[][] bob = wg.getWorld();
        ter.renderFrame(bob);
    }

    /**Open paths between intersecting hallways and rooms.
     * @param grid of tiles representing the world.
     * */
    private void openOverlaps(TETile[][] grid) {
        for (int x = 1; x < world.length - 1; x += 1) {
            for (int y = 1; y < world[0].length - 1; y += 1) {
                if (world[x][y] == Tileset.WALL) {
                    TETile left = world[x - 1][y];
                    TETile right = world[x + 1][y];
                    if (world[x][y + 1] == Tileset.FLOOR && world[x][y - 1] == Tileset.FLOOR) {
                        world[x][y] = Tileset.FLOOR;
                    } else if (left == Tileset.FLOOR && right == Tileset.FLOOR) {
                        world[x][y] = Tileset.FLOOR;

                    }
                }
            }
        }
    }

    /**Add player to random open floor tile.*/
    private void addPlayer() {
        boolean madePlayer = false;
        int i, j;
        while (!madePlayer) {
            i = RANDOM.nextInt(world.length - 1) + 1;
            j = RANDOM.nextInt(world[0].length - 1) + 1;
            if (world[i][j] == Tileset.FLOOR) {
                player = new Player(i, j);
                world[i][j] = Tileset.AVATAR;
                madePlayer = true;
            }
        }
    }

    /**Randomly add a locked door to world.*/
    private void addLockedDoor() {
        int addDoor = 0;
        int i, j;
        while (addDoor < 4) {
            i = RANDOM.nextInt(world.length - 1) + 1;
            j = RANDOM.nextInt(world[0].length - 1) + 1;
            if (world[i][j] == Tileset.WALL) {
                TETile right = world[i + 1][j];
                TETile left = world[i - 1][j];
                TETile up = world[i][j + 1];
                TETile down = world[i][j - 1];
                TETile floor = Tileset.FLOOR;
                TETile wall = Tileset.WALL;
                if (right == wall && left == wall && (up == floor || down == floor)) {
                    world[i][j] = Tileset.LOCKED_DOOR;
                    addDoor++;
                } else if (up == wall && down == wall && (left == floor || right == floor)) {
                    world[i][j] = Tileset.LOCKED_DOOR;
                    addDoor++;
                }
            }
        }
    }

    private void addWater() {
        int waterC = RANDOM.nextInt(3) + 3;
        int i, j;
        int c = 0;
        while (c < waterC) {
            i = RANDOM.nextInt(world.length - 1) + 1;
            j = RANDOM.nextInt(world[0].length - 1) + 1;
            if (world[i][j] == Tileset.FLOOR) {
                world[i][j] = Tileset.WATER;
                c++;
            }
        }
    }
}
