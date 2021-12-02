package task7;

import util.BSTOperations;
import util.BinarySearchTreeNode;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class MyRunnable implements Runnable {
    private final ExecutorService tpe;
    private final AtomicInteger counter;
    private final CompletableFuture<String> completableFuture;
    private final BinarySearchTreeNode<Integer> binarySearchTree;
    private final int value;

    public MyRunnable(ExecutorService tpe, AtomicInteger counter,
                      CompletableFuture<String> completableFuture,
                      BinarySearchTreeNode<Integer> binarySearchTree, int value) {
        this.tpe = tpe;
        this.counter = counter;
        this.completableFuture = completableFuture;
        this.binarySearchTree = binarySearchTree;
        this.value = value;
    }

    @Override
    public void run() {
        if (binarySearchTree != null) {
            if (value == binarySearchTree.getValue()) {
                System.out.println("Found value: " + binarySearchTree.getValue());
                tpe.shutdown();
            } else if (binarySearchTree.getValue().compareTo(value) > 0) {
                counter.incrementAndGet();
                Runnable t = new MyRunnable(tpe, counter, completableFuture, binarySearchTree.getLeft(), value);
                tpe.submit(t);
            } else {
                counter.incrementAndGet();
                Runnable t = new MyRunnable(tpe, counter, completableFuture, binarySearchTree.getRight(), value);
                tpe.submit(t);
            }
        }
        int left = counter.decrementAndGet();
        if (left == 0) {
            completableFuture.complete(null);
            tpe.shutdown();
        }
    }
}

public class Main {
    private static <T extends Comparable<T>> void searchValue(BinarySearchTreeNode<T> binarySearchTree, T value) {
        if (binarySearchTree != null) {
            if (value.equals(binarySearchTree.getValue())) {
                System.out.println("Found value: " + binarySearchTree.getValue());
            } else if (binarySearchTree.getValue().compareTo(value) > 0) {
                searchValue(binarySearchTree.getLeft(), value);
            } else {
                searchValue(binarySearchTree.getRight(), value);
            }
        }
    }

    public static void main(String[] args) {
        BinarySearchTreeNode<Integer> binarySearchTree = new BinarySearchTreeNode<>(3);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 6);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 9);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 5);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 10);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 1);

        searchValue(binarySearchTree, 5);

        System.out.println();

        ExecutorService tpe = Executors.newFixedThreadPool(4);
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        AtomicInteger counter = new AtomicInteger(0);
        counter.incrementAndGet();
        tpe.submit(new MyRunnable(tpe, counter, completableFuture, binarySearchTree, 5));

        String result = null;
        try {
            result = completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (result == null) {
            System.out.println("Value not found");
        }
    }
}
