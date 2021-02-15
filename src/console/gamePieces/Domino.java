package console.gamePieces;

import java.util.ArrayList;
import java.util.List;

public class Domino {
    private int leftSide;
    private int rightSide;

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

    private void rotateDomino() {
        int dummy = leftSide;
        leftSide = rightSide;
        rightSide = dummy;
    }

    @Override
    public String toString() {
        return "[" + leftSide + "  " + rightSide + "]";
    }
}
