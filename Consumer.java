public class Consumer implements Runnable {
 
    private final SharedResource resource;
 
    public Consumer(SharedResource resource) {
        this.resource = resource;
    }
 
    @Override
    public void run() {
        int received;
        while ((received = resource.get()) != -1) {
            // Process the value (here: just print it)
            System.out.printf("[Consumer] Processing %d ...%n", received);
        }
        System.out.println("[Consumer] Received sentinel — shutting down.");
    }
}