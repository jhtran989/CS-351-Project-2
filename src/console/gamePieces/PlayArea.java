package console.gamePieces;

import basePieces.PlayAreaBase;
import console.MainConsole;
import console.players.HumanPlayer;
import basePieces.Player;
import console.players.PlayerConsole;

public class PlayArea extends PlayAreaBase<Domino> {
    public PlayArea(Boneyard boneyard, PlayerConsole humanPlayer,
                    PlayerConsole computerPlayer) {
        super(boneyard, humanPlayer, computerPlayer);
    }

    public void startGame() {
        printInitialMessage();
        humanPlayer.setOtherPlayer(computerPlayer);
        computerPlayer.setOtherPlayer(humanPlayer);
        printPlayArea(humanPlayer);

        Player<Domino> currentPlayer = null;
        while (true) {
            for (Player<Domino> player : playerList) {
                humanPlayer.printTray();

                if (MainConsole.DEBUG) {
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

    protected void initiateEndGame(Player<Domino> lastPlayer) {
        int humanCount = humanPlayer.getPlayAreaCountDomino();
        int computerCount = computerPlayer.getPlayAreaCountDomino();

        if (MainConsole.DEBUG) {
            //FIXME
            System.out.println("Human count: " + humanCount);
            System.out.println("Computer count: " + computerCount);
        }

        if (humanCount == computerCount) {
            System.out.println("Since both players have the same count, the "
                    + lastPlayer.getName() + " won the game!");
        } else if (humanCount < computerCount) {
            System.out.println("The " + humanPlayer.getName() + " player won " +
                    "the game!");
        } else {
            System.out.println("The " + computerPlayer.getName() + " player " +
                    "won the game!");
        }
    }

    private void printPlayArea(Player<Domino> currentPlayer) {
        System.out.println("Boneyard contains " + boneyard.getNumDominos() +
                " dominos");

        Player<Domino> otherPlayer = getOtherPlayer(currentPlayer);

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

//        if (!otherPlayer.isPlayAreaEmpty()) {
//            if (currentPlayer.isShift()) {
//                currentPlayer.addShift();
//                otherPlayer.removeShift();
//            } else {
//                otherPlayer.addShift();
//                currentPlayer.removeShift();
//            }
//        }

        if (MainConsole.DEBUG) {
            //FIXME
            System.out.println("Human shift: " + humanPlayer.isShift());
            System.out.println("Computer shift: " + computerPlayer.isShift());
        }

        System.out.println("Play area dominos:");
        for (Player<Domino> player : playerList) {
            player.printPlayAreaDominos();
        }
    }

    private void printInitialMessage() {
        System.out.println("Dominos!");
        System.out.println("Computer has " + computerPlayer.getNumDominos());
    }

    @Override
    protected Player<Domino> getOtherPlayer(Player<Domino> player) {
        if (player instanceof HumanPlayer) {
            return computerPlayer;
        } else {
            return humanPlayer;
        }
    }
}
