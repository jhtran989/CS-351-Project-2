package exceptions;

public class DominoMismatchException extends Exception {
    public DominoMismatchException() {
        super("Sorry, the domino you just played doesn't match the half " +
                "domino right above it. Please try again.");
    }
}
