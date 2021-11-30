package shortestPathsFloyd_Warshall;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static CyclicBarrier barrier;

    public static void main(String[] args) {
        int M = 9;
        int[][] graph = {{0, 1, M, M, M},
                {1, 0, 1, M, M},
                {M, 1, 0, 1, 1},
                {M, M, 1, 0, M},
                {M, M, 1, M, 0}};

        // Parallelize me (You might want to keep the original code in order to compare)
        for (int k = 0; k < 5; k++) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    graph[i][j] = Math.min(graph[i][k] + graph[k][j], graph[i][j]);
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();

        int P = 4;
        Thread[] threads = new Thread[P];
        barrier = new CyclicBarrier(P);

        for (int i = 0; i < P; i++) {
            threads[i] = new Thread(new MyThread(i, M, 5, graph, P, barrier));
            threads[i].start();
        }

        for (int i = 0; i < P; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }

    }
}

class MyThread implements Runnable{
    int[][] graph;
    int start, end;
    int M, N;
    CyclicBarrier barrier;

    public MyThread(int id, int M, int N, int[][] graph, int P, CyclicBarrier barrier) {
        start = (int) (id * (double) N/P);
        end = (int) Math.min((id + 1) * (double) N/P, N);
        this.graph = graph;
        this.M = M;
        this.N = N;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        // Parallelize me (You might want to keep the original code in order to compare)
        for (int k = 0; k < 5; k++) {
            for (int i = start; i < end; i++) {
                for (int j = 0; j < 5; j++) {
                    graph[i][j] = Math.min(graph[i][k] + graph[k][j], graph[i][j]);
                }
            }
            try {
                Main.barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}

