import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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
        // store list of all prime numbers
        List<Integer> primeNums = new ArrayList<>();

        // equally divide the numbers into the threads
        int rangeSize = upperLimit / numThreads;

        for (int i = 0; i < numThreads; i++) {
            // current partition min
            int min = i * rangeSize + 1;
            // current partition max
            int max = (i == numThreads - 1) ? upperLimit : (i + 1) * rangeSize;

            final int finalMin = min;
            final int finalMax = max;

            // create and start new thread
            Thread thread = new Thread(() -> {
                for (int num = finalMin; num <= finalMax; num++) {
                    if (isPrime(num)) {
                        // System.out.println("[" + getTimeNow() + "] Thread " + threadId + " found prime: " + num);
                        // mutual exclusion
                        // multiple threads modify primeNums simultaneously
                        synchronized (primeNums) {
                            primeNums.add(num);
                        }
                    }
                }
                // lalala test
                // synchronized (Main.class) {
                // System.out.println("[Thread " + threadId + "] Found primes: " + primeNums.get(threadId) + "\n");
                // }
            });
            threads.add(thread);
            thread.start();
        }

        // wait for all threads
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(primeNums);
        System.out.println("Found prime numbers: " + primeNums + "\n");
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
                    try {
                        configValues.put(parts[0], Integer.parseInt(parts[1]));
                    } catch (NumberFormatException e) {
                        System.err.println("Error in `config.txt`: '" + parts[1] + "' is not a valid integer.");
                    }
                } else {
                    System.err.println("Error in `config.txt`: Missing '" + line + "'");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading `config.txt`: " + e.getMessage());
        }

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
    public static boolean isPrime(int n) {
        
        // eliminate numbers less than or equal to 1
        if (n <= 1) {
            return false;
        }

        if (n == 2 || n == 3) {
            return true;
        }
        
        // check for divisibility by 2 and 3
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }

        // for numbers greater than 3
        // check divisibility starting from 5
        for (int i = 5; i <= Math.sqrt(n); i = i + 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        
        return true;
    }
}
