package gui;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoneyardGUI<DominoType extends Domino> extends Boneyard<DominoType> {
    public BoneyardGUI() {
        dominoBoneyard = (List<DominoType>) DominoGUI.setUpBoneyardGUI();
        random = ThreadLocalRandom.current();
    }

    @Override
    public List<DominoType> giveInitialHand() {
        return super.giveInitialHand();
    }
}
