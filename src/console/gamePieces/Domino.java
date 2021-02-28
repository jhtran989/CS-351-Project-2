package console.gamePieces;

import java.util.ArrayList;
import java.util.List;

/**
 * The "Base" class of the domino. Contains left and right values (as well as
 * orientation defined by the relative positions of the values)
 */
public class Domino {
    private int leftSide;
    private int rightSide;

    // created only for shifting purposes (not actually a blank domino, but
    // to incorporate the row shift by half a domino)
    public static Domino HALF_BLANK = new Domino(0, 0) {
        @Override
        public String toString() {
            return "   ";
        }
    };

    public Domino(int leftSide, int rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    /**
     * Only used to get an instance of Domino (and any subclass of Domino)
     */
    public Domino() {
    }

    /**
     * Creates a deep clone of the domino using a constructor (just sets the
     * left and right values to the same values as the domino given)
     *
     * Note: NOT used (after some updates)
     * @param domino domino object to deep clone
     */
    public Domino(Domino domino) {
        this.leftSide = domino.getLeftSide();
        this.rightSide = domino.getRightSide();
    }

    /**
     * Sequentially adds all 28 dominos to the boneyard
     *
     * @return list of dominos initially in the boneyard
     */
    public static List<Domino> setUpBoneyard() {
        List<Domino> newBoneyard = new ArrayList<>();

        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                newBoneyard.add(new Domino(i, j));
            }
        }

        return newBoneyard;
    }

    public void rotateDomino() {
        int dummy = leftSide;
        leftSide = rightSide;
        rightSide = dummy;
    }

    public int getLeftSide() {
        return leftSide;
    }

    public int getRightSide() {
        return rightSide;
    }

    @Override
    public String toString() {
        return "[" + leftSide + "  " + rightSide + "]";
    }
}
