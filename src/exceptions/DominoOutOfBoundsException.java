package exceptions;

public class DominoOutOfBoundsException extends Exception {
    public DominoOutOfBoundsException() {
        super("Sorry, the domino index entered is out of bounds. Please try " +
                "again.");
    }
}
