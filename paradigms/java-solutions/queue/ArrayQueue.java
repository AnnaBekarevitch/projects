package queue;
import java.util.Arrays;
import java.util.Objects;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[1];
    private int front;

    @Override
    public void enqueueImpl(final Object element) {
        ensureCapacity(size + 1);
        elements[(size + front) % elements.length] = element;
    }
    private void ensureCapacity(final int size) {
        if (elements.length < size) {
            int length = elements.length;
            elements = Arrays.copyOf(elements, 2 * size);
            for (int i = 0; i < size - 1; i++) {
                elements[(front + i) % elements.length] = elements[(front + i) % length];
            }
        }
    }

    @Override
    public Object dequeueImpl() {
        final Object result = elements[front];
        elements[front] = null;
        front++;
        front %= elements.length;
        return result;
    }

    @Override
    public Object elementImpl() {
        return elements[front];
    }

    @Override
    public void clearImpl() {
        Arrays.fill(elements, null);
        front = 0;
    }

    public Object[] toArray() {
        Object[] a = new Object[size];
        for (int i = 0; i < size; i++) {
            a[i] = elements[(i + front) % elements.length];
        }
        return a;
    }

    public ArrayQueue makeCopy() {
        final ArrayQueue copy = new ArrayQueue();
        copy.size = size;
        copy.elements = Arrays.copyOf(elements, size);
        return copy;
    }

    public int indexOf(final Object value) {
        for (int i = 0; i < size; i++) {
            if (elements[(i + front) % elements.length].equals(value)) {
                return i;
            }
        }
        return -1;
    }
    public int lastIndexOf(final Object value) {
        for (int i = size - 1; i >= 0; i--) {
            if (elements[(i + front) % elements.length].equals(value)) {
                return i;
            }
        }
        return -1;
    }

}
