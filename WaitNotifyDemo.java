public class WaitNotifyDemo {
 
    public static void main(String[] args) throws InterruptedException {
 
        System.out.println("=== wait() / notify() Coordination Demo ===");
        System.out.println();
 
        SharedResource resource = new SharedResource();
 
        Thread producerThread = new Thread(new Producer(resource), "Producer");
        Thread consumerThread = new Thread(new Consumer(resource), "Consumer");
 
        // Start Consumer FIRST — it will immediately block in wait()
        // because bChanged is false. This proves the guard loop works.
        consumerThread.start();
        producerThread.start();
 
        // Main waits for both to finish before printing the summary
        producerThread.join();
        consumerThread.join();
 
        System.out.println();
        System.out.println("Both threads finished — no data was lost or duplicated.");
    }
}