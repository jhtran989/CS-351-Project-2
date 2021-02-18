package console.constants;

public enum SideOfBoard {
    LEFT(0),
    RIGHT(1);
    private int encodingIndex;

    SideOfBoard(int encodingIndex) {
        this.encodingIndex = encodingIndex;
    }
}
