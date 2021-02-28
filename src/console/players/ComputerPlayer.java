package console.players;

import console.MainConsole;
import console.gamePieces.Boneyard;
import constants.SideOfBoard;
import console.gamePieces.Domino;

import java.util.Arrays;
import java.util.Map;

/**
 * Computer player for the console version. Kind of simple besides the
 * algorithm to find a viable move. The noticeable difference from the human
 * player is the drawSequence() method that clumps all of the draws of
 * dominos together
 */
public class ComputerPlayer extends PlayerConsole {
    public ComputerPlayer(Boneyard boneyard) {
        super(boneyard);
    }

    /**
     * If there was a match found, play the domino. If not, initiate the draw
     * sequence until a match is found. When drawing is not possible, pass
     * the turn.
     */
    @Override
    public void conductTurn() {
        super.conductTurn();

        Domino dominoMatch = findDominoInHand(true);
        if (dominoMatch == null) {
            if (!drawSequence()) {
                System.out.println("Out of moves...");
                System.out.println("The Computer player ended its turn...");
                takeTurn = false;
                return;
            }
        } else {
            SideOfBoard matchSide = checkDominoMatch(dominoMatch);
            playDomino(matchSide, dominoMatch);
        }

        takeTurn = true;
    }

    @Override
    public String getName() {
        return "Computer";
    }

    /**
     * Checks if the Domino given in the parameter matches one of the two end
     * dominos of the human player; if so, return 0 for a left domino match,
     * and return 1 for a right domino match; else, return -1 for no match
     *
     * @param domino
     * @return
     */
    private SideOfBoard checkDominoMatch(Domino domino) {
        if (MainConsole.DEBUG) {
            //FIXME
            System.out.println("Check domino match call");
        }

        SideOfBoard matchSide = null;
        for (Map.Entry<SideOfBoard, Integer> sideNumEntry :
                sideNumMatchPair.entrySet()) {
            matchSide = hand.setDominoOrientation(
                    sideNumEntry.getValue(),
                    domino, sideNumEntry.getKey());

            if (matchSide != null) {
                break;
            }
        }

        if (MainConsole.DEBUG) {
            //FIXME
            System.out.println("Checking " + domino);
            System.out.println("Match side: " + matchSide == null ? "no " +
                    "match" : matchSide);
        }

        return matchSide;
    }

    /**
     * Keeps drawing until a match is found, or the boneyard runs out of
     * dominos (whichever comes first)
     * @return true if a match was found; false if no match (can't keep drawing)
     */
    private boolean drawSequence() {
        Domino newDomino = hand.drawDomino();
        System.out.println("The computer drew " +  newDomino);

        SideOfBoard matchSide;
        while (newDomino != null) {
            matchSide = checkDominoMatch(newDomino);

            if (matchSide != null) {
                playDomino(matchSide, newDomino);

                return true;
            } else {
                newDomino = hand.drawDomino();
                System.out.println("The computer drew " + newDomino);
            }
        }

        return false;
    }

    private void playDomino(SideOfBoard matchSide, Domino newDomino) {
        System.out.print("Playing " + newDomino + " at ");
        if (matchSide == SideOfBoard.LEFT) {
            System.out.println("left");
            playDominoInPlayArea(newDomino, matchSide);

            shift = false;
        } else {
            System.out.println("right");
            playDominoInPlayArea(newDomino, matchSide);

            // No change to shift (same shift position)
            // Any player can place a domino to the right and still
            // have no shift; the other case would require one of the
            // players to skip a turn...
        }
    }

    @Override
    public void printTray() {
        System.out.print("Computer's ");
        super.printTray();
    }

    @Override
    public String toString() {
        return "Computer" + super.toString();
    }
}
