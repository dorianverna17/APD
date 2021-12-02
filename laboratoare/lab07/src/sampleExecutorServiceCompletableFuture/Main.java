package sampleExecutorServiceCompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService tpe = Executors.newFixedThreadPool(4);
		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		AtomicInteger counter = new AtomicInteger(0);
		counter.incrementAndGet();
		tpe.submit(new MyRunnable(tpe, "D:\\Facultate\\Anul3\\APD\\repo_APD\\laboratoare\\lab07\\files", "somefile.txt", counter, completableFuture));

		String result = completableFuture.get();
		if (result != null) {
			System.out.println("File was found at this path: " + result);
		} else {
			System.out.println("File was not found");
		}
	}
}
