package game;

public class Game {
    private final boolean log;
    private final Player[] players;

    public Game(final boolean log, final Player ... player) {
        this.log = log;
        players = new Player[player.length];
        for (int i = 0; i < player.length; i++) {
            this.players[i] = player[i];
        }
    }

    public int play(Board board) {
        while (true) {
            for (int i = 0; i < players.length; i++) {
                final int result = move(board, players[i], i + 1, (i + 1) % players.length + 1);
                if (result != -1) {
                    return result;
                }
            }
        }
    }

    private int move(final Board board, final Player player, final int no, final int next) {
        final Move move = player.move(board.getSavePosition());
        final Result result = board.makeMove(move);
        log("Player " + no + " move: " + move);
        log("Position:\n" + board);
        if (result == Result.WIN) {
            log("Player " + no + " won");
            return no;
        } else if (result == Result.LOSE) {
            log("Player " + no + " lose");
            return next;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else {
            return -1;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
