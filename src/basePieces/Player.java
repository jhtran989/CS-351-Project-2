package basePieces;

import console.MainConsole;
import constants.SideOfBoard;
import console.gamePieces.Domino;

import java.util.*;

/**
 * Base for the Player class. Contains a variety of variables to check during
 * the course of the game as well as key game pieces, like the hand and list
 * of dominos in the play area. Has the primary method conductTurn() to
 * execute the primary actions in the game (GUI modifies this a bit since the
 * actions are moved to the buttons).
 *
 * @param <DominoType>
 */
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
    protected SideOfBoard matchSideOtherPlayer;

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

    /**
     * The primary method for the console version for all the actions and
     * checks to be performed during a turn. Relegates down to just side
     * checks in the GUI version, as mentioned above.
     */
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

    public void setTakeTurn(boolean takeTurn) {
        this.takeTurn = takeTurn;
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
                    matchSideOtherPlayer = SideOfBoard.LEFT;
                } else { // extend to the right
                    numsToMatch.add(
                            otherPlayer.getLastPlayDomino().getRightSide());
                    matchSideOtherPlayer = SideOfBoard.RIGHT;
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

    /**
     * Actually sets the mapping of the values to match and the side on to
     * domino to match with. The general algorithm is to check if this player
     * has less, equal, or more dominos in play. If this player has less,
     * then there's two options to check (mostly for the computer since the
     * player goes first), and if this player has the same, then there's only
     * one availabel move to make (mostly for the player for the same reason).
     */
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
                sideNumMatchPair.put(matchSideOtherPlayer, numsToMatch.get(0));
            } else {
                System.out.println("No valid moves...");
                return;
            }
        }
    }

    /**
     * Debug uses and to crucially return from the call in findDominoInHand()
     * to circumvent the weird conditions during the first turn (mainly for
     * the human player since there's really nothing to check)
     *
     * @return if there no dominos in play already (specific conditions to
     * check), then return true; otherwise, return false
     */
    protected boolean handDEBUG() {
        if (Main.DEBUG) {
            //FIXME
            System.out.println("Find in hand call");
        }

        if (!setupNumsToMatch) {
            if (Main.DEBUG) {
                System.out.println("Exiting call...");
            }
            return true;
            // just some output that is NOT
            // null so
            // canPlayDomino is still true (only to circumvent the first turn)
        }

        return false;
    }

    /**
     * Checks to see if there is a possible domino in the hand that can be
     * played. Goes through the possible mappings and returns the first
     * domino that can be played (as a valid move).
     *
     * Precondition: sideNumMatchPair has already been set to the correct
     * condtions (values and sides) to check
     * @param autoRotate if the domino should be rotated before being returned
     * @return the matching domino (null if none are found)
     */
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

    /**
     * Counts the dominos in the play area and returns the total count (end
     * game)
     *
     * @return count of dominos in play area
     */
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

    /**
     * If the domino should be played on the left, then the domino should be
     * inserted at the beginning of the list of play area dominos; otherwise,
     * insert at the end
     *
     * @param handIndex index of domino in hand to be played
     * @param matchSide which side to play the domino
     */
    public void playDominoInPlayArea(int handIndex, SideOfBoard matchSide) {
        if (matchSide == SideOfBoard.LEFT) {
            playAreaDominos.add(0,
                    hand.playDomino(handIndex));
        } else {
            playAreaDominos.add(hand.playDomino(handIndex));
        }
    }

    /**
     * Same as above
     *
     * @param domino
     * @param matchSide
     */
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
