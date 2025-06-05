**Initial Prompt: Basic Car Component Setup**
*   **Role:** Act as a lead software developer for a racing simulation game.
*   **Task:** Define the initial Java classes for basic car components: Engine, Tyres, and AerodynamicKit.
*   **Requirements:**
    *   Each class should have a constructor.
    *   `Engine` should have attributes for `name` (e.g., "Standard V6", "Turbocharged V8") and a base `power_rating` (integer).
    *   `Tyres` should have attributes for `compound` (e.g., "Soft", "Medium", "Hard") and a base `grip_level` (integer).
    *   `AerodynamicKit` should have an attribute for `kit_name` (e.g., "Basic Aero").
*   **Instructions:** Provide the Java code for these three class definitions. Your response should be a single Java code block containing the definitions for the `Engine`, `Tyres`, and `AerodynamicKit` classes. Each class must include a constructor that initializes its specified attributes using explicit type declarations (e.g., `private String name;`, `private int powerRating;`).

**Prompt 2: Expanding Component Attributes & Performance Impact**
*   **Role:** Act as a game balance designer.
*   **Task:** Modify the existing `Engine`, `Tyres`, and `AerodynamicKit` classes to include more detailed performance-affecting attributes.
*   **Requirements:**
    *   `Engine`: Add `fuel_efficiency` (double, e.g., km/l) and `reliability` (double, 0.0 to 1.0).
    *   `Tyres`: Add `wear_rate` (double, higher means wears faster) and `optimal_temp_range` (e.g., a simple class or two `double` fields for min/max temp, (90, 110) degrees Celsius).
    *   `AerodynamicKit`: Add `drag_coefficient` (double) and `downforce_value` (integer).
    *   Ensure all new attributes are initialized in the constructors.
*   **Instructions:** Update the classes from Prompt 1. Your response should include:
    1.  The complete, updated Java class definitions for `Engine`, `Tyres`, and `AerodynamicKit`, incorporating the new attributes in their constructors (with explicit types).
    2.  Java code defining at least three distinct variations for `Engine` (e.g., "Standard V6", "Turbocharged V8", "EcoBoost I4") and `Tyres` (e.g., "Soft Compound", "Medium Compound", "Hard Compound"). Present these variations either as:
        *   Direct instantiations of the classes (e.g., `Engine engineV6 = new Engine("Standard V6", 300, 7.5, 0.95);`).
        *   Or, as a data structure, such as a `List` of `Map<String, Object>` or a `List` of custom objects (e.g., `List<Engine> engineVariations = new ArrayList<>(); engineVariations.add(new Engine("Standard V6", 300, 7.5, 0.95));`).
    For each variation, assign plausible and distinct values for all its attributes, including the new ones, as exemplified in the prompt's requirements.

**Prompt 3: Defining the RaceCar Class**
*   **Role:** Act as a system architect for the racing simulation.
*   **Task:** Create a `RaceCar` class that aggregates the car components and defines overall performance metrics.
*   **Requirements:**
    *   The `RaceCar` constructor should accept instances of `Engine`, `Tyres`, and `AerodynamicKit`.
    *   It should have attributes for `top_speed` (km/h), `acceleration_time_0_100` (seconds), `handling_rating` (1-10), and `base_fuel_consumption_per_lap` (liters).
    *   Implement placeholder methods `calculate_top_speed()`, `calculate_acceleration()`, `calculate_handling()`, and `calculate_fuel_consumption()`. For now, these methods can return fixed placeholder values (e.g., `top_speed = 200`, `acceleration_time_0_100 = 5.0`).
*   **Instructions:** Define the `RaceCar` class. Show how an instance of `RaceCar` would be created using example component instances (you can use the variations defined in Prompt 2).

**Prompt 4: Defining Race Tracks and Race Conditions**
*   **Role:** Act as a track designer and race event coordinator.
*   **Task:** Define Java classes or data structures to represent `RaceTrack` and `RaceConditions`.
*   **Requirements:**
    *   `RaceTrack`: Attributes should include `name`, `length_km` (float), `number_of_laps` (integer), `tyre_wear_factor` (float, e.g., 1.0 for normal, >1.0 for abrasive), `fuel_consumption_factor` (float, e.g., 1.0 for normal, >1.0 for high consumption).
    *   `RaceConditions`: Attributes should include `weather` ("Dry", "Wet", "Damp"), `air_temperature` (Celsius), `track_temperature` (Celsius).
