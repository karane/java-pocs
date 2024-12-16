package org.karane;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFutureExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Create a Callable task that computes the sum of numbers from 1 to 100
        Callable<Integer> sumTask = () -> {
            int sum = 0;
            for (int i = 1; i <= 100; i++) {
                Thread.sleep(10);
                sum += i;
            }
            return sum;
        };

        // Submit the task to the executor
        Future<Integer> futureResult = executorService.submit(sumTask);

        try {
            System.out.println("Calculating the sum...");

            // Get the result of the computation (blocks if not done yet)
            int result = futureResult.get();

            System.out.println("The sum of numbers from 1 to 100 is: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Shut down the executor service
            executorService.shutdown();
        }
    }
}