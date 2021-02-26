package console.players;

import constants.SideOfBoard;
import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import exceptions.*;
import utilities.CustomParser;

import java.util.Arrays;
import java.util.Scanner;

public class HumanPlayer extends PlayerConsole {
    private final Scanner scanner;
    private SideOfBoard matchSide;

    public HumanPlayer(Boneyard boneyard) {
        super(boneyard);

        numsToMatch = new int[1];
        scanner = new Scanner(System.in);
    }

    @Override
    protected Domino findDominoInHand(boolean autoRotate) {
        if (handDEBUG()) {
            return Domino.HALF_BLANK;
        }

        return super.findDominoInHand(autoRotate);
    }

    @Override
    public void conductTurn() {
        super.conductTurn();

        printTray();

        System.out.println("[p] Play Domino");
        System.out.println("[d] Draw from boneyard");
        System.out.println("[q] Quit");

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

    /**
     * The Human player only has one move (besides the first turn) during the
     * course of the game -- there will only be one domino on the
     * ComputerPlayer's playing area to extend from
     */
    @Override
    protected void setNumsToMatch() {
        if (!otherPlayer.isPlayAreaEmpty()) {
            setupNumsToMatch = true;

            if (isShift()) { // extend to the left
                numsToMatch[0] = otherPlayer.getFirstPlayDomino().getLeftSide();
                matchSide = SideOfBoard.LEFT;
            } else { // extend to the right
                numsToMatch[0] = otherPlayer.getLastPlayDomino().getRightSide();
                matchSide = SideOfBoard.RIGHT;
            }

            //FIXME
            System.out.println("Nums to match: " + Arrays.toString(numsToMatch));
        } else { // should not occur since the end game should end, but just in
            // case...
            setupNumsToMatch = false;
        }
    }

//    @Override
//    protected Domino findDominoInHand() {
//        //FIXME
//        System.out.println("Find in hand call");
//
//        //FIXME
//        if (!setupNumsToMatch) {
//            System.out.println("Exiting call...");
//            return Domino.HALF_BLANK; // just some output that is NOT null so
//            // canPlayDomino is still true (only to circumvent the first turn)
//        }
//
//        Domino dominoMatch = null;
//        for (int toMatch : numsToMatch) {
//            dominoMatch = hand.searchDomino(toMatch);
//
//            if (dominoMatch != null) {
//                //FIXME
//                System.out.println("Num to match: " + toMatch);
//                System.out.println("Side: " + matchSide);
//
//                break;
//            }
//        }
//
//        //FIXME
//        if (dominoMatch != null) {
//            System.out.println("Domino match " + dominoMatch);
//        } else {
//            System.out.println("No match");
//        }
//
//        return dominoMatch;
//    }

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
                System.out.println("left");

                if (setupNumsToMatch) {
                    if (domino.getRightSide() != numsToMatch[0]) {
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
                    if (domino.getLeftSide() != numsToMatch[0]) {
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
    protected void setSideNumMatchPair() {
        sideNumMatchPair.clear();

        if (!otherPlayer.isPlayAreaEmpty()) {
            sideNumMatchPair.put(matchSide, numsToMatch[0]);
        }
    }

    @Override
    public String toString() {
        return "Human" + super.toString();
    }
}
