package gui.players;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import console.players.Player;

public class HumanPlayerGUI<DominoType extends Domino> extends Player<DominoType> {
    public HumanPlayerGUI(Boneyard<DominoType> boneyard) {
        super(boneyard);
    }

    @Override
    public void conductTurn() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    protected void setNumsToMatch() {

    }

    @Override
    protected Domino findDominoInHand() {
        return null;
    }

    @Override
    protected void setSideNumMatchPair() {

    }
}
