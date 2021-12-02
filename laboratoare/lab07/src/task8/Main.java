package task8;

import util.BSTOperations;
import util.BinarySearchTreeNode;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class Calculator extends RecursiveTask<Integer> {
    private final BinarySearchTreeNode<Integer> tree;

    public Calculator(BinarySearchTreeNode<Integer> tree) {
        this.tree = tree;
    }

    @Override
    protected Integer compute() {
        if (tree == null) {
            return 0;
        }

        Calculator left = new Calculator(tree.getLeft());
        Calculator right = new Calculator(tree.getRight());

        left.fork();
        right.fork();

        return 1 + Math.max(left.join(), right.join());
    }
}

public class Main {
    private static <T extends Comparable<T>> int calculateMaximumHeight(BinarySearchTreeNode<T> binarySearchTree) {
        if (binarySearchTree == null) {
            return 0;
        }

        return 1 + Math.max(
                calculateMaximumHeight(binarySearchTree.getRight()),
                calculateMaximumHeight(binarySearchTree.getLeft())
        );
    }

    public static void main(String[] args) {
        BinarySearchTreeNode<Integer> binarySearchTree = new BinarySearchTreeNode<>(3);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 6);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 9);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 2);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 10);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 1);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 12);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 13);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 11);

        System.out.println(calculateMaximumHeight(binarySearchTree));

        System.out.println();

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        Calculator calculator = new Calculator(binarySearchTree);
        forkJoinPool.execute(calculator);
        try {
            System.out.println(calculator.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
