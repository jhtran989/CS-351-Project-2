package basePieces;

import console.MainConsole;
import console.gamePieces.Domino;
import constants.SideOfBoard;
import exceptions.DominoOutOfBoundsException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HandBase<DominoType extends Domino> {
    protected final BoneyardBase<DominoType> boneyard;
    protected final List<DominoType> hand;
    protected static ThreadLocalRandom random;

    public HandBase(BoneyardBase<DominoType> boneyard) {
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
    public DominoType playDomino(DominoType domino) {
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
     * @param matchSide
     * @return
     */
    public DominoType searchDominoAutoRotate(int dominoValue,
                                         SideOfBoard matchSide) {
        int dominoIndex = 0;
        for (DominoType domino : hand) {
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
        for (DominoType domino : hand) {
            if (dominoValue == domino.getLeftSide()
                    || dominoValue == domino.getRightSide()) {
                if (Main.DEBUG) {
                    System.out.println("Index: " + dominoIndex);
                }

                return domino;
            }

            dominoIndex++;
        }

        return null; // returns null if the domino value was not found
    }

    public void rotateDEBUG(int dominoValue, DominoType domino,
                            SideOfBoard matchSide) {
        if (dominoValue == domino.getLeftSide()) {
            if (matchSide == SideOfBoard.LEFT) {
                if (Main.DEBUG) {
                    System.out.println("Before rotating: " + domino);
                    System.out.println("Rotating...");
                    domino.rotateDomino();
                    System.out.println("After rotating: " + domino);
                    domino.rotateDomino();

                    System.out.println("Play at left");
                }
            } else {
                if (Main.DEBUG) {
                    System.out.println("No rotation needed");
                    System.out.println("Play at right");
                }
            }
        } else if (dominoValue == domino.getRightSide()) {
            if (matchSide == SideOfBoard.RIGHT) {
                if (Main.DEBUG) {
                    System.out.println("Before rotating: " + domino);
                    System.out.println("Rotating...");
                    domino.rotateDomino();
                    System.out.println("After rotating: " + domino);
                    domino.rotateDomino();

                    System.out.println("Play at right");
                }
            } else {
                if (Main.DEBUG) {
                    System.out.println("No rotation needed");
                    System.out.println("Play at left");
                }
            }
        }
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
    public SideOfBoard setDominoOrientation(int dominoValue, DominoType domino,
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

    public DominoType getDomino(int index) {
        return hand.get(index);
    }

    @Override
    public String toString() {
        return "HandBase{}";
    }
}
