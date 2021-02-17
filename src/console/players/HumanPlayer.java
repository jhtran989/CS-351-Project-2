package console.players;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import exceptions.DominoOutOfBoundsException;
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
    public void conductTurn() {
        System.out.println("[p] Play Domino");
        System.out.println("[d] Draw from boneyard");
        System.out.println("[q] Quit");

        String input = scanner.nextLine();
        try {
            char parsedInput = CustomParser.parseChar(input);

            switch (parsedInput) {
                case 'p':
                    playDomino();
                case 'd':
                    drawDomino();
                case 'q':
                    System.out.println("Thanks for playing!");
                    return;
                default:
                    throw new InputErrorException();
            }
        } catch (InputErrorException inputErrorException) {
            System.out.println(inputErrorException.getMessage());
            conductTurn();
        }
    }

    private void playDomino() {
        String input;

        try {
            System.out.println("Which Domino? (Index will start at 0 at the " +
                    "leftmost position.)");
            input = scanner.nextLine();
            int dominoIndex = CustomParser.parseInt(input);
            hand.checkDominoBounds(dominoIndex);
            Domino domino = hand.playDomino(dominoIndex);

            System.out.println("Left or Right? (l/r)");
            input = scanner.nextLine();
            char leftRight = CustomParser.parseChar(input);
            if (leftRight != 'l' && leftRight != 'r') {
                throw new InputErrorException();
            }

            System.out.println("Rotate first? (y/n)");
            input = scanner.nextLine();
            char rotate = CustomParser.parseChar(input);
            if (rotate != 'y' && rotate != 'n') {
                throw new InputErrorException();
            }
            if (rotate == 'y') {
                domino.rotateDomino();
            }

            System.out.print("Playing " + domino + " at ");
            if (leftRight == 'l') {
                playAreaDominos.add(0, domino);
                System.out.println("left");

                if (getNumDominos() > 1) {
                    shift = true;
                }
            } else {
                playAreaDominos.add(domino);
                System.out.println("right");
            }
        } catch (InputErrorException | DominoOutOfBoundsException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void drawDomino() {
        System.out.println("Drawing a random domino from the boneyard...");

        Domino domino = hand.drawDomino();
        System.out.println("You drew " + domino);
    }

    @Override
    public String toString() {
        return "Human" + super.toString();
    }
}
