
package queue;

import queue.ArrayQueueModule;
import queue.ArrayQueueADT;
import queue.ArrayQueue;
import java.util.Arrays;
import java.util.Objects;

public class MyTest {

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.enqueue(i);
        }

        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                    ArrayQueueModule.size() + " " +
                            ArrayQueueModule.element() + " " +
                            ArrayQueueModule.dequeue()
            );
        }

        ArrayQueueADT queue = ArrayQueueADT.create();
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queue, i);
        }
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(
                    ArrayQueueADT.size(queue) + " " +
                            ArrayQueueADT.element(queue) + " " +
                            ArrayQueueADT.dequeue(queue)
            );
        }

        ArrayQueue queue1 = new ArrayQueue();
        for (int i = 0; i < 10; i++) {
            queue1.enqueue(i);
        }

        while (!queue1.isEmpty()) {
            System.out.println(queue1.size() + " " +
                    queue1.element() + " " + queue1.dequeue());
        }

    }
}