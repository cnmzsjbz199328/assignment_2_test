import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Import your existing model classes
// import AerodynamicKit;
// import Engine;
// import RaceCar;
// import RaceConditions;
// import RaceStrategyOptimiser;
// import RaceTrack;
// import TemperatureRange;
// import Tyres;


public class Main extends Application {

    // Lists to hold component variations
    private List<Engine> engineVariations;
    private List<Tyres> tyreVariations;
    private List<AerodynamicKit> aeroKitVariations;
    private List<RaceTrack> trackVariations;
    private List<RaceConditions> conditionVariations;

    // Input Controls
    private ComboBox<Engine> engineComboBox;
    private ComboBox<Tyres> tyresComboBox;
    private ComboBox<AerodynamicKit> aeroKitComboBox;
    private ComboBox<RaceTrack> trackComboBox;
    private ComboBox<RaceConditions> conditionsComboBox;
    private TextField carWeightField;
    private TextField fuelTankCapacityField;

    // Action Buttons
    private Button configureButton;
    private Button startSimulationButton;

    // Car Stats Display
    private Label carStatsTitleLabel;
    private Label topSpeedLabel;
    private Label accelerationLabel;
    private Label accelerationProfileLabel;
    private Label handlingLabel;
    private Label corneringAbilityLabel;
    private Label fuelConsumptionLabel;

    // Simulation Progress and Output
    private Label simulationTitleLabel;
    private ProgressBar raceProgressBar;
    private Label currentFuelLabel;
    private Label currentTyreWearLabel;
    private TextArea raceLogTextArea;
    private Label outputLabel; // Declared outputLabel here

    // Selected/Configured Objects
    private RaceCar selectedRaceCar;
    private RaceTrack selectedRaceTrack;
    private RaceConditions selectedRaceConditions;
    private RaceStrategyOptimiser raceOptimiser; // Keep an instance for simulateLap

    // Executor for background tasks
    private ExecutorService executorService;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Race Strategy Optimiser");

        // Initialize Executor Service
        executorService = Executors.newSingleThreadExecutor();

        // 1. Integrate Previous Data: Create instances of component variations
        initializeGameAssets();

        // 2. Input Controls: Create ComboBoxes and populate them
        engineComboBox = new ComboBox<>();
        engineComboBox.setItems(FXCollections.observableArrayList(engineVariations));
        engineComboBox.setPromptText("Select Engine");

        tyresComboBox = new ComboBox<>();
        tyresComboBox.setItems(FXCollections.observableArrayList(tyreVariations));
        tyresComboBox.setPromptText("Select Tyres");

        aeroKitComboBox = new ComboBox<>();
        aeroKitComboBox.setItems(FXCollections.observableArrayList(aeroKitVariations));
        aeroKitComboBox.setPromptText("Select Aerodynamic Kit");

        trackComboBox = new ComboBox<>();
        trackComboBox.setItems(FXCollections.observableArrayList(trackVariations));
        trackComboBox.setPromptText("Select Race Track");

        conditionsComboBox = new ComboBox<>();
        conditionsComboBox.setItems(FXCollections.observableArrayList(conditionVariations));
        conditionsComboBox.setPromptText("Select Race Conditions");

        carWeightField = new TextField("1000.0"); // Default value
        carWeightField.setPromptText("Car Weight (kg)");

        fuelTankCapacityField = new TextField("80.0"); // Default value
        fuelTankCapacityField.setPromptText("Fuel Tank Capacity (L)");


        // 3. Action Buttons
        configureButton = new Button("Configure Car & Track");
        configureButton.setOnAction(e -> handleConfigureButton());

        startSimulationButton = new Button("Start Race Simulation");
        startSimulationButton.setOnAction(e -> handleStartSimulationButton());
        startSimulationButton.setDisable(true); // Disabled until configuration is done

        // 4. Car Stats Display
        carStatsTitleLabel = new Label("--- Race Car Performance Stats ---");
        topSpeedLabel = new Label("Top Speed: N/A");
        accelerationLabel = new Label("0-100 km/h: N/A");
        accelerationProfileLabel = new Label("Acceleration Profile: N/A");
        handlingLabel = new Label("Handling (1-10): N/A");
        corneringAbilityLabel = new Label("Cornering Ability (1-100): N/A");
        fuelConsumptionLabel = new Label("Base Fuel Consumption (per lap): N/A");

