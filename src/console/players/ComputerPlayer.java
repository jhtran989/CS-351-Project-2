package console.players;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;

import java.util.Arrays;

public class ComputerPlayer extends Player {
    public ComputerPlayer(Boneyard boneyard) {
        super(boneyard);

        numsToMatch = new int[2];
    }

    @Override
    public void conductTurn() {
        super.conductTurn();

        Domino dominoMatch = findDominoInHand();
        int matchIndex = checkDominoMatch(dominoMatch);
        if (dominoMatch == null) {
            if (!drawSequence()) {
                System.out.println("Out of dominos...");
                takeTurn = false;
                return;
            }
        } else {
            // Deep copy...
            // Domino playDomino = new Domino(dominoMatch);
            playDomino(matchIndex, dominoMatch);
        }

        takeTurn = true;
    }

    @Override
    public String getName() {
        return "Computer";
    }

    @Override
    protected void setNumsToMatch() {
        if (!otherPlayer.playAreaDominos.isEmpty()) {
            setupNumsToMatch = true;

            if (otherPlayer.getPlayNumDominos() == 1) {
                Domino singleDomino = otherPlayer.getFirstPlayDomino();
                numsToMatch[0] = singleDomino.getLeftSide();
                numsToMatch[1] = singleDomino.getRightSide();
            } else {
                Domino firstDomino = otherPlayer.getFirstPlayDomino();
                Domino lastDomino = otherPlayer.getLastPlayDomino();
                numsToMatch[0] = firstDomino.getLeftSide();
                numsToMatch[1] = lastDomino.getRightSide();
            }

            //FIXME
            System.out.println("Nums to match: " + Arrays.toString(numsToMatch));
        } else { // should not occur since the end game should end, but just in
            // case...
            setupNumsToMatch = false;
        }
    }

    @Override
    protected Domino findDominoInHand() {
        //FIXME
        System.out.println("Find in hand call");

        //FIXME
        if (!setupNumsToMatch) {
            System.out.println("Exiting call...");
            return Domino.HALF_BLANK; // just some output that is NOT null so
            // canPlayDomino is still true (only to circumvent the first turn)
        }

        Domino dominoMatch = null; // default value
        int matchIndex = 0;
        for (int toMatch : numsToMatch) {
            dominoMatch = hand.searchDominoAutoRotate(toMatch,
                    matchIndex);

            if (dominoMatch != null) {
                //FIXME
                System.out.println("Num to match: " + toMatch);
                System.out.println("Side: " + matchIndex);

                break;
            }

            matchIndex++;
        }

        //FIXME
        if (dominoMatch != null) {
            System.out.println("Domino match " + dominoMatch);
        } else {
            System.out.println("No match");
        }

        return dominoMatch;
    }

    /**
     * Checks if the Domino given in the parameter matches one of the two end
     * dominos of the human player; if so, return 0 for a left domino match,
     * and return 1 for a right domino match; else, return -1 for no match
     *
     * @param domino
     * @return
     */
    private int checkDominoMatch(Domino domino) {
        //FIXME
        System.out.println("Check domino match call");

        int match = -1;
        int matchIndex = 0;
        for (int toMatch : numsToMatch) {
            match = hand.setDominoOrientation(toMatch,
                    domino, matchIndex);

            if (match != -1) {
                break;
            }

            matchIndex++;
        }

        //FIXME
        System.out.println("Checking " + domino);
        System.out.println("Match value: " + match);

        return match != -1 ? matchIndex : match;
    }

    //TODO: Figure out left/right shifts...
    private boolean drawSequence() {
        Domino newDomino = hand.drawDomino();
        System.out.println("The computer drew " + newDomino);

        int matchIndex; // can only be -1, 0, or 1
        while (newDomino != null) {
            matchIndex = checkDominoMatch(newDomino);
            if (matchIndex != -1) {
                // Deep copy...
                // Domino playDomino = new Domino(newDomino);
                playDomino(matchIndex, newDomino);

                return true;
            } else {
                newDomino = hand.drawDomino();
                System.out.println("The computer drew " + newDomino);
            }
        }

        return false;
    }

    private void playDomino(int matchIndex, Domino newDomino) {
        System.out.print("Playing " + newDomino + " at ");
        if (matchIndex == 0) {
            System.out.println("left");
            playDominoInPlayArea(newDomino);

            shift = false;
        } else {
            System.out.println("right");
            playDominoInPlayArea(newDomino);

            shift = true;
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
