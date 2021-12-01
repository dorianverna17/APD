package readersWriters.readerPriority;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Main {
    public static final int WRITERS = 6;
    public static final int READERS = 8;

    public static int currentReaders = 0;
    public static final boolean[] hasRead = new boolean[READERS];
    public static final boolean[] hasWritten = new boolean[WRITERS];

    // used to make sure threads try to go in the same to the shared zone
    public static CyclicBarrier barrier = new CyclicBarrier(READERS + WRITERS);

    // mutex (sau semafor) folosit pentru a modifica numărul de cititori
    static Semaphore readers = new Semaphore(1);
    // semafor (sau mutex) folosit pentru protejarea resursei comune
    static Semaphore readWriteSem = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {
        Thread[] readers = new Reader[READERS];
        Thread[] writers = new Writer[WRITERS];

        for (int i = 0; i < READERS; i++) {
            readers[i] = new Reader(i);
        }

        for (int i = 0; i < WRITERS; i++) {
            writers[i] = new Writer(i);
        }

        for (Thread reader: readers) {
            reader.start();
        }

        for (Thread writer: writers) {
            writer.start();
        }

        for (Thread reader: readers) {
            reader.join();
        }

        for (Thread writer: writers) {
            writer.join();
        }
    }
}
