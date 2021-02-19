package console.gamePieces;

import console.Main;
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

    public void startGame() {
        printInitialMessage();
        humanPlayer.setOtherPlayer(computerPlayer);
        computerPlayer.setOtherPlayer(humanPlayer);
        printPlayArea(humanPlayer);

        Player currentPlayer = null;
        while (true) {
            for (Player player : playerList) {
                humanPlayer.printTray();

                if (Main.DEBUG) {
                    //FIXME
                    computerPlayer.printTray();
                }

                currentPlayer = player;
                System.out.println(currentPlayer);
                player.conductTurn();
                printPlayArea(currentPlayer);

                if (currentPlayer.isPlayAreaEmpty()
                        || !(humanPlayer.isTakeTurn()
                        || computerPlayer.isTakeTurn())) {
                    break;
                }
            }

            assert currentPlayer != null;
            if (currentPlayer.isPlayAreaEmpty() || !(humanPlayer.isTakeTurn()
                    || computerPlayer.isTakeTurn())) {
                break;
            }
        }

        initiateEndGame(currentPlayer);
    }

    private void initiateEndGame(Player lastPlayer) {
        int humanCount = humanPlayer.getCountDomino();
        int computerCount = computerPlayer.getCountDomino();

        if (Main.DEBUG) {
            //FIXME
            System.out.println("Human count: " + humanCount);
            System.out.println("Computer count: " + computerCount);
        }

        if (humanCount == computerCount) {
            System.out.println("Since both players have the same count, the "
                    + lastPlayer.getName() + " won the game!");
        } else if (humanCount < computerCount) {
            System.out.println("The " + humanPlayer.getName() + " won the " +
                    "game!");
        } else {
            System.out.println("The " + computerPlayer.getName() + " won the " +
                    "game!");
        }
    }

    private void printPlayArea(Player currentPlayer) {
        System.out.println("Boneyard contains " + boneyard.getNumDominos() +
                " dominos");

        Player otherPlayer = getOtherPlayer(currentPlayer);

        if (!otherPlayer.isPlayAreaEmpty()) {
            if (currentPlayer.isShift()) {
                currentPlayer.addShift();
                otherPlayer.removeShift();
            } else {
                otherPlayer.addShift();
                currentPlayer.removeShift();
            }
        }

        if (Main.DEBUG) {
            //FIXME
            System.out.println("Human shift: " + humanPlayer.isShift());
            System.out.println("Computer shift: " + computerPlayer.isShift());
        }

        System.out.println("Play area dominos:");
        for (Player player : playerList) {
            player.printPlayAreaDominos();
        }
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
