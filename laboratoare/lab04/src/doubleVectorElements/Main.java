package doubleVectorElements;

public class Main extends Thread {
    int id;
    int start;
    int end;
    int N, P;
    static int[] v;

    Main(int id, int N, int P) {
        this.id = id;
        this.N = N;
        this.P = P;

        this.start = id * N / P;

        if ((id + 1) * N / P > N) {
            this.end = N;
        } else {
            this.end = (id + 1) * N / P;
        }
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            v[i] *= 2;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int N = 100000013;
        int[] aux = new int[N];
        int P = 4; // the program should work for any P <= N

        for (int i = 0; i < N; i++) {
            aux[i] = i;
        }

        v = aux;

        // Parallelize me using P threads
        Thread[] threads = new Thread[P];
        for (int i = 0; i < P; i++) {
            threads[i] = new Thread(new Main(i, N, P));
            threads[i].start();
        }

        for (int i = 0; i < P; i++) {
            threads[i].join();
        }

        for (int i = 0; i < N; i++) {
            if (v[i] != i * 2) {
                System.out.println("Wrong answer");
                System.exit(1);
            }
        }
        System.out.println("Correct");
    }

}
