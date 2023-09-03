import java.util.Scanner;

public class A_2 {
    public static void main(final String[] args) {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        int b = in.nextInt();
        int n = in.nextInt();
        System.out.println(2 * ((n - b + (b - a) - 1) / (b - a)) + 1);
    }
}
