package racesimulation;

import java.util.LinkedHashMap;
import java.util.Map;

// AerodynamicKit class (UPDATED for Prompt 6)
public class AerodynamicKit {
    private String kitName;
    private double dragCoefficient;
    private int downforceValue;
    private int topSpeed; // Direct value from spec
    private double fuelEfficiency; // Direct value from spec
    private int corneringAbility; // Direct value from spec

    public AerodynamicKit(String kitName, double dragCoefficient, int downforceValue, int topSpeed, double fuelEfficiency, int corneringAbility) {
        this.kitName = kitName;
        this.dragCoefficient = dragCoefficient;
        this.downforceValue = downforceValue;
        this.topSpeed = topSpeed;
        this.fuelEfficiency = fuelEfficiency;
        this.corneringAbility = corneringAbility;
    }

    // Static factory method to get all kits as per specification.md
    public static Map<String, AerodynamicKit> getAllSpecKits() {
        // Use LinkedHashMap to preserve insertion order for the UI ComboBox
        Map<String, AerodynamicKit> kits = new LinkedHashMap<>();
        kits.put("Standard Kit", new AerodynamicKit("Standard Kit", 0.30, 200, 250, 12, 6));
        kits.put("Downforce-Focused Kit", new AerodynamicKit("Downforce-Focused Kit", 0.35, 350, 220, 10, 9));
        kits.put("Low-Drag Kit", new AerodynamicKit("Low-Drag Kit", 0.25, 150, 280, 14, 5));
        // Note: For variable kits, we take an average or a representative value.
        kits.put("Adjustable Aero Kit", new AerodynamicKit("Adjustable Aero Kit", 0.31, 250, 250, 12, 8));
        kits.put("Ground Effect Kit", new AerodynamicKit("Ground Effect Kit", 0.27, 400, 240, 12, 8));
        kits.put("Drag Reduction System", new AerodynamicKit("Drag Reduction System", 0.25, 200, 290, 13, 6));
        kits.put("Wet Weather Kit", new AerodynamicKit("Wet Weather Kit", 0.32, 220, 230, 11, 7));
        kits.put("Hybrid Kit", new AerodynamicKit("Hybrid Kit", 0.29, 260, 260, 12, 7));
        kits.put("Extreme Aero Kit", new AerodynamicKit("Extreme Aero Kit", 0.40, 500, 200, 9, 10));
        return kits;
    }

    // Getters
    public String getKitName() { return kitName; }
    public double getDragCoefficient() { return dragCoefficient; }
    public int getDownforceValue() { return downforceValue; }
    public int getTopSpeed() { return topSpeed; }
    public double getFuelEfficiency() { return fuelEfficiency; }
    public int getCorneringAbility() { return corneringAbility; }

    @Override
    public String toString() {
        return kitName;
    }
}
