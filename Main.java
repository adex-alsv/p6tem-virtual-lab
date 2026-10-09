import java.util.List; 

public class Main {
    public static void main(String[] args) {
        VirtualLab lab = new VirtualLab("P6tem Virtual Lab", 20);
        CSVExperimentLoader loader = new CSVExperimentLoader();

        List<LabActivity> loaded = loader.loadFromCSV("experiments.csv");
        for (LabActivity a : loaded) {
            lab.addActivity(a);
        }

        lab.runAll();
        System.out.println();
        System.out.println(lab.generateReport());
        lab.exportReportToCSV("session_report.csv");
    }
}