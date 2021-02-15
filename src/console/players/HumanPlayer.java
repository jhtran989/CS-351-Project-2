package console.players;

import console.gamePieces.Boneyard;
import exceptions.InputErrorException;
import utilities.CustomParser;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private Scanner scanner;

    public HumanPlayer(Boneyard boneyard) {
        super(boneyard);
        scanner = new Scanner(System.in);
    }

    @Override
    protected void conductTurn() {
        System.out.println(hand);
        System.out.println("Human's turn");

        System.out.println("[p] Play Domino");
        System.out.println("[d] Draw from boneyard");
        System.out.println("[q] Quit");

        String input = scanner.nextLine();
        try {
            char parsedInput = CustomParser.parseChar(input);

            switch (parsedInput) {
                case 'p':
                case 'd':
                case 'q':
                    System.out.println("Thanks for playing!");
                    return;
                default:
            }
        } catch (InputErrorException inputErrorException) {
            System.out.println(inputErrorException.getMessage());
            conductTurn();
        }
    }

    private void playDomino() {
        String input;

        try {
            System.out.println("Which Domino?");
            input = scanner.nextLine();
            int dominoIndex = CustomParser.parseInt(input);

            System.out.println("Left or Right? (l/r)");
            System.out.println("Rotate first? (y/n)");
        } catch (InputErrorException inputErrorException) {
            inputErrorException.printStackTrace();
        }
    }
}
