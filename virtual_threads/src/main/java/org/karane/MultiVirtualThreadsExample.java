package org.karane;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiVirtualThreadsExample {
    public static void main(String[] args) throws InterruptedException {
        // Create a virtual thread executor
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        Random random = new Random();

        // Submit multiple tasks with random sleep durations
        for (int i = 1; i <= 5; i++) {
            final int taskNumber = i;
            executorService.submit(() -> {
                int sleepTime = random.nextInt(3000) + 1000; // Random sleep time between 1000 and 4000 ms
                System.out.println("Task " + taskNumber + " is running on virtual thread: " 
                        + Thread.currentThread() + " (Sleep time: " + sleepTime + " ms)");
                try {
                    Thread.sleep(sleepTime); // Simulate work with random sleep
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Task " + taskNumber + " completed after " + sleepTime + " ms.");
            });
        }

        executorService.awaitTermination(10L, TimeUnit.SECONDS);
        executorService.shutdown();
    }
}
