package org.karane;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadComparison {
    public static void main(String[] args) {
        int taskCount = 100_000;

        System.out.println("Platform Threads:");
        measurePerformanceAndMemory(taskCount, true);

        System.out.println("Virtual Threads:");
        measurePerformanceAndMemory(taskCount, false);
    }

    private static void measurePerformanceAndMemory(int taskCount, boolean isPlatformThread) {
        Runtime runtime = Runtime.getRuntime();

        // Run garbage collection to minimize noise
        System.gc();
        long beforeUsedMemory = runtime.totalMemory() - runtime.freeMemory();

        // Measure execution time
        long startTime = System.nanoTime();

        if (isPlatformThread) {
            executeTasksWithPlatformThreads(taskCount);
        } else {
            executeTasksWithVirtualThreads(taskCount);
        }

        long endTime = System.nanoTime();

        // Run garbage collection to minimize noise again
        System.gc();
        long afterUsedMemory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("Execution Time: " + Duration.ofNanos(endTime - startTime).toMillis() + " ms");
        System.out.println("Memory Used: " + (afterUsedMemory - beforeUsedMemory) / 1024 + " KB\n");
    }

    private static void executeTasksWithPlatformThreads(int taskCount) {
        Thread[] threads = new Thread[taskCount];

        for (int i = 0; i < taskCount; i++) {
            threads[i] = new Thread(() -> simulateTask());
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    private static void executeTasksWithVirtualThreads(int taskCount) {
        ThreadFactory virtualThreadFactory = Thread.ofVirtual().factory();
        var executor = Executors.newThreadPerTaskExecutor(virtualThreadFactory);

        for (int i = 0; i < taskCount; i++) {
            executor.execute(() -> simulateTask());
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all tasks to finish
        }
    }

    private static void simulateTask() {
        // Simulate some work
        for (int i = 0; i < 1_000; i++) {
            Math.sqrt(i);
        }
    }
}
