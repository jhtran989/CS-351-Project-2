package gui.gamePieces;

import basePieces.BoneyardBase;
import basePieces.PlayAreaBase;
import basePieces.Player;
import console.MainConsole;
import console.gamePieces.Domino;
import gui.MainGUI;
import gui.players.HumanPlayerGUI;
import gui.players.PlayerGUI;
import javafx.scene.layout.HBox;

public class PlayAreaGUI extends PlayAreaBase<DominoGUI> {
    private Player<DominoGUI> currentPlayer;
    private Player<DominoGUI> otherPlayer;
    private HBox humanPlayAreaHB;
    private HBox computerPlayAreaHB;

    public PlayAreaGUI(BoneyardBase<DominoGUI> boneyard,
                       Player<DominoGUI> humanPlayer,
                       Player<DominoGUI> computerPlayer) {
        super(boneyard, humanPlayer,
                computerPlayer);
    }

    public void startGame() {
        currentPlayer = humanPlayer;
        otherPlayer = computerPlayer;
        humanPlayer.setOtherPlayer(computerPlayer);
        computerPlayer.setOtherPlayer(humanPlayer);

        if (MainGUI.DEBUG) {
            //FIXME
            System.out.println("Human shift: " + humanPlayer.isShift());
            System.out.println("Computer shift: " + computerPlayer.isShift());
        }
    }

    public void transitionPhase() {
        if (MainGUI.DEBUG) {
            System.out.println("Current player: " + currentPlayer);
            System.out.println("Other player: " + otherPlayer);
            humanPlayer.printTray();
            computerPlayer.printTray();
        }

        if (!(currentPlayer.isPlayAreaEmpty() &&
                otherPlayer.isPlayAreaEmpty())) {
            if (currentPlayer.isShift()) {
                currentPlayer.addShift();
                otherPlayer.removeShift();
            } else {
                otherPlayer.addShift();
                currentPlayer.removeShift();
            }
        }

        if (MainGUI.DEBUG) {
            //FIXME
            System.out.println("Human shift: " + humanPlayer.isShift());
            System.out.println("Computer shift: " + computerPlayer.isShift());
        }

        updatePlayArea();
        printPlayArea();

        currentPlayer = getOtherPlayer(currentPlayer);
        otherPlayer = getOtherPlayer(currentPlayer);

        if (MainGUI.DEBUG) {
            System.out.println("Current player: " + currentPlayer);
            System.out.println("Other player: " + otherPlayer);
            humanPlayer.printTray();
            computerPlayer.printTray();
        }

        if (currentPlayer.isPlayAreaEmpty()
                || !(humanPlayer.isTakeTurn()
                || computerPlayer.isTakeTurn())) {
            initiateEndGame(currentPlayer);
        }
    }

    protected void updatePlayArea() {
        humanPlayAreaHB.getChildren().clear();
        humanPlayAreaHB.getChildren().addAll(
                ((PlayerGUI) humanPlayer).getPlayAreaImageList());
        computerPlayAreaHB.getChildren().clear();
        computerPlayAreaHB.getChildren().addAll(
                ((PlayerGUI) computerPlayer).getPlayAreaImageList());
    }

    public void setPlayArea(HBox humanPlayAreaHB, HBox computerPlayAreaHB) {
        this.humanPlayAreaHB = humanPlayAreaHB;
        this.computerPlayAreaHB = computerPlayAreaHB;
    }

    protected void initiateEndGame(Player<DominoGUI> lastPlayer) {
        int humanCount = humanPlayer.getPlayAreaCountDomino();
        int computerCount = computerPlayer.getPlayAreaCountDomino();


    }

    private void printPlayArea() {
        System.out.println("Boneyard contains " + boneyard.getNumDominos() +
                " dominos");

        System.out.println("Play area dominos:");
        if (MainGUI.DEBUG) {
            if (humanPlayer.isShift()) {
                System.out.printf("   ");
            }
        }
        humanPlayer.printPlayAreaDominos();
        if (MainGUI.DEBUG) {
            if (computerPlayer.isShift()) {
                System.out.printf("   ");
            }
        }
        computerPlayer.printPlayAreaDominos();
    }

    @Override
    protected Player<DominoGUI> getOtherPlayer(Player<DominoGUI> player) {
        if (player instanceof HumanPlayerGUI) {
            return computerPlayer;
        } else {
            return humanPlayer;
        }
    }
}
