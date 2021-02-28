package gui.players;

import constants.SideOfBoard;
import exceptions.*;
import gui.MainGUI;
import gui.gamePieces.*;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class HumanPlayerGUI extends PlayerGUI {
    protected HBox humanHandHB;

    public HumanPlayerGUI(BoneyardGUI boneyard) {
        super(boneyard);
    }

    @Override
    public void conductTurn() {
        super.conductTurn();

        if (MainGUI.DEBUG) {
            System.out.println("Can play domino: " + canPlayDomino);
            System.out.println("Can draw domino: " + canDrawDomino);
        }

        if (!(canPlayDomino || canDrawDomino)) {
            guiStuff.getGameUpdateLabel().updateLabel("Note: You " +
                    "have no valid moves. Please end your turn by pressing " +
                    "the Pass button");
        }
    }

    public boolean playDomino() {
        DominoGUI matchDomino = findDominoInHand(false);

        try {
            if (matchDomino == null) {
                canPlayDomino = false;
                throw new PlayDominoException();
            } else {
                canPlayDomino = true;
            }

            SideOfBoard sideToPlay = guiStuff.getSideOfBoardComboBox().getValue();
            Rotate rotate = guiStuff.getRotateComboBox().getValue();
            if (rotate == Rotate.ROTATE_ON) {
                matchDomino.rotateDomino();
            }

            HandGUI handGUI = (HandGUI) hand;
            outputDominoWithIndex(matchDomino);
            String outputMessage =
                    "Playing " + guiStuff.getGameUpdateLabel().
                            getOutputMessage() + " at ";
            DominoGUI currentDomino = handGUI.getCurrentDominoClick();
            int currentDominoIndex = handGUI.getCurrentDominoClickIndex();

            if (sideToPlay == SideOfBoard.LEFT) {
                outputMessage += "left";
                guiStuff.getGameUpdateLabel().updateLabel(
                        outputMessage);

                if (setupNumsToMatch) {
                    if (currentDomino.getRightSide() != numsToMatch.get(0)) {
                        throw new DominoMismatchException();
                    }
                }

                playDominoInPlayArea(
                        currentDominoIndex,
                        SideOfBoard.LEFT);

                if (getNumDominos() > 1) {
                    shift = false;
                }

                updateHumanHandHB();

                // updates the conditions canPlayDomino and canDrawDomino for
                // the next turn
                canPlayDomino = true;
                canDrawDomino = true;

                return true;
            } else {
                outputMessage += "right";
                guiStuff.getGameUpdateLabel().updateLabel(
                        outputMessage);

                if (setupNumsToMatch) {
                    if (currentDomino.getLeftSide() != numsToMatch.get(0)) {
                        throw new DominoMismatchException();
                    }
                }

                playDominoInPlayArea(
                        currentDominoIndex,
                        SideOfBoard.RIGHT);

                // No change to shift (same shift position)
                // Any player can place a domino to the right and still
                // have no shift; the other case would require one of the
                // players to skip a turn...

                updateHumanHandHB();

                // updates the conditions canPlayDomino and canDrawDomino for
                // the next turn
                canPlayDomino = true;
                canDrawDomino = true;

                return true;
            }
        } catch (PlayDominoException | DominoMismatchException exception) {
            System.out.println(exception.getMessage());
            guiStuff.getGameUpdateLabel().updateLabel(
                    exception.getMessage());
            return false;
        }
    }

    public void drawDomino() {
        DominoGUI matchDomino = findDominoInHand(false);

        try {
            if (matchDomino != null) {
                canDrawDomino = false;
                throw new DrawDominoException();
            } else {
                canDrawDomino = true;
            }

            DominoGUI domino = hand.drawDomino();
            if (domino == null) {
                guiStuff.getGameUpdateLabel().updateLabel("Boneyard " +
                        "is out of dominos...");
                canDrawDomino = false;
                return;
            }

            guiStuff.getGameUpdateLabel().updateLabel("You drew "
                    + domino);

            updateHumanHandHB();

        } catch (DrawDominoException exception) {
            System.out.println(exception.getMessage());
            guiStuff.getGameUpdateLabel().updateLabel(
                    exception.getMessage());
        }
    }

    private void outputDominoWithIndex(DominoGUI dominoGUI) {
        guiStuff.getGameUpdateLabel().updateLabel(
                dominoGUI.toString() + " at" +
                " index " + ((HandGUI) hand).getCurrentDominoClickIndex());
    }

    public void setHumanHandHB(HBox humanHandHB) {
        this.humanHandHB = humanHandHB;
    }

    private void updateHumanHandHB() {
        humanHandHB.getChildren().clear();
        humanHandHB.getChildren().addAll(getHandImageList());
    }

    private int getDominoImageClickIndex(DominoGUI dominoGUI) {
        return hand.getIndex(dominoGUI);
    }

    private EventHandler<MouseEvent> getDominoImageClickEvent(
            ImageView imageView) {
        HandGUI handGUI = (HandGUI) hand;

        return event -> {
            currentDomino = handGUI.findDominoInHandMap(imageView);
        };
    }

    private EventHandler<MouseEvent> getDominoImageClickEvent(
            DominoGUI dominoGUI) {
        return event -> {
            currentDomino = dominoGUI;
        };
    }

    @Override
    public String getName() {
        return "Human GUI";
    }

    @Override
    public void printTray() {
        System.out.print("Human's ");
        super.printTray();
    }
}
