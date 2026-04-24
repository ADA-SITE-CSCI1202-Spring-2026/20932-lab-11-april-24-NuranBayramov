public class DynamicScaling {
 
    public static void main(String[] args) throws InterruptedException {
 
        // ── 1. RETRIEVE CORE COUNT ──────────────────────────────────────────
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("=== Dynamic Scaling & Hardware Awareness ===");
        System.out.println("Logical processors detected: " + cores);
        System.out.println();
 
        // ── 2. BENCHMARK WITH 1 THREAD ──────────────────────────────────────
        long singleThreadTime = runBenchmark(1);
        System.out.printf("1 thread    → %,d ms%n", singleThreadTime);
 
        // ── 3. BENCHMARK WITH MAX THREADS (one per logical core) ───────────
        long multiThreadTime = runBenchmark(cores);
        System.out.printf("%d threads  → %,d ms%n", cores, multiThreadTime);
 
        // ── 4. SPEEDUP SUMMARY ──────────────────────────────────────────────
        System.out.println();
        if (multiThreadTime > 0) {
            double speedup = (double) singleThreadTime / multiThreadTime;
            System.out.printf("Speedup: %.2fx  (theoretical max = %dx)%n", speedup, cores);
        }
    }
 
    
    private static long runBenchmark(int threadCount) throws InterruptedException {
 
        // ── 2a. Allocate Thread array matching the requested count ──────────
        Thread[] threads = new Thread[threadCount];
 
        // ── 2b. Initialise each Thread with a unique MathTask ───────────────
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new MathTask(i), "worker-" + i);
        }
 
        // ── 2c. Start timing, then spawn all threads ─────────────────────────
        long startTime = System.currentTimeMillis();
 
        for (Thread t : threads) {
            t.start();
        }
 
        // ── 2d. Join every thread — Main blocks until ALL finish ─────────────
        for (Thread t : threads) {
            t.join();   // crucial: ensures Main waits for the full workload
        }
 
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}