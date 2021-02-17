package console.gamePieces;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Domino> setUpBoneyard() {
        List<Domino> newBoneyard = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            for (int j = i; j < 6; j++) {
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
