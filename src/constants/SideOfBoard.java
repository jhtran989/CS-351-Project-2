package constants;

/**
 * Enum for the sides of the board that the domino can be played (left and
 * right)
 */
public enum SideOfBoard {
    LEFT(0),
    RIGHT(1);

    private final int encodingIndex;

    SideOfBoard(int encodingIndex) {
        this.encodingIndex = encodingIndex;
    }

    @Override
    public String toString() {
        if (encodingIndex == 0) {
            return "Left - 0";
        } else {
            return "Right - 1";
        }
    }
}
