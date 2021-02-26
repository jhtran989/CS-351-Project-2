package gui;

import basePieces.BoneyardBase;
import basePieces.PlayAreaBase;
import basePieces.Player;
import gui.players.HumanPlayerGUI;

public class PlayAreaGUI extends PlayAreaBase<DominoGUI> {
    public PlayAreaGUI(BoneyardBase<DominoGUI> boneyard,
                       Player<DominoGUI> humanPlayer,
                       Player<DominoGUI> computerPlayer) {
        super(boneyard, humanPlayer,
                computerPlayer);
    }

    @Override
    protected Player<DominoGUI> getOtherPlayer(Player<DominoGUI> player) {
        if (player instanceof HumanPlayerGUI) {
            return humanPlayer;
        } else {
            return computerPlayer;
        }
    }

    private void displayPlayArea() {

    }
}
