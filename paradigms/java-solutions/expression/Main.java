package expression;
import expression.generic.GenericTabulator;
public class Main {
    public static void main(final String[] args) {
        Object[][][] result = (new GenericTabulator()).tabulate(args[0].substring(1), args[1], -2, 2, -2, 2, -2, 2);
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                System.out.print("i=" + i + "j=" + j + "k=-2..2: ");
                for (int k = -2; k <= 2; k++) {
                    System.out.print(result[2 + i][2 + j][2 + k] + " ");
                }
                System.out.println();
            }
        }
    }
}
