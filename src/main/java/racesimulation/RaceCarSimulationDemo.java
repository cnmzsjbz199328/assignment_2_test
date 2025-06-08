package racesimulation;

class RaceCarSimulationDemo {
    public static void main(String[] args) {
        // Create instances of component variations
        Engine standardV6 = new Engine("Standard V6", 300, 7.5, 0.95);
        Engine turbochargedV8 = new Engine("Turbocharged V8", 550, 5.0, 0.88);
        Engine ecoBoostI4 = new Engine("EcoBoost I4", 180, 12.0, 0.99);

        Tyres softCompound = new Tyres("Soft Compound", 95, 0.15, 80, 100);
        Tyres mediumCompound = new Tyres("Medium Compound", 80, 0.08, 90, 110);
        Tyres hardCompound = new Tyres("Hard Compound", 65, 0.03, 100, 120);

        // Specific Aerodynamic Kits Data
        AerodynamicKit standardKit = new AerodynamicKit("Standard Kit", 0.33, 1200, 1.0, 1.0);
        AerodynamicKit downforceFocusedKit = new AerodynamicKit("Downforce-Focused Kit", 0.38, 2800, 0.95, 1.2);
        AerodynamicKit lowDragKit = new AerodynamicKit("Low-Drag Kit", 0.25, 800, 1.1, 0.9);
        AerodynamicKit groundEffectKit = new AerodynamicKit("Ground Effect Kit", 0.30, 3500, 1.02, 1.35);

        // Example Car Weights and Fuel Tank Capacities
        double lightCarWeight = 950.0; // kg
        double mediumCarWeight = 1100.0; // kg
        double heavyCarWeight = 1250.0; // kg

        double smallTank = 60.0;  // L
        double mediumTank = 80.0; // L
        double largeTank = 100.0; // L

        System.out.println("--- Creating a High-Performance RaceCar (Light, Medium Tank) ---");
        RaceCar highPerformanceCar = new RaceCar(turbochargedV8, softCompound, downforceFocusedKit, lightCarWeight, mediumTank);
        System.out.println(highPerformanceCar);

        System.out.println("\n--- Creating a Balanced RaceCar (Medium Weight, Large Tank) ---");
        RaceCar balancedCar = new RaceCar(standardV6, mediumCompound, standardKit, mediumCarWeight, largeTank);
        System.out.println(balancedCar);

        System.out.println("\n--- Creating an Economy/Cruiser RaceCar (Heavy, Small Tank) ---");
        RaceCar economyCar = new RaceCar(ecoBoostI4, hardCompound, lowDragKit, heavyCarWeight, smallTank);
        System.out.println(economyCar);
        
        System.out.println("\n--- Creating a RaceCar with Ground Effect (Medium Weight, Medium Tank) ---");
        RaceCar groundEffectCar = new RaceCar(turbochargedV8, mediumCompound, groundEffectKit, mediumCarWeight, mediumTank);
        System.out.println(groundEffectCar);

        // Accessing new attributes
        System.out.println("\n--- Accessing new attributes for High-Performance Car ---");
        System.out.println("Fuel Tank Capacity: " + highPerformanceCar.getFuelTankCapacity() + " L");
        System.out.println("Car Weight: " + highPerformanceCar.getCarWeight() + " kg");
        System.out.println("Acceleration Profile: " + highPerformanceCar.getAccelerationProfile());
        System.out.println("Cornering Ability Rating: " + highPerformanceCar.getCorneringAbilityRating() + "/100");

        System.out.println("\n--- Accessing new attributes for Balanced Car ---");
        System.out.println("Fuel Tank Capacity: " + balancedCar.getFuelTankCapacity() + " L");
        System.out.println("Car Weight: " + balancedCar.getCarWeight() + " kg");
        System.out.println("Acceleration Profile: " + balancedCar.getAccelerationProfile());
        System.out.println("Cornering Ability Rating: " + balancedCar.getCorneringAbilityRating() + "/100");
    }
}