package basePieces;

import console.gamePieces.Domino;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoneyardBase<DominoType extends Domino> {
    protected List<DominoType> dominoBoneyard;
    protected static ThreadLocalRandom random;
    protected final int DOMINO_MAX = 6;

    public BoneyardBase() {
        random = ThreadLocalRandom.current();
    }

    public List<DominoType> giveInitialHand() {
        List<DominoType> initialHand = new ArrayList<>();

        int dominoIndex;
        for (int i = 0; i <= DOMINO_MAX; i++) {
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
