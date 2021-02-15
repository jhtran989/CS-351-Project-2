package console.players;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import console.gamePieces.Hand;

import java.util.List;

public abstract class Player {
    protected Hand hand;
    protected List<Domino> playedDominos;

    public Player(Boneyard boneyard) {
        hand = new Hand(boneyard);
    }

    protected abstract void conductTurn();
}
