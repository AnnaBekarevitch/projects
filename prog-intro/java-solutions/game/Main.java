package game;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int result = 0;
        do {
            while (true) {
                System.out.println("Please enter 4 numbers separated by a space without punctuation marks:");
                System.out.println("people n m k  number");
                System.out.println("if you want to exit enter");
                System.out.println("exit");
                Scanner in = new Scanner(System.in);
                String p = in.next();
                if (p.equals("exit")) {
                    return;
                }
                String n = in.next();
                String m = in.next();
                String k = in.next();
                try {
                    int people = Integer.parseInt(p);
                    int sizeN = Integer.parseInt(n);
                    int sizeM = Integer.parseInt(m);
                    int sizeK = Integer.parseInt(k);
                    final HumanPlayer[] newPlayers = new HumanPlayer[people];
                    for (int i = 0; i < people; i++) {
                        newPlayers[i] = new HumanPlayer();
                    }
                    final Game game = new Game(false, newPlayers);
                    TicTacToeBoard bord = new TicTacToeBoard(sizeN, sizeM,
                            sizeK, people);
                    if (sizeN == 11 && sizeM == 11) {
                        for (int i = 0; i < sizeN; i++) {
                            bord.blockPosition(i, i);
                            bord.blockPosition(i, sizeM - i - 1);
                        }
                    }
                    result = game.play(bord);
                    System.out.println("Game result: player number " + result + " is winning!");
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect input format.");
                    System.out.println("Example: 2 3 3 3");
                }
            }
        } while (result != 0);
    }
}