*   **Instructions:** Provide Java code for these classes/data structures. Create and show data for at least 3 distinct race tracks with varying characteristics (e.g., Monaco - short, twisty, high wear; Monza - long straights, low wear; Silverstone - mix, medium wear).

**Prompt 5: Implementing Initial Performance Calculations in RaceCar**
*   **Role:** Act as a physics engine programmer.
*   **Task:** Implement the logic within the `RaceCar` class methods (`calculate_top_speed()`, `calculate_acceleration()`, `calculate_handling()`, `calculate_fuel_consumption()`) to derive performance metrics based on the equipped components.
*   **Requirements:**
    *   `calculate_top_speed()`: Should be influenced by `Engine.power_rating` and `AerodynamicKit.drag_coefficient`.
    *   `calculate_acceleration()` (for 0-100 km/h time): Should be influenced by `Engine.power_rating` and an assumed fixed `car_weight` (e.g., 1000 kg).
    *   `calculate_handling()`: Should be influenced by `Tyres.grip_level` and `AerodynamicKit.downforce_value`.
    *   `calculate_fuel_consumption()` (base per lap): Should be influenced by `Engine.fuel_efficiency`.
    *   The `RaceCar` attributes (`top_speed`, `acceleration_time_0_100`, etc.) should be populated by calling these calculation methods within the `__init__` or a dedicated update method.
*   **Instructions:** Update the `RaceCar` class with these calculation methods. The formulas can be simplified (e.g., `top_speed = (engine.power_rating / aero_kit.drag_coefficient) * 0.5`) but must show the relationship between components and performance.

**Prompt 6: Incorporating Specific Aerodynamic Kits**
*   **Role:** Act as an aerodynamics specialist.
*   **Task:** Update the `AerodynamicKit` class and create specific instances based on a list: Standard Kit, Downforce-Focused Kit, Low-Drag Kit, and Ground Effect Kit.
*   **Requirements:**
    *   Define a data structure (e.g., a list of dictionaries or a dictionary of objects) representing "Sample parameter values for different aerodynamic kits". This data should include `kit_name`, `drag_coefficient`, `downforce_value`. You should also include qualitative or simple numerical modifiers for `impact_on_top_speed` (e.g., a multiplier or a direct adjustment value) and `impact_on_cornering` (e.g., a multiplier or direct adjustment).
    *   Create plausible values for these attributes for at least: "Standard Kit", "Downforce-Focused Kit", "Low-Drag Kit", and "Ground Effect Kit".
    *   The `AerodynamicKit` class should be able to store these attributes.
    *   Adjust `RaceCar.calculate_handling()` and `RaceCar.calculate_top_speed()` to incorporate the specific impacts/modifiers from these detailed aero kits.
*   **Instructions:** Provide the updated `AerodynamicKit` class, the data structure defining the specific aero kits, and the updated `RaceCar` calculation methods demonstrating how these kits affect performance.

**Prompt 7: Defining Undecided Variables (Fuel Tank, Acceleration Model, Cornering)**
*   **Role:** Act as the lead game designer making decisions on core mechanics.
*   **Task:** Define and integrate: `fuel_tank_capacity`, a refined acceleration representation, and a quantified `cornering_ability_rating`.
*   **Requirements:**
    *   `RaceCar` should have a `fuel_tank_capacity` attribute. This should be a parameter to the `RaceCar` constructor. Provide 3 example capacities (e.g., Small - 60L, Medium - 80L, Large - 100L).
    *   Refine `RaceCar.calculate_acceleration()`: Instead of only a 0-100 time, this method (or a new one like `get_acceleration_profile()`) should determine an acceleration tier (e.g., "Aggressive", "Balanced", "Conservative") based on `Engine.power_rating` and `car_weight` (consider making `car_weight` a `RaceCar` attribute or parameter). The method should return this tier as a string. The `acceleration_time_0_100` can still be calculated as before or be influenced by this tier.
    *   Define `cornering_ability_rating`: This should be a numerical output (e.g., 1-100) calculated by a new method in `RaceCar`, `calculate_cornering_ability()`. It should be derived from `Tyres.grip_level`, `AerodynamicKit.downforce_value`, and any specific cornering modifiers from the `AerodynamicKit` (as defined in Prompt 6).
