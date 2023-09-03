package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueModule {

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
    private static Object[] elements = new Object[10];
    private static int size;
    private static int front;

    // Pred: element != null
    // Post: n' = n + 1 && a[n'] == element && immutable(n)
    public static void enqueue(final Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size + 1);
        elements[(size + front) % elements.length] = element;
        size++;
    }
    private static void ensureCapacity(final int size) {
        if (elements.length < size) {
            int length = elements.length;
            elements = Arrays.copyOf(elements, 2 * size);
            for (int i = 0; i < size - 1; i++) {
                elements[(front + i) % elements.length] = elements[(front + i) % length];
            }
        }

    }

    // Pred: n >= 1
    // Post: n' = n - 1 && immutable(n') && R = a[1]
    public static Object dequeue() {
        assert size >= 1;
        final Object result = elements[front];
        elements[front] = null;
        front++;
        front %= elements.length;
        size--;
        return result;
    }

    // Pred: n >= 1
    // Post: n' == n && immutable(n) && R = a[1]
    public static Object element() {
        assert size >= 1;
        return elements[front];
    }

    // Pred: true
    // Post: R = n && n' == n && immutable(n)
    public static int size() {
        return size;
    }

    // Pred: true
    // Post: R = (n == 0) && n' == n && immutable(n)
    public static boolean isEmpty() {
        return size == 0;
    }

    // Pred: true
    // Post: n' = 0
    public static void clear() {
        Arrays.fill(elements, null);
        size = 0;
        front = 0;
    }
    public static Object[] toArray() {
        Object[] a = new Object[size];
        for (int i = 0; i < size; i++) {
            a[i] = elements[(i + front) % elements.length];
        }
        return a;
    }
}
