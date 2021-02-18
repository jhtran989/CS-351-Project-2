package console.gamePieces;

import exceptions.DominoOutOfBoundsException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Hand {
    private Boneyard boneyard;
    private List<Domino> hand;
    private static ThreadLocalRandom random;

    public Hand(Boneyard boneyard) {
        this.boneyard = boneyard;
        hand = boneyard.giveInitialHand();
        random = ThreadLocalRandom.current();
    }

    public Domino drawDomino() {
        Domino domino = boneyard.removeRandomDomino();

        if (domino != null) {
            hand.add(domino);
        }

        return domino;
    }

    public Domino playDomino(int index) {
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
    public Domino playDomino(Domino domino) {
        hand.remove(domino);
        return domino;
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
     * @return
     */
    public Domino searchDominoAutoRotate(int dominoValue, int matchIndex) {
        for (Domino domino : hand) {
            if (dominoValue == domino.getLeftSide()) {
                if (matchIndex == 0) {
                    domino.rotateDomino();
                }

                return domino;
            } else if (dominoValue == domino.getRightSide()) {
                if (matchIndex == 1) {
                    domino.rotateDomino();
                }

                return domino;
            }
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
    public Domino searchDomino(int dominoValue) {
        for (Domino domino : hand) {
            if (dominoValue == domino.getLeftSide()
                    || dominoValue == domino.getRightSide()) {
                return domino;
            }
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
    public int setDominoOrientation(int dominoValue, Domino domino,
                                    int matchIndex) {
        if (dominoValue == domino.getLeftSide()) {
            if (matchIndex == 0) {
                domino.rotateDomino();
                return 1;
            }

            return 0;
        } else if (dominoValue == domino.getRightSide()) {
            if (matchIndex == 1) {
                domino.rotateDomino();
                return 0;
            }

            return 1;
        }


        return -1;
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
