public class SharedResource {
 
    private int    value    = 0;
    private boolean bChanged = false;   // flag: has the Producer set a new value?
 
    // ── PRODUCER side ────────────────────────────────────────────────────────
 
    /**
     * Called by the Producer.
     * Sets the shared value, raises the flag, and wakes the waiting Consumer.
     *
     * synchronized  → thread must own this object's monitor to enter.
     */
    public synchronized void set(int newValue) {
        this.value    = newValue;
        this.bChanged = true;
 
        System.out.printf("[Producer] Set value = %d  →  calling notify()%n", newValue);
 
        // Wake up ONE thread that is wait()-ing on this object's monitor.
        notify();
    }
 
    // ── CONSUMER side ────────────────────────────────────────────────────────
 
    /**
     * Called by the Consumer.
     * Blocks until the Producer has placed a new value (bChanged == true),
     * then reads and resets the flag.
     *
     * synchronized  → thread must own this object's monitor to enter.
     */
    public synchronized int get() {
 
        // while — not if — guards against spurious wakeups
        while (!bChanged) {
            System.out.println("[Consumer] Nothing ready yet — calling wait()");
            try {
                wait();  // ① releases the lock  ② suspends this thread
                         // ③ re-acquires lock when notify() wakes it
            } catch (InterruptedException e) {
                // Restore the interrupted status so callers can detect it,
                // then exit cleanly rather than silently swallowing the signal.
                Thread.currentThread().interrupt();
                System.out.println("[Consumer] Interrupted while waiting!");
                return -1;
            }
        }
 
        // At this point bChanged == true and we own the lock
        bChanged = false;   // reset flag so the next get() will wait again
        System.out.printf("[Consumer] Got value = %d%n", value);
        return value;
    }
}