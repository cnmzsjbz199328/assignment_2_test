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
     * @param conditions The RaceConditions for the lap. (Currently not directly used here but available)
     */
    public void simulateLap(RaceCar car, RaceTrack track, RaceConditions conditions) {
        // Calculate fuel used this lap
        double fuelUsedThisLap = car.getBaseFuelConsumptionPerLap() * track.getFuelConsumptionFactor();
        car.setCurrentFuel(car.getCurrentFuel() - fuelUsedThisLap);

        // Calculate tyre wear this lap
        // Tyre wear rate is a direct percentage increase per lap, influenced by track factor
        double tyreWearThisLap = car.getTyres().getWearRate() * track.getTyreWearFactor();
        car.setCurrentTyreWear(car.getCurrentTyreWear() + tyreWearThisLap);
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