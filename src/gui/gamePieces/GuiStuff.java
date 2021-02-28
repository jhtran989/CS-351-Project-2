package gui.gamePieces;

import constants.SideOfBoard;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * The class that stores all the GUI pieces (together in one place) that
 * needs to be updated throughout the game (like which side of the board the
 * user wants to play the domino at)
 */
public class GuiStuff {
    private final CustomLabel gameUpdateLabel;
    private final CustomLabel dominoSelectedLabel;
    private final ComboBox<SideOfBoard> sideOfBoardComboBox;
    private final ComboBox<Rotate> rotateComboBox;
    private final Label boneyardLabel;
    private final Label humanPlayerNumDominosLabel;
    private final Label computerPlayerNumDominosLabel;

    public GuiStuff(CustomLabel gameUpdateLabel, CustomLabel dominoSelectedLabel,
                    ComboBox<SideOfBoard> sideOfBoardComboBox,
                    ComboBox<Rotate> rotateComboBox, Label boneyardLabel,
                    Label humanPlayerNumDominosLabel,
                    Label computerPlayerNumDominosLabel) {
        this.gameUpdateLabel = gameUpdateLabel;
        this.dominoSelectedLabel = dominoSelectedLabel;
        this.sideOfBoardComboBox = sideOfBoardComboBox;
        this.rotateComboBox = rotateComboBox;
        this.boneyardLabel = boneyardLabel;
        this.humanPlayerNumDominosLabel = humanPlayerNumDominosLabel;
        this.computerPlayerNumDominosLabel = computerPlayerNumDominosLabel;
    }

    public CustomLabel getGameUpdateLabel() {
        return gameUpdateLabel;
    }

    public CustomLabel getDominoSelectedLabel() {
        return dominoSelectedLabel;
    }

    public ComboBox<SideOfBoard> getSideOfBoardComboBox() {
        return sideOfBoardComboBox;
    }

    public ComboBox<Rotate> getRotateComboBox() {
        return rotateComboBox;
    }

    public Label getBoneyardLabel() {
        return boneyardLabel;
    }

    public Label getHumanPlayerNumDominosLabel() {
        return humanPlayerNumDominosLabel;
    }

    public Label getComputerPlayerNumDominosLabel() {
        return computerPlayerNumDominosLabel;
    }
}
