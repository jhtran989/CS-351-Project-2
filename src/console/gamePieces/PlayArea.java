package console.gamePieces;

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
        while (true) {
            for (Player player : playerList) {
                if (!boneyard.isEmpty()) {

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
            if (player.isShift()) {
                System.out.print("   ");
            }

            player.printPlayAreaDominos();
        }
    }

    private void printInitialMessage() {
        System.out.println("Dominos!");
        System.out.println("Computer has " + computerPlayer.getNumDominos());
    }
}