*   **Instructions:** Update the `RaceCar` class and its methods. Show how to instantiate `RaceCar` with different fuel tank capacities and how the new acceleration tier and cornering ability attributes are calculated and stored/accessed.

**Prompt 8: Basic Race Simulation and Strategy Optimiser Structure**
*   **Role:** Act as the AI programmer for race strategy.
*   **Task:** Outline the structure for a `RaceStrategyOptimiser` class and a basic `simulate_lap` function.
*   **Requirements:**
    *   `RaceStrategyOptimiser` constructor should take a `RaceCar` instance, a `RaceTrack` instance, and `RaceConditions`.
    *   It should have a method `plan_pit_stops()` which, for now, can return a very simple fixed strategy (e.g., pit every 10 laps, return `[10, 20, 30]`).
    *   `simulate_lap(race_car, race_track, race_conditions)` function (can be a method of `RaceStrategyOptimiser` or a standalone function):
        *   This function should calculate `fuel_used_this_lap` (based on `race_car.base_fuel_consumption_per_lap` and `race_track.fuel_consumption_factor`).
        *   It should calculate `tyre_wear_this_lap` (based on `race_car.tyres.wear_rate` and `race_track.tyre_wear_factor`).
        *   It should update the car's current fuel level and tyre condition. Add `current_fuel` and `current_tyre_wear` attributes to `RaceCar` (initialized in `__init__`, e.g., `current_fuel` to `fuel_tank_capacity`, `current_tyre_wear` to 0.0).
*   **Instructions:** Define the `RaceStrategyOptimiser` class structure and the `simulate_lap` function/method signature and its basic internal logic for updating fuel and tyre wear.

**Prompt 9: Implementing Core Strategy Optimisation Logic (Tyre Wear & Fuel)**
*   **Role:** Act as the chief race strategist.
*   **Task:** Enhance the `RaceStrategyOptimiser.plan_pit_stops()` method.
*   **Requirements:**
    *   The method should simulate a full race lap by lap, calling the `simulate_lap` function for each lap.
    *   It needs to consider:
        *   `RaceCar.current_fuel` and `RaceCar.fuel_tank_capacity`.
        *   `RaceCar.current_tyre_wear`, `RaceCar.tyres.wear_rate`, and a `max_tyre_wear_threshold` (e.g., 0.80 or 80%). This threshold can be a constant or a parameter.
        *   The `number_of_laps` for the `RaceTrack`.
    *   The optimiser should decide to pit if:
        *   Predicted fuel for the *next* lap is insufficient (i.e., `current_fuel` < `fuel_used_this_lap`).
        *   `current_tyre_wear` exceeds the `max_tyre_wear_threshold`.
    *   A pit stop (simulated effect):
        *   Resets `race_car.current_fuel` to `race_car.fuel_tank_capacity`.
        *   Resets `race_car.current_tyre_wear` to 0.0.
        *   (For future: allow changing tyre compound).
    *   The output should be a list of lap numbers on which a pit stop is made.
*   **Instructions:** Implement the `plan_pit_stops()` method with this logic. The `simulate_lap` function will be called repeatedly. Assume a fixed time penalty for a pit stop (e.g., 25 seconds) – store this as a constant, but it's not used in lap time calculation yet.

