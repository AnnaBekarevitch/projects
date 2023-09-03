package queue;

import java.util.Arrays;
import java.util.Objects;

public class LinkedQueue extends AbstractQueue {

    private class Node {
        private Object value;
        private Node prev;
        private Node next;
        public Node(final Object value, final Node prev, final Node next) {
            assert value != null;
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
    private Node front;
    private Node back;

    @Override
    public Object dequeueImpl() {
        final Object result = front.value;
        front = front.next;
        return result;
    }

    @Override
    public void enqueueImpl(final Object element) {
        Node newBack = new Node(Objects.requireNonNull(element), back, null);
        if (size == 0) {
            front = newBack;
            back = newBack;
        } else {
            back.next = newBack;
            back = newBack;
        }
    }
    @Override
    public Object elementImpl() {
        return front.value;
    }

    @Override
    public void clearImpl() {
        back = null;
        front = null;
    }

    public LinkedQueue makeCopy() {
        final LinkedQueue copy = new LinkedQueue();
        copy.size = size;
        copy.front = front;
        copy.back = back;
        return copy;
    }

    public int indexOf(final Object value) {
        Node now = front;
        int index = 0;
        while (now != null) {
            if (now.value.equals(value) && index < size) {
                return index;
            }
            index++;
            now = now.next;
        }
        return -1;
    }
    public int lastIndexOf(final Object value) {
        Node now = back;
        int index = size - 1;
        while (now != null && index >= 0) {
            if (now.value.equals(value)) {
                return index;
            }
            index--;
            now = now.prev;
        }
        return -1;
    }
}
