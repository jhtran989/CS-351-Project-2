package console.players;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import console.gamePieces.Hand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Player {
    protected Hand hand;
    protected List<Domino> playAreaDominos;
    protected boolean shift;
    protected boolean takeTurn;
    protected int[] numsToMatch;
    protected Player otherPlayer;

    public Player(Boneyard boneyard) {
        hand = new Hand(boneyard);
        playAreaDominos = new ArrayList<>();
        shift = false;
        takeTurn = true;
        numsToMatch = new int[2];
    }

    public void conductTurn() {
        setNumsToMatch();
    }

    public abstract String getName();

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

    public boolean isPlayAreaEmpty() {
        return playAreaDominos.isEmpty();
    }

    public void printTray() {
        System.out.println(hand);
    }

    protected void setNumsToMatch() {
        if (otherPlayer.getPlayNumDominos() == 1) {
            Domino singleDomino = otherPlayer.getFirstPlayDomino();
            numsToMatch[0] = singleDomino.getLeftSide();
            numsToMatch[1] = singleDomino.getRightSide();
        } else {
            Domino firstDomino = otherPlayer.getFirstPlayDomino();
            Domino lastDomino = otherPlayer.getLastPlayDomino();
            numsToMatch[0] = firstDomino.getLeftSide();
            numsToMatch[1] = lastDomino.getRightSide();
        }

        //FIXME
        System.out.println("Nums to match: " + Arrays.toString(numsToMatch));
    }

    protected Domino findDominoInHand() {
        //FIXME
        System.out.println("Find in hand call");

        Domino dominoMatch = null; // default value
        int matchIndex = 0;
        for (int toMatch : numsToMatch) {
            dominoMatch = hand.searchDomino(toMatch,
                    matchIndex);

            if (dominoMatch != null) {
                //FIXME
                System.out.println("Num to match: " + toMatch);
                System.out.println("Side: " + matchIndex);

                break;
            }

            matchIndex++;
        }

        //FIXME
        if (dominoMatch != null) {
            System.out.println("Domino match " + dominoMatch);
        } else {
            System.out.println("No match");
        }

        return dominoMatch;
    }

    public void setOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }

    @Override
    public String toString() {
        return "'s turn";
    }
}
