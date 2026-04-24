public class MathTask implements Runnable {
 
    private final int threadId;       // used as 'j' in the formula
    private volatile long result = 0; // volatile so the value is visible to Main
 
    public MathTask(int threadId) {
        this.threadId = threadId;
    }
 
    @Override
    public void run() {
        long sum = 0;
        int j = threadId;
 
        for (int i = 0; i < 10_000_000; i++) {
            // Heavy calculation: i^3 + i*j
            sum += (long) i * i * i + (long) i * j;
        }
 
        result = sum; // store so the compiler can't optimise the loop away
    }
 
    /** Optional: retrieve the computed result after join(). */
    public long getResult() {
        return result;
    }
}