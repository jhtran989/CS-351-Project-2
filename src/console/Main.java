package console;

import console.gamePieces.Boneyard;
import console.gamePieces.PlayArea;
import console.players.ComputerPlayer;
import console.players.HumanPlayer;
import console.players.Player;

public class Main {
    public static void main(String[] args) {
        Boneyard boneyard = new Boneyard();
        HumanPlayer humanPlayer = new HumanPlayer(boneyard);
        ComputerPlayer computerPlayer = new ComputerPlayer(boneyard);

        PlayArea playArea = new PlayArea(boneyard,
                humanPlayer, computerPlayer);

        playArea.startGame();
    }
}
