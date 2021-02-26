package basePieces;

import constants.SideOfBoard;
import console.gamePieces.Domino;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class Player<DominoType extends Domino> {
    protected HandBase<DominoType> hand;
    protected List<DominoType> playAreaDominos;
    protected boolean shift;
    protected boolean takeTurn;
    protected int[] numsToMatch;
    protected Player<DominoType> otherPlayer;
    protected boolean setupNumsToMatch;
    protected boolean canPlayDomino;
    protected boolean canDrawDomino;
    protected Map<SideOfBoard, Integer> sideNumMatchPair;
    protected Class<DominoType> dominoTypeClass;

    public Player(BoneyardBase<DominoType> boneyard) {
        // TODO: moved hand initialization to subclasses...
        playAreaDominos = new ArrayList<>();
        shift = false;
        takeTurn = true;
        setupNumsToMatch = false;
        canPlayDomino = true;
        canDrawDomino = true;
        sideNumMatchPair = new TreeMap<>();
    }

    public void conductTurn() {
        setNumsToMatch();
        setSideNumMatchPair();
    }

    protected abstract void setSideNumMatchPair();
    public abstract String getName();
    protected abstract void setNumsToMatch();

    //TODO: move...
    public abstract void printPlayAreaDominos();
    public abstract int getPlayNumDominos();
    public abstract DominoType getFirstPlayDomino();
    public abstract void addShift();
    public abstract void removeShift();

    protected boolean handDEBUG() {
        if (Main.DEBUG) {
            //FIXME
            System.out.println("Find in hand call");

            //FIXME
            if (!setupNumsToMatch) {
                System.out.println("Exiting call...");
                return true;
                // just some output that is NOT
                // null so
                // canPlayDomino is still true (only to circumvent the first turn)
            }
        }

        return false;
    }

    protected DominoType findDominoInHand(boolean autoRotate) {
        DominoType dominoMatch = null; // default value
        for (Map.Entry<SideOfBoard, Integer> sideNumMatchEntry :
                sideNumMatchPair.entrySet()) {
            if (autoRotate) {
                dominoMatch = hand.searchDominoAutoRotate(
                        sideNumMatchEntry.getValue(),
                        sideNumMatchEntry.getKey());
            } else {
                dominoMatch = hand.searchDomino(
                        sideNumMatchEntry.getValue());
            }

            if (dominoMatch != null) {
                if (Main.DEBUG) {
                    //FIXME
                    System.out.println("Num to match: "
                            + sideNumMatchEntry.getValue());
                    hand.rotateDEBUG(sideNumMatchEntry.getValue(),
                            dominoMatch,
                            sideNumMatchEntry.getKey());
                }

                break;
            }
        }

        if (Main.DEBUG) {
            //FIXME
            if (dominoMatch != null) {
                System.out.println("Domino match " + dominoMatch);
            } else {
                System.out.println("No match");
            }
        }

        return dominoMatch;
    }

    //FIXME: getting the list directly...
    public List<DominoType> getHandList() {
        return hand.getHand();
    }

    //FIXME: getting the list directly...
    public List<DominoType> getPlayAreaDominos() {
        return playAreaDominos;
    }

    public boolean isShift() {
        return shift;
    }

    public boolean isTakeTurn() {
        return takeTurn;
    }

    public int getPlayAreaCountDomino() {
        int totalPlayCount = 0;
        for (DominoType playAreaDomino : playAreaDominos) {
            totalPlayCount += playAreaDomino.getLeftSide();
            totalPlayCount += playAreaDomino.getRightSide();
        }

        return totalPlayCount;
    }

    public int getNumDominos() {
        return hand.getNumDominos();
    }

    public DominoType getLastPlayDomino() {
        return playAreaDominos.get(playAreaDominos.size() - 1);
    }

    public void playDominoInPlayArea(int handIndex, SideOfBoard matchSide) {
        if (matchSide == SideOfBoard.LEFT) {
            playAreaDominos.add(0,
                    hand.playDomino(handIndex));
        } else {
            playAreaDominos.add(hand.playDomino(handIndex));
        }
    }

    public void playDominoInPlayArea(DominoType domino, SideOfBoard matchSide) {
        if (matchSide == SideOfBoard.LEFT) {
            playAreaDominos.add(0, hand.playDomino(domino));
        } else {
            playAreaDominos.add(hand.playDomino(domino));
        }
    }

    public boolean isPlayAreaEmpty() {
        return playAreaDominos.isEmpty();
    }

    public void printTray() {
        System.out.println(hand);
    }

    public void setOtherPlayer(Player<DominoType> otherPlayer) {
        this.otherPlayer = otherPlayer;
    }
}
