package console;

import console.gamePieces.Boneyard;
import console.players.ComputerPlayer;
import console.players.HumanPlayer;
import console.players.Player;

public class Main {
    private Boneyard boneyard;
    private Player humanPlayer;
    private Player computerPlayer;

    private void startGame() {
        boneyard = new Boneyard();
        humanPlayer = new HumanPlayer(boneyard);
        computerPlayer = new ComputerPlayer(boneyard);


    }
}
