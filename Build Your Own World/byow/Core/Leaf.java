package byow.Core;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**@source BSP Tree Implementation.
 * @author Albert, Irving.
 * */

public class Leaf {
    /**Minimum length or height of a Leaf*/
    private final int minSize = 6;
    /**Bottom or Left section leaf after split.*/
    private Leaf leftChild;
    /**Top or Right section leaf after split.*/
    private Leaf rightChild;
    /**Room inside each leaf area*/
    private Room room;
    private List<Room> halls;
    /**Bottom leaf x coordinate of Leaf*/
    private int x;
    /**Bottom leaf y coordinate of Leaf*/
    private int y;
    private int w;
    private int h;
    private long SEED;
    private Random RANDOM;

    /**Construct leaf dividing up space.
     * @param seed random seed.
     * @param x bottom left x.
     * @param y bottom left y.
     * @param width of space.
     * @param height of space.
     * */
    public Leaf(long seed, int x, int y, int width, int height) {
        SEED = seed;
        RANDOM = new Random(seed);
        this.x = x;
        this.y = y;
        w = width;
        h = height;
    }

    /**Split Leaf randomly if size is too large in width or height.
     * @return if a split occurs and children are added.
     * */
    public boolean split() {
        if (leftChild != null || rightChild != null) {
            return false;
        }
        boolean splitHor = RANDOM.nextBoolean();
        if (w > h && (double) w / h >= 1.5) {
            splitHor = false;
        } else if (h > w && (double) h / w >= 1.5) {
            splitHor = true;
        }
        int max;
        if (splitHor) {
            max = h - minSize;
        } else {
            max = w - minSize;
        }
        if (max <= minSize) {
            return false;
        }

        int split = RANDOM.nextInt(max - minSize) + minSize;
        if (splitHor) {
            leftChild = new Leaf(SEED, x, y, w, split);
            rightChild = new Leaf(SEED, x, y + split, w, h - split);
        } else {
            leftChild = new Leaf(SEED, x, y, split, h);
            rightChild = new Leaf(SEED, x + split, y, w - split, h);
        }
        return true;
    }

    /**Add rooms to the leaves at greatest depth.*/
    public void addRoom() {
        if (leftChild != null || rightChild != null) {
            if (leftChild != null) {
                leftChild.addRoom();
            }
            if (rightChild != null) {
                rightChild.addRoom();
            }
            if (leftChild != null && rightChild != null) {
                makeHall(leftChild.getRoom(), rightChild.getRoom());
            }
        } else {
            int width = RANDOM.nextInt(w - 5) + 4;
            int height = RANDOM.nextInt(h - 5) + 4;
            int posX = RANDOM.nextInt(w - width - 1) + 1;
            int posY = RANDOM.nextInt(h - height - 1) + 1;
            Position pos = new Position(x + posX, y + posY);
            room = new Room(pos, width, height);
        }
    }

    /**Get left child leaf.
     * @return left child.
     * */
    public Leaf getLeftChild() {
        return leftChild;
    }

    /**Return right leaf.
     * @return right child.
     * */
    public Leaf getRightChild() {
        return rightChild;
    }

    /**Return width of leaf.
     * @return width.
     * */
    public int getWidth() {
        return w;
    }

    /**Return height of leaf.
     * @return h.
     * */
    public int getHeight() {
        return h;
    }

    /**Return room within the leaf.
     * @return room.
     * */
    public Room accessRoom() {
        return room;
    }

    /**Whether leaf has a room or not.
     * @return if there is a room in the leaf.
     * */
    public boolean hasRoom() {
        return room != null;
    }

