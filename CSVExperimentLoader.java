import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
            if (headerLine == null) {
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

                try {
                    switch (type) {
                        case "MECHANICS" -> {
                            double mass = Double.parseDouble(get(fields, col, "mass", rowNum));
                            double gravity = Double.parseDouble(get(fields, col, "gravity", rowNum));
                            activities.add(new MechanicsExperiment(activityId, activityName, description,
                                    maxDurationMinutes, mass, gravity));
                        }
                        case "PROJECTILE" -> {
                            double mass = Double.parseDouble(get(fields, col, "mass", rowNum));
                            double gravity = Double.parseDouble(get(fields, col, "gravity", rowNum));
                            double initialVelocity = Double.parseDouble(get(fields, col, "initialVelocity", rowNum));
                            double launchAngle = Double.parseDouble(get(fields, col, "launchAngle", rowNum));
                            double initialHeight = Double.parseDouble(get(fields, col, "initialHeight", rowNum));
                            activities.add(new ProjectileMotionExperiment(activityId, activityName, description,
                                    maxDurationMinutes, mass, gravity, initialVelocity, launchAngle, initialHeight));
                        }
                        case "CIRCUIT" -> {
                            double voltage = Double.parseDouble(get(fields, col, "voltage", rowNum));
                            String[] resistanceTokens = get(fields, col, "resistances", rowNum).split(";");
                            double[] resistances = new double[resistanceTokens.length];
                            for (int i = 0; i < resistanceTokens.length; i++) {
                                resistances[i] = Double.parseDouble(resistanceTokens[i].trim());
                            }

                            String circuitTypeRaw = get(fields, col, "circuitType", rowNum);
                            CircuitType circuitType;
                            try {
                                circuitType = CircuitType.valueOf(circuitTypeRaw.toUpperCase());
                            } catch (IllegalArgumentException e) {
                                System.out.println("[Invalid Parameter] Row " + rowNum + " -> circuitType: Invalid circuit type \""
                                        + circuitTypeRaw + "\" — please check that circuitType is exactly SERIES or PARALLEL.");
                                continue;
                            }

                            activities.add(new CircuitExperiment(activityId, activityName, description,
                                    maxDurationMinutes, voltage, resistances, circuitType));
                        }
                        default -> System.out.println("[Warning] Row " + rowNum + " skipped: unrecognized type \"" + type + "\".");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[Warning] Row " + rowNum + " skipped: invalid numeric value.");
                }
            }
        } catch (IOException e) {
            System.out.println("[Error] Could not read file: " + filePath);
        }

        return activities;
    }

    private String get(String[] fields, Map<String, Integer> col, String columnName, int rowNum) {
        Integer idx = col.get(columnName);
        if (idx == null || idx >= fields.length) {
            return "";
        }
        return fields[idx].trim();
    }
}