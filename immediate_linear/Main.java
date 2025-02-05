import java.util.*;

// config-related
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
// import java.util.List;
// import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// timestamp
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main extends Thread {
    public static void main(String[] args) {
        // get config values
        Map<String, Integer> configValues = new HashMap<>();
        configValues = getConfig();
        int numThreads = configValues.get("x"); // NOTE" NOT USED
        int upperLimit = configValues.get("y");

        // create list of threads
        // List<Thread> threads = new ArrayList<>();
        // TESTING
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // // crate hashmap of the numbers to check
        // // with info if its prime and the max divisor for checking divisibility (sqrt of the number)
        // Map<Integer, MyNumber> numberMap = new HashMap<>();

        // // initialize map with numbers, prime status, and max divisor (sqrt of number)
        // for (int num = 1; num <= upperLimit; num++) {
        //     numberMap.put(num, new MyNumber(null, 2, (int) Math.sqrt(num)));
        // }

        // hashmap storing the numbers and if they are prime
        Map<Integer, Boolean> numberMap = new HashMap<>();

        // for each number, check if prime
        for (int i = 1; i <= upperLimit; i++) {
            // get current number and its status
            int currNumber = i;
            Boolean isPrime = numberMap.get(currNumber);

            if (currNumber <= 1) {
                continue;
            }

            // // DETERMINED if prime/composite
            // if (isPrime != null) {
            //     continue;
            // }

            for (int divisor = 2; divisor <= Math.sqrt(currNumber); divisor++) {
                int currDivisor = divisor;
                
                executor.submit(() -> {
                    if (checkDivisibility(currNumber, currDivisor)) {
                        // set number as not prime or composite
                        synchronized (numberMap) {
                            numberMap.put(currNumber, false);
                        }
                    }
                });
            }
        }

            // // UNDETERMINED if prime/composite
            // // but DONE checking divisibility for current number
            // if (num.isPrime == null && num.currDivisor > num.maxDivisor) {
            //     // then set isPrime to TRUE
            //     num.isPrime = true;
            // }

            // // UNDETERMINED if prime/composite
            // // and NOT DONE checking divisibility for current number
            // if (num.isPrime == null && num.currDivisor < num.maxDivisor) {
            //     // use a thread to check for its
            //     Thread thread = new Thread(() -> {
            //         checkDivisibility(currNumber, num.currDivisor);
            //         num.currDivisor++;
            //     });
            //     threads.add(thread);
            //     thread.start();

            //     // if returns true
            //     // set isPrime = true;

            //     // if returns false
            //     // check divisibilty of 
            // }

            // executor.submit(() -> {
            //     // Check divisibility
            //     if (currNumber % num.currDivisor == 0) {
            //         synchronized (lock) {
            //             // If divisible, mark as composite
            //             if (num.isPrime == null) {
            //                 num.isPrime = false;
            //             }
            //         }
            //     }
            // });

            // executor.submit(() -> {
            //     while (num.currDivisor <= num.maxDivisor && num.isPrime == null) {
            //         if (currNumber % num.currDivisor == 0) {
            //             num.isPrime = false;
            //             break;
            //         }
            //         num.currDivisor++;
            //     }
            //     if (num.currDivisor > num.maxDivisor) {
            //         num.isPrime = true;
            //     }
            // });

        // // Shutdown the executor
        // executor.shutdown();
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
    public static boolean checkDivisibility (int dividend, int divisor) {
        if (dividend % divisor != 0) {
            return false;
        }

        return true;
    }
    
    /*
     * 
     * Misc
     * 
     */
    // static class MyNumber {
    //     Boolean isPrime; // tracks if number is prime
    //     int currDivisor;
    //     int maxDivisor; // tracks the max divisor (square root of the number)

    //     MyNumber(Boolean isPrime, int currDivisor, int maxDivisor) {
    //         this.isPrime = isPrime;
    //         this.currDivisor = currDivisor;
    //         this.maxDivisor = maxDivisor;
    //     }
    // }
}
