package queue;
public class MyLinkedQueueTest {
    public static void main(String[] args) {
        LinkedQueue queue1 = new LinkedQueue();
        LinkedQueue queue2 = new LinkedQueue();
        for (int i = 0; i < 5; i++) {
            queue1.enqueue("s_1_" + i);
            queue2.enqueue("s_2_" + i);
        }

    }
    public static void dump(LinkedQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " +
                    queue.element() + " " + queue.dequeue());
        }
    }
}
