package racesimulation;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Helper class to hold data for a pit stop, suitable for use in a JavaFX TableView.
 */
public class PitStopData {
    private final SimpleIntegerProperty lapNumber;
    private final SimpleStringProperty reason;

    public PitStopData(int lapNumber, String reason) {
        this.lapNumber = new SimpleIntegerProperty(lapNumber);
        this.reason = new SimpleStringProperty(reason);
    }

    public int getLapNumber() {
        return lapNumber.get();
    }

    public SimpleIntegerProperty lapNumberProperty() {
        return lapNumber;
    }

    public String getReason() {
        return reason.get();
    }

    public SimpleStringProperty reasonProperty() {
        return reason;
    }
}