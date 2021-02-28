package gui.players;

import basePieces.Player;
import constants.SideOfBoard;
import gui.gamePieces.BoneyardGUI;
import gui.gamePieces.DominoGUI;
import gui.gamePieces.GuiStuff;
import gui.gamePieces.HandGUI;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * As mentioned before, a lot of things were overridden with slightly
 * different implementation to account for more things to update and keep
 * track of. The play area map wasn't really used (as mentioned in the
 * README) since there wasn't a real advantage of using it, compared to just
 * a list of the images of the dominos
 */
public abstract class PlayerGUI extends Player<DominoGUI> {
    protected List<ImageView> playAreaImageList;
    protected Map<DominoGUI, ImageView> playAreaMap;
    protected DominoGUI currentDomino;
    private int dominoImageClickIndex;
    protected GuiStuff guiStuff;

    public PlayerGUI(BoneyardGUI boneyard) {
        super(boneyard);

        hand = new HandGUI(boneyard);
        playAreaImageList = new ArrayList<>();
        playAreaMap = new LinkedHashMap<>();
    }

    /**
     * Like before, the GUI stuff are initialized outside of the constructor
     * @param guiStuff the object containing all the GUI stuff
     */
    public void setGuiStuff(GuiStuff guiStuff) {
        this.guiStuff = guiStuff;
        ((HandGUI) hand).setLabels(guiStuff);
    }

    @Override
    protected DominoGUI findDominoInHand(boolean autoRotate) {
        if (handDEBUG()) {
            return new DominoGUI();
        }

        return super.findDominoInHand(autoRotate);
    }

    @Override
    public DominoGUI getFirstPlayDomino() {
        return playAreaDominos.get(0);
    }

    @Override
    public int getPlayAreaNumDominos() {
        return playAreaDominos.size();
    }

    @Override
    public void printTray() {
        System.out.println(hand);
    }

    @Override
    public void addShift() {
        if (!playAreaImageList.contains(DominoGUI.DOMINO_BLANK)) {
            playAreaImageList.add(0, DominoGUI.DOMINO_BLANK);
        }

        shift = true;
    }

    @Override
    public void removeShift() {
        playAreaImageList.remove(DominoGUI.DOMINO_BLANK);

        shift = false;
    }

    @Override
    public void playDominoInPlayArea(int handIndex, SideOfBoard matchSide) {
        DominoGUI dominoGUI = hand.playDomino(handIndex);
        if (matchSide == SideOfBoard.LEFT) {
            playAreaDominos.add(0,
                    dominoGUI);

            if (playAreaImageList.contains(DominoGUI.DOMINO_BLANK)) {
                playAreaImageList.add(1, dominoGUI.getDominoImage());
            } else {
                playAreaImageList.add(0, dominoGUI.getDominoImage());
            }
        } else {
            playAreaDominos.add(dominoGUI);
            playAreaImageList.add(dominoGUI.getDominoImage());
        }
    }

    @Override
    public void playDominoInPlayArea(DominoGUI domino, SideOfBoard matchSide) {
        DominoGUI dominoGUI = hand.playDomino(domino);
        if (matchSide == SideOfBoard.LEFT) {
            playAreaDominos.add(0,
                    dominoGUI);

            if (playAreaImageList.contains(DominoGUI.DOMINO_BLANK)) {
                playAreaImageList.add(1, dominoGUI.getDominoImage());
            } else {
                playAreaImageList.add(0, dominoGUI.getDominoImage());
            }
        } else {
            playAreaDominos.add(dominoGUI);
            playAreaImageList.add(dominoGUI.getDominoImage());
        }
    }

    public List<ImageView> getPlayAreaImageList() {
        return playAreaImageList;
    }

    public List<ImageView> getHandImageList() {
        HandGUI handGUI = (HandGUI) hand;
        return handGUI.getHandImageList();
    }

    /**
     * Overrides the conductTurn() method since we want the conditions
     * canPlayDomino and canDrawDomino to be handled a little differently...
     * (they're not set to true everytime one of the buttons is pressed since
     * an invalid move might occur for the human player)
     */
    @Override
    public void conductTurn() {
        setNumsToMatch();
        setSideNumMatchPair();
    }

    public String getDisplayName() {
        return getName() + " has " + hand.getNumDominos() + " dominos";
    }
}
