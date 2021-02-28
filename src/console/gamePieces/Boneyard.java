package console.gamePieces;

import basePieces.BoneyardBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Pretty much the same as the base, but has a unique method to set up the
 * boneyard
 */
public class Boneyard extends BoneyardBase<Domino> {
    public Boneyard() {
        super();

        dominoBoneyard = Domino.setUpBoneyard();
    }
}
