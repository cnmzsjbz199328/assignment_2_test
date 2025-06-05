
// RaceConditions class (from Prompt 4)
class RaceConditions {
    private String weather;
    private double airTemperature;
    private double trackTemperature;

    public RaceConditions(String weather, double airTemperature, double trackTemperature) {
        this.weather = weather;
        this.airTemperature = airTemperature;
        this.trackTemperature = trackTemperature;
    }

    public String getWeather() {
        return weather;
    }

    public double getAirTemperature() {
        return airTemperature;
    }

    public double getTrackTemperature() {
        return trackTemperature;
    }

    @Override
    public String toString() {
        return "RaceConditions{" +
                "weather='" + weather + '\'' +
                ", airTemperature=" + String.format("%.1f", airTemperature) + "°C" +
                ", trackTemperature=" + String.format("%.1f", trackTemperature) + "°C" +
                '}';
    }
}
