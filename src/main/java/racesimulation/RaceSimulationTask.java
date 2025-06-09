package racesimulation;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

// Assuming these model classes exist in the same package
// import RaceCar;
// import RaceTrack;
// import RaceConditions;
// import RaceStrategyOptimiser;
// import PitStopData; // If used by the callback

public class RaceSimulationTask extends Task<Void> {

    private final RaceCar raceCar;
    private final RaceTrack raceTrack;
    private final RaceConditions raceConditions;
    private final RaceStrategyOptimiser raceOptimiser;

    // Callbacks for UI updates
    private final Consumer<String> logMessageConsumer;
    private final Consumer<Double> updateFuelConsumer;
    private final Consumer<Double> updateTyreWearConsumer;
    private final BiConsumer<Integer, String> addPitStopConsumer;
    private final Consumer<Double> updateProgressConsumer;
    private final Consumer<String> simulationFinishedConsumer;

    public RaceSimulationTask(
            RaceCar raceCar,
            RaceTrack raceTrack,
            RaceConditions raceConditions,
            RaceStrategyOptimiser raceOptimiser,
            Consumer<String> logMessageConsumer,
            Consumer<Double> updateFuelConsumer,
            Consumer<Double> updateTyreWearConsumer,
            BiConsumer<Integer, String> addPitStopConsumer,
            Consumer<Double> updateProgressConsumer,
            Consumer<String> simulationFinishedConsumer) {
        this.raceCar = raceCar;
        this.raceTrack = raceTrack;
        this.raceConditions = raceConditions;
        this.raceOptimiser = raceOptimiser;
        this.logMessageConsumer = logMessageConsumer;
        this.updateFuelConsumer = updateFuelConsumer;
        this.updateTyreWearConsumer = updateTyreWearConsumer;
        this.addPitStopConsumer = addPitStopConsumer;
        this.updateProgressConsumer = updateProgressConsumer;
        this.simulationFinishedConsumer = simulationFinishedConsumer;
    }

    @Override
    protected Void call() throws Exception {
        int totalLaps = raceTrack.getNumberOfLaps();
        double maxTyreWearThreshold = RaceStrategyOptimiser.MAX_TYRE_WEAR_THRESHOLD;
        int pitStopCount = 0;

        for (int completedLap = 0; completedLap < totalLaps; completedLap++) {
            int currentLapNumber = completedLap + 1;

            // Simulate the current lap
            raceOptimiser.simulateLap(raceCar, raceTrack, raceConditions);

            // --- Update UI during the lap (using Platform.runLater) ---
            final int lap = currentLapNumber; // Need final variable for lambda
            Platform.runLater(() -> {
                logMessageConsumer.accept(String.format("Lap %d completed. Fuel: %.2f L, Tyres: %.2f%% wear.\n",
                        lap, raceCar.getCurrentFuel(), raceCar.getCurrentTyreWear() * 100));
                updateFuelConsumer.accept(raceCar.getCurrentFuel());
                updateTyreWearConsumer.accept(raceCar.getCurrentTyreWear() * 100);
                updateProgressConsumer.accept((double) lap / totalLaps);
            });

            // --- Pit Stop Decision AFTER the lap (based on state AFTER this lap, for the NEXT lap) ---
            if (currentLapNumber < totalLaps) { // No pit stop decision after the final lap
                double fuelNeededForNextLap = raceCar.getBaseFuelConsumptionPerLap() * raceTrack.getFuelConsumptionFactor();
                boolean pitForFuel = raceCar.getCurrentFuel() < fuelNeededForNextLap;
                boolean pitForTyres = raceCar.getCurrentTyreWear() >= maxTyreWearThreshold;

                if (pitForFuel || pitForTyres) {
                    // Perform pit stop actions
                    raceCar.setCurrentFuel(raceCar.getFuelTankCapacity()); // Refuel
                    raceCar.setCurrentTyreWear(0.0); // Change tyres
                    pitStopCount++; // Increment pit stop counter

                    // Declare final copies for use in lambda
                    final boolean finalPitForFuel = pitForFuel;
                    final boolean finalPitForTyres = pitForTyres;
                    final int finalPitStopLap = lap; // Capture the lap number for the pit stop

                    Platform.runLater(() -> {
                        String reason = "";
                        if (finalPitForFuel && finalPitForTyres) reason = "Fuel & Tyres";
                        else if (finalPitForFuel) reason = "Fuel";
                        else if (finalPitForTyres) reason = "Tyres";
                        logMessageConsumer.accept(String.format("--- Pit Stop at end of Lap %d (%s) ---\n", finalPitStopLap, reason));
                        addPitStopConsumer.accept(finalPitStopLap, reason); // Add actual pit stop to TableView
                        updateFuelConsumer.accept(raceCar.getCurrentFuel()); // Update UI immediately after pit
                        updateTyreWearConsumer.accept(raceCar.getCurrentTyreWear() * 100);
                    });

                    // Optional: Add a delay for the pit stop itself
                    Thread.sleep(1000); // 1 second pit stop simulation time
                }
            }

            // Add a small delay to simulate lap time
            Thread.sleep(50); // Simulate lap time (reduced for faster simulation)
        }

        // Simulation finished, update summary
        final int finalPitStopCount = pitStopCount; // Capture the final pit stop count
        Platform.runLater(() -> {
            simulationFinishedConsumer.accept(String.format("Race Finished! Total Pit Stops: %d. Final Fuel: %.2f L, Final Tyres: %.2f%% wear.",
                    finalPitStopCount, raceCar.getCurrentFuel(), raceCar.getCurrentTyreWear() * 100));
        });

        return null; // Task completed
    }
}
