import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import CustomThread;

public class Main {
    public static void main(String[] args) {
        Map<String, String> configValues = new HashMap<>();
        configValues = getConfig();

        // get config values
        String x = configValues.get("x");
        String y = configValues.get("y");
        String printVariation = configValues.get("print-variation");
        String taskDivisionScheme = configValues.get("task-division-scheme");

        // convert x and y from String to integer
        int noOfThreads = Integer.parseInt(x);
        int upperLimit = Integer.parseInt(y);

        // create x number of threads
        CustomThread[] threads = new CustomThread[noOfThreads];
        for (int i = 0; i < noOfThreads; i++) {
            threads[i] = new CustomThread(i);
            threads[i].start();
            System.out.println("Thread " + threads[i].getThreadId() + " started.");
        }
        
        /* 
         * 
         * Printing Variation
         * 
         */

        // print immediately
        if(printVariation.equals("immediate")) {
            // print thread id and timestamp also
            if(taskDivisionScheme.equals("straight")) {
                straightTaskDivision(threads, upperLimit, noOfThreads);
            } else if(taskDivisionScheme.equals("linear")) {
                linearTaskDivision(threads, upperLimit);
            }

        // wait until all threads are done then print everything
        } else if(printVariation.equals("wait")) {
            System.out.println(printVariation);
            // TODO
            // check if all threads are done
            // print results
            if(taskDivisionScheme.equals("straight")) {
                straightTaskDivision(threads, upperLimit, noOfThreads);
                // wait for all threads to finish then print results
                waitAndPrint(threads, noOfThreads);

            } else if(taskDivisionScheme.equals("linear")) {
                linearTaskDivision(threads, upperLimit);
                // wait for all threads to finish then print results
                waitAndPrint(threads, noOfThreads);
            }
        }

        /*
            * 
            * Task Division Scheme
            * 
            */

        // straight division of search range. (ie for 1 - 1000 and 4 threads the division will be 1-250, 251-500, and so forth)
        if(taskDivisionScheme.equals("straight")) {
            

        // search is linear but the threads are for divisibility testing of individual numbers
        } else if(taskDivisionScheme.equals("linear")) {
            System.out.println(taskDivisionScheme);
            // TODO
            for(int i = 1; i <= upperLimit; i++) {
                threads[i].isPrimeNumber(i);
            }
        }    
    }

    public static Map<String, String> getConfig() {
        String filePath = "config.txt";
        Map<String, String> configValues = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    configValues.put(parts[0], parts[1]);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading config file: " + e.getMessage());
        }

        // debugging
        // String x = configValues.get("x");
        // String y = configValues.get("y");
        // String printVariation = configValues.get("print-variation");
        // String taskDivisionScheme = configValues.get("task-division-scheme");
        
        // System.out.println("x = " + x);
        // System.out.println("y = " + y);
        // System.out.println("print-variation = " + printVariation);
        // System.out.println("task-division-scheme = " + taskDivisionScheme);

        return configValues;
    }

    /*
     * 
     * Printing functions
     * 
     */
    public static void waitAndPrint(CustomThread[] threads, int noOfThreads) {
        for (int i = 0; i < noOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All threads finished processing.");
    }

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

    public static void linearTaskDivision(CustomThread[] threads, int upperLimit) {
        for (int i = 1; i <= upperLimit; i++) {
            threads[i].isPrimeNumber(i);
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
