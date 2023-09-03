
package queue;
import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractQueue implements Queue {

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
//
// Pred: value != null
// Post: (R == -1 || (min(R): a[R] == value)) && n == n' && immutable(n)
//      indexOf(value)
// Pred: value != null
// Post: (R == -1 || (max(R): a[R] == value) && n == n' && immutable(n)
//      lastIndexOf(value)

    protected int size;

    // Pred: true
    // Post: R = n && n' == n && immutable(n)
    public int size() {
        return size;
    }

    // Pred: true
    // Post: R = (n == 0) && n' == n && immutable(n)
    public boolean isEmpty() {
        return size == 0;
    }

    // Pred: n >= 1
    // Post: n' = n - 1 && immutable(n') && R = a[1]
    public Object dequeue() {
        assert size >= 1;
        size--;
        return dequeueImpl();
    }
    protected abstract Object dequeueImpl();

    // Pred: element != null
    // Post: n' = n + 1 && a[n'] == element && immutable(n)
    public void enqueue(final Object element) {
        Objects.requireNonNull(element);
        enqueueImpl(element);
        size++;
    }
    protected abstract void enqueueImpl(final Object element);

    // Pred: n >= 1
    // Post: n' == n && immutable(n) && R = a[1]
    public Object element() {
        assert size >= 1;
        return elementImpl();
    }
    protected abstract Object elementImpl();

    // Pred: true
    // Post: n' = 0
    public void clear() {
        size = 0;
        clearImpl();
    }

    protected abstract void clearImpl();


    public abstract int indexOf(final Object value);
    public abstract int lastIndexOf(final Object value);
}
