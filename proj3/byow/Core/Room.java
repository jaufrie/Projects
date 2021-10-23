package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**@author ALbert Q, Irving C.*/
public class Room {
    private int botLeftX;
    private int botLeftY;
    private int length;
    private int height;
    private boolean[][] wall;
    private int left;
    private int right;
    private int top;
    private int bottom;

    public Room(Position botLeft, int length, int height) {
        botLeftX = botLeft.getX();
        botLeftY = botLeft.getY();
        this.length = length;
        this.height = height;
        left = botLeftX;
        right = botLeftX + length;
        top = botLeftY + height;
        bottom = botLeftY;
        wall = new boolean[length][height];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                if (j >= 1 && j < height - 1 && i >= 1 && i < length - 1) {
                    wall[i][j] = false;
                } else {
                    wall[i][j] = true;
                }
            }
        }
    }

    /**Add opening in room at specified location.
     * @param x horizontal position in room.
     * @param y vertical position in room.
     * */
    public void addOpening(int x, int y) {
        wall[x][y] = false;

    }

    /**Draw room based on wall and floor fillings.
     * @param grid of tiles to draw on.
     * */
    public void drawRoom(TETile[][] grid) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[botLeftX + i][botLeftY + j] != Tileset.FLOOR) {
                    if (wall[i][j]) {
                        grid[botLeftX + i][botLeftY + j] = Tileset.WALL;
                    } else {
                        grid[botLeftX + i][botLeftY + j] = Tileset.FLOOR;
                    }
                }

            }
        }
    }

    /**Length of room.
     * @return length.
     * */
    public int getLength() {
        return length;
    }

    /**Return height of room.
     * @return height.
     * */
    public int getHeight() {
        return height;
    }
    /**Return Left.
     * @return left.
     * */
    public int getLeft() {
        return left;
    }
    /**Return right side.
     * @return right.
     * */
    public int getRight() {
        return right;
    }
    /**Return top.
     * @return top.
     * */
    public int getTop() {
        return top;
    }
    /**Return bottom.
     * @return bottom.
     * */
    public int getBot() {
        return bottom;
    }

}
