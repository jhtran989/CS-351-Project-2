package utilities;

import gui.gamePieces.DominoGUI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class CustomImageView {
    public static ImageView generateEmptyImage() {
        WritableImage blank = new WritableImage(1, 1);
        PixelWriter pixelWriter = blank.getPixelWriter();

        Color color = new Color(1, 1, 1, 0);
        pixelWriter.setColor(0, 0, color);

        ImageView blankImage = new ImageView(blank);
        blankImage.setFitWidth(DominoGUI.DOMINO_WIDTH / 2.0);
        blankImage.setFitHeight(DominoGUI.DOMINO_HEIGHT);
        blankImage.setPreserveRatio(false);

        return blankImage;
    }
}
