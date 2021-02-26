package gui;

import console.gamePieces.Domino;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class DominoGUI extends Domino {
    private ImageView dominoImage;

    public DominoGUI(int leftSide, int rightSide, String url) {
        super(leftSide, rightSide);

        dominoImage = new ImageView(url);
        dominoImage.setFitWidth(100);
        dominoImage.setPreserveRatio(true);
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
}
