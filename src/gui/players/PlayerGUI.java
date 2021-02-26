package gui.players;

import basePieces.BoneyardBase;
import basePieces.Player;
import gui.DominoGUI;

public abstract class PlayerGUI extends Player<DominoGUI> {
    public PlayerGUI(BoneyardBase<DominoGUI> boneyard) {
        super(boneyard);
    }

    public void printTray() {
        System.out.println(hand);
    }
}
