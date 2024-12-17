package org.karane;

public class VirtualThreadsExample {

    public static void main(String[] args) {
        // Create and start a virtual thread
        Thread virtualThread = Thread.ofVirtual().start(() -> {
            System.out.println("Hello from a virtual thread! - " + Thread.currentThread());
            try {
                for(int i=0; i<100; i++) {
                    Thread.sleep(10);
                    System.out.print(".");
                }
                System.out.println();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Virtual thread was interrupted.");
            }
        });

        // Wait for the virtual thread to finish
        try {
            virtualThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread was interrupted.");
        }

        System.out.println("Virtual thread has finished execution.");
    }
}