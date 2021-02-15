package console.gamePieces;

import console.gamePieces.Boneyard;
import console.gamePieces.Domino;
import exceptions.DominoOutOfBoundsException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Hand {
    private Boneyard boneyard;
    private List<Domino> hand;
    private static ThreadLocalRandom random;

    public Hand(Boneyard boneyard) {
        this.boneyard = boneyard;
        hand = boneyard.giveInitialHand();
        random = ThreadLocalRandom.current();
    }

    public void drawDomino() {
        hand.add(boneyard.takeDomino());
    }

    public Domino playDomino(int index) {
        return hand.remove(index);
    }

    public void checkDominoBounds(int index) throws DominoOutOfBoundsException {
        if (index < 0 || index >= hand.size()) {
            throw new DominoOutOfBoundsException();
        }
    }

    public int getNumDominos() {
        return hand.size();
    }

    @Override
    public String toString() {
        String tray = "Tray: [";
        boolean start = true;
        for (Domino domino : hand) {
            if (!start) {
                tray += ", ";
            } else {
                start = false;
            }
            tray += domino;
        }
        tray += "]";
        return tray;
    }
}
