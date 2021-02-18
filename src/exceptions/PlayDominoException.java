package exceptions;

public class PlayDominoException extends Exception {
    public PlayDominoException() {
        super("Sorry, you don't have a domino that can extend the line of " +
                "play. Please draw a domino from the boneyard.");
    }
}
