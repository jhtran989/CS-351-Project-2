package console.players;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import exceptions.DominoOutOfBoundsException;
import exceptions.DrawDominoException;
import exceptions.InputErrorException;
import exceptions.PlayDominoException;
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
        super.conductTurn();

        printTray();

        System.out.println("[p] Play Domino");
        System.out.println("[d] Draw from boneyard");
        System.out.println("[q] Quit");

        String input = scanner.nextLine();
        try {
            char parsedInput = CustomParser.parseChar(input);

            switch (parsedInput) {
                case 'p':
                    playDomino();
                    takeTurn = true;
                    return;
                case 'd':
                    drawDomino();
                    conductTurn();
                case 'q':
                    System.out.println("The human player did not take a turn." +
                            "..");
                    takeTurn = false;
                    return;
                default:
                    throw new InputErrorException();
            }
        } catch (InputErrorException | PlayDominoException
                | DrawDominoException inputErrorException) {
            System.out.println(inputErrorException.getMessage());
            conductTurn();
        }
    }

    @Override
    public String getName() {
        return "Human";
    }

    private void playDomino() throws PlayDominoException {
        Domino matchDomino = findDominoInHand();
        if (matchDomino == null) {
            throw new PlayDominoException();
        }

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
                    shift = false;
                }
            } else {
                playAreaDominos.add(domino);
                System.out.println("right");

                if (getNumDominos() > 1) {
                    shift = true;
                }
            }
        } catch (InputErrorException | DominoOutOfBoundsException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private boolean drawDomino() throws DrawDominoException {
        Domino matchDomino = findDominoInHand();
        if (matchDomino != null) {
            throw new DrawDominoException();
        }

        System.out.println("Drawing a random domino from the boneyard...");

        Domino domino = hand.drawDomino();
        if (domino == null) {
            System.out.println("Out of dominos...");
            return false;
        }

        System.out.println("You drew " + domino);
        return true;
    }

    @Override
    public void printTray() {
        System.out.print("Human's ");
        super.printTray();
    }

    @Override
    public String toString() {
        return "Human" + super.toString();
    }
}
