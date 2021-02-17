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

    public Domino drawDomino() {
        Domino domino = boneyard.takeDomino();
        hand.add(domino);

        return domino;
    }

    public Domino playDomino(int index) {
        return hand.remove(index);
    }

    public void addHalfBlank() {
        if (!hand.contains(Domino.HALF_BLANK)) {
            hand.add(0, Domino.HALF_BLANK);
        }
    }

    public void removeHalfBlank() {
        hand.remove(Domino.HALF_BLANK);
    }

    public void checkDominoBounds(int index) throws DominoOutOfBoundsException {
        if (index < 0 || index >= hand.size()) {
            throw new DominoOutOfBoundsException();
        }
    }

    public int getNumDominos() {
        if (hand.contains(Domino.HALF_BLANK)) {
            return hand.size() - 1;
        }

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
