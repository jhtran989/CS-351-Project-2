package basePieces;

import console.MainConsole;
import constants.SideOfBoard;
import console.gamePieces.Domino;
import exceptions.PlayDominoException;

import java.util.*;

public abstract class Player<DominoType extends Domino> {
    protected HandBase<DominoType> hand;
    protected List<DominoType> playAreaDominos;
    protected boolean shift;
    protected boolean takeTurn;
    protected List<Integer> numsToMatch;
    protected Player<DominoType> otherPlayer;
    protected boolean setupNumsToMatch;
    protected boolean canPlayDomino;
    protected boolean canDrawDomino;
    protected Map<SideOfBoard, Integer> sideNumMatchPair;
    protected SideOfBoard matchSide;

    public Player(BoneyardBase<DominoType> boneyard) {
        // TODO: moved hand initialization to subclasses...
        playAreaDominos = new ArrayList<>();
        shift = false;
        takeTurn = true;
        numsToMatch = new ArrayList<>();
        setupNumsToMatch = false;
        canPlayDomino = true;
        canDrawDomino = true;
        sideNumMatchPair = new TreeMap<>();
    }

    public void conductTurn() {
        setNumsToMatch();
        setSideNumMatchPair();

        canDrawDomino = true;
        canPlayDomino = true;
    }

    public abstract String getName();

    //TODO: move...
    public abstract int getPlayAreaNumDominos();
    public abstract DominoType getFirstPlayDomino();
    public abstract void addShift();
    public abstract void removeShift();

    public void printPlayAreaDominos() {
        for (DominoType playAreaDomino : playAreaDominos) {
            System.out.print(playAreaDomino);
        }

        System.out.println();
    }

    /**
     * The Human player only has one move (besides the first turn) during the
     * course of the game -- there will only be one domino on the
     * Computer player's playing area to extend from (as long as no turns are
     * skipped...)
     *
     * The Computer player has two moves during the course of the game --
     * besides the first turn where the single domino can be extended from
     * either side for the Human player (as long as no turns are skipped...)
     *
     * Analyzing the play area, three cases occur depending on how many
     * dominos both players have in play
     */
    protected void setNumsToMatch() {
        numsToMatch.clear();

        if (!otherPlayer.isPlayAreaEmpty()) {
            setupNumsToMatch = true;

            if (getPlayAreaNumDominos() < otherPlayer.getPlayAreaNumDominos()) {
                if (otherPlayer.getPlayAreaNumDominos() == 1) {
                    Domino singleDomino = otherPlayer.getFirstPlayDomino();
                    numsToMatch.add(singleDomino.getLeftSide());
                    numsToMatch.add(singleDomino.getRightSide());
                } else {
                    Domino firstDomino = otherPlayer.getFirstPlayDomino();
                    Domino lastDomino = otherPlayer.getLastPlayDomino();
                    numsToMatch.add(firstDomino.getLeftSide());
                    numsToMatch.add(lastDomino.getRightSide());
                }
            } else if (getPlayAreaNumDominos() ==
                    otherPlayer.getPlayAreaNumDominos()) {
                if (isShift()) { // extend to the left
                    numsToMatch.add(
                            otherPlayer.getFirstPlayDomino().getLeftSide());
                    matchSide = SideOfBoard.LEFT;
                } else { // extend to the right
                    numsToMatch.add(
                            otherPlayer.getLastPlayDomino().getRightSide());
                    matchSide = SideOfBoard.RIGHT;
                }
            } else {
                System.out.println("Extreme case reached...");
                return; // an extreme case where the current player has no
                // valid moves...(the other player missed turns)
            }

            if (Main.DEBUG) {
                //FIXME
                System.out.println("Nums to match: " + numsToMatch);
            }
        } else { // should only occur at the beginning of the game (for
            // the player that goes first -- human player)
            setupNumsToMatch = false;
        }
    }

    protected void setSideNumMatchPair() {
        sideNumMatchPair.clear();

        if (!otherPlayer.isPlayAreaEmpty()) {
            if (getPlayAreaNumDominos() < otherPlayer.getPlayAreaNumDominos()) {
                SideOfBoard[] sidesArray = SideOfBoard.values();

                if (MainConsole.DEBUG) {
                    System.out.println("Side array: "
                            + Arrays.toString(sidesArray));
                }

                for (int i = 0; i < numsToMatch.size(); i++) {
                    sideNumMatchPair.put(sidesArray[i], numsToMatch.get(i));
                }
            } else if (getPlayAreaNumDominos() ==
                    otherPlayer.getPlayAreaNumDominos()) {
                sideNumMatchPair.put(matchSide, numsToMatch.get(0));
            } else {
                System.out.println("No valid moves...");
                return;
            }
        }
    }

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

    public void setCanPlayDomino(boolean canPlayDomino) {
        this.canPlayDomino = canPlayDomino;
    }

    public void setCanDrawDomino(boolean canDrawDomino) {
        this.canDrawDomino = canDrawDomino;
    }

    public void setOtherPlayer(Player<DominoType> otherPlayer) {
        this.otherPlayer = otherPlayer;
    }
}
