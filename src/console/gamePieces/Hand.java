package console.gamePieces;

import basePieces.HandBase;

public class Hand extends HandBase<Domino> {
    public Hand(Boneyard boneyard) {
        super(boneyard);
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
