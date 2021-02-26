package console.gamePieces;

import console.Main;
import console.constants.SideOfBoard;
import exceptions.DominoOutOfBoundsException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Hand<DominoType extends Domino> {
    protected final Boneyard<DominoType> boneyard;
    protected final List<DominoType> hand;
    protected static ThreadLocalRandom random;

    public Hand(Boneyard<DominoType> boneyard) {
        this.boneyard = boneyard;
        hand = boneyard.giveInitialHand();
        random = ThreadLocalRandom.current();
    }

    public List<DominoType> getHand() {
        return hand;
    }

    public DominoType drawDomino() {
        DominoType domino = boneyard.removeRandomDomino();

        if (domino != null) {
            hand.add(domino);
        }

        return domino;
    }

    public DominoType playDomino(int index) {
        return hand.remove(index);
    }

    /**
     * Removes the given domino from the hand and returns the same domino
     * (for consistency when overloading the method above)
     *
     * Precondition: the domino is already determined to contained in the hand
     *
     * @return the removed domino
     */
    public DominoType playDomino(Domino domino) {
        hand.remove(domino);
        return (DominoType) domino;
    }

    public void checkDominoBounds(int index) throws DominoOutOfBoundsException {
        if (index < 0 || index >= hand.size()) {
            throw new DominoOutOfBoundsException();
        }
    }

    public int getNumDominos() {
        return hand.size();
    }

    /**
     * If the domino is found with the given domino value, the matching value
     * will always be on the opposite side of the matchIndex for the returned
     * Domino object (i.e. the matching value on the left of the domino will
     * match the right value) where 0 is left and 1 is right
     *
     * Note: for the ComputerPlayer
     *
     * @param dominoValue
     * @param matchSide
     * @return
     */
    public Domino searchDominoAutoRotate(int dominoValue,
                                         SideOfBoard matchSide) {
        int dominoIndex = 0;
        for (Domino domino : hand) {
            if (dominoValue == domino.getLeftSide()) {
                if (matchSide == SideOfBoard.LEFT) {
                    if (Main.DEBUG) {
                        System.out.println("Domino (before rotate): "
                                + domino + " at index " + dominoIndex);
                    }

                    domino.rotateDomino();
                }

                return domino;
            } else if (dominoValue == domino.getRightSide()) {
                if (matchSide == SideOfBoard.RIGHT) {
                    if (Main.DEBUG) {
                        System.out.println("Domino (before rotate): "
                                + domino + " at index " + dominoIndex);
                    }

                    domino.rotateDomino();
                }

                return domino;
            }

            dominoIndex++;
        }

        return null; // returns null if the domino value was not found
    }

    /**
     * Checks to see if there is a domino in the hand with the given dominoValue
     * (left or right value)
     *
     * Note: for the HumanPlayer
     *
     * @param dominoValue value to check
     * @return true if there is a domino in the hand that has the
     * dominoValue; false otherwise
     */
    public DominoType searchDomino(int dominoValue) {
        int dominoIndex = 0;
        for (Domino domino : hand) {
            if (dominoValue == domino.getLeftSide()
                    || dominoValue == domino.getRightSide()) {
                if (Main.DEBUG) {
                    System.out.println("Index: " + dominoIndex);
                }

                return (DominoType) domino;
            }

            dominoIndex++;
        }

        return null; // returns null if the domino value was not found
    }

    /**
     * Sets the right orientation of the domino given the dominoValue is in
     * either the left or right side of the domino given
     *
     * Precondition: dominoValue is one of the values of the given domino
     * (left or right)
     *
     * @param dominoValue
     * @param domino
     * @return 0 if on left, 1 if on right, -1 if there's no match
     */
    public SideOfBoard setDominoOrientation(int dominoValue, Domino domino,
                                    SideOfBoard matchSide) {
        if (dominoValue == domino.getLeftSide()) {
            if (matchSide == SideOfBoard.LEFT) {
                domino.rotateDomino();
                return SideOfBoard.LEFT;
            }

            return SideOfBoard.RIGHT;
        } else if (dominoValue == domino.getRightSide()) {
            if (matchSide == SideOfBoard.RIGHT) {
                domino.rotateDomino();
                return SideOfBoard.RIGHT;
            }

            return SideOfBoard.LEFT;
        }


        return null;
    }

    public Domino getDomino(int index) {
        return hand.get(index);
    }

    public int countDominosInHand() {
        int totalCount = 0;
        for (Domino domino : hand) {
            totalCount += domino.getLeftSide();
            totalCount += domino.getRightSide();
        }

        return totalCount;
    }

    @Override
    public String toString() {
        String tray = "Tray: [";

        boolean start = true;
        for (Domino domino : hand) {
            if (!start) {
                tray += ", ";
            } else {
                start = false;
            }

            tray += domino;
        }

        tray += "]";
        return tray;
    }
}
