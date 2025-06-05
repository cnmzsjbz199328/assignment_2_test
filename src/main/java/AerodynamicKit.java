
// AerodynamicKit class (UPDATED for Prompt 6)
class AerodynamicKit {
    private String kitName;
    private double dragCoefficient; // Lower is better for top speed
    private int downforceValue;     // Higher is better for handling
    private double impactOnTopSpeed;  // Multiplier, e.g., 1.0 for no change, >1.0 for increase, <1.0 for decrease
    private double impactOnCornering; // Multiplier, e.g., 1.0 for no change, >1.0 for increase

    public AerodynamicKit(String kitName, double dragCoefficient, int downforceValue, double impactOnTopSpeed, double impactOnCornering) {
        this.kitName = kitName;
        this.dragCoefficient = dragCoefficient;
        this.downforceValue = downforceValue;
        this.impactOnTopSpeed = impactOnTopSpeed;
        this.impactOnCornering = impactOnCornering;
    }

    public String getKitName() {
        return kitName;
    }

    public double getDragCoefficient() {
        return dragCoefficient;
    }

    public int getDownforceValue() {
        return downforceValue;
    }

    public double getImpactOnTopSpeed() {
        return impactOnTopSpeed;
    }

    public double getImpactOnCornering() {
        return impactOnCornering;
    }

    @Override
    public String toString() {
        return "AerodynamicKit{" +
                "kitName='" + kitName + '\'' +
                ", dragCoefficient=" + String.format("%.2f", dragCoefficient) +
                ", downforceValue=" + downforceValue + " N" +
                ", impactOnTopSpeed=" + String.format("%.2f", impactOnTopSpeed) + "x" +
                ", impactOnCornering=" + String.format("%.2f", impactOnCornering) + "x" +
                '}';
    }
}
