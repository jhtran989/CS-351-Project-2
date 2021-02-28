package gui.gamePieces;

import basePieces.HandBase;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class HandGUI extends HandBase<DominoGUI> {
    //FIXME: temporary for seeing the hand...
    protected List<ImageView> handImageList;
    protected Map<DominoGUI, ImageView> handDominoImageMap;
    protected DominoGUI currentDominoClick;
    protected int currentDominoClickIndex;
    protected CustomLabel dominoSelectedLabel;
    protected EventHandler<MouseEvent> currentDominoClickEventSetup;
    protected EventHandler<MouseEvent> currentDominoClickEventDraw;

    public HandGUI(BoneyardGUI boneyard) {
        super(boneyard);

        handImageList = new ArrayList<>();
        handDominoImageMap = new LinkedHashMap<>();
        setDominoImageList();
        setMouseClickListenerInHand();
    }

    public void setDominoSelectedLabel(GuiStuff guiStuff) {
        dominoSelectedLabel = guiStuff.getDominoSelectedLabel();
    }

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

    @Override
    public DominoGUI playDomino(int index) {
        DominoGUI dominoGUI = super.playDomino(index);
        handImageList.remove(dominoGUI.getDominoImage());
        handDominoImageMap.remove(dominoGUI);
        removeMouseClickListenerInHand(dominoGUI);
        return dominoGUI;
    }

    @Override
    public DominoGUI playDomino(DominoGUI domino) {
        handImageList.remove(domino.getDominoImage());
        handDominoImageMap.remove(domino);
        removeMouseClickListenerInHand(domino);
        return super.playDomino(domino);
    }

    private void addDominoToHand(DominoGUI dominoGUI) {
        hand.add(dominoGUI);
        handImageList.add(dominoGUI.getDominoImage());
        handDominoImageMap.put(dominoGUI, dominoGUI.getDominoImage());
        addMouseClickListenerInHand(dominoGUI);
    }
}
