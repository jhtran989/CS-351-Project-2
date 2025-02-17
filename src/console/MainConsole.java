package console;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import console.gamePieces.PlayArea;
import console.players.ComputerPlayer;
import console.players.HumanPlayer;

/**
 * The Main of the Console version. Creates the main board pieces and calls
 * the main game method from the play area.
 */
public class MainConsole {
    //TODO: sort the protected stuff...
    public static final boolean DEBUG = true;

    public static void main(String[] args) {
        Boneyard boneyard = new Boneyard();
        HumanPlayer humanPlayer = new HumanPlayer(boneyard);
        ComputerPlayer computerPlayer =
                new ComputerPlayer(boneyard);

        PlayArea playArea = new PlayArea (boneyard,
                humanPlayer, computerPlayer);

        playArea.startGame();
    }
}
