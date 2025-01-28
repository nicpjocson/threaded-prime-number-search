import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Main extends Thread {
    public static void main(String[] args) {
        Map<String, Integer> configValues = new HashMap<>();
        configValues = getConfig();

        // get config values
        int x = configValues.get("x");
        int y = configValues.get("y");

        // create x number of threads
        CustomThread[] threads = new CustomThread[x];
        for (int i = 0; i < x; i++) {
            threads[i] = new CustomThread(i);
            threads[i].start();
            System.out.println("Thread " + threads[i].getThreadId() + " started.");
        }
        
        
    }

    public static Map<String, Integer> getConfig() {
        String filePath = "config.txt";
        Map<String, Integer> configValues = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    configValues.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading config file: " + e.getMessage());
        }

        // debugging
        // int x = configValues.get("x");
        // int y = configValues.get("y");
        
        // System.out.println("x = " + x);
        // System.out.println("y = " + y);

        return configValues;
    }

    /*
     * 
     * Printing functions
     * 
     */

    /*
     * 
     * Task Division functions
     * 
     */
    public static void straightTaskDivision(CustomThread[] threads, int upperLimit, int noOfThreads) {
        int rangeSize = upperLimit / noOfThreads;
                            
        // equally divide the numbers into the threads
        for (int i = 0; i < noOfThreads; i++) {
            // current partition min
            int min = i * rangeSize + 1;
            // current partition max
            int max = (i + 1) * rangeSize;

            // ensure the max value is exactly y
            if (i == noOfThreads - 1) {
                max = upperLimit;
            }

            // TODO
            threads[i].searchInRange(min, max);
        }
    }

    /*
     * 
     * Helper functions
     * 
     */
    public static void getTimeNow(String[] args) {
        LocalTime time = LocalTime.now();
        System.out.println("Current Time: " + time);
    }
}
