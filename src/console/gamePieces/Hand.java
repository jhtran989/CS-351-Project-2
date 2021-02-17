package console.gamePieces;

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
        Domino domino = boneyard.removeRandomDomino();

        if (domino != null) {
            hand.add(domino);
        }

        return domino;
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

    /**
     * If the domino is found with the given domino value, the matching value
     * will always be on the LEFT side (for convenience later on for coding
     * the AI for the computer play) for the returned Domino object
     *
     * @param dominoValue
     * @return
     */
    public Domino searchDomino(int dominoValue) {
        for (Domino domino : hand) {
            if (dominoValue == domino.getLeftSide()) {
                return domino;
            } else if (dominoValue == domino.getRightSide()) {
                domino.rotateDomino();
                return domino;
            }
        }

        return null; // returns null if the domino value was not found
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
