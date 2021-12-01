package oneProducerOneConsumer;

import java.util.concurrent.Semaphore;

public class Buffer {
    private int a = -1;
    Semaphore sem_gol = new Semaphore(1);
    Semaphore sem_plin = new Semaphore(0);

    void put(int value) {
        try {
            sem_gol.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            a = value;
        }

        sem_plin.release();
    }

    int get() {
        int aux;
        try {
            sem_plin.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            aux = a;
        }

        sem_gol.release();
        return aux;
    }
}

// alternative
//public class Buffer {
//    private int a = -1;
//
//    synchronized void put(int value) {
//        // daca a este -1 (neinitializat)
//        // atunci setam thread-ul sa astepte pana cand primeste notifyAll
//        // (pana cand a este pus inapoi pe -1)
//        if (a != -1) {
//            try {
//                this.wait();
//            } catch (Exception e) {
//                System.out.println("eroare");
//            }
//        }
//        // daca am modificat a-ul, spunem celuilalt thread ca
//        // poate continua
//        a = value;
//        this.notifyAll();
//    }
//
//    int get() {
//        int aux = -1;
//        synchronized (this) {
//            // daca a este neinitializat, atunci asteptam pana cand se initializeaza
//            if (a == -1) {
//                try {
//                    this.wait();
//                } catch (Exception e) {
//                    System.out.println("eroare");
//                }
//            }
//            // in acest moment ii spunem celuilalt thread ca am initializat a
//            // si ca poate sa isi continue executia
//            aux = a;
//            a = -1;
//            this.notifyAll();
//        }
//        return aux;
//    }
//}