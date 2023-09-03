import java.util.Scanner;
import java.util.HashMap;

public class I_4 {

    public static void main(final String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int xL =  Integer.MAX_VALUE;
        int yL =  Integer.MAX_VALUE;
        int xR = -Integer.MAX_VALUE;
        int yR = -Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int h = in.nextInt();
            xL = Math.min(xL, x - h);
            yL = Math.min(yL, y - h);
            xR = Math.max(xR, x + h);
            yR = Math.max(yR, y + h);
        }
        System.out.format("%d %d %d", (xL + xR) / 2, (yL + yR) / 2, (Math.max(xR - xL, yR - yL) + 1) / 2);
    }
}
