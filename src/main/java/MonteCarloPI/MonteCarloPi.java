package MonteCarloPI;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class MonteCarloPi {

    static final long NUM_POINTS = 50_000_000L;
    static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    public static void main(String[] args) throws InterruptedException, ExecutionException
    {

        System.out.println("Single threaded started: ");
        long startTime = System.nanoTime();
        double piWithoutThreads = estimatePiWithoutThreads(NUM_POINTS);
        long endTime = System.nanoTime();
        System.out.println("Monte Carlo Pi Approximation (single thread): " + piWithoutThreads);
        System.out.println("Time taken (single threads): " + (endTime - startTime) / 1_000_000 + " ms");


        System.out.printf("Multi threaded  started: (your device has %d logical threads)\n",NUM_THREADS);
        startTime = System.nanoTime();
        double piWithThreads = estimatePiWithThreads(NUM_POINTS, NUM_THREADS);
        endTime = System.nanoTime();
        System.out.println("Monte Carlo Pi Approximation (Multi-threaded): " + piWithThreads);
        System.out.println("Time taken (Multi-threaded): " + (endTime - startTime) / 1_000_000 + " ms");

    }

    public static double estimatePiWithoutThreads(long numPoints)
    {
        long pointsInsideCircle = 0;
        Random random = new Random();

        for (long i = 0; i < numPoints; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            if (x * x + y * y <= 1) {
                pointsInsideCircle++;
            }
        }
        return 4.0 * pointsInsideCircle / numPoints;
    }

    public static double estimatePiWithThreads(long numPoints, int numThreads) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        AtomicLong pointsInsideCircle = new AtomicLong(0);
        long pointsPerThread = numPoints / numThreads;
        long remainingPoints = numPoints % numThreads;
        for (int j = 0; j < numThreads; j++) {
            long threadPoints = pointsPerThread + (j < remainingPoints ? 1 : 0);
            executor.execute(() -> {
                Random random = new Random();
                long insideCircle = 0;

                for (long i = 0; i < threadPoints; i++) {
                    double x = random.nextDouble();
                    double y = random.nextDouble();

                    if (x * x + y * y <= 1) {
                        insideCircle++;
                    }
                }
                pointsInsideCircle.addAndGet(insideCircle);
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(10);
        }

        return 4.0 * pointsInsideCircle.get() / numPoints;
    }

}