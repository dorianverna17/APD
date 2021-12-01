package barber;

public class Barber extends Thread {
    @Override
    public void run() {
        int servedClients = 0;

        do {
            try {
                Main.sem_client.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Main.sem_chairs.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Main.chairs++;

            Main.sem_barber.release();
            Main.sem_chairs.release();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Barber served client");
            ++servedClients;

        } while (checkIfThereAnyClients());
    }

    private boolean checkIfThereAnyClients() {
        int count = 0;
        for (int client: Main.leftClients) {
            if (client == 0) {
                count++;
            }
        }

        return count != 0;
    }
}
