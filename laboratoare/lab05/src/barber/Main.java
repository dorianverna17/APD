package barber;

import java.util.concurrent.Semaphore;

public class Main {
    public final static int TOTAL_CHAIRS = 3;
    public final static int TOTAL_CLIENTS = 7;

    // a client can leave the shop served
    public final static int SERVED_CLIENT = 1;
    // a client can leave the shop unserved, as the barber was too busy and there no seats available
    public final static int UNSERVED_CLIENT = 2;

    public static int[] leftClients = new int[TOTAL_CLIENTS];

    // TODO: add semaphores
    static Semaphore sem_client = new Semaphore(0);
    static Semaphore sem_barber = new Semaphore(0);
    static Semaphore sem_chairs = new Semaphore(1);

    public static int clients = TOTAL_CLIENTS;
    public static int chairs = TOTAL_CHAIRS;

    public static void main(String[] args) throws InterruptedException {
        Thread barberThread = new Barber();
        Thread[] clientThreads = new Client[clients];

        for (int i = 0; i < clients; i++) {
            clientThreads[i] = new Client(i);
        }

        barberThread.start();
        for (Thread clientThread : clientThreads) {
            clientThread.start();
            Thread.sleep(100);
        }

        barberThread.join();
        for (Thread thread: clientThreads) {
            thread.join();
        }

        int unservedClients = 0;
        for (int client: leftClients) {
            if (client == UNSERVED_CLIENT) {
                unservedClients++;
            }
        }

        System.out.println("There were " + unservedClients + " unserved clients");
    }
}
