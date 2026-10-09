public class CircuitExperiment extends LabActivity {
    private double voltage;
    private double[] resistances;
    private double totalResistance;
    private double current;
    private CircuitType circuitType;

    public CircuitExperiment(String activityID, String activityName, String description, int maxDurationMinutes, double voltage, double[] resistances, CircuitType circuitType) {
        super(activityID, activityName, description, maxDurationMinutes);
        this.voltage = voltage;
        this.resistances = resistances;
        this.circuitType = circuitType;
    }

    @Override
    public void runSimulation() throws InvalidParameterException, SimulationException {
        if (voltage < 0) {
            throw new InvalidParameterException("voltage", "Voltage cannot be negative.");
        }
        for (double r : resistances) {
            if (r < 0) {
                throw new InvalidParameterException("resistances", "Resistance cannot be negative.");
            }
        }

        switch (circuitType) {
            case SERIES -> {
                totalResistance = 0;
                for (double r : resistances) {
                    totalResistance += r;
                }
            }
            case PARALLEL -> {
                double sumOfReciprocals = 0;
                for (double r : resistances) {
                    if (r == 0) {
                        throw new SimulationException(202, "A 0-ohm resistor in parallel short-circuits the network.");
                    }
                    sumOfReciprocals += 1.0 / r;
                }
                totalResistance = 1.0 / sumOfReciprocals;
            }
        }

        current = voltage / totalResistance;
    }

    @Override
    public String calculateResults() {
        return String.format("Total Resistance = %.2f ohm, Current = %.2f A", totalResistance, current);
    }
}