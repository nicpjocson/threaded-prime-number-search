import java.util.*;

// config-related
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
// import java.util.List;
// import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        Map<Integer, Boolean> numberMap = new HashMap<>();

        // initially set all numbers as prime
        for (int i = 1; i <= upperLimit; i++) {
            numberMap.put(i, true);
        }

        // for each number, check if prime
        for (int i = 1; i <= upperLimit; i++) {
            // get current number and its status
            int currNumber = i;

            // set numbers 1 or less as not prime
            if (currNumber <= 1) {
                // synchronized (numberMap) {
                    numberMap.put(currNumber, false);
                // }
                continue;
            }

            // create list of callables (1 callable = 1 divisor)
            List<Callable<Void>> divisibilityTasks = new ArrayList<>();

            // check current number's divisibility until its square root
            for (int j = 2; j <= Math.sqrt(currNumber); j++) {
                int currDivisor = j;

                // do thread task
                divisibilityTasks.add(() -> {
                    // if the number is divisible
                    if (currNumber % currDivisor == 0) {
                        // mark number as not prime / composite
                        numberMap.put(currNumber, false);
                    }
                    return null;
                });

                // // stop checking divisibility if current number is composite
                // if (!numberMap.get(currNumber)) {
                //     break;
                // }
                
                // executor.submit(() -> {
                //     synchronized (numberMap) {
                //         // if current number is divisible by at least one divisor then its composite
                //         if (currNumber % currDivisor == 0) {
                //             // set number as not prime
                //             numberMap.put(currNumber, false);
                //         }
                //     }
                // });
            }

            try {
                executor.invokeAll(divisibilityTasks);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

            // synchronized (numberMap) {
            if (numberMap.get(currNumber)) {
                System.out.println("[" + getTimeNow() + "] found prime: " + currNumber);
                // Thread " + thread + " 
            }
            // }
        }
        
        executor.shutdown();
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

    /*
     * 
     * Search functions
     * 
     */
    // public static boolean isDivisible (int dividend, int divisor) {
    //     if (dividend % divisor != 0) {
    //         return false;
    //     }

    //     return true;
    // }
    
    /*
     * 
     * Misc
     * 
     */
    //
}
