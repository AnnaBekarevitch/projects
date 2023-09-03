package game;

import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(SavePosition position) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(position.getTurn() + "'s move");
            out.println("Enter row and column");
            try {
                String positionR = in.next();
                String positionC = in.next();
                
                final Move move = new Move(Integer.parseInt(positionR) - 1,
                                            Integer.parseInt(positionC) - 1,
                        position);
                return move;
            } catch (NumberFormatException e) {
                out.println("Incorrect input format.");
                out.println("Please enter 2 numbers separated by a space without punctuation marks:");
                out.println("row and column number: x, y");
                out.println("1 <= x <= sizeN, 1 <= x <= sizeM");
                out.println("Example: 1 1");
            } catch (NoSuchElementException e) {
                System.out.println("Incorrect input format.");
            } catch (IllegalStateException e) {
                System.out.println("Incorrect input format.");
            }

        }
    }
}