        // 5. Simulation Progress and Output
        simulationTitleLabel = new Label("--- Race Simulation ---");
        raceProgressBar = new ProgressBar(0);
        raceProgressBar.setPrefWidth(300); // Make it wider
        currentFuelLabel = new Label("Current Fuel: N/A");
        currentTyreWearLabel = new Label("Current Tyre Wear: N/A");
        raceLogTextArea = new TextArea();
        raceLogTextArea.setEditable(false);
        raceLogTextArea.setWrapText(true);
        raceLogTextArea.setPrefHeight(200); // Give it some height
        outputLabel = new Label("Select components and click 'Configure Car & Track'"); // Initialized outputLabel here


        // 6. Layout: Arrange controls in a VBox
        VBox root = new VBox(10); // 10 pixels spacing between children
        root.setPadding(new Insets(15)); // Padding around the VBox
        root.getChildren().addAll(
                new Label("Select Car Components:"),
                engineComboBox,
                tyresComboBox,
                aeroKitComboBox,
                new Label("Car Specifics:"),
                carWeightField,
                fuelTankCapacityField,
                new Label("Select Race Details:"),
                trackComboBox,
                conditionsComboBox,
                configureButton,
                carStatsTitleLabel,
                topSpeedLabel,
                accelerationLabel,
                accelerationProfileLabel,
                handlingLabel,
                corneringAbilityLabel,
                fuelConsumptionLabel,
                simulationTitleLabel,
                startSimulationButton,
                raceProgressBar,
                currentFuelLabel,
                currentTyreWearLabel,
                raceLogTextArea,
                outputLabel // Added outputLabel to the layout
        );

        // Set up the Scene and Stage
        Scene scene = new Scene(root, 450, 750); // Adjusted window size to fit outputLabel
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Helper method to create instances of game assets (components, tracks, conditions).
     */
    private void initializeGameAssets() {
        // Engine variations (from Prompt 2)
        engineVariations = new ArrayList<>();
        engineVariations.add(new Engine("Standard V6", 300, 7.5, 0.95));
        engineVariations.add(new Engine("Turbocharged V8", 550, 5.0, 0.88));
        engineVariations.add(new Engine("EcoBoost I4", 180, 12.0, 0.99));

        // Tyre variations (from Prompt 2)
        tyreVariations = new ArrayList<>();
        tyreVariations.add(new Tyres("Soft Compound", 95, 0.15, 80, 100));
        tyreVariations.add(new Tyres("Medium Compound", 80, 0.08, 90, 110));
        tyreVariations.add(new Tyres("Hard Compound", 65, 0.03, 100, 120));

        // Aerodynamic Kit variations (from Prompt 6)
        aeroKitVariations = new ArrayList<>();
        aeroKitVariations.add(new AerodynamicKit("Standard Kit", 0.33, 1200, 1.0, 1.0));
        aeroKitVariations.add(new AerodynamicKit("Downforce-Focused Kit", 0.38, 2800, 0.95, 1.2));
        aeroKitVariations.add(new AerodynamicKit("Low-Drag Kit", 0.25, 800, 1.1, 0.9));
        aeroKitVariations.add(new AerodynamicKit("Ground Effect Kit", 0.30, 3500, 1.02, 1.35));

        // Race Track variations (from Prompt 4)
        trackVariations = new ArrayList<>();
        trackVariations.add(new RaceTrack("Monaco", 3.337, 78, 1.3, 1.1)); // High wear, high consumption
        trackVariations.add(new RaceTrack("Monza", 5.793, 53, 0.8, 0.9));   // Low wear, low consumption
        trackVariations.add(new RaceTrack("Silverstone", 5.891, 52, 1.0, 1.0)); // Medium wear, medium consumption

        // Race Conditions variations (from Prompt 4)
        conditionVariations = new ArrayList<>();
        conditionVariations.add(new RaceConditions("Dry", 25.0, 35.0));
        conditionVariations.add(new RaceConditions("Wet", 15.0, 18.0));
        conditionVariations.add(new RaceConditions("Damp", 20.0, 25.0));
    }