    /**Access all rooms within a leaf.
     * @return a room within the leaf.
     * */
    public Room getRoom() {
        if (room != null) {
            return room;
        } else {
            Room leftRoom = null;
            Room rightRoom = null;
            if (leftChild != null) {
                leftRoom = leftChild.getRoom();
            }
            if (rightChild != null) {
                rightRoom = rightChild.getRoom();
            }
            if (leftRoom == null && rightRoom == null) {
                return null;
            } else if (rightRoom == null) {
                return leftRoom;
            } else if (leftRoom == null) {
                return rightRoom;
            } else if (RANDOM.nextBoolean()) {
                return leftRoom;
            } else {
                return rightRoom;
            }
        }
    }
    /**
     * Create hall connecting 2 rooms.
     * @param room1 first room.
     * @param room2 second room.
     * */
    public void makeHall(Room room1, Room room2) {
        halls = new ArrayList<>();
        int pt1x = RANDOM.nextInt(room1.getRight() - room1.getLeft() - 3) + (room1.getLeft() + 1);
        int pt1y = RANDOM.nextInt(room1.getTop() - room1.getBot() - 3) + (room1.getBot() + 1);
        int pt2x = RANDOM.nextInt(room2.getRight() - room2.getLeft() - 3) + (room2.getLeft() + 1);
        int pt2y = RANDOM.nextInt(room2.getTop() - room2.getBot() - 3) + (room2.getBot() + 1);
        int wi = pt2x - pt1x;
        int he = pt2y - pt1y;
        if (wi < 0) {
            if (he < 0) {
                if (RANDOM.nextBoolean()) {
                    Room hall1 = new Room(new Position(pt2x, pt1y), Math.abs(wi), 3);
                    Room hall2 = new Room(new Position(pt2x, pt2y), 3, Math.abs(he));
                    halls.add(hall1);
                    halls.add(hall2);
                } else {
                    Room hall1 = new Room(new Position(pt2x, pt2y), Math.abs(wi) + 1, 3);
                    Room hall2 = new Room(new Position(pt1x, pt2y), 3, Math.abs(he) + 1);
                    halls.add(hall1);
                    halls.add(hall2);
                }
            } else if (he > 0) {
                if (RANDOM.nextBoolean()) {
                    Room hall1 = new Room(new Position(pt2x, pt1y), Math.abs(wi) + 1, 3);
                    Room hall2 = new Room(new Position(pt2x, pt1y), 3, Math.abs(he) + 1);
                    halls.add(hall1);
                    halls.add(hall2);
                } else {
                    Room hall1 = new Room(new Position(pt2x, pt2y), Math.abs(wi) + 1, 3);
                    Room hall2 = new Room(new Position(pt1x, pt1y), 3, Math.abs(he) + 1);
                    halls.add(hall1);
                    halls.add(hall2);
                }
            } else if (he == 0) {
                Room hall1 = new Room(new Position(pt2x, pt2y), Math.abs(wi) + 1, 3);
                halls.add(hall1);
            }
        } else if (wi > 0) {
            if (he < 0) {
                if (RANDOM.nextBoolean()) {
                    Room hall1 = new Room(new Position(pt1x, pt2y), Math.abs(wi) + 1, 3);
                    Room hall2 = new Room(new Position(pt1x, pt2y), 3, Math.abs(he) + 1);
                    halls.add(hall1);
                    halls.add(hall2);
                } else {
                    Room hall1 = new Room(new Position(pt1x, pt1y), Math.abs(wi) + 1, 3);
                    Room hall2 = new Room(new Position(pt2x, pt2y), 3, Math.abs(he) + 1);
                    halls.add(hall1);
                    halls.add(hall2);
                }
            } else if (he > 0) {
                if (RANDOM.nextBoolean()) {
                    Room hall1 = new Room(new Position(pt1x, pt1y), Math.abs(wi) + 1, 3);
                    Room hall2 = new Room(new Position(pt2x, pt1y), 3, Math.abs(he) + 1);
                    halls.add(hall1);
                    halls.add(hall2);
                } else {
                    Room hall1 = new Room(new Position(pt1x, pt2y), Math.abs(wi) + 1, 3);
                    Room hall2 = new Room(new Position(pt1x, pt1y), 3, Math.abs(he) + 1);
                    halls.add(hall1);
                    halls.add(hall2);
                }
            } else if (he == 0) {
                Room hall1 = new Room(new Position(pt1x, pt1y), Math.abs(wi) + 1, 3);
                halls.add(hall1);

            }
        } else if (wi == 0) {
            if (he < 0) {
                Room hall2 = new Room(new Position(pt2x, pt2y), 3, Math.abs(he) + 1);
                halls.add(hall2);
            } else if (he > 0) {
                Room hall2 = new Room(new Position(pt1x, pt1y), 3, Math.abs(he) + 1);
                halls.add(hall2);
            }
        }
    }

    /**Return halls of a leaf.
     * @return halls list.
     * */
    public List<Room> getHalls() {
        return halls;
    }

    /**Determines if leaf has halls.
     * @return if leaf has nonempty halls list.
     * */
    public boolean hasHalls() {
        return halls != null;
    }
}
