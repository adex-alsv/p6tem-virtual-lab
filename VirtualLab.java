import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class VirtualLab {
    private String labName;
    private LabActivity[] activities;
    private int activityCount;

    public VirtualLab(String labName, int capacity) {
        this.labName = labName;
        this.activities = new LabActivity[capacity];
        this.activityCount = 0;
    }

    public void addActivity(LabActivity a) {
        if (activityCount < activities.length) {
            activities[activityCount] = a;
            activityCount++;
        }
    }

    public void runAll() {
        for (int i = 0; i < activityCount; i++) {
            LabActivity activity = activities[i];
            activity.startActivity();
            try {
                activity.runSimulation();
                activity.endActivity();
                activity.displayResults();
            } catch (InvalidParameterException e){
                activity.status = "ERROR";
                System.out.println("[Invalid Parameter] " + activity.getActivityName() + " -> " + e.getParameterName() + ": " + e.getMessage());
            } catch (SimulationException e) {
                activity.status = "ERROR";
                System.out.println("[Simulation Error #" + e.getErrorCode() + "] " + activity.getActivityName() + " -> " + e.getMessage());
            }
        }
    }
    
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(labName).append(" Report ===\n");
        for (int i = 0; i < activityCount; i++){
            LabActivity a = activities[i];
            sb.append("[").append(a.getActivityId()).append("] ").append(a.getActivityName()).append(" - ").append(a.getStatus());
            
            if (a.getStatus().equals("COMPLETED")) {
                sb.append(" | ").append(a.calculateResults());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void exportReportToCSV(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("activityId,activityName,status,results");
            for (int i = 0; i < activityCount; i++) {
                LabActivity a = activities[i];
                String results = a.getStatus().equals("COMPLETED") ? a.calculateResults() : "N/A";
                writer.println(escape(a.getActivityId()) + "," + escape(a.getActivityName()) + "," + escape(a.getStatus()) + "," + escape(results));
            }
        } catch (IOException e) {
            System.out.println("[Error] Could not write file: "+ filePath);
        }
    }

    // Quotes a field so commas inside results (e.g. "Range = 5, Max Height = 2") don't break columns
    private String escape(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    } 
}