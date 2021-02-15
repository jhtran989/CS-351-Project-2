package utilities;

import exceptions.InputErrorException;

import java.util.InputMismatchException;

public class CustomParser {
    public static char parseChar(String input) throws InputErrorException {
        if (input.length() != 1) {
            throw new InputErrorException();
        } else {
            return input.charAt(0);
        }
    }

    public static int parseInt(String input) throws InputErrorException {
        try {
            return Integer.parseInt(input);
        } catch (InputMismatchException inputMismatchException) {
            throw new InputErrorException();
        }
    }
}
