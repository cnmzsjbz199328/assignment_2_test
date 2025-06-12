package racesimulation;

class RaceCar {
    private Engine engine;
    private Tyres tyres;
    private AerodynamicKit aeroKit;
    private double carWeight; // kg
    private double fuelTankCapacity; // Liters

    // Current status attributes
    private double currentFuel;       // Liters
    private double currentTyreWear;   // Percentage (0.0 to 1.0, where 1.0 is 100% worn)

    // Attributes for overall performance metrics, populated by calculation methods
    private double topSpeed;                // km/h
    private double accelerationTime0To100;  // seconds
    private String accelerationProfile;     // "Aggressive", "Balanced", "Conservative"
    private int handlingRating;             // 1-10
    private int corneringAbilityRating;     // 1-100
    private double baseFuelConsumptionPerLap; // liters

    // Preset allowed values for car weight and fuel tank capacity
    public static final java.util.List<Double> ALLOWED_WEIGHTS = java.util.Arrays.asList(900.0, 1000.0, 1100.0);
    public static final java.util.List<Double> ALLOWED_FUEL_CAPACITIES = java.util.Arrays.asList(60.0, 70.0, 80.0);

    public RaceCar(Engine engine, Tyres tyres, AerodynamicKit aeroKit, double carWeight, double fuelTankCapacity) {
        if (!ALLOWED_WEIGHTS.contains(carWeight)) {
            throw new IllegalArgumentException("Car weight must be one of the allowed preset values: " + ALLOWED_WEIGHTS);
        }
        if (!ALLOWED_FUEL_CAPACITIES.contains(fuelTankCapacity)) {
            throw new IllegalArgumentException("Fuel tank capacity must be one of the allowed preset values: " + ALLOWED_FUEL_CAPACITIES);
        }
        this.engine = engine;
        this.tyres = tyres;
        this.aeroKit = aeroKit;
        this.carWeight = carWeight;
        this.fuelTankCapacity = fuelTankCapacity;

        // Initialize current status
        this.currentFuel = this.fuelTankCapacity; // Start with a full tank
        this.currentTyreWear = 0.0;               // Start with fresh tyres

        // Populate performance attributes by calling calculation methods
        this.topSpeed = calculateTopSpeed();
        this.accelerationTime0To100 = calculate0To100AccelerationTime();
        this.accelerationProfile = determineAccelerationProfile();
        this.handlingRating = calculateHandling();
        this.corneringAbilityRating = calculateCorneringAbility();
        this.baseFuelConsumptionPerLap = calculateBaseFuelConsumptionPerLap();
    }

    // Getters for the component instances
    public Engine getEngine() {
        return engine;
    }

    public Tyres getTyres() {
        return tyres;
    }

    public AerodynamicKit getAeroKit() {
        return aeroKit;
    }

    // Getters for car-specific parameters
    public double getCarWeight() {
        return carWeight;
    }

    public double getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    // Getters and Setters for current status attributes
    public double getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = Math.max(0, currentFuel); // Ensure fuel doesn't go below 0
    }

    public double getCurrentTyreWear() {
        return currentTyreWear;
    }

    public void setCurrentTyreWear(double currentTyreWear) {
        this.currentTyreWear = Math.min(1.0, Math.max(0, currentTyreWear)); // Clamp between 0.0 and 1.0
    }

    // Getters for the calculated performance attributes
    public double getTopSpeed() {
        return topSpeed;
    }

    public double getAccelerationTime0To100() {
        return accelerationTime0To100;
    }

    public String getAccelerationProfile() {
        return accelerationProfile;
    }

    public int getHandlingRating() {
        return handlingRating;
    }

    public int getCorneringAbilityRating() {
        return corneringAbilityRating;
    }

    public double getBaseFuelConsumptionPerLap() {
        return baseFuelConsumptionPerLap;
    }

    // Helper methods for UI integration
    public static java.util.List<Double> getAllowedWeights() {
        return ALLOWED_WEIGHTS;
    }
    public static java.util.List<Double> getAllowedFuelCapacities() {
        return ALLOWED_FUEL_CAPACITIES;
    }

    private double calculateTopSpeed() {
        // Top speed is now primarily determined by the aero kit, but slightly influenced by the engine's power.
        return this.aeroKit.getTopSpeed() + (this.engine.getPowerRating() / 100.0);
    }

    private double calculate0To100AccelerationTime() {
        return (this.carWeight / engine.getPowerRating()) * 5.0;
    }

    private String determineAccelerationProfile() {
        double powerToWeightRatio = engine.getPowerRating() / this.carWeight;
        if (powerToWeightRatio > 0.45) {
            return "Aggressive";
        } else if (powerToWeightRatio > 0.25) {
            return "Balanced";
        } else {
            return "Conservative";
        }
    }

    private int calculateHandling() {
        // Handling is now primarily determined by the aero kit's cornering ability, slightly adjusted by tyre grip.
        return (int) Math.round(this.aeroKit.getCorneringAbility() * (1 + (this.tyres.getGripLevel() - 5) / 50.0));
    }

    private int calculateCorneringAbility() {
        // The detailed cornering ability rating is now primarily from the aero kit, adjusted by tyre grip.
        double baseAbility = this.aeroKit.getCorneringAbility() * 10; // Scale to 1-100
        double gripModifier = 1 + (this.tyres.getGripLevel() - 5) / 20.0; // Tyres give a +/- bonus
        return (int) Math.round(Math.min(100.0, Math.max(1.0, baseAbility * gripModifier)));
    }

    private double calculateBaseFuelConsumptionPerLap() {
        // Fuel consumption is now a combination of the engine's efficiency and the aero kit's efficiency.
        // We can average them or make one a primary factor. Let's average them.
        double combinedEfficiency = (this.engine.getFuelEfficiency() + this.aeroKit.getFuelEfficiency()) / 2.0;
        return 30.0 / combinedEfficiency;
    }

    @Override
    public String toString() {
        return "RaceCar configured with:\n" +
                "  " + engine + "\n" +
                "  " + tyres + "\n" +
                "  " + aeroKit + "\n" +
                "  Car Weight: " + String.format("%.1f", carWeight) + " kg\n" +
                "  Fuel Tank Capacity: " + String.format("%.1f", fuelTankCapacity) + " L\n" +
                "--- Current Status ---" + "\n" +
                "  Current Fuel: " + String.format("%.2f", currentFuel) + " L\n" +
                "  Current Tyre Wear: " + String.format("%.2f%%", currentTyreWear * 100) + "\n" +
                "--- Performance Metrics ---" + "\n" +
                "  Top Speed: " + String.format("%.1f", topSpeed) + " km/h" + "\n" +
                "  0-100 km/h: " + String.format("%.2f", accelerationTime0To100) + " s" + "\n" +
                "  Acceleration Profile: " + accelerationProfile + "\n" +
                "  Handling Rating (1-10): " + handlingRating + "/10" + "\n" +
                "  Cornering Ability (1-100): " + corneringAbilityRating + "/100" + "\n" +
                "  Base Fuel Consumption (per 'standard' lap): " + String.format("%.2f", baseFuelConsumptionPerLap) + " L";
    }
}