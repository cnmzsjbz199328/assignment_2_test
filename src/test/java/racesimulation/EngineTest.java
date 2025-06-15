package racesimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[WB_ENG]: Test for Engine class")
class EngineTest {
    static Engine engineTestOne;
    static Engine engineTestTwo;

    @BeforeEach
    void setUp () {
        engineTestOne = new Engine("Max", 3, 1.0, 1.2);
        engineTestTwo = new Engine("Verstappen", 4, 0.8, 1.0);
    }

    @DisplayName("[WB_ENG_01 - Critical]: Testing the Constructor.")
    @Test
    public void testConstructor () {
        assertAll(
                () -> assertEquals("Max", engineTestOne.getName()),
                () -> assertEquals(3, engineTestOne.getPowerRating()),
                () -> assertEquals(1.0, engineTestOne.getFuelEfficiency()),
                () -> assertEquals(1.2, engineTestOne.getReliability())
        );
    }

    @DisplayName("[WB_ENG_02 - Additional]: Test getter method for Name.")
    @Test
    public void testGetterName() {
        assertEquals("Verstappen", engineTestTwo.getName());
    }

    @DisplayName("[WB_ENG_03 - Additional]: Test getter method for Power Rating.")
    @Test
    public void testGetterPowerRating() {
        assertEquals(4, engineTestTwo.getPowerRating());
    }

    @DisplayName("[WB_ENG_04 - Critical]: Test getter method for Fuel Efficiency.")
    @Test
    public void testGetterFuelEfficiency() {
        assertEquals(0.8, engineTestTwo.getFuelEfficiency());
    }

    @DisplayName("[WB_ENG_05 - Additional]: Test getter method for Reliability.")
    @Test
    public void testGetterReliability() {
        assertEquals(1.0, engineTestTwo.getReliability());
    }

    @DisplayName("[WB_ENG_06 - Critical]: Test to string method. ")
    @Test
    public void testToString() {
        String expectationTestOne = "Engine{" +
                "name='Max\'" + ", powerRating=3 HP" +
                ", fuelEfficiency=1.0 km/l" +
                ", reliability=120%" +
                '}';
        String expectationTestTwo = "Engine{" +
                "name='Verstappen\'" + ", powerRating=4 HP" +
                ", fuelEfficiency=0.8 km/l" +
                ", reliability=100%" +
                '}';
        assertEquals(expectationTestOne, engineTestOne.toString());
        assertEquals(expectationTestTwo, engineTestTwo.toString());
    }
}