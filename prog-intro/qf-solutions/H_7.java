import java.util.Scanner;

public class H_7 {

    public static void main(final String[] args) {
        Scanner in = new Scanner(System.in);
        int sizeT = in.nextInt();
        int[] transactions = new int[sizeT + 1]; // 1 индексация, для удобного вычисления префиксных сумм
        for (int i = 1; i <= sizeT; ++i) {
            transactions[i] = in.nextInt();
        }
        int maxTransactions = transactions[1];
        int sizeSumT = 0;
        for (int i = 1; i <= sizeT; i++) {
            maxTransactions = Math.max(maxTransactions, transactions[i]);
            sizeSumT += transactions[i];
            transactions[i] += transactions[i - 1];
        }
        int[] whereQuery = new int[sizeSumT];
        int[] hashResult = new int[sizeSumT + 1];
        for (int i = 1, j = 0; i <= sizeT; i++) {
            for (;j < transactions[i]; j++) {
                whereQuery[j] = i;
            }
        }
        int sizeQ = in.nextInt();
        for (int queries = 0; queries < sizeQ; queries++) {
            int query = in.nextInt();
            if (query < maxTransactions) {
                System.out.println("Impossible");
            } else if (hashResult[query] > 0) {
                System.out.println(hashResult[query]);
            } else {
                int result = 1;
                int block = 0;
                while (transactions[block] + query < sizeSumT) {
                    block = whereQuery[transactions[block] + query] - 1;
                    result += 1;
                }
                hashResult[query] = result;
                System.out.println(result);
            }
        }
    }
}