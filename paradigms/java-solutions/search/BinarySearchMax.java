package search;

public class BinarySearchMax {
    // Pred: args.length > 0 && ((i in [1, a.length) a[i - 1] > a[i]) || (Exist k : 0 < k < args.length && i in [1, k) args[i - 1] < args[i] && i in [k + 1, args.length) args[i - 1] < args[i] && args[k - 1] > args[k]))
    // Post: R: R in a && for i in [0, a.length)  a[i] <= R
    public static void main(final String[] args) {
        int sum = 0;
        // args.length > 0
        int[] a = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            a[i] = Integer.parseInt(args[i]);
            sum += a[i];
        }
        // a.length > 0 && a[0] > a[a.length - 1] && i in [0, k] j in (k, a.length) a[i] > a[j]
        int x = 0;
        if (sum % 2 == 1) {
            x = binarySearch(a);
        } else {
            x = recBinarySearch(a, 0, a.length);

        }
        System.out.println(a[x]);
        // i in [0, a.length) a[i] <= a[x]
    }
    // Pred: args.length > 0 && ((i in [1, a.length) a[i - 1] > a[i]) || (Exist k : 0 < k < args.length && i in [1, k) args[i - 1] < args[i] && i in [k + 1, args.length) args[i - 1] < args[i] && args[k - 1] > args[k]))
    // Post: R : i in [0, a.length) && i in [0, a.length) a[i] <= a[R]
    public static int binarySearch(final int[] a) {
        int l = 0;
        int r = a.length;
        // args.length > 0 && l`, r` in [0, a.length) && l` < r` && i in [1, l`) a[i - 1] > a[i] && i in (r`, a.lenght) a[i - 1] > a[i] && i in [0, l] j in [r, a.length) a[i] > a[j]

        while (l + 1 < r) {

            // r` > l` + 1 -->  Exists m : l` < m < r`
            int m = (l + r) / 2;
            // Exists k: i in [0, a.length) a[k] >= a[i] && a[l'] > a[r'] --> l' <= k <= r'
            if (a[l] < a[m])  {
                // a[l'] > a[r'] -->  l' = m && r' = r
                l = m;
                // i in [l, m] j in (m, r) a[i] > a[j] && i in [l, m) a[i] < a[i + 1]
            } else {
                // a[l'] > a[r'] ->  r' = m && l' = l
                r = m;
                //  i in [l, m) j in [m, r) a[i] > a[j] && (i in [m, r) a[i] < a[i + 1] && a[l] >= a[r - 1] --> i in [m, r) a[i] < a[l])
            }
            // -> I
        }
        // I && !
        // R: i in [0, a.length) a[i] <= a[R] && a[l] > a[r] && r - l == 1 && i in [0, a.length - 1) (i != R && a[i] < a[i + 1] || i == R && a[i] > a[i + 1]) --> R = l
        return l;
    }

    // Pred: args.length > 0 && l, r in [0, a.length) && l < r && i in [1, l) a[i - 1] > a[i] && i in (r, a.length) a[i - 1] > a[i] && ((i in [1, a.length) a[i - 1] > a[i]) || (Exist k : 0 < k < args.length && i in [1, k) args[i - 1] < args[i] && i in [k + 1, args.length) args[i - 1] < args[i] && args[k - 1] > args[k]))

    // Post: R : i in [0, a.length) && i in [0, a.length) a[i] <= a[R]
    public static int recBinarySearch(final int[] a, int l, int r) {
        if (l + 1 < r) {
            // r - l > 1 => Exist m : l < m < r
            int m = (l + r) / 2;
            // n > 0 && Exist k: i in [0, a.length) a[k] >= a[i] && a[l] > a[r] -> l <= a[k] <= r
            if (a[l] < a[m]) {
                // a[l] > a[r] ->  l' = m && r' = r

                return recBinarySearch(a, m, r);
            } else {
                // a[l] > a[r] ->  r' = m && l' = l
                //
                return recBinarySearch(a, l, m);
            }
        }
        // R: i in [0, a.length) a[i] <= a[R] && a[l] > a[r] && r - l == 1 && i in [0, a.length - 1) (i != R && a[i] < a[i + 1] || i == R && a[i] > a[i + 1]) --> R = l
        return l;
    }
}

