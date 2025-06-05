
// Tyres class (from Prompt 2)
class Tyres {
    private String compound;
    private int gripLevel;             // Base integer level
    private double wearRate;           // higher means wears faster
    private TemperatureRange optimalTempRange;

    public Tyres(String compound, int gripLevel, double wearRate, double minOptimalTemp, double maxOptimalTemp) {
        this.compound = compound;
        this.gripLevel = gripLevel;
        this.wearRate = wearRate;
        this.optimalTempRange = new TemperatureRange(minOptimalTemp, maxOptimalTemp);
    }

    public String getCompound() { return compound; }
    public int getGripLevel() { return gripLevel; }
    public double getWearRate() { return wearRate; }
    public TemperatureRange getOptimalTempRange() { return optimalTempRange; }

    @Override
    public String toString() {
        return "Tyres{" +
                "compound='" + compound + '\'' +
                ", gripLevel=" + gripLevel +
                ", wearRate=" + String.format("%.2f", wearRate) +
                ", optimalTempRange=" + optimalTempRange +
                '}';
    }
}