**Prompt 10: JavaFX Application - Core Setup & Input Controls**
    *   **Role:** UI Architect.
    *   **Task:** Establish the foundational JavaFX application and integrate user input controls for selecting car components, track, and race conditions.
    *   **Requirements:**
    *   Create a `Main` class extending `javafx.application.Application` with the standard `start` method and main entry point.
    *   Initialize the `primaryStage` with a title.
    *   Integrate Previous Data: Create an instance of each component variation (e.g., `Engine`, `Tyres`, `AerodynamicKit` variations from Prompt 2/6, `RaceTrack` and `RaceConditions` from Prompt 4). Store these in suitable Java collections (e.g., `List<Engine>`, `List<RaceTrack>`).
    *   Input Controls:
    *   Use `ComboBox<Engine>`, `ComboBox<Tyres>`, `ComboBox<AerodynamicKit>` for selecting car components.
    *   Use `ComboBox<RaceTrack>` for selecting the track.
    *   Use `ComboBox<RaceConditions>` for selecting race conditions.
    *   Make sure the `ComboBox` objects display a meaningful string representation of the selected object (you might need to override `toString()` in your model classes or use a `StringConverter`).
    *   Layout: Arrange these `ComboBox`es within a `VBox` or `GridPane` for a clean input section.
    *   Action Button: Add a `Button` (e.g., "Configure Car & Track", "Start Simulation Setup").
    *   Placeholder Output: Add a simple `Label` or `TextArea` to display a message when the button is clicked (e.g., "Configuration selected!").
    *   Set up a basic `BorderPane` or `VBox` as the root layout for the `Scene`.
    *   **Instructions:** Provide the complete Java code for the `Main` class. You will need to import your existing `Engine`, `Tyres`, `AerodynamicKit`, `RaceCar`, `RaceTrack`, `RaceConditions`, and `RaceStrategyOptimiser` classes. Instantiate a few variations of each model class at the beginning of your `start` method or in a helper method, and populate the `ComboBox`es with them.

**Prompt 11: JavaFX Application - Displaying Calculated Stats & Simulation Progress**
*   **Role:** Interaction Designer.
*   **Task:** Enhance the UI to display the `RaceCar`'s calculated performance metrics and provide real-time updates during a simulated race.
*   **Requirements:**
    *   Display Car Stats: When the "Configure Car & Track" button is clicked (or immediately upon component selection), update a dedicated area in the UI to show the selected `RaceCar`'s calculated `top_speed`, `acceleration_time_0_100`, `handling_rating`, `cornering_ability_rating`, and `base_fuel_consumption_per_lap`. Use `Label`s for these.
    *   Simulation Trigger: Add a new `Button` (e.g., "Start Race Simulation") which, when clicked, initiates the race simulation process using `RaceStrategyOptimiser` and `simulate_lap` logic.
    *   Real-time Race Log:
        *   Use a `TextArea` or a `ListView<String>` to display a lap-by-lap log of the simulation (e.g., "Lap X: Fuel Y Liters, Tyres Z% wear. Pit stop taken." or similar).
        *   Crucially, the simulation loop (from `plan_pit_stops` and `simulate_lap`) must run on a background thread (e.g., using `Task` or `Service` or a simple `Thread`) to prevent the UI from freezing.
        *   All UI updates from the background thread must be wrapped in `Platform.runLater(() -> { ... });` to ensure they happen on the JavaFX Application Thread.
    *   Progress Indicators:
        *   Add a `ProgressBar` to show the overall race progress (e.g., `current_lap` / `total_laps`).
        *   Optionally, add `Label`s to show `current_fuel` and `current_tyre_wear` during the simulation.
*   **Instructions:** Update the `Main` class from Prompt 10. You'll need to define a `RaceCar` instance dynamically based on user selections. Implement the button actions to trigger calculations and the simulation, ensuring proper threading for UI responsiveness.

