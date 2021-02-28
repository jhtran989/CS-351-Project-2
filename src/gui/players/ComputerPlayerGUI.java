package gui.players;

import basePieces.BoneyardBase;
import console.MainConsole;
import console.gamePieces.Domino;
import constants.SideOfBoard;
import gui.MainGUI;
import gui.gamePieces.BoneyardGUI;
import gui.gamePieces.DominoGUI;

import java.util.Map;

/**
 * The computer player for the GUI version. It is essentially the same as the
 * console version, but due to lack of design foresight, a lot of the code
 * was just copy and pasted here (some output stuff to the console were
 * removed though and replaced with updates to the Labels in the GUI)
 */
public class ComputerPlayerGUI extends PlayerGUI {
    public ComputerPlayerGUI(BoneyardGUI boneyard) {
        super(boneyard);
    }

    /**
     * Pretty much the same as the console version
     */
    @Override
    public void conductTurn() {
        super.conductTurn();

        DominoGUI dominoMatch = findDominoInHand(true);
        if (dominoMatch == null) {
            if (!drawSequence()) {
                System.out.println("Out of moves...");
                System.out.println("The Computer player ended its turn...");
                guiStuff.getGameUpdateLabel().updateLabel(
                        "The computer " +
                        "player passed its turn with no valid moves...");
                takeTurn = false;
                return;
            }
        } else {
            SideOfBoard matchSide = checkDominoMatch(dominoMatch);
            playDomino(matchSide, dominoMatch);
        }

        takeTurn = true;
    }

    /**
     * Checks if the Domino given in the parameter matches one of the two end
     * dominos of the human player; if so, return 0 for a left domino match,
     * and return 1 for a right domino match; else, return -1 for no match
     *
     * @param domino
     * @return
     */
    private SideOfBoard checkDominoMatch(DominoGUI domino) {
        if (MainGUI.DEBUG) {
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

        if (MainGUI.DEBUG) {
            //FIXME
            System.out.println("Checking " + domino);
            System.out.println("Match side: " + matchSide == null ? "no " +
                    "match" : matchSide);
        }

        return matchSide;
    }

    private boolean drawSequence() {
        DominoGUI newDomino = hand.drawDomino();
        System.out.println("The computer drew " + newDomino);
        guiStuff.getGameUpdateLabel().updateLabel(
                "The computer drew " + newDomino);
        guiStuff.getComputerPlayerNumDominosLabel().setText(getDisplayName());

        SideOfBoard matchSide;
        while (newDomino != null) {
            matchSide = checkDominoMatch(newDomino);

            if (matchSide != null) {
                playDomino(matchSide, newDomino);

                return true;
            } else {
                newDomino = hand.drawDomino();
                System.out.println("The computer drew " + newDomino);
                guiStuff.getGameUpdateLabel().updateLabel(
                        "The computer drew " + newDomino);
                guiStuff.getComputerPlayerNumDominosLabel().
                        setText(getDisplayName());
            }
        }

        return false;
    }

    private void playDomino(SideOfBoard matchSide, DominoGUI newDomino) {
        String playDominoText = "Computer GUI playing " + newDomino + " at ";
        System.out.print(playDominoText);
        if (matchSide == SideOfBoard.LEFT) {
            System.out.println("left");
            playDominoText += "left";
            playDominoInPlayArea(newDomino, matchSide);

            shift = false;
        } else {
            System.out.println("right");
            playDominoText += "right";
            playDominoInPlayArea(newDomino, matchSide);

            // No change to shift (same shift position)
            // Any player can place a domino to the right and still
            // have no shift; the other case would require one of the
            // players to skip a turn...
        }

        guiStuff.getGameUpdateLabel().updateLabel(playDominoText);
        guiStuff.getComputerPlayerNumDominosLabel().setText(getDisplayName());
    }

    @Override
    public void printTray() {
        System.out.print("Computer's ");
        super.printTray();
    }

    @Override
    public String getName() {
        return "Computer GUI";
    }
}
