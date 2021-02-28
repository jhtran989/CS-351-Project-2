package exceptions;

public class NoValidMovesException extends Exception {
    public NoValidMovesException() {
        super("There have no valid moves available. Please pass the turn...");
    }
}
