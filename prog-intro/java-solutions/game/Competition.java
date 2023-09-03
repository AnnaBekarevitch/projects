package game;

import java.util.Locale;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Competition {
    public static void main(String[] args) {
        int result = 0;
        do {
            while (true) {
                System.out.println("Competition!");
                System.out.println("Please enter 4 numbers separated by a space without punctuation marks:");
                System.out.println("people n m k  number");
                System.out.println("if you want to exit enter");
                System.out.println("exit");
                Scanner in = new Scanner(System.in);
                String p = in.next();
                String n = in.next();
                String m = in.next();
                String k = in.next();
                if (p.equals("exit")) {
                    return;
                }
                try {
                    int people = Integer.parseInt(p);
                    int sizeN = Integer.parseInt(n);
                    int sizeM = Integer.parseInt(m);
                    int sizeK = Integer.parseInt(k);
                    final ArrayList<Player> newPlayers = new ArrayList<>();
                    final ArrayList<Integer> numberOfPlayer = new ArrayList<>();
                    for (int i = 0; i < people; i++) {
                        numberOfPlayer.add(i + 1);
                        while (true) {
                            System.out.println("Human player? Write:");
                            System.out.println("yes/no");
                            String ans = in.next().toLowerCase();
                            if ("yes".equals(ans)) {
                                newPlayers.add(new HumanPlayer());
                                break;
                            } else if ("no".equals(ans)) {
                                newPlayers.add(new RandomPlayer());
                                break;
                            }
                            System.out.println("Incorrect input format.");
                        }

                    }
                    final ArrayList<Integer> answer = new ArrayList<>();
                    final ArrayList<Integer> left = new ArrayList<>();
                    final ArrayList<Integer> right = new ArrayList<>();
                    int evenPeople = people + people % 2;
                    for (int i = 0; i < evenPeople / 2; i++) {
                        answer.add(0);
                        right.add(i);
                    }
                    for (int i = evenPeople - 1; i >= evenPeople / 2; i--) {
                        left.add(i);
                        answer.add(0);
                    }
                    for (int c = 0; c < people - 1; c++) {
                        for (int j = 0; j < evenPeople / 2; j++) {
                            if (left.get(j) >= people || right.get(j) >= people) {
                                continue;
                            }
                            for (int i = 0; i < 2; i++) {
                                int first = getFirstPlayer(i, j, left, right);
                                int second = getSecondPlayer(i, j, left, right);
                                System.out.println("X: player " + (first + 1) + " vs  O: player " + (second + 1));
                                Game game = new Game(false, newPlayers.get(first), newPlayers.get(second));
                                TicTacToeBoard bord = new TicTacToeBoard(sizeN, sizeM,
                                        sizeK, 2);
                                result = game.play(bord);
                                if (result == 0) {
                                    answer.set(first, answer.get(first) + 1);
                                    answer.set(second, answer.get(second) + 1);
                                    System.out.println("Game result: draw!");
                                    continue;
                                } else if (result == 1) {
                                    answer.set(first, answer.get(first) + 3);
                                    result = second + 1;
                                } else if (result == 2) {
                                    answer.set(second, answer.get(second) + 3);
                                    result = second + 1;
                                }
                                System.out.println("Game result: player number " + result + " is winning!");
                            }
                        }
                        left.add(right.get(evenPeople / 2 - 1));
                        right.add(1, left.get(0));
                        right.remove(evenPeople / 2);
                        left.remove(0);
                    }
                    for (int i = 0; i < people; i++) {
                        System.out.println((i + 1) + "player points " + answer.get(i));
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect input format.");
                    System.out.println("Example: 3 3 3");
                } catch (NoSuchElementException e) {
                    System.out.println("Incorrect input format.");
                    System.out.println("Example: 3 3 3");
                } catch (IllegalStateException e) {
                    System.out.println("Incorrect input format.");
                    System.out.println("Example: 3 3 3");
                }
            }
        } while (result != 0);
    }
    private static int getFirstPlayer(int circle, int index, final ArrayList<Integer> left, final ArrayList<Integer> right) {
        return circle % 2 == 0 ? left.get(index) : right.get(index);
    }
    private static int getSecondPlayer(int circle, int index, final ArrayList<Integer> left, final ArrayList<Integer> right) {
        return circle % 2 == 1 ? left.get(index) : right.get(index);
    }
}
