public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int front;


    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 4;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(T item) {
        if (size == 0) {
            nextLast = nextLast + 1;
        }
        items[nextFirst] = item;
        size = size + 1;
        nextFirst = nextFirst - 1;
        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }
        if (size == items.length) {
            resize(size * 2);
        }
    }

    @Override
    public void addLast(T item) {
        if (size == 0) {
            nextFirst = nextFirst - 1;
        }
        if (nextLast >= items.length) {
            nextLast = 0;
        }
        items[nextLast] = item;
        size = size + 1;
        nextLast = nextLast + 1;
        if (size == items.length) {
            resize(size * 2);
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Double hipUsage = (((double) (size) - 1.0) / (double) (items.length));
        if ((items.length >= 16) && (hipUsage * 100.0 < 25.0)) {
            downsize(size * 2);
        }
        if (nextFirst == items.length - 1) {
            nextFirst = -1;
        }
        T returnVal = items[nextFirst + 1];
        nextFirst = nextFirst + 1;
        size = size - 1;
        if (size == 0) {
            nextFirst = 4;
            nextLast = 4;
        }
        return returnVal;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Double hipUsage = (((double) (size) - 1.0) / (double) (items.length));
        if ((items.length >= 16) && (hipUsage * 100.0 < 25.0)) {
            downsize(size * 2);
        }
        if (nextLast == 0) {
            nextLast = items.length;
        }
        T returnVal = items[nextLast - 1];
        nextLast = nextLast - 1;
        size = size - 1;
        if (size == 0) {
            nextFirst = 4;
            nextLast = 4;
        }
        return returnVal;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        if (index > (items.length - 1) - (nextFirst + 1)) {
            index = index - ((items.length - 1) - (nextFirst + 1) + 1);
        } else {
            index = nextFirst + 1 + index;
        }
        return items[index];
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i = i + 1) {
            System.out.print(get(i));
            System.out.print(" ");
        }
        System.out.print("\n");
    }

    private void resize(int len) {
        T[] copy = (T[]) new Object[len];
        int tracker = nextFirst + 1;
        for (int i = 0; i < size; i = i + 1) {
            copy[tracker] = get(i);
            tracker = tracker + 1;
        }
        nextLast = tracker;
        items = copy;
    }
    private void downsize(int len) {
        T[] copy = (T[]) new Object[len];
        int tracker = 5;
        for (int i = 0; i < size; i = i + 1) {
            copy[tracker] = get(i);
            tracker = tracker + 1;
            if (tracker >= copy.length) {
                tracker = 0;
            }
        }
        items = copy;
        nextFirst = 4;
        nextLast = tracker;
    }
}
