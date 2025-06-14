package racesimulation;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class PitStopDataTest {

    static PitStopData pitStopTestOne;
    static PitStopData pitStopTestTwo;
    static PitStopData pitStopTestThree;

    @BeforeEach
    void setUp() {
        pitStopTestOne = new PitStopData(50, "Lack of Fuel");
        pitStopTestTwo = new PitStopData(30, "Tyre worn out");
    }

    @DisplayName("WB_PTSD_01: Testing the Constructor. (Criticality: Critical)")
    @Test
    public void testConstructor() {
        assertAll(
                () -> assertEquals(50, pitStopTestOne.getLapNumber()),
                () -> assertEquals("Lack of Fuel", pitStopTestOne.getReason())
        );
    }

    @DisplayName("WB_PTSD_02: Test getter method for Lap Number. (Criticality: Core)")
    @Test
    public void testGetterLapNumber() {
        assertEquals(30, pitStopTestTwo.getLapNumber());
    }

    @DisplayName("WB_PTSD_03: Test getter method for Reason. (Criticality: Core)")
    @Test
    public void testGetterReason() {
        assertEquals("Tyre worn out", pitStopTestTwo.getReason());
    }

    @DisplayName("WB_PTSD_04: Test property value for Lap Number Property. (Criticality: Core)")
    @Test
    public void testLapNumberProperty() {
        SimpleIntegerProperty testNumbers = pitStopTestTwo.lapNumberProperty();
        assertEquals(30, testNumbers.getValue());
        assertNotEquals(29, testNumbers.getValue());
        assertNotEquals(31, testNumbers.getValue());
    }

    @DisplayName("WB_PTSD_05: Test property value for Reason Property. (Criticality: Core)")
    @Test
    public void testReasonProperty() {
        SimpleStringProperty testReason = pitStopTestTwo.reasonProperty();
        assertEquals("Tyre worn out", testReason.get());
    }

    @ParameterizedTest(name = "WB_PTSD_06: Test null and empty property value for Reason Property. (Criticality: Core)")
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