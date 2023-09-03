
package queue;
public interface Queue {
    // Model: a[1]..a[n]
// Invariant: n >= 0 && for i=1..n: a[i] != null
//
// Let: immutable(n): for i=1..n: a'[i] == a[i]
//
// Pred: element != null
// Post: n' = n + 1 && a[n'] == element && immutable(n)
//     enqueue(element)
//
// Pred: n >= 1
// Post: n' = n - 1 && immutable(n') && R = a[1]
//     dequeue()
//
// Pred: n >= 1
// Post: n' == n && immutable(n) && R = a[1]
//     element()
//
// Pred: true
// Post: R = n && n' == n && immutable(n)
//     size()
//
// Pred: true
// Post: R = (n == 0) && n' == n && immutable(n)
//     isEmpty()
//
// Pred: true
// Post: n' = 0
//     clear()

    // Pred: true
    // Post: R = n && n' == n && immutable(n)
    int size();

    // Pred: true
    // Post: R = (n == 0) && n' == n && immutable(n)
    boolean isEmpty();

    // Pred: n >= 1
    // Post: n' = n - 1 && immutable(n') && R = a[1]
    Object dequeue();
    // Pred: element != null
    // Post: n' = n + 1 && a[n'] == element && immutable(n)
    void enqueue(final Object element);
    // Pred: n >= 1
    // Post: n' == n && immutable(n) && R = a[1]
    Object element();

    // Pred: true
    // Post: n' = 0
    void clear();

    // Pre: true
    // Post R.n = n && for i=1..n: R.a[i] = a[i] && n' = n && immutable(n)
    Queue makeCopy();

    // Pred: value != null
    // Post: (R == -1 || (min(R): a[R] == value)) && n == n' && immutable(n)
    int indexOf(final Object value);

    // Pred: value != null
    // Post: (R == -1 || (max(R): a[R] == value) && n == n' && immutable(n)
    int lastIndexOf(final Object value);
}
