package racesimulation;

// Re-including helper and component classes for completeness and context
// TemperatureRange class (from Prompt 2)
class TemperatureRange {
    private double minTemp;
    private double maxTemp;

    public TemperatureRange(double minTemp, double maxTemp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    @Override
    public String toString() {
        return "(" + minTemp + " - " + maxTemp + " °C)";
    }
}
