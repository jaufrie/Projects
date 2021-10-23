package byow.Core;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Water {
    private final static int TRY = 3;
    private final static int PATH = 4;
    private int xPos;
    private int yPos;
    private TETile[][] grid;
    private int[][] world;
    private Player player;

    public Water(int startX, int startY, TETile[][] grid, Player target) {
        xPos = startX;
        yPos = startY;
        this.grid = grid;
        player = target;

    }

    public void move(Player p, TETile[][] grid) {
        targetTraverse(player.getxPos(), player.getyPos());

    }

    /**Move player left one tile on grid.
     * @param grid of tiles.
     * */
    public void moveLeft(TETile[][] grid) {
        TETile tile = grid[xPos - 1][yPos];
        if (tile == Tileset.FLOOR) {
            grid[xPos][yPos] = Tileset.FLOOR;
            xPos = xPos - 1;
            grid[xPos][yPos] = Tileset.WATER;
        }
    }

    /**Move player right one tile on grid.
     * @param grid of tiles.
     * */
    public void moveRight(TETile[][] grid) {
        TETile tile = grid[xPos + 1][yPos];
        if (tile == Tileset.FLOOR) {
            grid[xPos][yPos] = Tileset.FLOOR;
            xPos = xPos + 1;
            grid[xPos][yPos] = Tileset.WATER;
        }
    }

    /**Move player up one tile on grid.
     * @param grid of tiles.
     * */
    public void moveUp(TETile[][] grid) {
        TETile tile = grid[xPos][yPos + 1];
        if (tile == Tileset.FLOOR) {
            grid[xPos][yPos] = Tileset.FLOOR;
            yPos = yPos + 1;
            grid[xPos][yPos] = Tileset.WATER;
        }
    }

    /**Move player down one tile on grid.
     * @param grid of tiles.
     * */
    public void moveDown(TETile[][] grid) {
        TETile tile = grid[xPos][yPos - 1];
        if (tile == Tileset.FLOOR) {
            grid[xPos][yPos] = Tileset.FLOOR;
            yPos = yPos - 1;
            grid[xPos][yPos] = Tileset.WATER;
        }
    }

    public boolean targetTraverse(int i, int j) {
        if (!(inWidth(i) && inHeight(j) && isOpen(i, j) && !isTried(i, j))) {
            return false;
        }
        if (i == player.getxPos() && j == player.getyPos()) {
            world[i][j] = PATH;
            return true;
        } else {
            world[i][j] = TRY;
        }
        // North
        if (targetTraverse(i - 1, j)) {
            world[i-1][j] = PATH;
            return true;
        }
        // East
        if (targetTraverse(i, j + 1)) {
            world[i][j + 1] = PATH;
            return true;
        }
        // South
        if (targetTraverse(i + 1, j)) {
            world[i + 1][j] = PATH;
            return true;
        }
        // West
        if (targetTraverse(i, j - 1)) {
            world[i][j - 1] = PATH;
            return true;
        }
        return false;
    }

    private boolean inWidth(int i) {
        return i >= 0 && i < grid.length;
    }
    private boolean inHeight(int i) {
        return i >= 0 && i < grid[0].length;
    }
    private boolean isOpen(int i, int j) {
        return grid[i][j] == Tileset.FLOOR || grid[i][j] == Tileset.AVATAR;
    }

    private boolean isTried(int i, int j) {
        return world[i][j] == TRY;
    }

    private int[][] convertGrid() {
        world = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                TETile tile = grid[i][j];
                if (tile == Tileset.FLOOR || tile == Tileset.AVATAR) {
                    world[i][j] = 1;
                } else if (tile == Tileset.WALL || tile == Tileset.LOCKED_DOOR) {
                    world[i][j] = 0;
                } else if (tile == Tileset.UNLOCKED_DOOR || tile == Tileset.NOTHING) {
                    world[i][j] = 0;
                } else {
                    world[i][j] = 2;
                }
            }
        }
        return world;
    }

    private void extra(Player p) {
        int pX = p.getxPos();
        int pY = p.getyPos();
        int dX = pX - xPos;
        int dY = pY - yPos;
        int closeAxis, farAxis;
        //int mX = Math.max(Math.abs(dX), Math.abs(dY));
        if (Math.abs(dX) > Math.abs(dY)) {
            closeAxis = dY;
            farAxis = dX;
            if (dY > 0) {

            }
        } else {
            closeAxis = dX;
            farAxis = dY;
        }
    }
}
