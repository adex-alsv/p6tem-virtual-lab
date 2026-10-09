public class MechanicsExperiment extends LabActivity {
    protected double mass;
    protected double gravity;
    private double weightResults;

    public MechanicsExperiment(String activityID, String activityName, String description, int maxDurationMinutes, double mass, double gravity) {
        super(activityID, activityName, description, maxDurationMinutes);
        this.mass = mass;
        this.gravity = gravity;
    }

    @Override
    public void runSimulation() throws InvalidParameterException {
        if (mass <= 0) {
            throw new InvalidParameterException("mass", "Mass must be greater than 0.");
        }
        weightResults = mass * gravity;
    }

    @Override
    public String calculateResults() {
        return String.format("Weight = %.2f N", weightResults);
    }
}