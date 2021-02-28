package gui;

import constants.SideOfBoard;
import gui.gamePieces.*;
import gui.players.ComputerPlayerGUI;
import gui.players.HumanPlayerGUI;
import gui.players.PlayerGUI;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class MainGUI extends Application {
    public static boolean DEBUG = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BoneyardGUI boneyard = new BoneyardGUI();
        HumanPlayerGUI humanPlayer = new HumanPlayerGUI(boneyard);
        ComputerPlayerGUI computerPlayer =
                new ComputerPlayerGUI(boneyard);

        PlayAreaGUI playArea = new PlayAreaGUI(boneyard,
                humanPlayer, computerPlayer);

//        //FIXME: Hand object
//        HandGUI hand = new HandGUI(boneyard);
//        hand.setDominoImageList();
//
//        HBox humanTray = new HBox();
//        humanTray.getChildren().addAll(hand.getHandImageList());
//        humanTray.setAlignment(Pos.CENTER);

        // Display text
        Label boneyardLabel =
                new Label(boneyard.toString());
        Label humanPlayerNumDominosLabel =
                new Label(humanPlayer.getDisplayName());
        Label computerPlayerNumDominosLabel =
                new Label(computerPlayer.getDisplayName());
        VBox displayTextVB = new VBox();
        displayTextVB.getChildren().addAll(boneyardLabel,
                humanPlayerNumDominosLabel, computerPlayerNumDominosLabel);
        displayTextVB.setAlignment(Pos.TOP_CENTER);

        // Play area - Human Player
        HBox humanPlayAreaHB = new HBox();
        humanPlayAreaHB.getChildren().addAll(
                humanPlayer.getPlayAreaImageList());
        humanPlayAreaHB.setAlignment(Pos.CENTER_LEFT);

        // Play area - Computer Player
        HBox computerPlayAreaHB = new HBox();
        computerPlayAreaHB.getChildren().addAll(
                computerPlayer.getPlayAreaImageList());
        computerPlayAreaHB.setAlignment(Pos.CENTER_LEFT);

        // Combined Play area
        Label combinedPlayAreaLabel = new Label("Play Area:");
        VBox combinedPlayAreaVB = new VBox();
        combinedPlayAreaVB.getChildren().addAll(combinedPlayAreaLabel,
                humanPlayAreaHB,
                computerPlayAreaHB);
        combinedPlayAreaVB.setAlignment(Pos.TOP_CENTER);

        // Play area scroll pane
        ScrollPane playAreaScrollPane = new ScrollPane();
        playAreaScrollPane.setContent(combinedPlayAreaVB);
        playAreaScrollPane.setFitToWidth(true);
        playAreaScrollPane.setFitToHeight(true);

        // Hand of Human player
        Label humanHandLabel = new Label("Human Hand:");
        HBox humanHandHB = new HBox();
        humanHandHB.getChildren().addAll(humanPlayer.getHandImageList());
        humanHandHB.setAlignment(Pos.CENTER);

        VBox humanHandVB = new VBox();
        humanHandVB.getChildren().addAll(humanHandLabel,
                humanHandHB);
        humanHandVB.setAlignment(Pos.BOTTOM_CENTER);

        // Human hand scroll pane
        ScrollPane humanHandScrollPane = new ScrollPane();
        humanHandScrollPane.setContent(humanHandVB);
        humanHandScrollPane.setFitToWidth(true);
        humanHandScrollPane.setFitToHeight(true);

        // Side of board to play
        Label sideOfBoardLabel = new Label("Which side of board to play?");
        ComboBox<SideOfBoard> sideOfBoardComboBox =
                new ComboBox<>();
        sideOfBoardComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(SideOfBoard sideOfBoard) {
                if (sideOfBoard == null) {
                    return null;
                }

                return sideOfBoard.toString();
            }

            @Override
            public SideOfBoard fromString(String string) {
                return null;
            }
        });
        sideOfBoardComboBox.getItems().addAll(SideOfBoard.values());
        sideOfBoardComboBox.setValue(SideOfBoard.LEFT);
        HBox sideOfBoardHB = new HBox(30);
        sideOfBoardHB.getChildren().addAll(sideOfBoardLabel,
                sideOfBoardComboBox);

        // Rotate the domino first before playing
        Label rotateLabel = new Label("Rotate the domino before playing?");
        ComboBox<Rotate> rotateComboBox = new ComboBox<>();
        rotateComboBox.setConverter(new StringConverter<Rotate>() {
            @Override
            public String toString(Rotate rotate) {
                if (rotate == null) {
                    return null;
                }

                return rotate.toString();
            }

            @Override
            public Rotate fromString(String string) {
                return null;
            }
        });
        rotateComboBox.getItems().addAll(Rotate.values());
        rotateComboBox.setValue(Rotate.ROTATE_OFF);
        HBox rotateHB = new HBox(30);
        rotateHB.getChildren().addAll(rotateLabel, rotateComboBox);

        // Output message
        String gameUpdateInitialMessage = "Game Message:";
        String gameUpdateOutputMessage = "";
        CustomLabel gameUpdateLabel =
                new CustomLabel(gameUpdateInitialMessage,
                        gameUpdateOutputMessage);
        gameUpdateLabel.setWrapText(true);

        // Current selected domino - Human player
        String dominoSelectedInitialMessage = "Current selected " +
                "domino:";
        String dominoSelectedOutputMessage = "none selected";
        CustomLabel dominoSelectedLabel =
                new CustomLabel(dominoSelectedInitialMessage,
                        dominoSelectedOutputMessage);
        dominoSelectedLabel.setWrapText(true);

        // Buttons for the Human player
        Button playButton = new Button("Play domino");
        Button drawButton = new Button("Draw from boneyard");
        Button passButton = new Button("Pass turn");
        VBox buttonOptionsVB = new VBox(10);
        buttonOptionsVB.getChildren().addAll(playButton, drawButton,
                passButton);
        buttonOptionsVB.setAlignment(Pos.TOP_CENTER);

        // Human player options (without buttons)
        VBox entireOptionsVB = new VBox(20);
        entireOptionsVB.getChildren().addAll(gameUpdateLabel,
                dominoSelectedLabel, sideOfBoardHB,
                rotateHB);
        entireOptionsVB.setAlignment(Pos.TOP_CENTER);