    /**
     * Handles the action when the Configure Car & Track button is clicked.
     * Creates the RaceCar instance and displays its stats.
     */
    private void handleConfigureButton() {
        Engine selectedEngine = engineComboBox.getSelectionModel().getSelectedItem();
        Tyres selectedTyres = tyresComboBox.getSelectionModel().getSelectedItem();
        AerodynamicKit selectedAeroKit = aeroKitComboBox.getSelectionModel().getSelectedItem();
        selectedRaceTrack = trackComboBox.getSelectionModel().getSelectedItem();
        selectedRaceConditions = conditionsComboBox.getSelectionModel().getSelectedItem();

        double carWeight;
        double fuelTankCapacity;

        try {
            carWeight = Double.parseDouble(carWeightField.getText());
            fuelTankCapacity = Double.parseDouble(fuelTankCapacityField.getText());
            if (carWeight <= 0 || fuelTankCapacity <= 0) {
                 throw new NumberFormatException("Values must be positive.");
            }
        } catch (NumberFormatException e) {
            outputLabel.setText("Invalid input for Car Weight or Fuel Tank Capacity: " + e.getMessage());
            clearCarStats();
            startSimulationButton.setDisable(true);
            return;
        }


        if (selectedEngine != null && selectedTyres != null && selectedAeroKit != null &&
            selectedRaceTrack != null && selectedRaceConditions != null) {

            // Create the RaceCar instance based on selections and inputs
            selectedRaceCar = new RaceCar(selectedEngine, selectedTyres, selectedAeroKit, carWeight, fuelTankCapacity);

            // Display calculated stats
            topSpeedLabel.setText(String.format("Top Speed: %.1f km/h", selectedRaceCar.getTopSpeed()));
            accelerationLabel.setText(String.format("0-100 km/h: %.2f s", selectedRaceCar.getAccelerationTime0To100()));
            accelerationProfileLabel.setText("Acceleration Profile: " + selectedRaceCar.getAccelerationProfile());
            handlingLabel.setText(String.format("Handling (1-10): %d/10", selectedRaceCar.getHandlingRating()));
            corneringAbilityLabel.setText(String.format("Cornering Ability (1-100): %d/100", selectedRaceCar.getCorneringAbilityRating()));
            fuelConsumptionLabel.setText(String.format("Base Fuel Consumption (per lap): %.2f L", selectedRaceCar.getBaseFuelConsumptionPerLap()));

            // Enable simulation button
            startSimulationButton.setDisable(false);
            raceLogTextArea.clear(); // Clear previous log
            outputLabel.setText("Configuration successful. Ready to simulate.");

        } else {
            outputLabel.setText("Please select all components, track, and conditions.");
            clearCarStats();
            startSimulationButton.setDisable(true);
        }
    }

    /**
     * Clears the displayed car statistics.
     */
    private void clearCarStats() {
        topSpeedLabel.setText("Top Speed: N/A");
        accelerationLabel.setText("0-100 km/h: N/A");
        accelerationProfileLabel.setText("Acceleration Profile: N/A");
        handlingLabel.setText("Handling (1-10): N/A");
        corneringAbilityLabel.setText("Cornering Ability (1-100): N/A");
        fuelConsumptionLabel.setText("Base Fuel Consumption (per lap): N/A");
    }


