import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {
    public Map<String, String> getConfig() {
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
}
