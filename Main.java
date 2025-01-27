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


        // create x number of threads
        for(int i = 0; i < noOfThreads; i++) {
            System.out.println("created thread");
            // TODO
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

        // wait until all threads are done then print everything
        } else if(printVariation == "wait") {
            System.out.println(printVariation);
            // TODO
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

        // search is linear but the threads are for divisibility testing of individual numbers
        } else if(taskDivisionScheme == "linear") {
            System.out.println(taskDivisionScheme);
            // TODO
        }
    }
}
