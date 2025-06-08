package racesimulation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos; // Import Pos for alignment
import javafx.geometry.Orientation; // Keep this import
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*; // Import all layout classes
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
// import PitStopData; // Import the new helper class


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
    private Button resetButton; // New Reset button

    // Car Stats Display
    // Removed carStatsTitleLabel as TitledPane handles title or we use a simple Label
    private Label topSpeedLabel;
    private Label accelerationLabel;
    private Label accelerationProfileLabel;
    private Label handlingLabel;
    private Label corneringAbilityLabel;
    private Label fuelConsumptionLabel;
    private Label fuelTankCapacityDisplayLabel; // Display fuel tank capacity from car
    private Label carWeightDisplayLabel; // Display car weight from car


    // Simulation Progress and Output
    // Removed simulationTitleLabel as TitledPane handles title or we use a simple Label
    private ProgressBar raceProgressBar;
    private Label currentFuelLabel;
    private Label currentTyreWearLabel;
    private TextArea raceLogTextArea; // Keep for general log
    // Removed outputLabel, using statusLabel at the bottom instead

    // Pit Stop Display
    // Removed pitStopsTitleLabel as TitledPane handles title or we use a simple Label
    private TableView<PitStopData> pitStopTableView;
    private ObservableList<PitStopData> pitStopDataList; // Data source for TableView

    // Race Summary Display
    // Removed raceSummaryLabel, using statusLabel at the bottom instead

    // Status/Summary Label (at the bottom)
    private Label statusLabel; // Renamed from outputLabel for clarity in BorderPane.BOTTOM

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
        primaryStage.setWidth(1200); // Set initial width to at least 1200
        primaryStage.setHeight(1000); // Set initial height to at least 700

        // Initialize Executor Service
        executorService = Executors.newSingleThreadExecutor();

        // 1. Integrate Previous Data: Create instances of game assets (components, tracks, conditions).
        initializeGameAssets();

        // --- UI Layout ---

        // Root Layout: BorderPane
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root-pane"); // CSS class

        // Top Section: Input Controls and Buttons (VBox for vertical arrangement)
        VBox topPane = new VBox(10); // Use VBox for vertical arrangement, 10 pixels spacing
        topPane.setPadding(new Insets(15));
        topPane.setAlignment(Pos.TOP_LEFT); // Align items to the top left

        // 2. Input Controls: Create ComboBoxes and populate them
        engineComboBox = new ComboBox<>();
        engineComboBox.setItems(FXCollections.observableArrayList(engineVariations));
        engineComboBox.setPromptText("Select Engine");
        engineComboBox.getStyleClass().add("component-combo-box"); // CSS class
        engineComboBox.setMaxWidth(Double.MAX_VALUE); // Allow ComboBox to grow horizontally

        tyresComboBox = new ComboBox<>();
        tyresComboBox.setItems(FXCollections.observableArrayList(tyreVariations));
        tyresComboBox.setPromptText("Select Tyres");
        tyresComboBox.getStyleClass().add("component-combo-box"); // CSS class
        tyresComboBox.setMaxWidth(Double.MAX_VALUE); // Allow ComboBox to grow horizontally

        aeroKitComboBox = new ComboBox<>();
        aeroKitComboBox.setItems(FXCollections.observableArrayList(aeroKitVariations));
        aeroKitComboBox.setPromptText("Select Aerodynamic Kit");
        aeroKitComboBox.getStyleClass().add("component-combo-box"); // CSS class
        aeroKitComboBox.setMaxWidth(Double.MAX_VALUE); // Allow ComboBox to grow horizontally

        trackComboBox = new ComboBox<>();
        trackComboBox.setItems(FXCollections.observableArrayList(trackVariations));
        trackComboBox.setPromptText("Select Race Track");
        trackComboBox.getStyleClass().add("race-detail-combo-box"); // CSS class
        trackComboBox.setMaxWidth(Double.MAX_VALUE); // Allow ComboBox to grow horizontally

        conditionsComboBox = new ComboBox<>();
        conditionsComboBox.setItems(FXCollections.observableArrayList(conditionVariations));
        conditionsComboBox.setPromptText("Select Race Conditions");
        conditionsComboBox.getStyleClass().add("race-detail-combo-box"); // CSS class
        conditionsComboBox.setMaxWidth(Double.MAX_VALUE); // Allow ComboBox to grow horizontally

        carWeightField = new TextField("1000.0"); // Default value
        carWeightField.setPromptText("Car Weight (kg)");
        carWeightField.setPrefWidth(100); // Set a preferred width
        carWeightField.getStyleClass().add("input-field"); // CSS class
        carWeightField.setMaxWidth(Double.MAX_VALUE); // Allow TextField to grow horizontally


        fuelTankCapacityField = new TextField("80.0"); // Default value
        fuelTankCapacityField.setPromptText("Fuel Tank Capacity (L)");
        fuelTankCapacityField.setPrefWidth(120); // Set a preferred width
        fuelTankCapacityField.getStyleClass().add("input-field"); // CSS class
        fuelTankCapacityField.setMaxWidth(Double.MAX_VALUE); // Allow TextField to grow horizontally


        // 3. Action Buttons
        configureButton = new Button("Configure Car & Track");
        configureButton.setOnAction(e -> handleConfigureButton());
        configureButton.getStyleClass().add("action-button"); // CSS class
        configureButton.setMaxWidth(Double.MAX_VALUE); // Allow Button to grow horizontally


        startSimulationButton = new Button("Start Race Simulation");
        startSimulationButton.setOnAction(e -> handleStartSimulationButton());
        startSimulationButton.setDisable(true);
        startSimulationButton.getStyleClass().add("action-button"); // CSS class
        startSimulationButton.setMaxWidth(Double.MAX_VALUE); // Allow Button to grow horizontally


        resetButton = new Button("Reset"); // Initialize Reset button
        resetButton.setOnAction(e -> handleResetButton());
        resetButton.getStyleClass().add("action-button"); // CSS class
        resetButton.setMaxWidth(Double.MAX_VALUE); // Allow Button to grow horizontally


        // Add controls to the topPane (VBox) in the specified vertical order
        topPane.getChildren().addAll(
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
                startSimulationButton, // Moved start button here
                resetButton // Moved reset button here
        );
        root.setTop(topPane);


        // Center Section: HBox with three VBox panels (remains the same for parallel display)
        HBox centerContentPane = new HBox(15); // 15 pixels spacing between panels
        centerContentPane.setPadding(new Insets(0, 15, 0, 15)); // Add horizontal padding
        centerContentPane.setAlignment(Pos.TOP_CENTER); // Align panels to the top
        BorderPane.setMargin(centerContentPane, new Insets(15, 0, 15, 0)); // Add vertical margin to center pane


        // Panel 1: Race Car Performance Stats (VBox)
        VBox statsPane = new VBox(5); // 5 pixels spacing within the panel
        statsPane.getStyleClass().add("info-panel"); // Apply panel styling
        HBox.setHgrow(statsPane, Priority.ALWAYS); // Allow stats pane to grow horizontally
        VBox.setVgrow(statsPane, Priority.ALWAYS); // Allow stats pane to grow vertically

        Label statsTitleLabel = new Label("Race Car Performance Stats"); // Title Label
        statsTitleLabel.getStyleClass().add("section-title"); // Apply title styling

        // 4. Car Stats Display - Initialize labels
        topSpeedLabel = new Label("Top Speed: N/A");
        accelerationLabel = new Label("0-100 km/h: N/A");
        accelerationProfileLabel = new Label("Acceleration Profile: N/A");
        handlingLabel = new Label("Handling (1-10): N/A");
        corneringAbilityLabel = new Label("Cornering Ability (1-100): N/A");
        fuelConsumptionLabel = new Label("Base Fuel Consumption (per lap): N/A");
        fuelTankCapacityDisplayLabel = new Label("Fuel Tank Capacity: N/A"); // Display from car object
        carWeightDisplayLabel = new Label("Car Weight: N/A"); // Display from car object


        statsPane.getChildren().addAll(
                statsTitleLabel,
                topSpeedLabel,
                accelerationLabel,
                accelerationProfileLabel,
                handlingLabel,
                corneringAbilityLabel,
                fuelConsumptionLabel,
                fuelTankCapacityDisplayLabel,
                carWeightDisplayLabel
        );


        // Panel 2: Race Simulation Log (VBox)
        VBox logPane = new VBox(5);
        logPane.getStyleClass().add("info-panel"); // Apply panel styling
        HBox.setHgrow(logPane, Priority.ALWAYS); // Allow log pane to grow horizontally
        VBox.setVgrow(logPane, Priority.ALWAYS); // Allow log pane to grow vertically


        Label logTitleLabel = new Label("Race Simulation Log"); // Title Label
        logTitleLabel.getStyleClass().add("section-title"); // Apply title styling

        // 5. Simulation Progress and Output - Initialize controls
        raceProgressBar = new ProgressBar(0);
        raceProgressBar.setMaxWidth(Double.MAX_VALUE); // Make it fill width
        currentFuelLabel = new Label("Current Fuel: N/A");
        currentTyreWearLabel = new Label("Current Tyre Wear: N/A");
        raceLogTextArea = new TextArea();
        raceLogTextArea.setEditable(false);
        raceLogTextArea.setWrapText(true);
        // raceLogTextArea.setPrefHeight(150); // Removed fixed height
        raceLogTextArea.getStyleClass().add("log-area"); // CSS class
        VBox.setVgrow(raceLogTextArea, Priority.ALWAYS); // Make TextArea grow vertically

        logPane.getChildren().addAll(
                logTitleLabel,
                raceProgressBar,
                currentFuelLabel,
                currentTyreWearLabel,
                raceLogTextArea
        );


        // Panel 3: Pit Stops (VBox)
        VBox pitStopsPane = new VBox(5);
        pitStopsPane.getStyleClass().add("info-panel"); // Apply panel styling
        HBox.setHgrow(pitStopsPane, Priority.ALWAYS); // Allow pit stops pane to grow horizontally
        VBox.setVgrow(pitStopsPane, Priority.ALWAYS); // Allow pit stops pane to grow vertically


        Label pitStopsTitleLabel = new Label("Pit Stops"); // Title Label
        pitStopsTitleLabel.getStyleClass().add("section-title"); // Apply title styling

        // 6. Pit Stop Display (TableView) - Initialize TableView
        pitStopTableView = new TableView<>();
        pitStopDataList = FXCollections.observableArrayList();
        pitStopTableView.setItems(pitStopDataList);
        pitStopTableView.setPlaceholder(new Label("No pit stops planned or taken yet.")); // Message when empty
        // pitStopTableView.setPrefHeight(150); // Removed fixed height

        TableColumn<PitStopData, Integer> lapColumn = new TableColumn<>("Lap Number");
        lapColumn.setCellValueFactory(new PropertyValueFactory<>("lapNumber"));
        lapColumn.setPrefWidth(100); // Adjust width as needed
        // lapColumn.setResizable(false); // Optional: Prevent resizing

        TableColumn<PitStopData, String> reasonColumn = new TableColumn<>("Reason for Pit Stop");
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        reasonColumn.setPrefWidth(200); // Adjust width as needed
        // reasonColumn.setResizable(false); // Optional: Prevent resizing

        // Adjust column resize policy to distribute extra space
        pitStopTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pitStopTableView.getColumns().addAll(lapColumn, reasonColumn); // Add columns
        pitStopTableView.getStyleClass().add("pit-stop-table"); // CSS class
        VBox.setVgrow(pitStopTableView, Priority.ALWAYS); // Make TableView grow vertically


        pitStopsPane.getChildren().addAll(
                pitStopsTitleLabel,
                pitStopTableView
        );


        // Add the three VBox panels to the HBox
        centerContentPane.getChildren().addAll(statsPane, logPane, pitStopsPane);
        root.setCenter(centerContentPane);


        // Bottom Section: Status Label (BorderPane.BOTTOM)
        // 7. Race Summary Display - Removed raceSummaryLabel
        // 8. Layout - Removed old VBox root setup
        statusLabel = new Label("Select components and click 'Configure Car & Track'"); // Renamed from outputLabel
        statusLabel.getStyleClass().add("status-label");
        BorderPane.setMargin(statusLabel, new Insets(0, 15, 15, 15)); // Add margin
        root.setBottom(statusLabel);


        // Set up the Scene and Stage
        Scene scene = new Scene(root); // Scene size is now determined by Stage size
        // Link the CSS file
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

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
     * Also calculates and displays the initial pit stop strategy.
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
            showErrorAlert("Invalid Input", "Please enter valid positive numbers for Car Weight and Fuel Tank Capacity.");
            statusLabel.setText("Configuration failed: Invalid input."); // Use statusLabel
            clearCarStats();
            clearPitStops();
            // raceSummaryLabel.setText("Race Summary: N/A"); // Removed raceSummaryLabel
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
            fuelTankCapacityDisplayLabel.setText(String.format("Fuel Tank Capacity: %.1f L", selectedRaceCar.getFuelTankCapacity())); // Display from car object
            carWeightDisplayLabel.setText(String.format("Car Weight: %.1f kg", selectedRaceCar.getCarWeight())); // Display from car object


            // Calculate and display planned pit stops (using a temporary optimiser instance)
            RaceStrategyOptimiser tempOptimiser = new RaceStrategyOptimiser(
                new RaceCar(selectedEngine, selectedTyres, selectedAeroKit, carWeight, fuelTankCapacity), // Use a copy for planning
                selectedRaceTrack,
                selectedRaceConditions
            );
            List<Integer> plannedPitLaps = tempOptimiser.planPitStops();
            displayPlannedPitStops(plannedPitLaps);


            // Enable simulation button
            startSimulationButton.setDisable(false);
            raceLogTextArea.clear(); // Clear previous log
            // raceSummaryLabel.setText("Race Summary: N/A"); // Removed raceSummaryLabel
            statusLabel.setText("Configuration successful. Ready to simulate."); // Use statusLabel

        } else {
            showErrorAlert("Selection Error", "Please select all components, track, and conditions.");
            statusLabel.setText("Configuration failed: Missing selections."); // Use statusLabel
            clearCarStats();
            clearPitStops();
            // raceSummaryLabel.setText("Race Summary: N/A"); // Removed raceSummaryLabel
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
        fuelTankCapacityDisplayLabel.setText("Fuel Tank Capacity: N/A");
        carWeightDisplayLabel.setText("Car Weight: N/A");
    }

    /**
     * Displays the planned pit stops in the TableView.
     * @param plannedLaps The list of lap numbers for planned pit stops.
     */
    private void displayPlannedPitStops(List<Integer> plannedLaps) {
        pitStopDataList.clear(); // Clear previous data
        if (plannedLaps.isEmpty()) {
            pitStopTableView.setPlaceholder(new Label("No pit stops planned by the initial strategy."));
        } else {
            pitStopTableView.setPlaceholder(new Label("")); // Clear placeholder if data exists
            for (int lap : plannedLaps) {
                // For planned stops, we don't have a specific reason yet from planPitStops()
                // A more advanced optimiser would provide this. For now, use a generic reason.
                 pitStopDataList.add(new PitStopData(lap, "Planned"));
            }
        }
        pitStopTableView.refresh(); // Refresh TableView after updating data
    }

     /**
      * Clears the pit stop TableView.
      */
    private void clearPitStops() {
        pitStopDataList.clear();
        pitStopTableView.setPlaceholder(new Label("No pit stops planned or taken yet."));
        pitStopTableView.refresh(); // Refresh TableView after clearing
    }


    /**
     * Handles the action when the Start Race Simulation button is clicked.
     * Initiates the race simulation in a background thread.
     */
    private void handleStartSimulationButton() {
        if (selectedRaceCar == null || selectedRaceTrack == null || selectedRaceConditions == null) {
            showErrorAlert("Simulation Error", "Please configure car and track first.");
            statusLabel.setText("Simulation failed: Not configured."); // Use statusLabel
            return;
        }

        // Disable buttons during simulation
        configureButton.setDisable(true);
        startSimulationButton.setDisable(true);
        resetButton.setDisable(true); // Disable reset during simulation

        // Reset car state for simulation
        selectedRaceCar.setCurrentFuel(selectedRaceCar.getFuelTankCapacity());
        selectedRaceCar.setCurrentTyreWear(0.0);

        // Clear previous simulation output and pit stops
        raceLogTextArea.clear();
        pitStopDataList.clear(); // Clear planned stops, will add actual stops
        pitStopTableView.refresh();
        pitStopTableView.setPlaceholder(new Label("Simulating race...")); // Update placeholder
        // raceSummaryLabel.setText("Race Summary: Simulating..."); // Removed raceSummaryLabel
        raceProgressBar.setProgress(0);
        currentFuelLabel.setText("Current Fuel: " + String.format("%.2f L", selectedRaceCar.getCurrentFuel()));
        currentTyreWearLabel.setText("Current Tyre Wear: " + String.format("%.2f%%", selectedRaceCar.getCurrentTyreWear() * 100));
        statusLabel.setText("Simulation started..."); // Use statusLabel


        // Create RaceStrategyOptimiser for simulation logic (specifically simulateLap)
        // Note: The optimiser instance itself doesn't need to be recreated if car/track/conditions haven't changed,
        // but we need its simulateLap method. We can reuse the instance or create a new one.
        // Creating a new one ensures it starts with the current state of selectedRaceCar.
        raceOptimiser = new RaceStrategyOptimiser(selectedRaceCar, selectedRaceTrack, selectedRaceConditions);


        // Create a Task for the background simulation
        Task<Void> simulationTask = new Task<Void>() {
            private int pitStopCount = 0; // Counter for pit stops taken

            @Override
            protected Void call() throws Exception {
                int totalLaps = selectedRaceTrack.getNumberOfLaps();
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
                    if (currentLapNumber < totalLaps) { // No pit stop decision after the final lap
                         double fuelNeededForNextLap = selectedRaceCar.getBaseFuelConsumptionPerLap() * selectedRaceTrack.getFuelConsumptionFactor();
                         boolean pitForFuel = selectedRaceCar.getCurrentFuel() < fuelNeededForNextLap;
                         boolean pitForTyres = selectedRaceCar.getCurrentTyreWear() >= maxTyreWearThreshold;

                         if (pitForFuel || pitForTyres) {
                             // Perform pit stop actions
                             selectedRaceCar.setCurrentFuel(selectedRaceCar.getFuelTankCapacity()); // Refuel
                             selectedRaceCar.setCurrentTyreWear(0.0); // Change tyres
                             pitStopCount++; // Increment pit stop counter

                             Platform.runLater(() -> {
                                 String reason = "";
                                 if (pitForFuel && pitForTyres) reason = "Fuel & Tyres";
                                 else if (pitForFuel) reason = "Fuel";
                                 else if (pitForTyres) reason = "Tyres";
                                 raceLogTextArea.appendText(String.format("--- Pit Stop at end of Lap %d (%s) ---\n", lap, reason));
                                 pitStopDataList.add(new PitStopData(lap, reason)); // Add actual pit stop to TableView
                                 pitStopTableView.setPlaceholder(new Label("")); // Clear placeholder if first pit stop
                                 pitStopTableView.refresh();
                                 currentFuelLabel.setText("Current Fuel: " + String.format("%.2f L", selectedRaceCar.getCurrentFuel())); // Update UI immediately after pit
                                 currentTyreWearLabel.setText("Current Tyre Wear: " + String.format("%.2f%%", selectedRaceCar.getCurrentTyreWear() * 100));
                             });

                             // Optional: Add a delay for the pit stop itself
                             Thread.sleep(1000); // 1 second pit stop simulation time
                         }
                    }


                    // Add a small delay to visualize progress
                    Thread.sleep(50); // Simulate lap time (reduced for faster simulation)
                }

                // Simulation finished, update summary
                Platform.runLater(() -> {
                    statusLabel.setText(String.format("Race Finished! Total Pit Stops: %d. Final Fuel: %.2f L, Final Tyres: %.2f%% wear.",
                            pitStopCount, selectedRaceCar.getCurrentFuel(), selectedRaceCar.getCurrentTyreWear() * 100)); // Use statusLabel
                });


                return null; // Task completed
            }
        };

        // Handle task completion
        simulationTask.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                statusLabel.setText("Race Simulation Complete!"); // Use statusLabel
                configureButton.setDisable(false);
                // startSimulationButton remains disabled until re-configuration or reset
                resetButton.setDisable(false); // Enable reset after simulation
                pitStopTableView.refresh();
            });
        });

        simulationTask.setOnFailed(event -> {
             Platform.runLater(() -> {
                 statusLabel.setText("Simulation failed: " + simulationTask.getException().getMessage()); // Use statusLabel
                 configureButton.setDisable(false);
                 // startSimulationButton remains disabled
                 resetButton.setDisable(false); // Enable reset after simulation
                 pitStopTableView.refresh();
             });
        });


        // Run the task in the background
        executorService.execute(simulationTask);
    }

    /**
     * Handles the action when the Reset button is clicked.
     * Clears the UI and resets the state.
     */
    private void handleResetButton() {
        // Reset input controls
        engineComboBox.getSelectionModel().clearSelection();
        tyresComboBox.getSelectionModel().clearSelection();
        aeroKitComboBox.getSelectionModel().clearSelection();
        trackComboBox.getSelectionModel().clearSelection();
        conditionsComboBox.getSelectionModel().clearSelection();
        carWeightField.setText("1000.0");
        fuelTankCapacityField.setText("80.0");

        // Clear displayed stats and simulation info
        clearCarStats();
        raceLogTextArea.clear();
        raceProgressBar.setProgress(0);
        currentFuelLabel.setText("Current Fuel: N/A");
        currentTyreWearLabel.setText("Current Tyre Wear: N/A");
        clearPitStops();
        // raceSummaryLabel.setText("Race Summary: N/A"); // Removed raceSummaryLabel
        statusLabel.setText("Application reset. Select components to begin."); // Use statusLabel

        // Reset internal state
        selectedRaceCar = null;
        selectedRaceTrack = null;
        selectedRaceConditions = null;
        raceOptimiser = null;

        // Reset button states
        configureButton.setDisable(false);
        startSimulationButton.setDisable(true);
        resetButton.setDisable(false); // Reset button is always enabled after reset
    }


    /**
     * Displays an error alert dialog.
     * @param title The title of the alert.
     * @param message The message to display.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message);
        alert.showAndWait();
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