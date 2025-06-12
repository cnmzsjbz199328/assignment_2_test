package racesimulation;
import java.util.ArrayList;
import java.util.List;

class RaceStrategyOptimiser {
    private RaceCar raceCar;
    private RaceTrack raceTrack;
    private RaceConditions raceConditions;

    // Constants for strategy calculation
    // Changed to public so it can be accessed from Main.java
    public static final double MAX_TYRE_WEAR_THRESHOLD = 0.80; // Pit if tyre wear is 80% or more
    private static final double PIT_STOP_TIME_PENALTY_SECONDS = 25.0; // Not used in lap time calculation yet

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
        // --- Calculate environmental adjustment factors ---
        double weatherWearFactor = 1.0;
        double temperatureFuelFactor = 1.0;

        // Adjust tyre wear based on weather
        switch (conditions.getWeather()) {
            case WET:
                weatherWearFactor = 0.75; // Wet track reduces wear
                break;
            case DAMP:
                weatherWearFactor = 0.90; // Damp track slightly reduces wear
                break;
            case DRY:
            default:
                weatherWearFactor = 1.0;
                break;
        }

        // Adjust fuel consumption based on air temperature (colder air is denser)
        if (conditions.getAirTemperature() < 10) {
            temperatureFuelFactor = 1.05; // Colder air increases fuel consumption
        } else if (conditions.getAirTemperature() > 30) {
            temperatureFuelFactor = 0.98; // Warmer air slightly decreases consumption
        }

        // --- Apply calculations ---

        // Calculate fuel used this lap, adjusted by track and temperature
        double fuelUsedThisLap = car.getBaseFuelConsumptionPerLap() * track.getFuelConsumptionFactor() * temperatureFuelFactor;
        car.setCurrentFuel(car.getCurrentFuel() - fuelUsedThisLap);

        // Calculate tyre wear this lap, adjusted by track and weather
        double tyreWearThisLap = car.getTyres().getWearRate() * track.getTyreWearFactor() * weatherWearFactor;
        car.setCurrentTyreWear(car.getCurrentTyreWear() + tyreWearThisLap);
    }

    /**
     * Checks if a pit stop is necessary after a lap and performs it if needed.
     * This should be called after simulateLap for the current lap.
     * @param currentLapNumber The lap number that has just been completed.
     * @param totalLaps The total number of laps in the race.
     * @return The reason for the pit stop ("Fuel", "Tyres", "Fuel & Tyres"), or null if no pit stop was taken.
     */
    public String checkAndPerformPitStop(int currentLapNumber, int totalLaps) {
        if (currentLapNumber >= totalLaps) {
            return null; // No pit stop decisions after the final lap
        }

        // Use the same temperature factor as in simulateLap for consistent fuel calculation
        double temperatureFuelFactor = 1.0;
        if (this.raceConditions.getAirTemperature() < 10) {
            temperatureFuelFactor = 1.05;
        } else if (this.raceConditions.getAirTemperature() > 30) {
            temperatureFuelFactor = 0.98;
        }
        double fuelNeededForNextLap = this.raceCar.getBaseFuelConsumptionPerLap() * this.raceTrack.getFuelConsumptionFactor() * temperatureFuelFactor;

        boolean pitForFuel = this.raceCar.getCurrentFuel() < fuelNeededForNextLap;
        boolean pitForTyres = this.raceCar.getCurrentTyreWear() >= MAX_TYRE_WEAR_THRESHOLD;

        String reason = null;
        if (pitForFuel && pitForTyres) {
            reason = "Fuel & Tyres";
        } else if (pitForFuel) {
            reason = "Fuel";
        } else if (pitForTyres) {
            reason = "Tyres";
        }

        if (reason != null) {
            // Perform pit stop actions
            this.raceCar.setCurrentFuel(this.raceCar.getFuelTankCapacity());
            this.raceCar.setCurrentTyreWear(0.0);
        }

        return reason;
    }

    /**
     * Plans a pit stop strategy based on fuel and tyre wear.
     * This method simulates the race lap by lap and decides when to pit.
     * The RaceCar's state (currentFuel, currentTyreWear) will be modified by this simulation.
     * It's assumed the raceCar is in its initial state (full fuel, zero wear) when this method is called,
     * or it should be reset externally if this method is called multiple times on the same optimiser instance
     * with the intent of a fresh simulation.
     * For robustness, this method will internally reset the car's state at the beginning of planning.
     *
     * @return A list of lap numbers at the END of which a pit stop is made.
     */
    public List<Integer> planPitStops() {
        List<Integer> pitStopLaps = new ArrayList<>();
        int totalLaps = raceTrack.getNumberOfLaps();

        if (totalLaps <= 0) {
            return pitStopLaps; // No laps to simulate, no pit stops.
        }

        // Reset car to initial state for this planning simulation
        this.raceCar.setCurrentFuel(this.raceCar.getFuelTankCapacity());
        this.raceCar.setCurrentTyreWear(0.0);

        for (int completedLap = 0; completedLap < totalLaps; completedLap++) {
            int currentLapNumber = completedLap + 1;

            // Before simulating, ensure car can actually complete it (basic check)
            double fuelNeededForThisLap = this.raceCar.getBaseFuelConsumptionPerLap() * this.raceTrack.getFuelConsumptionFactor();
            if (this.raceCar.getCurrentFuel() < fuelNeededForThisLap && currentLapNumber > 1) {
                // This implies a miscalculation or an extreme scenario where the car couldn't even start the previous lap without pitting.
                // The logic below should ideally prevent this by pitting earlier.
                // If this happens, it means the car ran out of fuel. For now, we'll assume the pit logic handles it.
            }

            simulateLap(this.raceCar, this.raceTrack, this.raceConditions);

            // Decision point: After completing 'currentLapNumber', decide if a pit stop is needed before the next lap
            // No pit stop decision after the final lap
            if (currentLapNumber < totalLaps) {
                double fuelNeededForNextLap = this.raceCar.getBaseFuelConsumptionPerLap() * this.raceTrack.getFuelConsumptionFactor();
                boolean pitForFuel = this.raceCar.getCurrentFuel() < fuelNeededForNextLap;
                boolean pitForTyres = this.raceCar.getCurrentTyreWear() >= MAX_TYRE_WEAR_THRESHOLD;

                if (pitForFuel || pitForTyres) {
                    pitStopLaps.add(currentLapNumber); // Pit at the end of currentLapNumber
                    this.raceCar.setCurrentFuel(this.raceCar.getFuelTankCapacity()); // Refuel
                    this.raceCar.setCurrentTyreWear(0.0); // Change tyres
                    // System.out.println("Debug: Pit stop planned at end of lap " + currentLapNumber); // Optional debug
                }
            }
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