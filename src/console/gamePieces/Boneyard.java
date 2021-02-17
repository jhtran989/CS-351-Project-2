package console.gamePieces;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Boneyard {
    private List<Domino> dominoBoneyard;
    private static ThreadLocalRandom random;

    public Boneyard() {
        dominoBoneyard = Domino.setUpBoneyard();
        random = ThreadLocalRandom.current();
    }

    public List<Domino> giveInitialHand() {
        List<Domino> initialHand = new ArrayList<>();

        int dominoIndex;
        for (int i = 0; i < 7; i++) {
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
    public Domino removeRandomDomino() {
        if (dominoBoneyard.isEmpty()) {
            return null;
        }

        int dominoIndex = random.nextInt(dominoBoneyard.size());
        return dominoBoneyard.remove(dominoIndex);
    }

    public boolean isEmpty() {
        return dominoBoneyard.isEmpty();
    }

    public int getNumDominos() {
        return dominoBoneyard.size();
    }
}
