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

        // Log the initial conditions at the start of the simulation
        Platform.runLater(() -> {
            logMessageConsumer.accept(String.format("Simulation starting under %s conditions...\n", raceConditions.getName()));
        });

        for (int completedLap = 0; completedLap < totalLaps; completedLap++) {
            int currentLapNumber = completedLap + 1;

            // Simulate the current lap
            raceOptimiser.simulateLap(raceCar, raceTrack, raceConditions);

            // --- Update UI during the lap (using Platform.runLater) ---
            final int lap = currentLapNumber; // Need final variable for lambda
            Platform.runLater(() -> {
                String weatherEffectLog = "";
                switch (raceConditions.getWeather()) {
                    case WET:
                        weatherEffectLog = " (Wet track reducing tyre wear)";
                        break;
                    case DAMP:
                        weatherEffectLog = " (Damp track slightly reducing tyre wear)";
                        break;
                }
                logMessageConsumer.accept(String.format("Lap %d completed. Fuel: %.2f L, Tyres: %.2f%% wear.%s\n",
                        lap, raceCar.getCurrentFuel(), raceCar.getCurrentTyreWear() * 100, weatherEffectLog));
                updateFuelConsumer.accept(raceCar.getCurrentFuel());
                updateTyreWearConsumer.accept(raceCar.getCurrentTyreWear() * 100);
                updateProgressConsumer.accept((double) lap / totalLaps);
            });

            // --- Pit Stop Decision and Action moved to RaceStrategyOptimiser ---
            String pitReason = raceOptimiser.checkAndPerformPitStop(currentLapNumber, totalLaps);

            if (pitReason != null) {
                pitStopCount++;
                final String reasonForUI = pitReason;
                final int pitLap = currentLapNumber;
                Platform.runLater(() -> {
                    logMessageConsumer.accept(String.format("--- Pit Stop taken at end of lap %d. Reason: %s ---\n", pitLap, reasonForUI));
                    addPitStopConsumer.accept(pitLap, reasonForUI);
                    // Update UI immediately after pit stop
                    updateFuelConsumer.accept(raceCar.getCurrentFuel());
                    updateTyreWearConsumer.accept(raceCar.getCurrentTyreWear() * 100);
                });
            }

            // Pause to make the simulation visible
            Thread.sleep(200);
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
