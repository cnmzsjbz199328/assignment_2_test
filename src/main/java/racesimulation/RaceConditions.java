package racesimulation;

public class RaceConditions {
    public enum Weather {
        DRY, WET, DAMP
    }

    private final String name; // e.g., "Dry", "Wet", "Damp"
    private final Weather weather;
    private final double airTemperature; // Celsius
    private final double trackTemperature; // Celsius
    private final double humidity; // Percentage (0.0 to 1.0)

    public RaceConditions(String name, Weather weather, double airTemperature, double trackTemperature, double humidity) {
        this.name = name;
        this.weather = weather;
        this.airTemperature = airTemperature;
        this.trackTemperature = trackTemperature;
        this.humidity = humidity;
    }

    // Getters
    public String getName() { return name; }
    public Weather getWeather() { return weather; }
    public double getAirTemperature() { return airTemperature; }
    public double getTrackTemperature() { return trackTemperature; }
    public double getHumidity() { return humidity; }

    @Override
    public String toString() {
        return String.format("%s (Air: %.0f°C, Track: %.0f°C)", name, airTemperature, trackTemperature);
    }
}
