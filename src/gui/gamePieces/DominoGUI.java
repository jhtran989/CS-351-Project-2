package gui.gamePieces;

import console.gamePieces.Domino;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import utilities.CustomImageView;

import java.util.ArrayList;
import java.util.List;

public class DominoGUI extends Domino {
    private ImageView dominoImage;
    public static final double DOMINO_WIDTH = 130.0;
    public static final double DOMINO_HEIGHT =
            DOMINO_WIDTH * (65.0 / 125.0);
    public static final ImageView DOMINO_BLANK =
            CustomImageView.generateEmptyImage();

    public DominoGUI(int leftSide, int rightSide, String url) {
        super(leftSide, rightSide);

        dominoImage = new ImageView(url);
        dominoImage.setFitWidth(DOMINO_WIDTH);
        dominoImage.setPreserveRatio(true);
    }

    public DominoGUI() {
    }

    public ImageView getDominoImage() {
        return dominoImage;
    }

    public static List<DominoGUI> setUpBoneyardGUI() {
        List<DominoGUI> newBoneyard = new ArrayList<>();
        String urlEnd = "@0.5x.png";

        String initialFilePath = "file:resources/";
        String urlBeg;
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                urlBeg = initialFilePath + i + "_" + j;
                newBoneyard.add(new DominoGUI(i, j,
                        urlBeg + urlEnd));
            }
        }

        return newBoneyard;
    }

    @Override
    public void rotateDomino() {
        super.rotateDomino();

        dominoImage.setRotate(dominoImage.getRotate() + 180);
    }
}
