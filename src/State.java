import com.sun.jdi.StringReference;

public class State {
    // well don't use lol
    private final char player;
    private final String board;
    private final String move;
    public State(String stateAsString) {
        this.player = stateAsString.charAt(0);
        this.board = stateAsString.substring(1, 4);
        this.move = stateAsString.substring(4);
    }

    public char getPlayer() {
        return player;
    }

    public String getBoard() {
        return board;
    }

    public String getMove() {
        return move;
    }

    public String toString() {
        return "[" + player + board + ", " + move + "]";
    }
}
