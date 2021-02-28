package basePieces;

import console.gamePieces.Domino;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Base for the Boneyard
 *
 * @param <DominoType>
 */
public class BoneyardBase<DominoType extends Domino> {
    protected List<DominoType> dominoBoneyard;
    protected static ThreadLocalRandom random;
    protected final int DOMINO_MAX = 6;
    protected final int NUM_DOMINO_IN_HAND = 7;

    public BoneyardBase() {
        random = ThreadLocalRandom.current();
    }

    /**
     * Generates an initial hand up to the specified number each player
     * should get (gives random dominos)
     *
     * @return list of random dominos for the initial hand
     */
    public List<DominoType> giveInitialHand() {
        List<DominoType> initialHand = new ArrayList<>();

        int dominoIndex;
        for (int i = 0; i < NUM_DOMINO_IN_HAND; i++) {
            dominoIndex = random.nextInt(dominoBoneyard.size());
            initialHand.add(dominoBoneyard.remove(dominoIndex));
        }

        return initialHand;
    }

    /**
     * Removes a random domino, if there's still a domino in the boneyard;
     * else, returns null
     *
     * @return (above)
     */
    public DominoType removeRandomDomino() {
        if (dominoBoneyard.isEmpty()) {
            return null;
        }

        int dominoIndex = random.nextInt(dominoBoneyard.size());
        return dominoBoneyard.remove(dominoIndex);
    }

    public int getNumDominos() {
        return dominoBoneyard.size();
    }
}
