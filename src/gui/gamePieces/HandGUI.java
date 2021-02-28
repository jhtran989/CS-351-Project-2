package gui.gamePieces;

import basePieces.HandBase;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.*;

/**
 * Much different from the console version...There's a lot of other things to
 * check (like which domino is currently selected in the hand) and results in
 * a lot of the methods being overridden from the base class. It also uses
 * some of the GUI stuff to update the label of which domino was selected,
 * which first needs an event handler since the images themselves (ImageView)
 * doesn't have a built in event handler for a mouse click (has to be added
 * for every domino added to the hand, and removed for every domino that
 * leaves the hand...)
 */
public class HandGUI extends HandBase<DominoGUI> {
    //FIXME: temporary for seeing the hand...
    protected List<ImageView> handImageList;
    protected Map<DominoGUI, ImageView> handDominoImageMap;
    protected DominoGUI currentDominoClick;
    protected int currentDominoClickIndex;
    protected CustomLabel dominoSelectedLabel;
    protected Label boneyardLabel;
    protected EventHandler<MouseEvent> currentDominoClickEventSetup;
    protected EventHandler<MouseEvent> currentDominoClickEventDraw;

    public HandGUI(BoneyardGUI boneyard) {
        super(boneyard);

        handImageList = new ArrayList<>();
        handDominoImageMap = new LinkedHashMap<>();
        setDominoImageList();
        setMouseClickListenerInHand();
    }

    /**
     * The labels are not initialized in the constructor (called separately
     * to set the labels so they can be updated)
     *
     * @param guiStuff object containing the GUI stuff
     */
    public void setLabels(GuiStuff guiStuff) {
        dominoSelectedLabel = guiStuff.getDominoSelectedLabel();
        boneyardLabel = guiStuff.getBoneyardLabel();
    }

    /**
     * Initially sets the image list of dominos used to display them in the
     * GUI version
     */
    //TODO: should be set initially
    public void setDominoImageList() {
        //FIXME
        System.out.println("Print the hand: " + hand);

        for (DominoGUI dominoType: hand) {
            //FIXME
            System.out.println("Fetching...");
            ImageView imageView = dominoType.getDominoImage();
            handImageList.add(imageView);
            handDominoImageMap.put(dominoType, imageView);
        }
    }

    public List<ImageView> getHandImageList() {
        return handImageList;
    }

    public Map<DominoGUI, ImageView> getHandDominoImageMap() {
        return handDominoImageMap;
    }

    /**
     * Using a map, finds the domino linked to the given image of the domino
     * (works since its one to one: every key (domino) has a unique value
     * (image))
     * @param imageView
     * @return
     */
    public DominoGUI findDominoInHandMap(ImageView imageView) {
        for (Map.Entry<DominoGUI, ImageView> dominoGUIImageViewEntry :
                handDominoImageMap.entrySet()) {
            if (dominoGUIImageViewEntry.getValue() == imageView) {
                return dominoGUIImageViewEntry.getKey();
            }
        }

        return null;
    }

    protected void setMouseClickListenerInHand() {
        for (Map.Entry<DominoGUI, ImageView> dominoGUIImageViewEntry :
                handDominoImageMap.entrySet()) {
            currentDominoClickEventSetup = event -> {
                currentDominoClick = dominoGUIImageViewEntry.getKey();
                currentDominoClickIndex = getIndex(currentDominoClick);
                updateDominoSelectedLabel();
            };
            dominoGUIImageViewEntry.getValue().addEventHandler(
                    MouseEvent.MOUSE_CLICKED,
                    currentDominoClickEventSetup);
        }
    }

    protected void addMouseClickListenerInHand(DominoGUI dominoGUI) {
        currentDominoClickEventDraw = event -> {
            currentDominoClick = dominoGUI;
            currentDominoClickIndex = getIndex(currentDominoClick);
            updateDominoSelectedLabel();
        };

        dominoGUI.getDominoImage().addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                currentDominoClickEventDraw);
    }

    protected void removeMouseClickListenerInHand(DominoGUI dominoGUI) {
        if (currentDominoClickEventSetup != null) {
            dominoGUI.getDominoImage().removeEventHandler(
                    MouseEvent.MOUSE_CLICKED,
                    currentDominoClickEventSetup);
        }

        if (currentDominoClickEventDraw != null) {
            dominoGUI.getDominoImage().removeEventHandler(
                    MouseEvent.MOUSE_CLICKED,
                    currentDominoClickEventDraw);
        }
    }

    public DominoGUI getCurrentDominoClick() {
        return currentDominoClick;
    }

    public int getCurrentDominoClickIndex() {
        return currentDominoClickIndex;
    }

    public void updateDominoSelectedLabel() {
        dominoSelectedLabel.updateLabel(
                currentDominoClick + " " +
                "at index " + currentDominoClickIndex);
    }

    @Override
    public DominoGUI drawDomino() {
        DominoGUI dominoGUI = boneyard.removeRandomDomino();

        if (dominoGUI != null) {
            addDominoToHand(dominoGUI);
        } else {
            System.out.println("The boneyard is out of dominos...");
        }

        return dominoGUI;
    }

    /**
     * In addition to the implementation in the console version, the map had
     * to be updated (so the images are also updated) as well as removing the
     * mouse click listener so weird things don't happen when the user clicks
     * on them in the play area...
     *
     * @param index index of domino in hand to play
     * @return domino to play
     */
    @Override
    public DominoGUI playDomino(int index) {
        DominoGUI dominoGUI = super.playDomino(index);
        handImageList.remove(dominoGUI.getDominoImage());
        handDominoImageMap.remove(dominoGUI);
        removeMouseClickListenerInHand(dominoGUI);
        return dominoGUI;
    }

    /**
     * In addition to the implementation in the console version, the map had
     * to be updated (so the images are also updated) as well as removing the
     * mouse click listener so weird things don't happen when the user clicks
     * on them in the play area...
     *
     * @param domino domino in hand to play
     * @return domino to play
     */
    @Override
    public DominoGUI playDomino(DominoGUI domino) {
        handImageList.remove(domino.getDominoImage());
        handDominoImageMap.remove(domino);
        removeMouseClickListenerInHand(domino);
        return super.playDomino(domino);
    }

    /**
     * Just puts all of the things to do when adding a domino to the hand in
     * one place (updates the map, adds the mouse click listener, and updates
     * the number of dominos in the boneyard since a domino was drawn)
     *
     * @param dominoGUI domino to add to hand
     */
    private void addDominoToHand(DominoGUI dominoGUI) {
        hand.add(dominoGUI);
        handImageList.add(dominoGUI.getDominoImage());
        handDominoImageMap.put(dominoGUI, dominoGUI.getDominoImage());
        addMouseClickListenerInHand(dominoGUI);

        boneyardLabel.setText(boneyard.toString());
    }
}
