package readersWriters.writerPriority;


import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Main {
    public static final int WRITERS = 6;
    public static final int READERS = 8;

    public static int currentReaders = 0;
    public static int currentWriters = 0;
    public static int waitingReaders = 0;
    public static int waitingWriters = 0;

    public static final boolean[] hasRead = new boolean[READERS];
    public static final boolean[] hasWritten = new boolean[WRITERS];

    // used to make sure threads try to go in the same to the shared zone
    public static CyclicBarrier barrier = new CyclicBarrier(READERS + WRITERS);

    // semafor folosit pentru a pune scriitori în așteptare, dacă avem un scriitor
    // sau unul sau mai mulți cititori în zona de memorie (zona critică)
    static Semaphore sem_writer = new Semaphore(0);

    // semafor folosit pentru a pune cititori în așteptare dacă avem un scriitor care scrie în zona de memorie
    // sau dacă avem scriitori în așteptare (deoarece ei au prioritate față de cititori)
    static Semaphore sem_reader = new Semaphore(0);

    // semafor folosit pe post de mutex pentru protejarea zonei de memorie (zona critică)
    static Semaphore enter = new Semaphore(1);

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
