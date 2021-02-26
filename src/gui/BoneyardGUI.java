package gui;

import basePieces.BoneyardBase;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoneyardGUI extends BoneyardBase<DominoGUI> {
    public BoneyardGUI() {
        super();

        dominoBoneyard = DominoGUI.setUpBoneyardGUI();
    }

    @Override
    public List<DominoGUI> giveInitialHand() {
        return super.giveInitialHand();
    }
}
