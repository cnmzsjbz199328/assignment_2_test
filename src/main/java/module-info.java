module com.mycompany.racesimulation { // Replace with your actual module name if different

    // Declare dependencies on required JavaFX modules
    requires javafx.controls;
    requires javafx.fxml; // Keep if you might use FXML later
    requires javafx.graphics;
    requires javafx.base;

    // Open the package containing your main application class to javafx.graphics
    // This is necessary for the JavaFX launcher to access your Main class via reflection
    opens racesimulation to javafx.graphics; // Changed from main.java

    // If you use FXML and your FXML files are in the new package, you might also need:
    // opens racesimulation to javafx.fxml; // Changed from main.java

    // Export the package containing your main application class
    // This makes your main class visible to the launcher
    exports racesimulation; // Changed from main.java

    // If you have other packages that need to be accessed by other modules (e.g., test modules),
    // you might need additional 'exports' or 'opens' statements.
}