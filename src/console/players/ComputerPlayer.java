package console.players;

import console.gamePieces.Boneyard;

public class ComputerPlayer extends Player {
    private HumanPlayer humanPlayer;
    public ComputerPlayer(Boneyard boneyard, HumanPlayer humanPlayer) {
        super(boneyard);
        this.humanPlayer = humanPlayer;
    }

    @Override
    public void conductTurn() {

    }

    private boolean findDominoInHand() {
        int[] numsToMatch = new int[2];
        if (humanPlayer.playAreaDominos.size() == 1) {
            numsToMatch[0] =
        }
    }

    @Override
    public String toString() {
        return "Computer" + super.toString();
    }
}
