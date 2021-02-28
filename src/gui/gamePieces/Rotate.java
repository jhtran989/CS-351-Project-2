package gui.gamePieces;

public enum Rotate {
    ROTATE_ON(true),
    ROTATE_OFF(false);
    private boolean rotate;

    Rotate(boolean rotate) {
        this.rotate = rotate;
    }

    @Override
    public String toString() {
        return rotate ? "Yes" : "No";
    }
}
