package multipleProducersMultipleConsumers;

import java.util.concurrent.ArrayBlockingQueue;

public class Buffer {
	ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);

	void put(int value) {
        try {
            queue.put(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	int get() {
	    int aux = -1;
        try {
            aux = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	    return aux;
	}
}
