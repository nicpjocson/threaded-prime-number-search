import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Thread {
    public static void main(String[] args) {
        // get config values
        Map<String, Integer> configValues = new HashMap<>();
        configValues = getConfig();
        int numThreads = configValues.get("x");
        int upperLimit = configValues.get("y");

        // create list of threads
        List<Thread> threads = new ArrayList<>();

        // store list of prime numbers
        List<List<Integer>> primeNums = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            primeNums.add(new ArrayList<>());
        }

        // equally divide the numbers into the threads
        int rangeSize = upperLimit / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int threadId = i; // for readability
            // current partition min
            int min = i * rangeSize + 1;
            // current partition max
            int max = (i == numThreads - 1) ? upperLimit : (i + 1) * rangeSize;

            final int finalMin = min;
            final int finalMax = max;

            // create and start the thread
            Thread thread = new Thread(() -> {
                for (int num = finalMin; num <= finalMax; num++) {
                    if (isPrime(num)) {
                        synchronized (primeNums.get(threadId)) {
                            primeNums.get(threadId).add(num);
                        }
                        // System.out.println("Thread " + threadId + " found prime: " + num + " @" + getTimeNow());
                    }
                }
                System.out.println("[Thread " + threadId + "] Found primes: " + primeNums.get(threadId) + "\n");
                // test
                // synchronized (Main.class) {
                // System.out.println("[Thread " + threadId + "] Found primes: " + primeNums.get(threadId) + "\n");
                // }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 
     * Config functions
     * 
     */
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
    public static String getTimeNow() {
        // get current time
        LocalTime time = LocalTime.now();
        // format time as string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String strTime = time.format(formatter);

        return strTime;
    }

    /*
     * 
     * Search functions
     * 
     */
    // prime number definition... :|
    //
    // a whole number greater than 1 
    // that cannot be exactly divided 
    // by any whole number other than itself and 1
    public static boolean isPrime(int num) {
        if (num == 1) return false;
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
