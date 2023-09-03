import java.util.Scanner;
import java.util.HashMap;

public class B_5 {

    public static final int SOLVE_CONST = 710;

    public static void main(final String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for (int i = 0, result = -25000; i < n; i++, result++) {
            System.out.println(SOLVE_CONST * result);
        }
    }
}
