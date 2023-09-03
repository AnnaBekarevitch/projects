package game;

public interface Board {
    SavePosition getSavePosition();
    Cell getCell();
    Result makeMove(Move move);
}
