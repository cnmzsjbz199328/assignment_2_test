package racesimulation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


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

    // Input Controls
    private ComboBox<Engine> engineComboBox;
    private ComboBox<Tyres> tyresComboBox;
    private ComboBox<AerodynamicKit> aeroKitComboBox;
    private ComboBox<RaceTrack> trackComboBox;
    private ComboBox<RaceConditions> conditionsComboBox;
    private ComboBox<Double> carWeightComboBox;
    private ComboBox<Double> fuelTankCapacityComboBox;

    // Action Buttons
    private Button configureButton;
    private Button startSimulationButton;
    private Button resetButton;

    // Car Stats Display
    private Label topSpeedLabel;
    private Label accelerationLabel;
    private Label accelerationProfileLabel;
    private Label handlingLabel;
    private Label corneringAbilityLabel;
    private Label fuelConsumptionLabel;
    private Label fuelTankCapacityDisplayLabel;
    private Label carWeightDisplayLabel;


    // Simulation Progress and Output
    private ProgressBar raceProgressBar;
    private Label currentFuelLabel;
    private Label currentTyreWearLabel;
    private TextArea raceLogTextArea;

    // Pit Stop Display
    private TableView<PitStopData> pitStopTableView;
    private ObservableList<PitStopData> pitStopDataList;

    // Status/Summary Label (at the bottom)
    private Label statusLabel;

    // Selected/Configured Objects
    private RaceCar selectedRaceCar;
    private RaceTrack selectedRaceTrack;
    private RaceConditions selectedRaceConditions;
    private RaceStrategyOptimiser raceOptimiser;

    // Executor for background tasks
    private ExecutorService executorService;

    // Game Asset Provider
    private GameAssetProvider assetProvider;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Race Strategy Optimiser");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(1000);

        // Initialize Executor Service
        executorService = Executors.newSingleThreadExecutor();

        // Initialize Game Asset Provider
        assetProvider = new GameAssetProvider();

        // --- UI Layout ---

        // Root Layout: BorderPane
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root-pane");

        // Top Section: Input Controls and Buttons (VBox for vertical arrangement)
        VBox topPane = new VBox(10);
        topPane.setPadding(new Insets(15));
        topPane.setAlignment(Pos.TOP_LEFT);

        // 2. Input Controls: Create ComboBoxes and populate them using GameAssetProvider
        engineComboBox = new ComboBox<>();
        engineComboBox.setItems(FXCollections.observableArrayList(assetProvider.getEngineVariations()));
        engineComboBox.setPromptText("Select Engine");
        engineComboBox.getStyleClass().add("component-combo-box");
        engineComboBox.setMaxWidth(Double.MAX_VALUE);

        tyresComboBox = new ComboBox<>();
        tyresComboBox.setItems(FXCollections.observableArrayList(assetProvider.getTyreVariations()));
        tyresComboBox.setPromptText("Select Tyres");
        tyresComboBox.getStyleClass().add("component-combo-box");
        tyresComboBox.setMaxWidth(Double.MAX_VALUE);

        aeroKitComboBox = new ComboBox<>();
        aeroKitComboBox.setItems(FXCollections.observableArrayList(assetProvider.getAeroKitVariations()));
        aeroKitComboBox.setPromptText("Select Aerodynamic Kit");
        aeroKitComboBox.getStyleClass().add("component-combo-box");
        aeroKitComboBox.setMaxWidth(Double.MAX_VALUE);

        trackComboBox = new ComboBox<>();
        trackComboBox.setItems(FXCollections.observableArrayList(assetProvider.getTrackVariations()));
        trackComboBox.setPromptText("Select Race Track");
        trackComboBox.getStyleClass().add("race-detail-combo-box");
        trackComboBox.setMaxWidth(Double.MAX_VALUE);

        conditionsComboBox = new ComboBox<>();
        conditionsComboBox.setItems(FXCollections.observableArrayList(assetProvider.getConditionVariations()));
        conditionsComboBox.setPromptText("Select Race Conditions");
        conditionsComboBox.getStyleClass().add("race-detail-combo-box");
        conditionsComboBox.setMaxWidth(Double.MAX_VALUE);

        carWeightComboBox = new ComboBox<>();
        carWeightComboBox.setItems(FXCollections.observableArrayList(RaceCar.getAllowedWeights()));
        carWeightComboBox.setPromptText("Select Car Weight (kg)");
        carWeightComboBox.setMaxWidth(Double.MAX_VALUE);

        fuelTankCapacityComboBox = new ComboBox<>();
        fuelTankCapacityComboBox.setItems(FXCollections.observableArrayList(RaceCar.getAllowedFuelCapacities()));
        fuelTankCapacityComboBox.setPromptText("Select Fuel Tank Capacity (L)");
        fuelTankCapacityComboBox.setMaxWidth(Double.MAX_VALUE);

        // 3. Action Buttons
        configureButton = new Button("Configure Car & Track");
        configureButton.setOnAction(e -> handleConfigureButton());
        configureButton.getStyleClass().add("action-button");
        configureButton.setMaxWidth(Double.MAX_VALUE);


        startSimulationButton = new Button("Start Race Simulation");
        startSimulationButton.setOnAction(e -> handleStartSimulationButton());
        startSimulationButton.setDisable(true);
        startSimulationButton.getStyleClass().add("action-button");
        startSimulationButton.setMaxWidth(Double.MAX_VALUE);


        resetButton = new Button("Reset");
        resetButton.setOnAction(e -> handleResetButton());
        resetButton.getStyleClass().add("action-button");
        resetButton.setMaxWidth(Double.MAX_VALUE);


        // Add controls to the topPane (VBox) in the specified vertical order
        topPane.getChildren().addAll(
                new Label("Select Car Components:"),
                engineComboBox,
                tyresComboBox,
                aeroKitComboBox,
                new Label("Car Specifics:"),
                carWeightComboBox,
                fuelTankCapacityComboBox,
                new Label("Select Race Details:"),
                trackComboBox,
                conditionsComboBox,
                configureButton,
                startSimulationButton,
                resetButton
        );
        root.setTop(topPane);


        // Center Section: HBox with three VBox panels
        HBox centerContentPane = new HBox(15);
        centerContentPane.setPadding(new Insets(0, 15, 0, 15));
        centerContentPane.setAlignment(Pos.TOP_CENTER);
        BorderPane.setMargin(centerContentPane, new Insets(15, 0, 15, 0));


        // Panel 1: Race Car Performance Stats (VBox)
        VBox statsPane = new VBox(5);
        statsPane.getStyleClass().add("info-panel");
        HBox.setHgrow(statsPane, Priority.ALWAYS);
        VBox.setVgrow(statsPane, Priority.ALWAYS);

        Label statsTitleLabel = new Label("Race Car Performance Stats");
        statsTitleLabel.getStyleClass().add("section-title");

        // 4. Car Stats Display - Initialize labels
        topSpeedLabel = new Label("Top Speed: N/A");
        accelerationLabel = new Label("0-100 km/h: N/A");
        accelerationProfileLabel = new Label("Acceleration Profile: N/A");
        handlingLabel = new Label("Handling (1-10): N/A");
        corneringAbilityLabel = new Label("Cornering Ability (1-100): N/A");
        fuelConsumptionLabel = new Label("Base Fuel Consumption (per lap): N/A");
        fuelTankCapacityDisplayLabel = new Label("Fuel Tank Capacity: N/A");
        carWeightDisplayLabel = new Label("Car Weight: N/A");


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
        logPane.getStyleClass().add("info-panel");
        HBox.setHgrow(logPane, Priority.ALWAYS);
        VBox.setVgrow(logPane, Priority.ALWAYS); // Corrected syntax


        Label logTitleLabel = new Label("Race Simulation Log");
        logTitleLabel.getStyleClass().add("section-title");

        // 5. Simulation Progress and Output - Initialize controls
        raceProgressBar = new ProgressBar(0);
        raceProgressBar.setMaxWidth(Double.MAX_VALUE);
        currentFuelLabel = new Label("Current Fuel: N/A");
        currentTyreWearLabel = new Label("Current Tyre Wear: N/A");
        raceLogTextArea = new TextArea();
        raceLogTextArea.setEditable(false);
        raceLogTextArea.setWrapText(true);
        raceLogTextArea.getStyleClass().add("log-area");
        VBox.setVgrow(raceLogTextArea, Priority.ALWAYS);

        logPane.getChildren().addAll(
                logTitleLabel,
                raceProgressBar,
                currentFuelLabel,
                currentTyreWearLabel,
                raceLogTextArea
        );


        // Panel 3: Pit Stops (VBox)
        VBox pitStopsPane = new VBox(5);
        pitStopsPane.getStyleClass().add("info-panel");
        HBox.setHgrow(pitStopsPane, Priority.ALWAYS);
        VBox.setVgrow(pitStopsPane, Priority.ALWAYS); // Corrected syntax


        Label pitStopsTitleLabel = new Label("Pit Stops");
        pitStopsTitleLabel.getStyleClass().add("section-title");

        // 6. Pit Stop Display (TableView) - Initialize TableView
        pitStopTableView = new TableView<>();
        pitStopDataList = FXCollections.observableArrayList();
        pitStopTableView.setItems(pitStopDataList);
        pitStopTableView.setPlaceholder(new Label("No pit stops planned or taken yet."));

        TableColumn<PitStopData, Integer> lapColumn = new TableColumn<>("Lap Number");
        lapColumn.setCellValueFactory(new PropertyValueFactory<>("lapNumber"));
        lapColumn.setPrefWidth(100);

        TableColumn<PitStopData, String> reasonColumn = new TableColumn<>("Reason for Pit Stop");
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        reasonColumn.setPrefWidth(200);

        pitStopTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        pitStopTableView.getColumns().addAll(lapColumn, reasonColumn);
        pitStopTableView.getStyleClass().add("pit-stop-table");
        VBox.setVgrow(pitStopTableView, Priority.ALWAYS);

        pitStopsPane.getChildren().addAll(pitStopsTitleLabel, pitStopTableView); // Add title and table to the pane


        // Add the three VBox panels to the HBox
        centerContentPane.getChildren().addAll(statsPane, logPane, pitStopsPane);
        root.setCenter(centerContentPane);


        // Bottom Section: Status Label (BorderPane.BOTTOM)
        statusLabel = new Label("Select components and click 'Configure Car & Track'");
        statusLabel.getStyleClass().add("status-label");
        BorderPane.setMargin(statusLabel, new Insets(0, 15, 15, 15));
        root.setBottom(statusLabel);


        // Set up the Scene and Stage
        Scene scene = new Scene(root);
        // Link the CSS file
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles the action when the Configure Car & Track button is clicked.
     * Creates the RaceCar instance and displays its stats.
     * Also calculates and displays the initial pit stop strategy.
     */
    private void handleConfigureButton() {
        Engine selectedEngine = engineComboBox.getValue();
        Tyres selectedTyres = tyresComboBox.getValue();
        AerodynamicKit selectedAeroKit = aeroKitComboBox.getValue();
        Double selectedCarWeight = carWeightComboBox.getValue();
        Double selectedFuelTankCapacity = fuelTankCapacityComboBox.getValue();
        RaceTrack selectedTrack = trackComboBox.getValue();
        RaceConditions selectedConditions = conditionsComboBox.getValue();

        if (selectedEngine == null || selectedTyres == null || selectedAeroKit == null ||
            selectedCarWeight == null || selectedFuelTankCapacity == null ||
            selectedTrack == null || selectedConditions == null) {
            showErrorAlert("Missing Selection", "Please select all car components, car specifics, and race details.");
            return;
        }

        try {
            selectedRaceCar = new RaceCar(selectedEngine, selectedTyres, selectedAeroKit, selectedCarWeight, selectedFuelTankCapacity);
        } catch (IllegalArgumentException ex) {
            showErrorAlert("Invalid Car Configuration", ex.getMessage());
            return;
        }

        // Display calculated stats
        topSpeedLabel.setText(String.format("Top Speed: %.1f km/h", selectedRaceCar.getTopSpeed()));
        accelerationLabel.setText(String.format("0-100 km/h: %.2f s", selectedRaceCar.getAccelerationTime0To100()));
        accelerationProfileLabel.setText("Acceleration Profile: " + selectedRaceCar.getAccelerationProfile());
        handlingLabel.setText(String.format("Handling (1-10): %d/10", selectedRaceCar.getHandlingRating()));
        corneringAbilityLabel.setText(String.format("Cornering Ability (1-100): %d/100", selectedRaceCar.getCorneringAbilityRating()));
        fuelConsumptionLabel.setText(String.format("Base Fuel Consumption (per lap): %.2f L", selectedRaceCar.getBaseFuelConsumptionPerLap()));
        fuelTankCapacityDisplayLabel.setText(String.format("Fuel Tank Capacity: %.1f L", selectedRaceCar.getFuelTankCapacity()));
        carWeightDisplayLabel.setText(String.format("Car Weight: %.1f kg", selectedRaceCar.getCarWeight()));


        // Calculate and display planned pit stops (using a temporary optimiser instance)
        RaceStrategyOptimiser tempOptimiser = new RaceStrategyOptimiser(
            new RaceCar(selectedEngine, selectedTyres, selectedAeroKit, selectedCarWeight, selectedFuelTankCapacity), // Use a copy for planning
            selectedTrack,
            selectedConditions
        );
        List<Integer> plannedPitLaps = tempOptimiser.planPitStops();
        displayPlannedPitStops(plannedPitLaps);


        // Enable simulation button
        startSimulationButton.setDisable(false);
        raceLogTextArea.clear();
        raceProgressBar.setProgress(0);
        currentFuelLabel.setText("Current Fuel: N/A");
        currentTyreWearLabel.setText("Current Tyre Wear: N/A");
        statusLabel.setText("Configuration successful. Ready to simulate.");

        this.selectedRaceTrack = selectedTrack;
        this.selectedRaceConditions = selectedConditions;
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
        pitStopDataList.clear();
        if (plannedLaps.isEmpty()) {
            pitStopTableView.setPlaceholder(new Label("No pit stops planned by the initial strategy."));
        } else {
            pitStopTableView.setPlaceholder(new Label(""));
            for (int lap : plannedLaps) {
                 pitStopDataList.add(new PitStopData(lap, "Planned"));
            }
        }
        pitStopTableView.refresh();
    }

     /**
      * Clears the pit stop TableView.
      */
    private void clearPitStops() {
        pitStopDataList.clear();
        pitStopTableView.setPlaceholder(new Label("No pit stops planned or taken yet."));
        pitStopTableView.refresh();
    }


    /**
     * Handles the action when the Start Race Simulation button is clicked.
     * Initiates the race simulation in a background thread using RaceSimulationTask.
     */
    private void handleStartSimulationButton() {
        if (selectedRaceCar == null || selectedRaceTrack == null || selectedRaceConditions == null) {
            showErrorAlert("Simulation Error", "Please configure car and track first.");
            statusLabel.setText("Simulation failed: Not configured.");
            return;
        }

        // Disable buttons during simulation
        configureButton.setDisable(true);
        startSimulationButton.setDisable(true);
        resetButton.setDisable(true);

        // Reset car state for simulation
        selectedRaceCar.setCurrentFuel(selectedRaceCar.getFuelTankCapacity());
        selectedRaceCar.setCurrentTyreWear(0.0);

        // Clear previous simulation output and pit stops
        raceLogTextArea.clear();
        pitStopDataList.clear();
        pitStopTableView.refresh();
        pitStopTableView.setPlaceholder(new Label("Simulating race..."));
        raceProgressBar.setProgress(0);
        currentFuelLabel.setText("Current Fuel: " + String.format("%.2f L", selectedRaceCar.getCurrentFuel()));
        currentTyreWearLabel.setText("Current Tyre Wear: " + String.format("%.2f%%", selectedRaceCar.getCurrentTyreWear() * 100));
        statusLabel.setText("Simulation started...");


        // Create RaceStrategyOptimiser for simulation logic (specifically simulateLap)
        raceOptimiser = new RaceStrategyOptimiser(selectedRaceCar, selectedRaceTrack, selectedRaceConditions);

        // Define UI update callbacks, ensuring they run on the JavaFX Application Thread
        Consumer<String> logMessageConsumer = message -> raceLogTextArea.appendText(message);
        Consumer<Double> updateFuelConsumer = fuel -> currentFuelLabel.setText("Current Fuel: " + String.format("%.2f L", fuel));
        Consumer<Double> updateTyreWearConsumer = wear -> currentTyreWearLabel.setText("Current Tyre Wear: " + String.format("%.2f%%", wear));
        BiConsumer<Integer, String> addPitStopConsumer = (lap, reason) -> {
            pitStopDataList.add(new PitStopData(lap, reason));
            pitStopTableView.setPlaceholder(new Label("")); // Clear placeholder if first pit stop
            pitStopTableView.refresh();
        };
        Consumer<Double> updateProgressConsumer = progress -> raceProgressBar.setProgress(progress);
        Consumer<String> simulationFinishedConsumer = summary -> statusLabel.setText(summary);


        // Create and run the RaceSimulationTask
        RaceSimulationTask simulationTask = new RaceSimulationTask(
                selectedRaceCar,
                selectedRaceTrack,
                selectedRaceConditions,
                raceOptimiser,
                logMessageConsumer,
                updateFuelConsumer,
                updateTyreWearConsumer,
                addPitStopConsumer,
                updateProgressConsumer,
                simulationFinishedConsumer
        );

        // Handle task completion
        simulationTask.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                statusLabel.setText("Race Simulation Complete!");
                configureButton.setDisable(false);
                startSimulationButton.setDisable(true); // Keep start disabled until re-configure
                resetButton.setDisable(false);
                pitStopTableView.refresh(); // Refresh one last time
            });
        });

        simulationTask.setOnFailed(event -> {
             Platform.runLater(() -> {
                 statusLabel.setText("Simulation failed: " + simulationTask.getException().getMessage());
                 configureButton.setDisable(false);
                 startSimulationButton.setDisable(true); // Keep start disabled
                 resetButton.setDisable(false);
                 pitStopTableView.refresh(); // Refresh one last time
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
        carWeightComboBox.getSelectionModel().clearSelection();
        fuelTankCapacityComboBox.getSelectionModel().clearSelection();

        // Clear displayed stats and simulation info
        clearCarStats();
        raceLogTextArea.clear();
        raceProgressBar.setProgress(0);
        currentFuelLabel.setText("Current Fuel: N/A");
        currentTyreWearLabel.setText("Current Tyre Wear: N/A");
        clearPitStops();
        statusLabel.setText("Application reset. Select components to begin.");

        // Reset internal state
        selectedRaceCar = null;
        selectedRaceTrack = null;
        selectedRaceConditions = null;
        raceOptimiser = null;

        // Reset button states
        configureButton.setDisable(false);
        startSimulationButton.setDisable(true);
        resetButton.setDisable(false);
    }


    /**
     * Displays an error alert dialog.
     * @param title The title of the alert.
     * @param message The message to display.
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
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