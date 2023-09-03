import java.util.Scanner;

public class J_1 {

    public static final int CODE_0 = '0';

    public static void main(final String[] args) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int[][] spots = new int[size][size];
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            String inputLine = in.next();
            for (int j = 0; j < size; j++) {
                spots[i][j] = inputLine.charAt(j);
                spots[i][j] -= CODE_0;
            }
        }

        for (int vertexV = 0; vertexV < size; vertexV++) {
            for (int vertexU = vertexV + 1; vertexU < size; vertexU++) {
                if (spots[vertexV][vertexU] != 0) {
                    result[vertexV][vertexU] = 1;
                    for (int vertexW = vertexU + 1; vertexW < size; vertexW++) {
                        spots[vertexV][vertexW] = (spots[vertexV][vertexW] + 10 - spots[vertexU][vertexW]) % 10;
                    }
                    spots[vertexV][vertexU] -= 1;
                }
            }
        }
        for (int vertexV = 0; vertexV < size; vertexV++) {
            for (int vertexU = 0; vertexU < size; vertexU++) {
                System.out.print(result[vertexV][vertexU]);
            }
            System.out.println();
        }
    }
}
