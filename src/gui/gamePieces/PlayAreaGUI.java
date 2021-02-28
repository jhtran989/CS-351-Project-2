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
    private String endGameMessage;

    public PlayAreaGUI(BoneyardBase<DominoGUI> boneyard,
                       Player<DominoGUI> humanPlayer,
                       Player<DominoGUI> computerPlayer) {
        super(boneyard, humanPlayer,
                computerPlayer);
    }

    public String getEndGameMessage() {
        return endGameMessage;
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

    public boolean transitionPhase() {
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

        if (currentPlayer.isPlayAreaEmpty()
                || !(humanPlayer.isTakeTurn()
                || computerPlayer.isTakeTurn())) {
            initiateEndGame(currentPlayer);
            return false;
        }

        currentPlayer = getOtherPlayer(currentPlayer);
        otherPlayer = getOtherPlayer(currentPlayer);

        if (MainGUI.DEBUG) {
            System.out.println("Current player: " + currentPlayer);
            System.out.println("Other player: " + otherPlayer);
            humanPlayer.printTray();
            computerPlayer.printTray();
        }

        return true;
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

        if (MainConsole.DEBUG) {
            //FIXME
            System.out.println("Human count: " + humanCount);
            System.out.println("Computer count: " + computerCount);
        }

        endGameMessage = "Human count: " + humanCount + "\n" + "Computer " +
                "count: " + computerCount + "\n";
        String winnerMessage;
        if (humanCount == computerCount) {
            winnerMessage = "Since both players have the same count, the "
                    + lastPlayer.getName() + " won the game!";
            endGameMessage += winnerMessage;
            System.out.println(winnerMessage);
        } else if (humanCount < computerCount) {
            winnerMessage = "The " + humanPlayer.getName() + " player won " +
                    "the game!";
            endGameMessage += winnerMessage;
            System.out.println(winnerMessage);
        } else {
            winnerMessage = "The " + computerPlayer.getName() + " player " +
                    "won the game!";
            endGameMessage += winnerMessage;
            System.out.println(winnerMessage);
        }
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
