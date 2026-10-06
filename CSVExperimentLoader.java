import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import java.util.Map; 

public class CSVExperimentLoader {
    // Header : type,activityId,activityNAme,descriptiono,maxDurationMinutes,mass,gravity,initialVelocity,launchAngle,initialHeight,voltage,resistances,circuitType
    // Unused colums for a given type are left blank. resistances uses ";" to seperate values.

    public List<LabActivity> loadFromCSV(String filePath) {
        List<LabActivity> activities = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            if (haederLine == null) {
                System.out.println("[Error] CSV file is empty.");
                return activities;
            }

            Map<String, Integer> col = new HashMap<>();
            String[] headers = headerLine.split(",", -1);
            for (int i = 0; i < headers.length; i++) {
                col.put(headers[i].trim(), i);
            }

            String line;
            int rowNum = 1;

            while ((line = reader.readLine()) != null) {
                rowNum++;
                if(line.isBlank()) {
                    continue;
                }

                String[] fields = line.split(",", -1);
                if (fields.length < headers.length) {
                    System.out.println("[Warning] Row " + rowNum + " skipped: expected " + headers.length + " columns, found " + fields.length + ".");
                    continue;
                }

                String type = get(fields, col, "type", rowNum).toUpperCase();
                String activityId = get(fields, col, "activityId", rowNum);
                String activityName = get(fields, col, "activityName", rowNum);
                String description = get(fields, col, "description", rowNum);

                int maxDurationMinutes;
                try {
                    maxDurationMinutes = Integer.parseInt(get(fields, col, "maxDurationMinutes", rowNum));
                } catch (NumberFormatException e) {
                    System.out.println("[Warning] Row " + rowNum + " skipped: invalid maxDurationMinutes.");
                    continue;
                }
            }
        }
    }
}