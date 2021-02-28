package gui.gamePieces;

import constants.SideOfBoard;
import javafx.scene.control.ComboBox;

public class GuiStuff {
    private CustomLabel gameUpdateLabel;
    private CustomLabel dominoSelectedLabel;
    private ComboBox<SideOfBoard> sideOfBoardComboBox;
    private ComboBox<Rotate> rotateComboBox;

    public GuiStuff(CustomLabel gameUpdateLabel, CustomLabel dominoSelectedLabel,
                    ComboBox<SideOfBoard> sideOfBoardComboBox,
                    ComboBox<Rotate> rotateComboBox) {
        this.gameUpdateLabel = gameUpdateLabel;
        this.dominoSelectedLabel = dominoSelectedLabel;
        this.sideOfBoardComboBox = sideOfBoardComboBox;
        this.rotateComboBox = rotateComboBox;
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
}
