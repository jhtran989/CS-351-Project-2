package gui;

import basePieces.HandBase;
import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import console.gamePieces.Hand;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class HandGUI extends HandBase<DominoGUI> {
    //FIXME: temporary for seeing the hand...
    public List<ImageView> dominoImageList;

    public HandGUI(BoneyardGUI boneyard) {
        super(boneyard);

//        //FIXME
        dominoImageList = new ArrayList<>();
//        setDominoImageList();
    }

    public void setDominoImageList() {
        //FIXME
        System.out.println("Print the hand: " + hand);

        for (DominoGUI dominoType: hand) {
            //FIXME
            System.out.println("Fetching...");
            ImageView imageView = dominoType.getDominoImage();
            dominoImageList.add(imageView);
        }
    }
}
