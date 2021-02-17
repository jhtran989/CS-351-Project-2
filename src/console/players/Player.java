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

    public int getPlayNumDominos() {
        if (playAreaDominos.contains(Domino.HALF_BLANK)) {
            return playAreaDominos.size() - 1;
        }

        return playAreaDominos.size();
    }

    public Domino getFirstPlayDomino() {
        if (playAreaDominos.contains(Domino.HALF_BLANK)) {
            return playAreaDominos.get(1);
        }

        return playAreaDominos.get(0);
    }

    public Domino getLastPlayDomino() {
        return playAreaDominos.get(playAreaDominos.size() - 1);
    }

    public void addShift() {
        if (!playAreaDominos.contains(Domino.HALF_BLANK)) {
            playAreaDominos.add(0, Domino.HALF_BLANK);
        }
    }

    public void removeShift() {
        playAreaDominos.remove(Domino.HALF_BLANK);
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
