package console.players;

import console.constants.SideOfBoard;
import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import console.gamePieces.Hand;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected Hand hand;
    protected List<Domino> playAreaDominos;
    protected boolean shift;
    protected boolean takeTurn;
    protected int[] numsToMatch;
    protected Player otherPlayer;
    protected boolean setupNumsToMatch;
    protected boolean canPlayDomino;
    protected boolean canDrawDomino;

    public Player(Boneyard boneyard) {
        hand = new Hand(boneyard);
        playAreaDominos = new ArrayList<>();
        shift = false;
        takeTurn = true;
        setupNumsToMatch = false;
        canPlayDomino = true;
        canDrawDomino = true;
    }

    public void conductTurn() {
        setNumsToMatch();
    }

    public abstract String getName();
    protected abstract void setNumsToMatch();
    protected abstract Domino findDominoInHand();

    public boolean isShift() {
        return shift;
    }

    public boolean isTakeTurn() {
        return takeTurn;
    }

    public int getCountDomino() {
        return hand.countDominosInHand();
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

    public void playDominoInPlayArea(int handIndex, SideOfBoard sideOfBoard) {
        if (sideOfBoard == SideOfBoard.LEFT) {
            playAreaDominos.add(0,
                    hand.playDomino(handIndex));
        } else {
            playAreaDominos.add(hand.playDomino(handIndex));
        }
    }

    public void playDominoInPlayArea(Domino domino) {
        playAreaDominos.add(hand.playDomino(domino));
    }

    public void addShift() {
        if (!playAreaDominos.contains(Domino.HALF_BLANK)) {
            playAreaDominos.add(0, Domino.HALF_BLANK);
        }

        shift = true;
    }

    public void removeShift() {
        playAreaDominos.remove(Domino.HALF_BLANK);
        shift = false;
    }

    public boolean isPlayAreaEmpty() {
        return playAreaDominos.isEmpty();
    }

    public void printTray() {
        System.out.println(hand);
    }

    public void setOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }

    @Override
    public String toString() {
        return "'s turn";
    }
}
