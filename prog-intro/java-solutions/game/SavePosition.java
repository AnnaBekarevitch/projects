package game;
import java.util.Map;

public class SavePosition implements Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.T, '-',
            Cell.F, '|',
            Cell.B, 'x'
    );
    private final Cell[][] cells;
    private int sizeN;
    private int sizeM;
    private Cell cell;

    public SavePosition(Cell[][] board, Cell turn) {
        cells = board;
        sizeN = board.length;
        sizeM = board[0].length;
        cell = turn;
    }

    public boolean isValid(Move move) {
        int row = move.getRow();
        int col = move.getColumn();
        return 0 <= row && row < sizeN
                && 0 <= col && col < sizeM
                && cells[row][row] == Cell.E
                && move.getValue() == getCell(row, col);
    }



    public Cell getCell(int r, int c) {
        return cells[r][c];
    }
    public Cell getTurn() {
        return cell;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int i = 1; i <= sizeM; i++) sb.append(Integer.toString(i));
        for (int r = 0; r < sizeN; r++) {
            sb.append("\n");
            sb.append(r + 1);
            for (int c = 0; c < sizeM; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}
