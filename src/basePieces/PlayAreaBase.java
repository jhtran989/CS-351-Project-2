package basePieces;

import console.gamePieces.Domino;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayAreaBase<DominoType extends Domino> {
    protected final BoneyardBase<DominoType> boneyard;
    protected final Player<DominoType> humanPlayer;
    protected final Player<DominoType> computerPlayer;
    protected List<Player<DominoType>> playerList;

    public PlayAreaBase(BoneyardBase<DominoType> boneyard,
                    Player<DominoType> humanPlayer,
                    Player<DominoType> computerPlayer) {
        this.boneyard = boneyard;
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
        playerList = new ArrayList<>();

        playerList.add(humanPlayer); // the human player will always go first
        playerList.add(computerPlayer);
    }

    protected abstract Player<DominoType> getOtherPlayer(
            Player<DominoType> player);
}
