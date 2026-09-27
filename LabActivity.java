public abstract class LabActivity {
    protected String activityId;
    protected String activityName;
    protected String description;
    protected String startTime;
    protected String endTime;
    protected String status;
    protected int maxDurationMinutes;

    public LabActivity(String activityID, String activityName, String description, String startTime, String endTime, String status, int maxDurationMinutes) {
        this.activityId = activityID;
        this.activityName = activityName;
        this.description = description;
        this.maxDurationMinutes = maxDurationMinutes;
        this.status = "NOT_STARTED";
    }

    public void startActivity() {
        this.status = "RUNNING";
    }

    public void endActivity() {
        this.status = "COMPLETED";
    }

    public String getActivityId() { return activityId; }
    public String getActivityName() { return activityName; }
    public String getDescription() { return description; }
}