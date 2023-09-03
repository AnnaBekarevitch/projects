package game;
import java.util.Arrays;

public class TicTacToeBoard implements Board, Position {
    private final Cell[][] cells;
    private boolean[][] valid;
    private final int sizeN, sizeM, sizeK, people;
    private Cell turn;
    private int nowTurn;
    private Cell[] turns = {Cell.X, Cell.O, Cell.T, Cell.F};
    private int totalMoves;

    public TicTacToeBoard(int n, int m, int k, int p) {
        sizeN = n;
        sizeM = m;
        sizeK = k;
        people = p;
        this.cells = new Cell[sizeN][sizeM];
        this.valid = new boolean[sizeN][sizeM];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        for (boolean[] row : valid) {
            Arrays.fill(row, true);
        }
        turn = Cell.X;
        nowTurn = 0;
    }

    public void blockPosition(int row, int column) {
        valid[row][column] = false;
        cells[row][column] = Cell.B;
    }

    @Override
    public SavePosition getSavePosition() {
        return new SavePosition(cells, turn);
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    private boolean makeChain(int row, int col)  {
        return col >= 0 && col < sizeM && row >= 0 && row < sizeN && cells[row][col] == turn;
    }
    private int maxSizeChain(final Move move) {
        final int[] goRow = {1, -1, 0,  0, -1, 1, -1,  1};
        final int[] goCol = {0,  0, 1, -1, -1, 1,  1, -1};
        int row = move.getRow();
        int col = move.getColumn();
        int maxChain = 1;
        for (int i = 0; i < 7; i += 2) {
            int lenght = 0;
            for (int j = 0; j < 2; j++) {
                int steps = 0;
                while (makeChain(row + steps * goRow[i + j], col + steps * goCol[i + j])) {
                    steps++;
                }
                lenght += steps;
            }
            maxChain = Math.max(maxChain, lenght - 1);
        }
        return maxChain;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        totalMoves++;
        cells[move.getRow()][move.getColumn()] = move.getValue();
        int maxChain = maxSizeChain(move);
        if (maxChain >= sizeK) {
            return Result.WIN;
        }
        if (totalMoves == sizeN * sizeM) {
            return Result.DRAW;
        }
        nowTurn = (nowTurn + 1) % people;
        turn =  turns[nowTurn];
        return Result.UNKNOWN;
    }

    public Cell[][] getCells() {
        return cells;
    }

    @Override
    public boolean isValid(final Move move) {
        int row = move.getRow();
        int col = move.getColumn();
        return 0 <= row && row < sizeN
                && 0 <= col && col < sizeM
                && cells[row][col] == Cell.E
                && turn == getCell()
                && valid[row][col];
    }
    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }
}
