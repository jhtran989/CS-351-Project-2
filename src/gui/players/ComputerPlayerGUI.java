package gui.players;

import basePieces.BoneyardBase;
import gui.DominoGUI;

public class ComputerPlayerGUI extends PlayerGUI {
    public ComputerPlayerGUI(BoneyardBase<DominoGUI> boneyard) {
        super(boneyard);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    protected void setNumsToMatch() {

    }

    @Override
    public void printPlayAreaDominos() {

    }

    @Override
    public int getPlayNumDominos() {
        return 0;
    }

    @Override
    public DominoGUI getFirstPlayDomino() {
        return null;
    }

    @Override
    public void addShift() {

    }

    @Override
    public void removeShift() {

    }

    @Override
    protected void setSideNumMatchPair() {

    }
}
