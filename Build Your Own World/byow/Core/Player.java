package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**@author Albert Q. Irivng C.*/

public class Player {
    private int xPos;
    private int yPos;

    /**Construct player with starting location at given position.
     * @param startX start x position.
     * @param startY start y coordinate.
     * */
    public Player(int startX, int startY) {
        xPos = startX;
        yPos = startY;
    }

    /**Move player left one tile on grid.
     * @param grid of tiles.
     * */
    public void moveLeft(TETile[][] grid) {
        TETile tile = grid[xPos - 1][yPos];
        if (tile == Tileset.FLOOR) {
            grid[xPos][yPos] = Tileset.FLOOR;
            xPos = xPos - 1;
            grid[xPos][yPos] = Tileset.AVATAR;
        } else if (tile == Tileset.LOCKED_DOOR) {
            grid[xPos - 1][yPos] = Tileset.UNLOCKED_DOOR;
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
            grid[xPos][yPos] = Tileset.AVATAR;
        } else if (tile == Tileset.LOCKED_DOOR) {
            grid[xPos + 1][yPos] = Tileset.UNLOCKED_DOOR;
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
            grid[xPos][yPos] = Tileset.AVATAR;
        } else if (tile == Tileset.LOCKED_DOOR) {
            grid[xPos][yPos + 1] = Tileset.UNLOCKED_DOOR;
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
            grid[xPos][yPos] = Tileset.AVATAR;
        } else if (tile == Tileset.LOCKED_DOOR) {
            grid[xPos][yPos - 1] = Tileset.UNLOCKED_DOOR;
        }
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}
