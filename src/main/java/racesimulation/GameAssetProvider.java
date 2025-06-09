package racesimulation;

import java.util.ArrayList;
import java.util.List;

// Assuming these model classes exist in the same package
// import AerodynamicKit;
// import Engine;
// import RaceConditions;
// import RaceTrack;
// import TemperatureRange; // If used by Tyres
// import Tyres;

public class GameAssetProvider {

    private List<Engine> engineVariations;
    private List<Tyres> tyreVariations;
    private List<AerodynamicKit> aeroKitVariations;
    private List<RaceTrack> trackVariations;
    private List<RaceConditions> conditionVariations;

    public GameAssetProvider() {
        // Initialize game assets (components, tracks, conditions)
        initializeGameAssets();
    }

    /**
     * Initializes instances of game assets (components, tracks, conditions).
     */
    private void initializeGameAssets() {
        // Engine variations (from Prompt 2)
        engineVariations = new ArrayList<>();
        engineVariations.add(new Engine("Standard V6", 300, 7.5, 0.95));
        engineVariations.add(new Engine("Turbocharged V8", 550, 5.0, 0.88));
        engineVariations.add(new Engine("EcoBoost I4", 180, 12.0, 0.99));

        // Tyre variations (from Prompt 2)
        tyreVariations = new ArrayList<>();
        // Assuming TemperatureRange class exists and Tyres constructor takes min/max temp
        // Note: The original Main.java used min/max temp directly, adjusting to use TemperatureRange if it exists.
        // If TemperatureRange doesn't exist or Tyres constructor is different, this might need adjustment based on actual model classes.
        // Based on the provided Main.java, the Tyres constructor takes gripLevel, wearRate, minTemp, maxTemp as doubles.
        tyreVariations.add(new Tyres("Soft Compound", 95, 0.15, 80, 100));
        tyreVariations.add(new Tyres("Medium Compound", 80, 0.08, 90, 110));
        tyreVariations.add(new Tyres("Hard Compound", 65, 0.03, 100, 120));

        // Aerodynamic Kit variations (from Prompt 6)
        aeroKitVariations = new ArrayList<>();
        // Assuming AerodynamicKit constructor takes name, drag, downforce, top speed impact, cornering impact
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

    public List<Engine> getEngineVariations() {
        return engineVariations;
    }

    public List<Tyres> getTyreVariations() {
        return tyreVariations;
    }

    public List<AerodynamicKit> getAeroKitVariations() {
        return aeroKitVariations;
    }

    public List<RaceTrack> getTrackVariations() {
        return trackVariations;
    }

    public List<RaceConditions> getConditionVariations() {
        return conditionVariations;
    }
}
