package console.gamePieces;

import console.players.HumanPlayer;
import console.players.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayArea {
    private Boneyard boneyard;
    private Player humanPlayer;
    private Player computerPlayer;
    List<Player> playerList;

    public PlayArea(Boneyard boneyard, Player humanPlayer,
                    Player computerPlayer) {
        this.boneyard = boneyard;
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
        playerList = new ArrayList<>();

        playerList.add(humanPlayer); // the human player will always go first
        playerList.add(computerPlayer);
    }

    private void startGame() {
        printInitialMessage();

        while (true) {
            for (Player player : playerList) {
                if (!boneyard.isEmpty()) {
                    printPlayArea();
                    humanPlayer.printTray();
                    player.conductTurn();
                } else {
                    return;
                }
            }
        }
    }

    private void printPlayArea() {
        System.out.println("Boneyard contains " + boneyard.getNumDominos() +
                "dominos");

        for (Player player : playerList) {
            Player currentPlayer = player;
            Player otherPlayer = getOtherPlayer(player);

            if (currentPlayer.isShift()) {
                currentPlayer.addShift();
                otherPlayer.removeShift();
            }

            player.printPlayAreaDominos();
        }

//        humanPlayer.resetShift();
//        computerPlayer.resetShift();
    }

    private Player getOtherPlayer(Player player) {
        if (player instanceof HumanPlayer) {
            return computerPlayer;
        } else {
            return humanPlayer;
        }
    }

    private void printInitialMessage() {
        System.out.println("Dominos!");
        System.out.println("Computer has " + computerPlayer.getNumDominos());
    }
}
