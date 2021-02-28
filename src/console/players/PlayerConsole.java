package console.players;

import basePieces.BoneyardBase;
import basePieces.HandBase;
import basePieces.Player;
import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import console.gamePieces.Hand;

/**
 * The base player class for the console version. As shown, hand
 * initialization will happen in the specific version and includes separate
 * implementation for the console version. Kind of simple since the dominos
 * themselves are printed and handled in the game logic.
 */
public abstract class PlayerConsole extends Player<Domino> {
    public PlayerConsole(Boneyard boneyard) {
        super(boneyard);

        hand = new Hand(boneyard);
    }

    @Override
    protected Domino findDominoInHand(boolean autoRotate) {
        if (handDEBUG()) {
            return new Domino();
        }

        return super.findDominoInHand(autoRotate);
    }

    @Override
    public int getPlayAreaNumDominos() {
        if (playAreaDominos.contains(Domino.HALF_BLANK)) {
            return playAreaDominos.size() - 1;
        }

        return playAreaDominos.size();
    }

    @Override
    public Domino getFirstPlayDomino() {
        if (playAreaDominos.contains(Domino.HALF_BLANK)) {
            return playAreaDominos.get(1);
        }

        return playAreaDominos.get(0);
    }

    @Override
    public void addShift() {
        if (!playAreaDominos.contains(Domino.HALF_BLANK)) {
            playAreaDominos.add(0, Domino.HALF_BLANK);
        }

        shift = true;
    }

    @Override
    public void removeShift() {
        playAreaDominos.remove(Domino.HALF_BLANK);
        shift = false;
    }

    @Override
    public String toString() {
        return "'s turn";
    }

    @Override
    public void printTray() {
        System.out.println(hand);
    }
}
