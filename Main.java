import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader();

        Map<String, String> configValues = new HashMap<>();
        configValues = configReader.getConfig();

        // get config values
        String x = configValues.get("x");
        String y = configValues.get("y");
        String printVariation = configValues.get("print-variation");
        String taskDivisionScheme = configValues.get("task-division-scheme");

        // convert x and y from String to integer
        int noOfThreads = Integer.parseInt(x);
        int upperLimit = Integer.parseInt(y);

        // debug
        // System.out.println(noOfThreads);
        // System.out.println(upperLimit);

        CustomThread[] threads = new CustomThread[noOfThreads];

        // create x number of threads
        for (int i = 0; i < noOfThreads; i++) {
            threads[i] = new CustomThread(i);
            System.out.println("Thread " + threads[i].getThreadId() + " created.");
        }
        
        /* 
         * 
         * Printing Variation
         * 
         */

        // print immediately
        if(printVariation.equals("immediate")) {
            System.out.println(printVariation);
            // TODO
            // print results

        // wait until all threads are done then print everything
        } else if(printVariation.equals("wait")) {
            System.out.println(printVariation);
            // TODO
            // store results
            // print results
        }

        /*
         * 
         * Task Division Scheme
         * 
         */

        // straight division of search range. (ie for 1 - 1000 and 4 threads the division will be 1-250, 251-500, and so forth)
        if(taskDivisionScheme.equals("straight")) {
            System.out.println(taskDivisionScheme);
            // equally divide the numbers into the threads

            // size of each part
            int rangeSize = upperLimit / noOfThreads;

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

        // search is linear but the threads are for divisibility testing of individual numbers
        } else if(taskDivisionScheme.equals("linear")) {
            System.out.println(taskDivisionScheme);
            // TODO
            for(int i = 1; i <= upperLimit; i++) {
                threads[i].isPrimeNumber(i);
            }
        }
    public static void getTimeNow(String[] args) {
        LocalTime time = LocalTime.now();
        System.out.println("Current Time: " + time);
    }
}
