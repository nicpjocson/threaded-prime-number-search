import java.util.*;

// config-related
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;

// timestamp
import java.text.SimpleDateFormat;

public class ImmediateLinear extends Thread {
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

        // initially set all numbers as prime
        for (int i = 1; i <= upperLimit; i++) {
            primeMap.put(i, true);
        }

        printProgramHeader();
        System.out.println("STARTED AT: " + getTimeNow());
        System.out.println("=================================================================");

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
            List<Callable<Void>> divisibilityTasks = new ArrayList<>();

            // handling 2 and 3
            // bc 2 and 3 do not enter the for loop below vv
            if (currNumber == 2 || currNumber == 3) {
                divisibilityTasks.add(() -> {
                    Thread currentThread = Thread.currentThread();

                    synchronized (threadMap) {
                        threadMap.put(currNumber, currentThread.getId());
                    }
                    return null;
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

                    return null;
                });
            }

            try {
                // NOTE: cannot make divisibilityTasks Runnable bc of this
                executor.invokeAll(divisibilityTasks);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

            // synchronized (primeMap) {
            if (primeMap.get(currNumber)) {
                System.out.println("[" + getTimeNow() + "] Thread " + threadMap.get(currNumber) + " found prime: " + currNumber); // ERROR HERE
            }
            // }
        }
        
        executor.shutdown();

        System.out.println("=================================================================");
        System.out.println("ENDED AT: " + getTimeNow());
        System.out.println("=================================================================");
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();

        long millis = now.getTime() % 1000;
        long nanos = System.nanoTime() % 1_000_000_000;

        return formatter.format(now) + String.format(".%03d%06d", millis, nanos);
    }

    public static void printProgramHeader() {
        System.out.println("=================================================================\n");
        System.out.println("Threaded Prime Number Search");
        System.out.println("Prints primes immediately, using linear task division.");
        System.out.println("Last modified: 7 February 2025 by Nicole Jocson S14\n");
        System.out.println("=================================================================");
    }
}
