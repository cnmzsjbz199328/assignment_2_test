package main.java;
import java.util.ArrayList;
import java.util.List;

class RaceStrategyOptimiser {
    private RaceCar raceCar;
    private RaceTrack raceTrack;
    private RaceConditions raceConditions;

    /**
     * Constructs a RaceStrategyOptimiser.
     *
     * @param raceCar        The RaceCar instance.
     * @param raceTrack      The RaceTrack instance.
     * @param raceConditions The RaceConditions instance.
     */
    public RaceStrategyOptimiser(RaceCar raceCar, RaceTrack raceTrack, RaceConditions raceConditions) {
        if (raceCar == null) {
            throw new IllegalArgumentException("RaceCar cannot be null.");
        }
        if (raceTrack == null) {
            throw new IllegalArgumentException("RaceTrack cannot be null.");
        }
        if (raceConditions == null) {
            throw new IllegalArgumentException("RaceConditions cannot be null.");
        }
        this.raceCar = raceCar;
        this.raceTrack = raceTrack;
        this.raceConditions = raceConditions;
    }

    /**
     * Simulates a single lap of the race, updating the car's fuel and tyre wear.
     *
     * @param car        The RaceCar to simulate the lap for.
     * @param track      The RaceTrack on which the lap is simulated.
     * @param conditions The RaceConditions for the lap.
     */
    public void simulateLap(RaceCar car, RaceTrack track, RaceConditions conditions) {
        // Calculate fuel used this lap
        double fuelUsedThisLap = car.getBaseFuelConsumptionPerLap() * track.getFuelConsumptionFactor();
        car.setCurrentFuel(car.getCurrentFuel() - fuelUsedThisLap);

        // Calculate tyre wear this lap
        // Tyre wear rate is a direct percentage increase per lap, influenced by track factor
        double tyreWearThisLap = car.getTyres().getWearRate() * track.getTyreWearFactor();
        car.setCurrentTyreWear(car.getCurrentTyreWear() + tyreWearThisLap);

        // Note: In a more complex simulation, raceConditions (e.g., weather) would also affect these.
        // For now, they are passed but not directly used in this basic version of simulateLap,
        // beyond their potential influence on track factors if those were dynamic.
    }

    /**
     * Plans a pit stop strategy.
     * For now, returns a very simple fixed strategy.
     *
     * @return A list of lap numbers on which a pit stop is planned.
     */
    public List<Integer> planPitStops() {
        // Placeholder: Simple fixed strategy - pit every 10 laps if race is long enough
        List<Integer> pitStopLaps = new ArrayList<>();
        int totalLaps = raceTrack.getNumberOfLaps();
        for (int lap = 10; lap < totalLaps; lap += 10) {
            pitStopLaps.add(lap);
        }
        return pitStopLaps;
    }

    // Getters for the main components if needed externally
    public RaceCar getRaceCar() {
        return raceCar;
    }

    public RaceTrack getRaceTrack() {
        return raceTrack;
    }

    public RaceConditions getRaceConditions() {
        return raceConditions;
    }
}