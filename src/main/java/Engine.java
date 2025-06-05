class Engine {
    private String name;
    private int powerRating;      // HP
    private double fuelEfficiency; // km/l
    private double reliability;    // 0.0 to 1.0

    public Engine(String name, int powerRating, double fuelEfficiency, double reliability) {
        this.name = name;
        this.powerRating = powerRating;
        this.fuelEfficiency = fuelEfficiency;
        this.reliability = reliability;
    }

    public String getName() {
        return name;
    }

    public int getPowerRating() {
        return powerRating;
    }

    public double getFuelEfficiency() {
        return fuelEfficiency;
    }

    public double getReliability() {
        return reliability;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "name='" + name + '\'' +
                ", powerRating=" + powerRating + " HP" +
                ", fuelEfficiency=" + String.format("%.1f", fuelEfficiency) + " km/l" +
                ", reliability=" + String.format("%.0f", reliability * 100) + "%" +
                '}';
    }
}
