package multipleProducersMultipleConsumers;

import java.util.concurrent.Semaphore;

public class Buffer {
    private int a = -1;
    Semaphore sem1 = new Semaphore(1);
    Semaphore sem2 = new Semaphore(0);

    public void put(int value) {
        try {
            sem1.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            a = value;
        }

        sem2.release();
    }

    public int get() {
        int aux;

        try {
            sem2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            aux = a;
        }

        sem1.release();

        return aux;
    }
}
