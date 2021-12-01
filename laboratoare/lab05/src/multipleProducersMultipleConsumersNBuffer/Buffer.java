package multipleProducersMultipleConsumersNBuffer;

import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Buffer {
    
    Queue<Integer> queue;
    Semaphore sem1;
    Semaphore sem2;

    public Buffer(int size) {
        queue = new LimitedQueue<>(size);
        sem1 = new Semaphore(size);
        sem2 = new Semaphore(0);
    }

	public void put(int value) {
        try {
            sem1.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            queue.add(value);
        }
        sem2.release();
	}

	public int get() {
        int a = -1;
        try {
            sem2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            Integer result = queue.poll();
            if (result != null) {
                a = result;
            }
        }
        sem1.release();
        return a;
	}
}
