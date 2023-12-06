import com.sun.jdi.StringReference;

public class State {
    // Instance variables?
    private char player;
    private String board;
    private String move;
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
