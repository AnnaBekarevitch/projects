package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    public static class State {
        private Phase phase = Phase.RUNNING;
        public final int size = 3;
        private Condition[][] cells = new Condition[size][size];
        private Condition turn = Condition.X;

        public State() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cells[i][j] = Condition.EMPTY;
                }
            }
        }

        public boolean has_content(int row, int col) {
            return cells[row][col] != Condition.EMPTY;
        }
        public boolean makeMove(int row, int col) {
            if (cells[row][col] != Condition.EMPTY) return false;
            cells[row][col] = turn;
            turn = turn == Condition.X ? Condition.O : Condition.X;
            return true;
        }


        private boolean checkLine(int row) {
            Condition symbol = cells[row][0];
            if (symbol == Condition.EMPTY) return false;
            for (int col = 1; col < size; col++) {
                if (symbol != cells[row][col]) return false;
            }
            return true;
        }
        private boolean checkColumn(int col) {
            Condition symbol = cells[0][col];
            if (symbol == Condition.EMPTY) return false;
            for (int row = 1; row < size; row++) {
                if (symbol != cells[row][col]) return false;
            }
            return true;
        }
        private boolean checkMainDiagonal() {
            Condition symbol = cells[0][0];
            if (symbol == Condition.EMPTY) return false;
            for (int col = 1; col < size; col++) {
                if (symbol != cells[col][col]) return false;
            }
            return true;
        }
        private boolean checkDiagonal() {
            Condition symbol = cells[0][size - 1];
            if (symbol == Condition.EMPTY) return false;
            for (int col = 1; col < size; col++) {
                if (symbol != cells[col][size-col-1]) return false;
            }
            return true;
        }

        private boolean hasNextMove() {
            for (int col = 0; col < size; col++) {
                for (int row = 0; row < size; row++) {
                    if (cells[col][row] == Condition.EMPTY) return true;
                }
            }
            return false;
        }

        public Phase checkPhase() {
            if (checkMainDiagonal()) return cells[0][0] == Condition.X ? Phase.WON_X : Phase.WON_O;
            if (checkDiagonal()) return cells[0][size-1] == Condition.X ? Phase.WON_X : Phase.WON_O;
            for (int i = 0; i < size; i++) {
                if (checkLine(i)) return cells[i][0] == Condition.X ? Phase.WON_X : Phase.WON_O;
                if (checkColumn(i)) return cells[0][i] == Condition.X ? Phase.WON_X : Phase.WON_O;
            }
            return (hasNextMove() ? Phase.RUNNING : Phase.DRAW);
        }

        public Phase getPhase() {
            return phase;
        }

        public int getSize() {
            return size;
        }

        public Condition[][] getCells() {
          return cells;
        }



        public Condition getTurn() {
            return turn;
        }

        public boolean getCrossesMove() { return turn == Condition.X; }
    }

    public enum Phase {
        RUNNING, WON_X, WON_O, DRAW
    }
    public enum Condition {
        EMPTY(" "),
        X("X"),
        O("O");

        private String val;
        Condition(String val){
            this.val = val;
        }

        @Override
        public String toString(){
            return this.val;
        }

        public boolean has_content() {
            return !this.val.equals(" ");
        }


    }


    public TicTacToePage() {

    }

    static public State state = new State();

    private void action(HttpServletRequest request, Map<String, Object> view) {

        if (request.getMethod().equals("POST") && "onMove".equals(request.getParameter("action"))) {
            for (int row = 0; row < state.size; row++) {
                for (int col = 0; col < state.size; col++) {
                    if (request.getParameter("cell_" + Integer.toString(row) + Integer.toString(col)) != null) {
                        state.makeMove(row, col);
                        state.phase = state.checkPhase();
                        //disabled

                    }
                }
            }
        }
        if (request.getMethod().equals("POST") && "newGame".equals(request.getParameter("action"))) {
            state = new State();
        }
        view.put("state", state);

    }



}
