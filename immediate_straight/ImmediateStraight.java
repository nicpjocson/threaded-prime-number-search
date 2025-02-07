/*
 * Threaded Prime Number Search Program
 */
// config-related
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

// timestamp
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImmediateStraight extends Thread {
    public static void main(String[] args) {
        // get config values
        Map<String, Integer> configValues = new HashMap<>();
        configValues = getConfig();
        int numThreads = configValues.get("x");
        int upperLimit = configValues.get("y");

        // create x number of threads
        Thread[] threads = new Thread[numThreads];

        // equally divide the numbers into the threads
        int rangeSize = upperLimit / numThreads;

        printProgramHeader();
        System.out.println("STARTED AT: " + getTimeNow());
        System.out.println("ENDED AT: " + getTimeNow());
        System.out.println("=================================================================");

        for (int i = 0; i < numThreads; i++) {
            int threadId = i; // for readability
            // current partition min
            int min = i * rangeSize + 1;
            // current partition max
            int max = (i == numThreads - 1) ? upperLimit : (i + 1) * rangeSize;

            final int finalMin = min;
            final int finalMax = max;

            // create and start the thread
            threads[i] = new Thread(() -> {
                for (int num = finalMin; num <= finalMax; num++) {
                    if (isPrime(num)) {
                        System.out.println("[" + getTimeNow() + "] Thread " + threadId + " found prime: " + num);
                    }
                }
            });
            threads[i].start();
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
        System.out.println("Prints primes immediately, using straight task division.");
        System.out.println("Last modified: 7 February 2025 by Nicole Jocson S14\n");
        System.out.println("=================================================================");
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
        
        if (n <= 1) {
            return false;
        }

        if (n == 2 || n == 3) {
            return true;
        }
        
        // check for multiples of 2 and 3
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }

        for (int i = 5; i <= Math.sqrt(n); i = i + 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        
        return true;
    }
}
