package racesimulation;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[WB_PSD]: Test for PitStopData class")
class PitStopDataTest {

    static PitStopData pitStopTestOne;
    static PitStopData pitStopTestTwo;
    static PitStopData pitStopTestThree;

    @BeforeEach
    void setUp() {
        pitStopTestOne = new PitStopData(50, "Lack of Fuel");
        pitStopTestTwo = new PitStopData(30, "Tyre worn out");
    }

    @DisplayName("[WB_PTS_01 - Critical]: Testing the Constructor.")
    @Test
    public void testConstructor() {
        assertAll(
                () -> assertEquals(50, pitStopTestOne.getLapNumber()),
                () -> assertEquals("Lack of Fuel", pitStopTestOne.getReason())
        );
    }

    @DisplayName("[WB_PTS_02 - Additional]: Test getter method for Lap Number.")
    @Test
    public void testGetterLapNumber() {
        assertEquals(30, pitStopTestTwo.getLapNumber());
    }

    @DisplayName("[WB_PTS_03 - Additional]: Test getter method for Reason.")
    @Test
    public void testGetterReason() {
        assertEquals("Tyre worn out", pitStopTestTwo.getReason());
    }

    @DisplayName("[WB_PTS_04 - Core]: Test property value for Lap Number Property.")
    @Test
    public void testLapNumberProperty() {
        SimpleIntegerProperty testNumbers = pitStopTestTwo.lapNumberProperty();
        assertAll(
                () -> assertEquals(30, testNumbers.getValue()),
                () -> assertNotEquals(29, testNumbers.getValue()),
                () -> assertNotEquals(31, testNumbers.getValue())
        );
    }

    @DisplayName("[WB_PTS_05 - Core]: Test property value for Reason Property.")
    @Test
    public void testReasonProperty() {
        SimpleStringProperty testReason = pitStopTestTwo.reasonProperty();
        assertEquals("Tyre worn out", testReason.get());
    }

    @ParameterizedTest(name = "[WB_PTS_06 - Core]: Test null and empty property value for Reason Property.")
    @NullAndEmptySource
    public void testNullAndEmptyReasonProperty(String test) {
        pitStopTestThree = new PitStopData(100, test);
        SimpleStringProperty nullAndEmptyTestReason = pitStopTestThree.reasonProperty();
        if (nullAndEmptyTestReason.get() == null) {
            assertNull(nullAndEmptyTestReason.get());
        } else {
            assertTrue(nullAndEmptyTestReason.get().isEmpty());
        }
    }
}