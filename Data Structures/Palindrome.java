public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        int index = word.length() - 1;
        Deque<Character> wordDeque = new ArrayDeque<>();
        for (int i = index; i >= 0; i--) {
            wordDeque.addFirst(word.charAt(i));
        }
        return wordDeque;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        String rev = "";
        Deque<Character> wordDeque = wordToDeque(word);
        for (int i = wordDeque.size(); i > 0; i--) {
            rev += Character.toString(wordDeque.removeLast());
        }
        return word.equals(rev);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if ((word == null) || cc == null) {
            return false;
        }
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        Palindrome palin = new Palindrome();
        Deque deque = palin.wordToDeque(word);
        if ((deque.size()) % 2 != 0) {
            int tracker = deque.size() - 1;
            for (int i = 0; i < deque.size() / 2; i++) {
                char firstGet = (char) deque.get(i);
                char secondGet = (char) deque.get(tracker);
                if (!cc.equalChars(firstGet, secondGet)) {
                    return false;
                }
                tracker = tracker - 1;
            }
            return true;
        } else {
            int tracker = deque.size() - 1;
            for (int i = 0; i < deque.size() / 2; i++) {
                char firstGet = (char) deque.get(i);
                char secondGet = (char) deque.get(tracker);
                if (!cc.equalChars(firstGet, secondGet)) {
                    return false;
                }
                tracker = tracker - 1;
            }
            return true;
        }
    }
}
