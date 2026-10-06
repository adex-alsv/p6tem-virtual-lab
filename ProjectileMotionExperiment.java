public class ProjectileMotionExperiment extends MechanicsExperiment {
    private double initialVelocity;
    private double launchAngle;
    private double initialHeight;
    private double rangeResult;
    private double maxHeightResult;
    private double timeOfFlightResult;

    public ProjectileMotionExperiment(String activityID, String activityName, String description, int maxDurationMinutes, double mass, double gravity, double initialVelocity, double launchAngle, double initialHeight) {
        super(activityID, activityName, description, maxDurationMinutes, mass, gravity);
        this.initialVelocity = initialVelocity;
        this.launchAngle = launchAngle;
        this.initialHeight = initialHeight;
    }

    @Override 
    public void runSimulation() throws InvalidParameterException {
        if (initialVelocity <= 0) {
            throw new InvalidParameterException("initialVelocity", "Initial velocity must be greater than 0.");
        }
        if (launchAngle < 0 || launchAngle > 90) {
            throw new InvalidParameterException("launchAngle", "Launch angle must be between 0 and 90 degrees.");
        }

        double angleRad = Math.toRadians(launchAngle);
        double vx = initialVelocity * Math.cos(angleRad);
        double vy = initialVelocity * Math.sin(angleRad);

        timeOfFlightResult = (vy + Math.sqrt(vy * vy + 2 * gravity * initialHeight)) / gravity;
        rangeResult = vx * timeOfFlightResult;
        maxHeightResult = initialHeight + (vy + vy) / (2 * gravity);
    }

    @Override
    public String calculateResults() {
        return "Time of Flight = " + timeOfFlightResult + " s, " + "Range = " + rangeResult + " m, " + "Max Height = " + maxHeightResult + " m";
    }
}