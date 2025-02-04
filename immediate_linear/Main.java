// config-related
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

// timestamp
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        // get config values
        Map<String, Integer> configValues = new HashMap<>();
        configValues = getConfig();
        int noOfThreads = configValues.get("x");
        int upperLimit = configValues.get("y");

        // create x number of threads
        Thread[] threads = new Thread[noOfThreads];

        // wah

        // rules??

        // for each number only check until its sqrt

        // once a number is prime do not check anymore

        // 
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
    //
    
    /*
     * 
     * Misc
     * 
     */
    //
}
