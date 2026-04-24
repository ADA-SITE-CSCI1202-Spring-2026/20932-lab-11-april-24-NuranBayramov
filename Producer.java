public class Producer implements Runnable {
 
    private final SharedResource resource;
 
    public Producer(SharedResource resource) {
        this.resource = resource;
    }
 
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            try {
                // Simulate work / data-gathering delay
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            resource.set(i * 10);   // produces 10, 20, 30, 40, 50
        }
 
        // Sentinel: tell the Consumer there is no more data
        resource.set(-1);
    }
}