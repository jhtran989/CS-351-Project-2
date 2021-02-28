package console.players;

import console.MainConsole;
import constants.SideOfBoard;
import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import exceptions.*;
import utilities.CustomParser;

import java.util.Scanner;

/**
 * The Human player for the console version. In order to sort out the input,
 * a scanner was used with a custom parser in the utilities package. Similar
 * to the computer player, but a lot more checking (thrown exceptions are
 * found in the exceptions package) of the input and making sure the player
 * doesn't mess up the game logic
 */
public class HumanPlayer extends PlayerConsole {
    private final Scanner scanner;

    public HumanPlayer(Boneyard boneyard) {
        super(boneyard);

        scanner = new Scanner(System.in);
    }

    /**
     * Includes three possible actions: play a domino, draw from the
     * boneyard, or pass the turn
     */
    @Override
    public void conductTurn() {
        super.conductTurn();

        printTray();

        System.out.println("[p] Play Domino");
        System.out.println("[d] Draw from boneyard");
        System.out.println("[q] Quit");

        if (MainConsole.DEBUG) {
            System.out.println("Can play domino: " + canPlayDomino);
            System.out.println("Can draw domino: " + canDrawDomino);
        }

        // Condition satisfied only when the player selects to play a domino
        // and draw from the boneyard to update the boolean values at least
        // once each
        if (!(canPlayDomino || canDrawDomino)) {
            System.out.println("Note: You have no valid moves. Please end " +
                    "your turn by quitting [q]");
        }

        String input = scanner.nextLine();
        try {
            char parsedInput = CustomParser.parseChar(input);

            switch (parsedInput) {
                case 'p':
                    playDomino();
                    takeTurn = true;
                    return;
                case 'd':
                    if (!drawDomino()) {
                        System.out.println("You cannot draw from the boneyard" +
                                " anymore...");
                    }
                    conductTurn();
                    return;
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
            System.out.println("Error: " + inputErrorException.getMessage());
            conductTurn();
        }
    }

    @Override
    public String getName() {
        return "Human";
    }

    private void playDomino() throws PlayDominoException {
        Domino matchDomino = findDominoInHand(false);

        if (matchDomino == null) {
            canPlayDomino = false;
            throw new PlayDominoException();
        } else {
            canPlayDomino = true;
        }

        String input;

        try {
            System.out.println("Which Domino? (Index will start at 0 at the " +
                    "leftmost position.)");
            input = scanner.nextLine();
            int dominoIndex = CustomParser.parseInt(input);
            hand.checkDominoBounds(dominoIndex);
            Domino domino = hand.getDomino(dominoIndex);

            System.out.println("Left or Right? (l/r)");
            input = scanner.nextLine();
            char leftRight = CustomParser.parseChar(input);
            SideOfBoard sideToPlay = null;
            if (leftRight != 'l' && leftRight != 'r') {
                throw new InputErrorException();
            } else {
                if (leftRight == 'l') {
                    sideToPlay = SideOfBoard.LEFT;
                } else {
                    sideToPlay = SideOfBoard.RIGHT;
                }
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
                System.out.println("left");

                if (setupNumsToMatch) {
                    if (domino.getRightSide() != numsToMatch.get(0) ||
                            ((getPlayAreaNumDominos() ==
                                    otherPlayer.getPlayAreaNumDominos())
                                    && sideToPlay != matchSideOtherPlayer)) {
                        throw new DominoMismatchException();
                    }
                }

                playDominoInPlayArea(dominoIndex,
                        SideOfBoard.LEFT);

                if (getNumDominos() > 1) {
                    shift = false;
                }
            } else {
                System.out.println("right");

                if (setupNumsToMatch) {
                    if (domino.getLeftSide() != numsToMatch.get(0) ||
                            ((getPlayAreaNumDominos() ==
                                    otherPlayer.getPlayAreaNumDominos())
                                    && sideToPlay != matchSideOtherPlayer)) {
                        throw new DominoMismatchException();
                    }
                }

                playDominoInPlayArea(dominoIndex,
                        SideOfBoard.RIGHT);

                // No change to shift (same shift position)
                // Any player can place a domino to the right and still
                // have no shift; the other case would require one of the
                // players to skip a turn...
            }
        } catch (InputErrorException | DominoOutOfBoundsException
                | DominoMismatchException exception) {
            System.out.println("Error: " + exception.getMessage());
            conductTurn();
        }
    }

    private boolean drawDomino() throws DrawDominoException {
        Domino matchDomino = findDominoInHand(false);
        if (matchDomino != null) {
            canDrawDomino = false;
            throw new DrawDominoException();
        } else {
            canDrawDomino = true;
        }

        System.out.println("Drawing a random domino from the boneyard...");

        Domino domino = hand.drawDomino();
        if (domino == null) {
            System.out.println("Out of dominos...");
            canDrawDomino = false;
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
