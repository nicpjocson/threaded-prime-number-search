// config-related
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

// timestamp
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main extends Thread {
    public static void main(String[] args) {
        // get config values
        Map<String, Integer> configValues = new HashMap<>();
        configValues = getConfig();
        int noOfThreads = configValues.get("x");
        int upperLimit = configValues.get("y");

        // create x number of threads
        Thread[] threads = new Thread[noOfThreads];

        // crate hashmap of the numbers to check
        // with info if its prime and the max divisor for checking divisibility (sqrt of the number)
        Map<Integer, Prime> numberMap = new HashMap<>();

        // initialize map with numbers, prime status, and max divisor (sqrt of number)
        for (int num = 1; num <= upperLimit; num++) {
            numberMap.put(num, new Prime(null, 1, (int) Math.sqrt(num)));
        }

        // rules??

        // for each number only check until its sqrt

        // once a number is prime do not check anymore

        // 

        // for (int i = 1; i <= upperLimit; i++) {
        //     Prime num = numberMap.get(i);

        //     // not done checking divisibility for a number
        //     if (num.isPrime == null && num.currDivisibility < num.maxDivisibility) {
        //         // whichever thread is not busy???
        //             // checkDivisibility(num, currDivisibility)
        //             // currDiv++
        //     }

        //     // done checking divisibility for a number
        //     if (num.isPrime == null && num.currDivisibility == num.maxDivisibility) {
        //         num.isPrime = false;
        //     }
        // }
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
    static class Prime {
        Boolean isPrime; // tracks if number is prime
        int currDivisibility; // counter from 1 to maxDivisibility, increments by 1
        int maxDivisibility; // tracks the max divisor (square root of the number)

        Prime(Boolean isPrime, int currDivisibility, int maxDivisibility) {
            this.isPrime = isPrime;
            this.currDivisibility = currDivisibility;
            this.maxDivisibility = maxDivisibility;
        }
    }
}
