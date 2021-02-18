package console.players;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;

public class ComputerPlayer extends Player {
    public ComputerPlayer(Boneyard boneyard) {
        super(boneyard);
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
            Domino playDomino = new Domino(dominoMatch);
            playDomino(matchIndex, playDomino);
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
                Domino playDomino = new Domino(newDomino);
                playDomino(matchIndex, playDomino);

                return true;
            } else {
                newDomino = hand.drawDomino();
                System.out.println("The computer drew " + newDomino);
            }
        }

        return false;
    }

    private void playDomino(int matchIndex, Domino newDomino) {
        hand.playDomino();

        System.out.print("Playing " + newDomino + " at ");
        if (matchIndex == 0) {
            playAreaDominos.add(0, newDomino);
            System.out.println("left");

            shift = false;
        } else {
            playAreaDominos.add(newDomino);
            System.out.println("right");

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
