package gui.gamePieces;

import basePieces.BoneyardBase;

import java.util.List;

/**
 * The boneyard for the GUI version. Pretty much the same as the console
 * version with the separate setup of the boneyard
 */
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
