module racesimulation {

    // Declare dependencies on required JavaFX modules
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;

    // Open the package containing your main application class to javafx.graphics
    // This is necessary for the JavaFX launcher to access your Main class via reflection
    opens racesimulation to javafx.graphics;

    // Export the package containing your main application class
    // This makes your main class visible to the launcher
    exports racesimulation;

    // If you have other packages that need to be accessed by other modules (e.g., test modules),
    // you might need additional 'exports' or 'opens' statements.
}