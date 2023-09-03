package queue;

import java.util.Arrays;
import java.util.Objects;

public class  ArrayQueueADT {

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

    private Object[] elements = new Object[1];
    private int size;
    private int front;

    public static ArrayQueueADT create() {
        ArrayQueueADT queue = new ArrayQueueADT();
        queue.elements = new Object[10];
        return queue;
    }


    // Pred: element != null
    // Post: n' = n + 1 && a[n'] == element && immutable(n)
    public static void enqueue(ArrayQueueADT queue, final Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, queue.size + 1);
        queue.elements[(queue.front + queue.size) % queue.elements.length] = element;
        queue.size++;
    }
    private static void ensureCapacity(ArrayQueueADT queue, final int size) {
        if (queue.elements.length < size) {
            int length = queue.elements.length;
            queue.elements = Arrays.copyOf(queue.elements, 2 * size);
            for (int i = 0; i < size - 1; i++) {
                queue.elements[(queue.front + i) % queue.elements.length] = queue.elements[(queue.front + i) % length];
            }
        }

    }

    // Pred: n >= 1
    // Post: n' = n - 1 && immutable(n') && R = a[1]
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size >= 1;
        final Object result = queue.elements[queue.front];
        queue.elements[queue.front] = null;
        queue.front++;
        queue.front %= queue.elements.length;
        queue.size--;
        return result;
    }

    // Pred: n >= 1
    // Post: n' == n && immutable(n) && R = a[1]
    public static Object element(ArrayQueueADT queue) {
        assert queue.size >= 1;
        return queue.elements[queue.front];
    }

    // Pred: true
    // Post: R = n && n' == n && immutable(n)
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // Pred: true
    // Post: R = (n == 0) && n' == n && immutable(n)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // Pred: true
    // Post: n' = 0
    public static void clear(ArrayQueueADT queue) {
        Arrays.fill(queue.elements, null);
        queue.size = 0;
        queue.front = 0;
    }
    public static Object[] toArray(ArrayQueueADT queue) {
        Object[] a = new Object[queue.size];
        for (int i = 0; i < queue.size; i++) {
            a[i] = queue.elements[(i + queue.front) % queue.elements.length];
        }
        return a;
    }
}
