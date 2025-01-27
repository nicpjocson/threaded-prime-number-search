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
            System.out.println("created thread");
        }
        
        /* 
         * 
         * Printing Variation
         * 
         */

        // print immediately
        if(printVariation == "immediate") {
            System.out.println(printVariation);
            // TODO
            // print results

        // wait until all threads are done then print everything
        } else if(printVariation == "wait") {
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
        if(taskDivisionScheme == "straight") {
            System.out.println(taskDivisionScheme);
            // TODO
            // equally divide the numbers into the threads

        // search is linear but the threads are for divisibility testing of individual numbers
        } else if(taskDivisionScheme == "linear") {
            System.out.println(taskDivisionScheme);
            // TODO
        }
    }
}
