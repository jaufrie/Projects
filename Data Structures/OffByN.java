public class OffByN implements CharacterComparator {
    private int byN;
    public OffByN(int N) {
        byN = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == byN;
    }
}
