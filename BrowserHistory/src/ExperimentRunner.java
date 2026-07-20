import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ExperimentRunner {
    public static void main(String[] args) {
        int capacity = 10000;
        int numRequests = 100000;
        
        LRUCache<Integer, String> cache = new LRUCache<>(capacity);
        Random random = new Random();
        
        // Arrays to store metrics in-memory to prevent IO overhead during measurement
        long[] executionTimes = new long[numRequests];
        String[] operationTypes = new String[numRequests];

        System.out.println("Starting experiment with " + numRequests + " requests...");
        
        // Pre-fill the cache a bit to simulate steady state
        for (int i = 0; i < capacity; i++) {
            cache.put(i, "Value" + i);
        }

        int keyRange = capacity * 2; // Create some cache misses
        
        for (int i = 0; i < numRequests; i++) {
            int key = random.nextInt(keyRange);
            boolean isPut = random.nextDouble() < 0.3; // 30% PUT, 70% GET
            
            long startTime = System.nanoTime();
            if (isPut) {
                cache.put(key, "Value" + key);
                operationTypes[i] = "PUT";
            } else {
                cache.get(key);
                operationTypes[i] = "GET";
            }
            long endTime = System.nanoTime();
            
            executionTimes[i] = endTime - startTime;
        }
        
        System.out.println("Experiment finished. Writing results to file...");
        
        try (FileWriter writer = new FileWriter("results.csv")) {
            writer.write("RequestID,OperationType,Time_ns\n");
            for (int i = 0; i < numRequests; i++) {
                writer.write((i + 1) + "," + operationTypes[i] + "," + executionTimes[i] + "\n");
            }
            System.out.println("Successfully saved results to results.csv.");
            ChartGenerator.generateAndOpenChart("results.csv", 1000);
        } catch (IOException e) {
            System.out.println("Error writing to CSV: " + e.getMessage());
        }
    }
}
