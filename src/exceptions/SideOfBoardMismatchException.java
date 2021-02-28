package exceptions;

public class SideOfBoardMismatchException extends Exception {
    public SideOfBoardMismatchException() {
        super("Sorry, you can't play on the chosen side of the board. Please " +
                "try again.");
    }
}
