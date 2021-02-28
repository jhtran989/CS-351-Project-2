package gui.players;

import constants.SideOfBoard;
import exceptions.*;
import gui.MainGUI;
import gui.gamePieces.*;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * The human player for the GUI version. A number of things were changed, and
 * most noticeably, the lack of direct questions from the console asking the
 * player about where and how the domino should be played. Instead, combo
 * boxes were used as the input and that definitely helped reduce the
 * checking required to proceed with the game
 */
public class HumanPlayerGUI extends PlayerGUI {
    protected HBox humanHandHB;

    public HumanPlayerGUI(BoneyardGUI boneyard) {
        super(boneyard);
    }

    /**
     * Relegated to just setting up the turn (setting the nums to match and
     * the sides of the board with the matching values). However, there's a
     * GUI implementation of showing when no valid moves are available (the
     * options of play and draw have to both be selected at least once before
     * the pop up window is shown with the message)
     */
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
            System.out.println("Note: You " +
                    "have no valid moves. Please end your turn by pressing " +
                    "the Pass button");

            Alert noValidMoveAlert = new Alert(Alert.AlertType.WARNING);
            noValidMoveAlert.setTitle("No valid moves available");
            noValidMoveAlert.setContentText("Note: You " +
                    "have no valid moves. Please end your turn by pressing " +
                    "the Pass button");
            noValidMoveAlert.show();
        }
    }

    /**
     * (Last minute change - see README in console version)
     * Plays the selected domino to the play area. There's still some
     * conditions to check, but remains largely the same (take turn condition
     * now pushed up from the play area in the console version to the player)
     *
     * @return true if the player was able to take a turn; false otherwise
     */
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

                if (MainGUI.DEBUG) {
                    System.out.println("Branch left");
                    System.out.println("Human num play area: " +
                            getPlayAreaNumDominos());
                    System.out.println("Computer num play area: " +
                            otherPlayer.getPlayAreaNumDominos());
                }

                if (setupNumsToMatch) {
                    if (getPlayAreaNumDominos() ==
                            otherPlayer.getPlayAreaNumDominos()) {
                        if (currentDomino.getRightSide() !=
                                numsToMatch.get(0)) {
                            if (MainGUI.DEBUG) {
                                System.out.println("Option 1a");
                            }
                            throw new DominoMismatchException();
                        } else if (sideToPlay != matchSideOtherPlayer) {
                            if (MainGUI.DEBUG) {
                                System.out.println("Option 1b");
                            }
                            throw new SideOfBoardMismatchException();
                        }
                    } else if (getPlayAreaNumDominos() <
                            otherPlayer.getPlayAreaNumDominos()) {
                        if (currentDomino.getLeftSide() != numsToMatch.get(0)
                                || currentDomino.getRightSide() !=
                                numsToMatch.get(1)) {
                            if (MainGUI.DEBUG) {
                                System.out.println("Option 2");
                            }
                            throw new DominoMismatchException();
                        }
                    } else {
                        if (MainGUI.DEBUG) {
                            System.out.println("Option 3");
                        }
                        throw new NoValidMovesException();
                    }
                }

                playDominoInPlayArea(
                        currentDominoIndex,
                        SideOfBoard.LEFT);
                guiStuff.getHumanPlayerNumDominosLabel().
                        setText(getDisplayName());

                if (getNumDominos() > 1) {
                    shift = false;
                }

                updateHumanHandHB();

                // updates the conditions canPlayDomino and canDrawDomino for
                // the next turn
                canPlayDomino = true;
                canDrawDomino = true;

                takeTurn = true;

                return true;
            } else {
                outputMessage += "right";
                guiStuff.getGameUpdateLabel().updateLabel(
                        outputMessage);

                if (MainGUI.DEBUG) {
                    System.out.println("Branch right");
                    System.out.println("Human num play area: " +
                            getPlayAreaNumDominos());
                    System.out.println("Computer num play area: " +
                            otherPlayer.getPlayAreaNumDominos());
                }

                if (setupNumsToMatch) {
                    if (getPlayAreaNumDominos() == otherPlayer.
                            getPlayAreaNumDominos()) {
                        if (currentDomino.getLeftSide() != numsToMatch.get(0)) {
                            if (MainGUI.DEBUG) {
                                System.out.println("Option 1a");
                            }
                            throw new DominoMismatchException();
                        } else if (sideToPlay != matchSideOtherPlayer) {
                            if (MainGUI.DEBUG) {
                                System.out.println("Option 1b");
                            }
                            throw new SideOfBoardMismatchException();
                        }
                    } else if (getPlayAreaNumDominos() <
                            otherPlayer.getPlayAreaNumDominos()) {
                        if (currentDomino.getLeftSide() != numsToMatch.get(0)
                                || currentDomino.getRightSide() !=
                                numsToMatch.get(1)) {
                            if (MainGUI.DEBUG) {
                                System.out.println("Option 2");
                            }
                            throw new DominoMismatchException();
                        }
                    } else {
                        if (MainGUI.DEBUG) {
                            System.out.println("Option 3");
                        }
                        throw new NoValidMovesException();
                    }
                }

                playDominoInPlayArea(
                        currentDominoIndex,
                        SideOfBoard.RIGHT);
                guiStuff.getHumanPlayerNumDominosLabel().
                        setText(getDisplayName());

                // No change to shift (same shift position)
                // Any player can place a domino to the right and still
                // have no shift; the other case would require one of the
                // players to skip a turn...

                updateHumanHandHB();

                // updates the conditions canPlayDomino and canDrawDomino for
                // the next turn
                canPlayDomino = true;
                canDrawDomino = true;

                takeTurn = true;

                return true;
            }
        } catch (PlayDominoException | DominoMismatchException
                | SideOfBoardMismatchException
                | NoValidMovesException exception) {
            System.out.println(exception.getMessage());
            guiStuff.getGameUpdateLabel().updateLabel(
                    exception.getMessage());
            return false;
        }
    }

    /**
     * Similar to play domino above (largely the same) and updates some of
     * the Labels in the GUI
     */
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
            guiStuff.getHumanPlayerNumDominosLabel().setText(getDisplayName());

            // Update the canPlayDomino conditional if the draw was successful
            canPlayDomino = true;

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
