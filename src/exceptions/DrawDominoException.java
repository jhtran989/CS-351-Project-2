package exceptions;

public class DrawDominoException extends Exception {
    public DrawDominoException() {
        super("Sorry, you already have a domino that can extend the line of " +
                "play. Please play a domino.");
    }
}