    /**
     * Handles the action when the Start Race Simulation button is clicked.
     * Initiates the race simulation in a background thread.
     */
    private void handleStartSimulationButton() {
        if (selectedRaceCar == null || selectedRaceTrack == null || selectedRaceConditions == null) {
            outputLabel.setText("Please configure car and track first.");
            return;
        }

        // Disable buttons during simulation
        configureButton.setDisable(true);
        startSimulationButton.setDisable(true);

        // Reset car state for simulation
        selectedRaceCar.setCurrentFuel(selectedRaceCar.getFuelTankCapacity());
        selectedRaceCar.setCurrentTyreWear(0.0);

        // Clear previous simulation output
        raceLogTextArea.clear();
        raceProgressBar.setProgress(0);
        currentFuelLabel.setText("Current Fuel: " + String.format("%.2f L", selectedRaceCar.getCurrentFuel()));
        currentTyreWearLabel.setText("Current Tyre Wear: " + String.format("%.2f%%", selectedRaceCar.getCurrentTyreWear() * 100));
        outputLabel.setText("Simulation started...");

        // Create RaceStrategyOptimiser for simulation logic (specifically simulateLap)
        raceOptimiser = new RaceStrategyOptimiser(selectedRaceCar, selectedRaceTrack, selectedRaceConditions);

        // Create a Task for the background simulation
        Task<Void> simulationTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int totalLaps = selectedRaceTrack.getNumberOfLaps();
                // Accessing the public static final constant
                double maxTyreWearThreshold = RaceStrategyOptimiser.MAX_TYRE_WEAR_THRESHOLD;

                for (int completedLap = 0; completedLap < totalLaps; completedLap++) {
                    int currentLapNumber = completedLap + 1;

                    // Simulate the current lap
                    raceOptimiser.simulateLap(selectedRaceCar, selectedRaceTrack, selectedRaceConditions);

                    // --- Update UI during the lap (using Platform.runLater) ---
                    final int lap = currentLapNumber; // Need final variable for lambda
                    Platform.runLater(() -> {
                        raceLogTextArea.appendText(String.format("Lap %d completed. Fuel: %.2f L, Tyres: %.2f%% wear.\n",
                                lap, selectedRaceCar.getCurrentFuel(), selectedRaceCar.getCurrentTyreWear() * 100));
                        currentFuelLabel.setText("Current Fuel: " + String.format("%.2f L", selectedRaceCar.getCurrentFuel()));
                        currentTyreWearLabel.setText("Current Tyre Wear: " + String.format("%.2f%%", selectedRaceCar.getCurrentTyreWear() * 100));
                        raceProgressBar.setProgress((double) lap / totalLaps);
                    });

                    // --- Pit Stop Decision AFTER the lap (based on state AFTER this lap, for the NEXT lap) ---
                    if (currentLapNumber < totalLaps) { // No pit stop after the final lap
                         double fuelNeededForNextLap = selectedRaceCar.getBaseFuelConsumptionPerLap() * selectedRaceTrack.getFuelConsumptionFactor();
                         boolean pitForFuel = selectedRaceCar.getCurrentFuel() < fuelNeededForNextLap;
                         boolean pitForTyres = selectedRaceCar.getCurrentTyreWear() >= maxTyreWearThreshold;

                         if (pitForFuel || pitForTyres) {
                             // Perform pit stop actions
                             selectedRaceCar.setCurrentFuel(selectedRaceCar.getFuelTankCapacity()); // Refuel
                             selectedRaceCar.setCurrentTyreWear(0.0); // Change tyres

                             Platform.runLater(() -> {
                                 String reason = "";
                                 if (pitForFuel && pitForTyres) reason = "Fuel & Tyres";
                                 else if (pitForFuel) reason = "Fuel";
                                 else if (pitForTyres) reason = "Tyres";
                                 raceLogTextArea.appendText(String.format("--- Pit Stop at end of Lap %d (%s) ---\n", lap, reason));
                                 currentFuelLabel.setText("Current Fuel: " + String.format("%.2f L", selectedRaceCar.getCurrentFuel())); // Update UI immediately after pit
                                 currentTyreWearLabel.setText("Current Tyre Wear: " + String.format("%.2f%%", selectedRaceCar.getCurrentTyreWear() * 100));
                             });

                             // Optional: Add a delay for the pit stop itself
                             Thread.sleep(1000); // 1 second pit stop simulation time
                         }
                    }


                    // Add a small delay to visualize progress
                    Thread.sleep(100); // Simulate lap time
                }

                return null; // Task completed
            }
        };

        // Handle task completion
        simulationTask.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                outputLabel.setText("Race Finished!");
                configureButton.setDisable(false);
                // startSimulationButton remains disabled until re-configuration or reset (if implemented later)
            });
        });

        simulationTask.setOnFailed(event -> {
             Platform.runLater(() -> {
                 outputLabel.setText("Simulation failed: " + simulationTask.getException().getMessage());
                 configureButton.setDisable(false);
                 // startSimulationButton remains disabled
             });
        });


        // Run the task in the background
        executorService.execute(simulationTask);
    }

    @Override
    public void stop() throws Exception {
        // Shut down the executor service when the application closes
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}