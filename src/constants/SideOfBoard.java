package constants;

public enum SideOfBoard {
    LEFT(0),
    RIGHT(1);

    private int encodingIndex;

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
