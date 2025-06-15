package racesimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[WB_ADK]: Test for AerodynamicKit class")
class AerodynamicKitTest {

    static AerodynamicKit aeroKitTestOne;
    static AerodynamicKit aeroKitTestTwo;
    static AerodynamicKit aeroKitTestThree;

    @BeforeEach
    void setUp () {
        aeroKitTestOne = new AerodynamicKit("Hello", 20.00,
                3, 200, 5.0, 1);
        aeroKitTestTwo = new AerodynamicKit("Testing", 10.00,
                4,400, 12.0, 2);
    }

    @DisplayName("[WB_ADK_01 - Critical]: Testing the Constructor.")
    @Test
    public void testConstructor () {
        assertAll(
                () -> assertEquals("Hello", aeroKitTestOne.getKitName()),
                () -> assertEquals(20.00, aeroKitTestOne.getDragCoefficient()),
                () -> assertEquals(3, aeroKitTestOne.getDownforceValue()),
                () -> assertEquals(200, aeroKitTestOne.getTopSpeed()),
                () -> assertEquals(5.0, aeroKitTestOne.getFuelEfficiency()),
                () -> assertEquals(1, aeroKitTestOne.getCorneringAbility())
        );
    }

    @DisplayName("[WB_ADK_02 - Additional]: Test getter method for Kit Name.")
    @Test
    public void testGetterKitName() {
        assertEquals("Testing", aeroKitTestTwo.getKitName());
    }

    @DisplayName("[WB_ADK_03 - Additional]: Test getter method for Drag Coefficient.")
    @Test
    public void testGetterDragCoefficient() {
        assertEquals(10.00, aeroKitTestTwo.getDragCoefficient());
    }

    @DisplayName("[WB_ADK_04 - Additional]: Test getter method for Downforce Value.")
    @Test
    public void testGetterDownforceValue() {
        assertEquals(4, aeroKitTestTwo.getDownforceValue());
    }

    @DisplayName("[WB_ADK_05 - Critical]: Test getter method for Top Speed.")
    @Test
    public void testGetterTopSpeed() {
        assertEquals(400, aeroKitTestTwo.getTopSpeed());
    }

    @DisplayName("[WB_ADK_06 - Critical]: Test getter method for Fuel Efficiency.")
    @Test
    public void testGetterFuelEfficiency() {
        assertEquals(12.0, aeroKitTestTwo.getFuelEfficiency());
    }

    @DisplayName("[WB_ADK_07 - Critical]: Test getter method for Cornering Ability.")
    @Test
    public void testGetterCorneringAbility() {
        assertEquals(2, aeroKitTestTwo.getCorneringAbility());
    }

    @ParameterizedTest(name = "[WB_ADK_08 - Critical]: Test method for getting all available kits stored under getAllSpecKits.")
    @CsvSource({"Standard Kit, 0.30, 200, 250, 12, 6", "Downforce-Focused Kit, 0.35, 350, 220, 10, 9",
            "Low-Drag Kit, 0.25, 150, 280, 14, 5", "Adjustable Aero Kit, 0.31, 250, 250, 12, 8",
            "Ground Effect Kit, 0.27, 400, 240, 12, 8", "Drag Reduction System, 0.25, 200, 290, 13, 6",
            "Wet Weather Kit, 0.32, 220, 230, 11, 7", "Hybrid Kit, 0.29, 260, 260, 12, 7",
            "Extreme Aero Kit, 0.40, 500, 200, 9, 10"})
    public void testGetAllSpecKits(String nameOfKits, double coefficientDrag, int valueOfDownforce, int maxSpeed,
                                   double efficientFuel, int abilityToCorner) {
        Map<String, AerodynamicKit> allAeroKits = AerodynamicKit.getAllSpecKits();
        aeroKitTestThree = allAeroKits.get(nameOfKits);

        assertAll(
                () -> assertEquals(9, allAeroKits.size()),
                () -> assertTrue(allAeroKits.containsKey(nameOfKits)),
                () -> assertEquals(nameOfKits, aeroKitTestThree.getKitName()),
                () -> assertEquals(coefficientDrag, aeroKitTestThree.getDragCoefficient()),
                () -> assertEquals(valueOfDownforce, aeroKitTestThree.getDownforceValue()),
                () -> assertEquals(maxSpeed, aeroKitTestThree.getTopSpeed()),
                () -> assertEquals(efficientFuel, aeroKitTestThree.getFuelEfficiency()),
                () -> assertEquals(abilityToCorner, aeroKitTestThree.getCorneringAbility())
        );
    }

    @DisplayName("[WB_ADK_09 - Critical]: Test to string method.")
    @Test
    public void testToString() {
        assertEquals("Hello", aeroKitTestOne.toString());
        assertEquals("Testing", aeroKitTestTwo.toString());
    }

    @DisplayName("[WB_ADK_10 - Core]: Testing if kit name sets to null and empty.")
    @Test
    public void testNullAndEmptyKitName() {
        AerodynamicKit aeroKitTestFour = new AerodynamicKit(null, 20.0, 1,
                10, 1.2, 2);
        AerodynamicKit aeroKitTestFive = new AerodynamicKit("", 10.0, 25,
                15, 62.1, 5);
        assertNull(aeroKitTestFour.getKitName());
        assertEquals("", aeroKitTestFive.getKitName());
    }

}