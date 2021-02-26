package console.gamePieces;

import basePieces.BoneyardBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Boneyard extends BoneyardBase<Domino> {
    public Boneyard() {
        super();

        dominoBoneyard = Domino.setUpBoneyard();
    }
}
