
// RaceTrack class (from Prompt 4)
class RaceTrack {
    private String name;
    private double length_km;
    private int numberOfLaps;
    private double tyreWearFactor;
    private double fuelConsumptionFactor;

    public RaceTrack(String name, double length_km, int numberOfLaps, double tyreWearFactor, double fuelConsumptionFactor) {
        this.name = name;
        this.length_km = length_km;
        this.numberOfLaps = numberOfLaps;
        this.tyreWearFactor = tyreWearFactor;
        this.fuelConsumptionFactor = fuelConsumptionFactor;
    }

    public String getName() {
        return name;
    }

    public double getLength_km() {
        return length_km;
    }

    public int getNumberOfLaps() {
        return numberOfLaps;
    }

    public double getTyreWearFactor() {
        return tyreWearFactor;
    }

    public double getFuelConsumptionFactor() {
        return fuelConsumptionFactor;
    }

    @Override
    public String toString() {
        return "RaceTrack{" +
                "name='" + name + '\'' +
                ", length_km=" + String.format("%.3f", length_km) + " km" +
                ", numberOfLaps=" + numberOfLaps +
                ", tyreWearFactor=" + String.format("%.2f", tyreWearFactor) +
                ", fuelConsumptionFactor=" + String.format("%.2f", fuelConsumptionFactor) +
                '}';
    }
}