//        // Holds everything
//        VBox controlsVB = new VBox(30);
//        controlsVB.getChildren().addAll(displayTextVB, combinedPlayAreaVB,
//                humanHandHB, entireOptionsVB);
//        controlsVB.setAlignment(Pos.CENTER);


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(displayTextVB);
        BorderPane.setAlignment(displayTextVB, Pos.TOP_CENTER);
        borderPane.setBottom(humanHandScrollPane);
        BorderPane.setAlignment(humanHandScrollPane, Pos.BOTTOM_CENTER);
        borderPane.setLeft(entireOptionsVB);
        BorderPane.setAlignment(entireOptionsVB, Pos.CENTER_LEFT);
        borderPane.setRight(buttonOptionsVB);
        BorderPane.setAlignment(buttonOptionsVB, Pos.CENTER_RIGHT);
        borderPane.setCenter(playAreaScrollPane);
        BorderPane.setAlignment(playAreaScrollPane, Pos.CENTER);

        primaryStage.setTitle("Dominos");
        primaryStage.setScene(new Scene(borderPane, 1200, 400));
        primaryStage.show();

        if (MainGUI.DEBUG) {
            System.out.println("Before resizing");
            System.out.println("Left side: " + entireOptionsVB.getWidth());
            System.out.println("Right side: " + buttonOptionsVB.getWidth());
        }

        // So the left and right sides of the border pane below have the same
        // width
        entireOptionsVB.setPrefWidth(entireOptionsVB.getWidth());
        buttonOptionsVB.setPrefWidth(entireOptionsVB.getWidth());

        // All of the GUI stuff will be stored in the guiStuff object and
        // keeps everything in one place
        GuiStuff guiStuff = new GuiStuff(gameUpdateLabel,
                dominoSelectedLabel,
                sideOfBoardComboBox,
                rotateComboBox);

        humanPlayer.setGuiStuff(guiStuff);
        humanPlayer.setHumanHandHB(humanHandHB);
        computerPlayer.setGuiStuff(guiStuff);
        playArea.setPlayArea(humanPlayAreaHB,
                computerPlayAreaHB);

        playArea.startGame();

        playButton.setOnAction(event -> {
            humanPlayer.conductTurn();
            if (humanPlayer.playDomino()) {
                playArea.transitionPhase();
                computerPlayer.conductTurn();
                if (computerPlayer.isTakeTurn()) {
                    playArea.transitionPhase();
                }
            }
        });

        drawButton.setOnAction(event -> {
            humanPlayer.conductTurn();
            humanPlayer.drawDomino();
        });

        passButton.setOnAction(event -> {
            // Resets the conditions canPlayDomino and canDrawDomino for the
            // human player (no other place to reset them since the
            // button event is handled in main...)
            humanPlayer.setCanDrawDomino(true);
            humanPlayer.setCanPlayDomino(true);

            playArea.transitionPhase();
            computerPlayer.conductTurn();
            if (computerPlayer.isTakeTurn()) {
                playArea.transitionPhase();
            }
        });
    }
}
