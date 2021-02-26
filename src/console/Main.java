package console;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import console.gamePieces.PlayArea;
import console.players.ComputerPlayer;
import console.players.HumanPlayer;

public class Main {
    //TODO: sort the protected stuff...
    public static final boolean DEBUG = true;

    public static void main(String[] args) {
        Boneyard<Domino> boneyard = new Boneyard<>();
        HumanPlayer<Domino> humanPlayer = new HumanPlayer<>(boneyard);
        ComputerPlayer<Domino> computerPlayer =
                new ComputerPlayer<>(boneyard);

        PlayArea<Domino> playArea = new PlayArea<>(boneyard,
                humanPlayer, computerPlayer);

        playArea.startGame();
    }
}
