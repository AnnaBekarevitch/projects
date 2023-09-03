import java.util.Scanner;
import java.util.HashMap;

public class M_4 {

    public static void main(final String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int sample = 0; sample < t; sample++) {
            int size = in.nextInt();
            int[] problems = new int[size];
            for (int i = 0; i < size; ++i) {
                problems[i] = in.nextInt();
            }
            HashMap<Integer, Integer> difficulty
                    = new HashMap<Integer, Integer>();
            int result = 0;
            for (int j = size - 1; j > 0; j--) {
                for (int i = 0; i < j; i++) {
                    Integer now = difficulty.get(2 * problems[j] - problems[i]);
                    if (now != null) {
                        result += now;
                    }
                }
                Integer add = difficulty.get(problems[j]);
                difficulty.put(problems[j], add != null ? add + 1 : 1);
            }
            System.out.println(result);
        }

    }
}
