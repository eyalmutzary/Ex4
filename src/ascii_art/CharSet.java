package ascii_art;

import java.util.HashSet;
import java.util.Set;

public class CharSet {
    private static final char MIN_CHAR = ' ';
    private static final char MAX_CHAR = '~';
    private static final String errMsg = "Did not add due to incorrect format";
    private final Set<Character> charSet;

    public CharSet() {
        this.charSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            charSet.add(Character.forDigit(i, 10));
        }
    }

    public CharSet(Set<Character> charSet) {
        this.charSet = charSet;
    }

    public void add(String addValue) {
        if (addValue.length() == 1) {
            addSingleChar(addValue.charAt(0));
            return;
        }

        if (addValue.length() == 3 && addValue.charAt(1) == '-') {
            addRange(addValue.charAt(0), addValue.charAt(2));
            return;
        }

        if (addValue.equals("all")) {
            addAll();
            return;
        }

        if (addValue.equals("space")) {
            addSpace();
            return;
        }

        System.out.println(errMsg);
    }

    private void addSingleChar(char c) {
        if (c < MIN_CHAR || c > MAX_CHAR) {
            System.out.println(errMsg);
            return;
        }
        charSet.add(c);
    }

    private void addRange(char letter1, char letter2) {
        char start = (char)Math.min((int)letter1, (int)letter2);
        char end = (char)Math.max((int)letter1, (int)letter2);
        if (!isValidRangeInput(start, end)) { return; }
        for (char i = start; i <= end; i++) {
            charSet.add(i);
        }
    }

    private boolean isValidRangeInput(char start, char end) {
        if (start < MIN_CHAR || start > MAX_CHAR || end < MIN_CHAR || end > MAX_CHAR) {
            System.out.println(errMsg);
            return false;
        }
        return true;
    }

    private void addAll() {
        for (char i = MIN_CHAR; i <= MAX_CHAR; i++) {
            charSet.add(i);
        }
    }

    private void addSpace() {
        charSet.add(' ');
    }


    public void remove(String arg) {
        if (arg.length() == 1) {
            removeSingleChar(arg.charAt(0));
            return;
        }

        if (arg.length() == 3 && arg.charAt(1) == '-') {
            removeRange(arg.charAt(0), arg.charAt(2));
            return;
        }

        if (arg.equals("all")) {
            removeAll();
            return;
        }

        if (arg.equals("space")) {
            removeSpace();
            return;
        }

        System.out.println(errMsg);
    }

    private void removeSingleChar(char c) {
        if (c < MIN_CHAR || c > MAX_CHAR) {
            System.out.println(errMsg);
            return;
        }
        charSet.remove(c);
    }

    private void removeRange(char letter1, char letter2) {
        char start = (char)Math.min((int)letter1, (int)letter2);
        char end = (char)Math.max((int)letter1, (int)letter2);
        if (!isValidRangeInput(start, end)) { return; }
        for (char i = start; i <= end; i++) {
            charSet.remove(i);
        }
    }

    private void removeAll() {
        for (char i = MIN_CHAR; i <= MAX_CHAR; i++) {
            charSet.remove(i);
        }
    }

    private void removeSpace() {
        charSet.remove(' ');
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Character c : charSet) {
            sb.append(c).append(" ");
        }
        return sb.toString();
    }

    public Character[] toArray() {
        Character[] arr = new Character[this.charSet.size()];
        int i=0;
        for (Character c : this.charSet){
            arr[i++] = c;
        }
        return arr;

    }



}
