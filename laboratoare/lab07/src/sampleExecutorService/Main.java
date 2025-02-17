package sampleExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

	public static void main(String[] args) {
		AtomicInteger inQueue = new AtomicInteger(0);
		ExecutorService tpe = Executors.newFixedThreadPool(4);

		inQueue.incrementAndGet();
		tpe.submit(new MyRunnable("D:\\Facultate\\Anul3\\APD\\repo_APD\\laboratoare\\lab07\\src", tpe, inQueue));
	}
}
