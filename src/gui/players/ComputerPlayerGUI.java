package gui.players;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import console.players.ComputerPlayer;

public class ComputerPlayerGUI<DominoType extends Domino>
        extends ComputerPlayer<DominoType> {
    public ComputerPlayerGUI(Boneyard<DominoType> boneyard) {
        super(boneyard);
    }
}
