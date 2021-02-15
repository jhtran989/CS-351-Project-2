package exceptions;

public class InputErrorException extends Exception {
    public InputErrorException() {
        super("Sorry, please enter a valid input.");
    }
}
