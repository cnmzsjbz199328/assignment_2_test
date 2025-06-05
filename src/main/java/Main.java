
package main.java;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

// Import your existing model classes
// import AerodynamicKit; // Assuming these are in the default package or correctly imported
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

    // Action Button
    private Button configureButton;

    // Placeholder Output
    private Label outputLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Race Strategy Optimiser");

        // 1. Integrate Previous Data: Create instances of component variations
        initializeGameAssets();

        // 2. Input Controls: Create ComboBoxes and populate them
        engineComboBox = new ComboBox<>();
        engineComboBox.setItems(FXCollections.observableArrayList(engineVariations));
        engineComboBox.setPromptText("Select Engine"); // Placeholder text

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

        // 3. Action Button
        configureButton = new Button("Configure Car & Track");
        configureButton.setOnAction(e -> handleConfigureButton());

        // 4. Placeholder Output
        outputLabel = new Label("Select components and click 'Configure Car & Track'");

        // 5. Layout: Arrange controls in a VBox
        VBox root = new VBox(10); // 10 pixels spacing between children
        root.setPadding(new Insets(15)); // Padding around the VBox
        root.getChildren().addAll(
                new Label("Select Car Components:"),
                engineComboBox,
                tyresComboBox,
                aeroKitComboBox,
                new Label("Select Race Details:"),
                trackComboBox,
                conditionsComboBox,
                configureButton,
                outputLabel
        );

        // Set up the Scene and Stage
        Scene scene = new Scene(root, 400, 400); // Initial window size
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
     * For now, just updates the output label.
     */
    private void handleConfigureButton() {
        // Get selected items (will be null if nothing is selected)
        Engine selectedEngine = engineComboBox.getSelectionModel().getSelectedItem();
        Tyres selectedTyres = tyresComboBox.getSelectionModel().getSelectedItem();
        AerodynamicKit selectedAeroKit = aeroKitComboBox.getSelectionModel().getSelectedItem();
        RaceTrack selectedTrack = trackComboBox.getSelectionModel().getSelectedItem();
        RaceConditions selectedConditions = conditionsComboBox.getSelectionModel().getSelectedItem();

        if (selectedEngine != null && selectedTyres != null && selectedAeroKit != null &&
            selectedTrack != null && selectedConditions != null) {
            // In the next prompt, we will create a RaceCar instance here
            // and display its calculated stats.
            outputLabel.setText("Configuration selected:\n" +
                                "Engine: " + selectedEngine.getName() + "\n" +
                                "Tyres: " + selectedTyres.getCompound() + "\n" +
                                "Aero Kit: " + selectedAeroKit.getKitName() + "\n" +
                                "Track: " + selectedTrack.getName() + "\n" +
                                "Conditions: " + selectedConditions.getWeather());
        } else {
            outputLabel.setText("Please select all components, track, and conditions.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
