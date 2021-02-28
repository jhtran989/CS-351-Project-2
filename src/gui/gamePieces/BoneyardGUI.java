package gui.gamePieces;

import basePieces.BoneyardBase;

import java.util.List;

public class BoneyardGUI extends BoneyardBase<DominoGUI> {
    public BoneyardGUI() {
        super();

        dominoBoneyard = DominoGUI.setUpBoneyardGUI();
    }

    @Override
    public String toString() {
        return "Boneyard contains " + dominoBoneyard.size() + " dominos";
    }
}
