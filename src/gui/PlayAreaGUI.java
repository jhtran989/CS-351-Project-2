package gui;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import console.gamePieces.PlayArea;
import console.players.Player;

public class PlayAreaGUI<DominoType extends Domino>
        extends PlayArea<DominoType> {
    public PlayAreaGUI(Boneyard<DominoType> boneyard, Player<DominoType> humanPlayer,
                       Player<DominoType> computerPlayer) {
        super(boneyard, humanPlayer,
                computerPlayer);
    }

    @Override
    public void startGame() {
        humanPlayer.setOtherPlayer(computerPlayer);
        computerPlayer.setOtherPlayer(humanPlayer);
    }

    private void displayPlayArea() {

    }
}
