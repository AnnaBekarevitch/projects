package search;

public class BinarySearch {
    // Pred: args.length > 0 && i in [2, args.length) args[i - 1] >= args[i] 
    // Post: R:  R in [0, n] && ( for i in [0, R) args[i + 1] > args[0]) && args[R + 1] <= args[0] || R == n)
    public static void main(final String[] args) {
        // args.length > 0
        int x = Integer.parseInt(args[0]);
        // args.length > 0
        if (args.length == 1) {
            // (args.length == 1) --> (Not exists R : for i in [0, R) args[i + 1] > args[0]) && args[R + 1] <= args[0])
            System.out.println(0);
            return;
        }
        // args.length >= 2
        int[] a = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        // a.length > 0 && i in [1, a.length) a[i - 1] >= a[i]
        if (a[0] <= x) {
            // (a.length > 0 && a[0] <= x) => (i in [0, a.length) a[i] <= x) --> (Exists R = 0: for i in [0, R) args[i + 1] > args[0]) && args[R + 1] <= args[0])
            System.out.println(0);
        } else if (a[a.length - 1] > x) {
            // (a.length > 0 && a[0] > x && a[a.length - 1] > x)  --> (i in [0, a.length) a[i] > x) --> (! Exists R : for i in [0, R) args[i + 1] > args[0]) && args[R + 1] <= args[0])
            System.out.println(a.length);
        } else {
            // (a.length > 0 && a[0] > x && a[a.length - 1] <= x) --> (Exists M : (i in 0 .. M - 1 a[i] > x) && (a[M] <= x)) --> (Exist R = M: for i in [0, R) args[i + 1] > args[0]) && args[R + 1] <= args[0])
            System.out.println(recBinarySearch(x, a, 0, a.length - 1));
        }
    }
    // Pred: i in [1, a.length) a[i - 1] >= a[i] && a[0] > x && a[a.length - 1] <= x
    // Post: res : res in [0, a.length) && i in [0, R) a[i] > x && a[res] <= x
    public static int binarySearch(final int x, final int[] a) {
        int l = 0;
        int r = a.length - 1;
        // i in [1, a.length) a[i - 1] >= a[i] && 0 <= l` < r` < a.length && i in [0, l`] a[i] > x && i in [r`, a.length) a[i] <= x
        while (l + 1 < r) {
            // r` > l` + 1 --> Exists m : l` < m < r`
            int m = (l + r) / 2;
            // i in [1, a.length) a[i - 1] >= a[i] && 0 <= l` < r` < a.length && i in [0, l`] a[i] > x && i in [r`, a.length) a[i] <= x && l` < m < r`
            if (a[m] > x) {
                // i in [0, m] a[i] > x
                l = m;
                // i in [1, a.length) a[i - 1] >= a[i] && 0 <= l` < r` < a.length && i in [0, l`] a[i] > x && i in [r`, a.length) a[i] <= x
            } else {
                // i in [m, a.length) a[i] <= x
                r = m;
                // i in [1, a.length) a[i - 1] >= a[i] && 0 <= l` < r` < a.length && i in [0, l`] a[i] > x && i in [r`, a.length) a[i] <= x
            }
        }
        // r` = l` + 1 && i in [1, a.length) a[i - 1] >= a[i] && 0 <= l` < r` < n && i in [0, l`] a[i] > x && i in [r`, a.length) a[i] <= x  --> res = r`
        return r;
    }

    // Pred: i in [1, a.length) a[i - 1] >= a[i] && l < r && l, r in [0, a.length) && i in [0, l] a[i] > x && i in [r, a.length) a[i] <= x
    // Post: res: (res in [0, a.length) && i in [0, res) a[i] > x && a[res] <= x) || res == n
    public static int recBinarySearch(final int x, final int[] a, int l, int r) {
        if (l + 1 < r) {
            // r - l > 1 --> Exist m : l < m < r
            int m = (l + r) / 2;
            // i in [1, a.length) a[i - 1] >= a[i] && 0 <= l < r < a.length && i in [0, l] a[i] > x && i in [r,  a.length) a[i] <= x && l < m < r
            if (a[m] > x) {
                // i in [0, m] a[i] > x
                // i in [1, a.length) a[i - 1] >= a[i] && 0 <= l < r < a.length && i in [0, m] a[i] > x && i in [r, a.length) a[i] <= x && l < m
                return recBinarySearch(x, a, m, r);
            } else {
                // i in [m, a.length) a[i] <= x
                // i in [1, a.length) a[i - 1] >= a[i] && 0 <= l < r < a.length && i in [0, l] a[i] > x && i in [m, a.length) a[i] <= x && m < r
                return recBinarySearch(x, a, l, m);
            }
        }
        //  r = l + 1 && i in [0, l] a[i] > x && i in [r, a.length) a[i] <= x  -->  res = r
        return r;
    }
}
