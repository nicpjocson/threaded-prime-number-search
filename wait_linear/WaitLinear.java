import java.util.*;

// config-related
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// timestamp
import java.text.SimpleDateFormat;

public class WaitLinear extends Thread {
    public static void main(String[] args) {
        // get config values
        Map<String, Integer> configValues = new HashMap<>();
        configValues = getConfig();
        int numThreads = configValues.get("x");
        int upperLimit = configValues.get("y");

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // hashmap storing the numbers and if they are prime
        Map<Integer, Boolean> primeMap = new HashMap<>();

        // map for storing thread ids associated with each number
        Map<Integer, Long> threadMap = new HashMap<>();

        // TEST
        List<Thread> threadList = new ArrayList<>();

        // initially set all numbers as prime
        for (int i = 1; i <= upperLimit; i++) {
            primeMap.put(i, true);
        }

        System.out.println("STARTED AT: " + getTimeNow() + "\n");

        // for each number, check if prime
        for (int i = 1; i <= upperLimit; i++) {
            // get current number and its status
            int currNumber = i;

            // set numbers 1 or less as not prime
            if (currNumber <= 1) {
                // synchronized (primeMap) {
                primeMap.put(currNumber, false);
                // }
                continue;
            }

            // create list of callables (1 callable = 1 divisor)
            List<Runnable> divisibilityTasks = new ArrayList<>();

            // handling 2 and 3
            // bc 2 and 3 do not enter the for loop below vv
            if (currNumber == 2 || currNumber == 3) {
                divisibilityTasks.add(() -> {
                    Thread currentThread = Thread.currentThread();

                    synchronized (threadMap) {
                        threadMap.put(currNumber, currentThread.getId());
                    }
                });
            }

            // check current number's divisibility until its square root
            for (int j = 2; j <= Math.sqrt(currNumber); j++) {
                int currDivisor = j;

                // do thread task
                divisibilityTasks.add(() -> {
                    Thread currentThread = Thread.currentThread();

                    // if the number is divisible
                    if (currNumber % currDivisor == 0) {
                        // mark number as not prime / composite
                        synchronized (primeMap) {
                            primeMap.put(currNumber, false);
                        }
                    }

                    synchronized (threadMap) {
                        // NOTE: https://stackoverflow.com/questions/1262051/should-java-thread-ids-always-start-at-0
                        threadMap.put(currNumber, currentThread.getId());
                    }
                });
            }

            for (Runnable task : divisibilityTasks) {
                Thread thread = new Thread(task);
                thread.start();
                threadList.add(thread);
            }
        }

        // wait for all threads to finish
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        // after all tasks are done, print prime numbers
        System.out.println("Found prime numbers: ");
        for (int i = 1; i <= upperLimit; i++) {
            if (primeMap.get(i)) {
                System.out.print(i + " ");
            }
        }
        
        executor.shutdown();

        System.out.println("\n\nENDED AT: " + getTimeNow());
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
                    // NON-INTEGER PARAMETER
                    } catch (NumberFormatException e) {
                        System.err.println("Error in `config.txt`: '" + parts[1] + "' is not a valid integer.");
                        System.exit(1); // exit if the condition not met
                    }
                // NO PARAMETER
                } else {
                    System.err.println("Error in `config.txt`: Missing '" + line + "'");
                    System.exit(1); // exit if the condition not met
                }
            }
        // OTHER
        } catch (IOException e) {
            System.err.println("Error reading `config.txt`: " + e.getMessage());
            System.exit(1); // exit if the condition not met
        }

        int x = configValues.get("x");
        int y = configValues.get("y");
        // System.out.println("x = " + x);
        // System.out.println("y = " + y);

        // VALIDATION: x <= y
        if (x > y) {
            System.err.println("Error reading `config.txt`: Value of 'x' must be less than or equal to 'y'. \nx = " + x + ", y = " + y);
            System.exit(1); // exit if the condition not met
        }

        // VALIDATION: x >= 1
        if (x < 1) {
            System.err.println("Error reading `config.txt`: Value of 'x' must be at least 1. \nx = " + x);
            System.exit(1); // exit if the condition not met
        }

        return configValues;
    }

    /*
     * 
     * Printing functions
     * 
     */
    public static String getTimeNow() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();

        return formatter.format(now);
    }
}
