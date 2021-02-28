package gui.gamePieces;

/**
 * Enum to determine if the domino should be rotated (on or off)
 */
public enum Rotate {
    ROTATE_ON(true),
    ROTATE_OFF(false);
    private final boolean rotate;

    Rotate(boolean rotate) {
        this.rotate = rotate;
    }

    @Override
    public String toString() {
        return rotate ? "Yes" : "No";
    }
}
