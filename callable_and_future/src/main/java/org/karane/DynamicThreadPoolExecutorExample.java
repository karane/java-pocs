package org.karane;

import java.util.Random;
import java.util.concurrent.*;

public class DynamicThreadPoolExecutorExample {
    public static void main(String[] args) {
        Random rand = new Random();
        // Create a ThreadPoolExecutor with dynamic sizing
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                0, // corePoolSize: Start with 0 threads
                Integer.MAX_VALUE, // maximumPoolSize: No limit on threads
                60L, TimeUnit.SECONDS, // Keep-alive time for idle threads
                new SynchronousQueue<>() // Task queue
        );

        // Submit multiple tasks
        for (int i = 1; i <= 5; i++) {
            final int taskNumber = i;
            executor.execute(() -> {
                System.out.println("Task " + taskNumber + " is running on thread: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(rand.nextLong(100, 3000)); // Simulate work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Task " + taskNumber + " completed.");
            });
        }

        executor.shutdown();
    }
}
