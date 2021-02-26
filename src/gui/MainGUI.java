package gui;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import console.gamePieces.Hand;
import console.gamePieces.PlayArea;
import console.players.ComputerPlayer;
import console.players.HumanPlayer;
import gui.players.ComputerPlayerGUI;
import gui.players.HumanPlayerGUI;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BoneyardGUI boneyard = new BoneyardGUI();
        HumanPlayerGUI humanPlayer = new HumanPlayerGUI(boneyard);
        ComputerPlayerGUI computerPlayer =
                new ComputerPlayerGUI(boneyard);

        PlayAreaGUI playArea = new PlayAreaGUI(boneyard,
                humanPlayer, computerPlayer);

        //FIXME: Hand object
        HandGUI hand = new HandGUI(boneyard);
        hand.setDominoImageList();

        HBox humanTray = new HBox();
        humanTray.getChildren().addAll(hand.dominoImageList);
        humanTray.setAlignment(Pos.CENTER);

        Pane root = new Pane();
        root.getChildren().add(humanTray);

        primaryStage.setTitle("Test");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }
}