**Prompt 12: JavaFX Application - Enhanced Pit Stop Display & Styling**
*   **Role:** Visual Designer & UX Enhancer.
*   **Task:** Improve the display of pit stop information and apply basic styling to enhance the user experience.
*   **Requirements:**
    *   Structured Pit Stop Display: Instead of just a `TextArea` for pit stops, use a `TableView<PitStopData>` (or similar generic type) to display the planned or executed pit stops.
        *   Define a simple helper class (e.g., `PitStopData` with fields like `lapNumber` and `reason` (e.g., "Fuel", "Tyres")).
        *   The `TableView` should have columns for "Lap Number" and "Reason for Pit Stop".
        *   Populate this `TableView` with the output from `RaceStrategyOptimiser.plan_pit_stops()` and any actual pit stops taken during the `simulate_lap` loop.
    *   Race Summary: After the simulation completes, display a summary. This could be a `Label` or `TextArea` indicating:
        *   "Race Finished!"
        *   Total pit stops.
        *   Final fuel/tyre status.
    *   Styling (Basic CSS):
        *   Create a simple CSS file (e.g., `style.css`) in the same directory as your `Main` class.
        *   Apply some basic styling to elements (e.g., `Button` color, `Label` font size, `VBox` padding).
        *   Link the CSS file to your `Scene` in the `start` method.
    *   Error Handling/User Feedback: Add simple alerts or status messages for invalid selections (e.g., if a car component is not selected before starting simulation).
    *   Reset Functionality (Optional but recommended): Add a "Reset" button to clear the UI and allow a new simulation.
*   **Instructions:** Provide the updated `Main` class incorporating the `TableView` and summary display. Also, include the content of a basic `style.css` file. Ensure that the `TableView` correctly reflects the pit stop strategy and events during the simulation.

Okay, here is the English version of Prompt 13:

---

**Prompt 13: JavaFX Application - Optimized Layout & Sizing**

*   **Role:** UI/UX Designer.
*   **Task:** Adjust the application's window dimensions and the layout of the core information display areas to make it more spacious, visually clear, and with parallel information display.
*   **Requirements:**
    *   **Initial Window Size:** Set the `primaryStage`'s initial width to at least `1200px` and its height to at least `1000px`.
    *   **Overall Layout:** Use a `BorderPane` as the root layout.
    *   **Top Region Layout:** Place the input controls (`ComboBoxes`, `TextFields`) and action buttons (`Configure`, `Start Simulation`, `Reset`) in the `TOP` region of the `BorderPane`. Arrange these controls vertically within a `VBox`.
    *   **Center Region Layout:**
        *   Within the `CENTER` region of the `BorderPane`, use an `HBox` as the primary container.
        *   Inside this `HBox`, place three main display areas side-by-side:
            1.  **Race Car Performance Stats:** This `VBox` should contain the `RaceCar`'s performance metrics (e.g., `top_speed`, `acceleration_time_0_100`, etc.). Add a clear title `Label` for this section (e.g., "Race Car Performance").
            2.  **Race Simulation Log:** This area should contain the `TextArea` or `ListView` for the lap-by-lap simulation log. Add a clear title `Label` for this section (e.g., "Race Log"). Ensure the `TextArea` or `ListView` can fully utilize the vertical space available in its section.
            3.  **Pit Stop Records:** This area should contain the `TableView` displaying pit stop information. Add a clear title `Label` for this section (e.g., "Pit Stops"). Ensure the `TableView` can fully utilize the vertical space available in its section.
        *   Add appropriate `spacing` and `padding` to the `HBox` and its internal `VBox` elements to provide better visual separation and readability.
    *   **Bottom Region Layout:** Place a `Label` (e.g., `statusLabel`) in the `BOTTOM` region of the `BorderPane` for status and summary messages.
    *   **Increased Area Height:** Ensure the three parallel areas (Performance Stats, Simulation Log, Pit Stops) collectively occupy most of the `CENTER` region's vertical space, and allow their internal `TextArea` and `TableView` to expand vertically.
    *   **User-Friendliness:** Ensure all input controls and buttons remain easily accessible within the new, wider window. Confirm that all existing functionalities (threaded simulation, real-time updates, etc.) still work correctly.
*   **Instructions:** Update the `start` method within your `Main` class. The primary changes will involve configuring the `Scene`'s root layout and the layout within the `TOP` and `CENTER` regions. You might need to adjust the `prefHeight` of the `TextArea` and `TableView` or use `VBox.setVgrow(component, Priority.ALWAYS)` to ensure they expand vertically as desired. Also, consider making minor adjustments to your `style.css` file to complement the new layout.