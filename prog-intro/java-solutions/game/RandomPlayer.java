package game;
import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer() {
        this(new Random());
    }

    @Override
    public Move move(SavePosition position) {
        while (true) {
            int r = random.nextInt(3);
            int c = random.nextInt(3);
            final Move move = new Move(r, c, position);
            return move;

        }
    }
}
