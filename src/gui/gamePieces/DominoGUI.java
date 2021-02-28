package gui.gamePieces;

import console.gamePieces.Domino;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import utilities.CustomImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Similar to the Domino class in the console version, but there's a lot of
 * management that trickles up the the classes that use them (instance
 * variables, interaction,...). There's the addition of an image for each
 * domino (the actual object used to visualize the domino). Similar to the
 * console version, a blank domino was used (this time, the domino had to be
 * created from scratch using the dimensions defined here)
 */
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

    /**
     * Same as the console version, but the images are also set from the
     * resources folder
     *
     * @return list of domino objects for the GUI version
     */
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

    /**
     * Also rotates the image of the domino
     */
    @Override
    public void rotateDomino() {
        super.rotateDomino();

        dominoImage.setRotate(dominoImage.getRotate() + 180);
    }
}
