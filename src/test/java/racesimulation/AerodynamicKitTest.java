package racesimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AerodynamicKitTest {

    static AerodynamicKit aeroKitTestOne;
    static AerodynamicKit aeroKitTestTwo;

    @BeforeEach
    void setUp () {
        aeroKitTestOne = new AerodynamicKit("Hello", 20.00,
                3, 1.0, 1.2);
        aeroKitTestTwo = new AerodynamicKit("Testing", 10.00,
                4, 0.8, 1.0);
    }

    @DisplayName("WB_ADKT_01: Testing the Constructor. (Criticality: Critical)")
    @Test
    public void testConstructor () {
        assertAll(
                () -> assertEquals("Hello", aeroKitTestOne.getKitName()),
                () -> assertEquals(20.00, aeroKitTestOne.getDragCoefficient()),
                () -> assertEquals(3, aeroKitTestOne.getDownforceValue()),
                () -> assertEquals(1.0, aeroKitTestOne.getImpactOnTopSpeed()),
                () -> assertEquals(1.2,aeroKitTestOne.getImpactOnCornering())
        );
    }

    @DisplayName("WB_ADKT_02: Test getter method for Kit Name. (Criticality: Core)")
    @Test
    public void getKitName() {
        assertEquals("Testing", aeroKitTestTwo.getKitName());
    }

    @DisplayName("WB_ADKT_03: Test getter method for Drag Coefficient. (Criticality: Core)")
    @Test
    public void getDragCoefficient() {
        assertEquals(10.00, aeroKitTestTwo.getDragCoefficient());
    }

    @DisplayName("WB_ADKT_04: Test getter method for Downforce Value. (Criticality: Core)")
    @Test
    public void getDownforceValue() {
        assertEquals(4, aeroKitTestTwo.getDownforceValue());
    }

    @DisplayName("WB_ADKT_05: Test getter method for Impact On Top Speed. (Criticality: Core)")
    @Test
    public void getImpactOnTopSpeed() {
        assertEquals(0.8, aeroKitTestTwo.getImpactOnTopSpeed());
    }

    @DisplayName("WB_ADKT_06: Test getter method for Impact On Cornering. (Criticality: Core)")
    @Test
    public void getImpactOnCornering() {
        assertEquals(1.0, aeroKitTestTwo.getImpactOnCornering());
    }

    @DisplayName("WB_ADKT_07: Test to string method. (Criticality: Additional")
    @Test
    public void testToString() {
        String expectationTestOne = "AerodynamicKit{" +
                "kitName='Hello\'" +
                ", dragCoefficient=20.00" +
                ", downforceValue=3 N" +
                ", impactOnTopSpeed=1.00x" +
                ", impactOnCornering=1.20x" +
                '}';
        String expectationTestTwo = "AerodynamicKit{" +
                "kitName='Testing\'" +
                ", dragCoefficient=10.00" +
                ", downforceValue=4 N" +
                ", impactOnTopSpeed=0.80x" +
                ", impactOnCornering=1.00x" +
                '}';
        assertEquals(expectationTestOne, aeroKitTestOne.toString());
        assertEquals(expectationTestTwo, aeroKitTestTwo.toString());
    }
}