package console.players;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;

public class ComputerPlayer extends Player {
    private HumanPlayer humanPlayer;
    private int[] numsToMatch;

    public ComputerPlayer(Boneyard boneyard, HumanPlayer humanPlayer) {
        super(boneyard);

        this.humanPlayer = humanPlayer;
        numsToMatch = new int[2];
    }

    @Override
    public void conductTurn() {
        setNumsToMatch();
    }

    private void setNumsToMatch() {
        if (humanPlayer.getPlayNumDominos() == 1) {
            Domino singleDomino = humanPlayer.getFirstPlayDomino();
            numsToMatch[0] = singleDomino.getLeftSide();
            numsToMatch[1] = singleDomino.getRightSide();
        } else {
            Domino firstDomino = humanPlayer.getFirstPlayDomino();
            Domino lastDomino = humanPlayer.getLastPlayDomino();
            numsToMatch[0] = firstDomino.getLeftSide();
            numsToMatch[1] = lastDomino.getRightSide();
        }
    }

    private Domino findDominoInHand() {
        int matchIndex = 0;
        Domino dominoMatch = null; // default value
        for (int toMatch : numsToMatch) {
            dominoMatch = hand.searchDomino(toMatch);

            if (dominoMatch != null) {
                if (matchIndex == 0) { // rotates the domino to the right
                    // orientations since the matching domino will always be
                    // on the left hand side (see documentation for
                    // searchDomino() in the Hand class)
                    dominoMatch.rotateDomino();
                }
                break;
            }

            matchIndex++;
        }

        return dominoMatch;
    }

    private boolean checkDominoMatch(Domino domino) {
        int matchIndex = 0;
        for (int toMatch : numsToMatch) {
            if (domino.getLeftSide() == toMatch) {
                if (matchIndex == 0) {
                    domino.rotateDomino();
                    return true;
                }
            } else if (domino.getRightSide() == toMatch) {
                return true;
            }
        }

        return false;
    }

    //TODO: Figure out left/right shifts...
    private boolean drawSequence() {
        Domino newDomino = hand.drawDomino();

        while (newDomino != null) {
            if (checkDominoMatch(newDomino)) {

            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Computer" + super.toString();
    }
}
