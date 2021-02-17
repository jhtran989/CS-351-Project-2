package console.players;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import console.gamePieces.Hand;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected Hand hand;
    protected List<Domino> playAreaDominos;
    protected boolean shift;

    public Player(Boneyard boneyard) {
        hand = new Hand(boneyard);
        playAreaDominos = new ArrayList<>();
        shift = false;
    }

    public abstract void conductTurn();

    public boolean isShift() {
        return shift;
    }

    public void printPlayAreaDominos() {
        for (Domino playAreaDomino : playAreaDominos) {
            System.out.print(playAreaDomino);
        }

        System.out.println();
    }

    public int getNumDominos() {
        return hand.getNumDominos();
    }

    public void addShift() {
        hand.addHalfBlank();
    }

    public void removeShift() {
        hand.removeHalfBlank();
    }

    public void resetShift() {
        shift = false;
    }

    public void printTray() {
        System.out.println(hand);
    }

    @Override
    public String toString() {
        return "'s turn";
    }
}
