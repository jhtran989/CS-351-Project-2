package console.players;

import console.MainConsole;
import console.gamePieces.Boneyard;
import constants.SideOfBoard;
import console.gamePieces.Domino;

import java.util.Arrays;
import java.util.Map;

public class ComputerPlayer extends PlayerConsole {
    public ComputerPlayer(Boneyard boneyard) {
        super(boneyard);

        numsToMatch = new int[2];
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

        Domino dominoMatch = findDominoInHand(true);
        if (dominoMatch == null) {
            if (!drawSequence()) {
                System.out.println("Out of dominos...");
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

    @Override
    protected void setNumsToMatch() {
        if (!otherPlayer.isPlayAreaEmpty()) {
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

            if (MainConsole.DEBUG) {
                //FIXME
                System.out.println("Nums to match: " + Arrays.toString(
                        numsToMatch));
            }
        } else { // should not occur since the end game should end, but just in
            // case...
            setupNumsToMatch = false;
        }
    }

//    @Override
//    protected Domino findDominoInHand() {
//        if (MainConsole.DEBUG) {
//            //FIXME
//            System.out.println("Find in hand call");
//
//            //FIXME
//            if (!setupNumsToMatch) {
//                System.out.println("Exiting call...");
//                return Domino.HALF_BLANK; // just some output that is NOT null so
//                // canPlayDomino is still true (only to circumvent the first turn)
//            }
//        }
//
//        Domino dominoMatch = null; // default value
//        for (Map.Entry<SideOfBoard, Integer> sideNumMatchEntry :
//                sideNumMatchPair.entrySet()) {
//            dominoMatch = hand.searchDominoAutoRotate(
//                    sideNumMatchEntry.getValue(),
//                            sideNumMatchEntry.getKey());
//
//            if (dominoMatch != null) {
//                if (MainConsole.DEBUG) {
//                    //FIXME
//                    System.out.println("Num to match: "
//                            + sideNumMatchEntry.getValue());
//                    System.out.println("Side: " + sideNumMatchEntry.getKey());
//                }
//
//                break;
//            }
//        }
//
//        if (MainConsole.DEBUG) {
//            //FIXME
//            if (dominoMatch != null) {
//                System.out.println("Domino match " + dominoMatch);
//            } else {
//                System.out.println("No match");
//            }
//        }
//
//        return dominoMatch;
//    }

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
//        int matchIndex = 0;
        for (Map.Entry<SideOfBoard, Integer> sideNumEntry :
                sideNumMatchPair.entrySet()) {
            matchSide = hand.setDominoOrientation(
                    sideNumEntry.getValue(),
                    domino, sideNumEntry.getKey());

            if (matchSide != null) {
                break;
            }
        }

//        for (int toMatch : numsToMatch) {
//            match = hand.setDominoOrientation(toMatch,
//                    domino, sideOfBoard);
//
//            if (match != -1) {
//                break;
//            }
//
//            matchIndex++;
//        }

        if (MainConsole.DEBUG) {
            //FIXME
            System.out.println("Checking " + domino);
            System.out.println("Match side: " + matchSide == null ? "no " +
                    "match" : matchSide);
        }

        return matchSide;
    }

    private boolean drawSequence() {
        Domino newDomino = hand.drawDomino();
        System.out.println("The computer drew " + newDomino);

        SideOfBoard matchSide; // can only be -1, 0, or 1
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
    protected void setSideNumMatchPair() {
        sideNumMatchPair.clear();

        if (!otherPlayer.isPlayAreaEmpty()) {
            SideOfBoard[] sidesArray = SideOfBoard.values();

            if (MainConsole.DEBUG) {
                System.out.println("Side array: "
                        + Arrays.toString(sidesArray));
            }

            for (int i = 0; i < numsToMatch.length; i++) {
                sideNumMatchPair.put(sidesArray[i], numsToMatch[i]);
            }
        }
    }

    @Override
    public String toString() {
        return "Computer" + super.toString();
    }
}
