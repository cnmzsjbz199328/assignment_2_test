package racesimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RaceCarTest {

    static RaceCar raceCarTestOne;
    static RaceCar raceCarTestTwo;
    static Engine testEngine;
    static Tyres testTyres;
    static AerodynamicKit testAerodynamicKit;

    @BeforeEach
    void setUp() {
        testEngine = new Engine("Max", 3, 1.0, 1.2);
        testTyres = new Tyres("Compound", 95, 1.0, 1.0, 10.0);
        testAerodynamicKit = new AerodynamicKit("Standard Kit", 0.30, 200,
                250, 12, 6);
        raceCarTestOne = new RaceCar(testEngine, testTyres, testAerodynamicKit,
                900.00, 60.0);
        raceCarTestTwo = new RaceCar(testEngine, testTyres, testAerodynamicKit,
                1100.00, 80.0);
    }

    @DisplayName("WB_RCT_01 - Critical: Testing constructor and getter (Engine Parameters).")
    @Test
    public void testConstructorEngineParameters() {
        // Test Engine parameters
        assertAll(
                () -> assertEquals("Max", raceCarTestOne.getEngine().getName()),
                () -> assertEquals(3, raceCarTestOne.getEngine().getPowerRating()),
                () -> assertEquals(1.0, raceCarTestOne.getEngine().getFuelEfficiency()),
                () -> assertEquals(1.2, raceCarTestOne.getEngine().getReliability())
        );
    }

    @DisplayName("WB_RCT_02 - Critical: Testing constructor and getter (Tyre Parameters).")
    @Test
    public void testConstructorTyreParameters() {
        TemperatureRange tempRangeOne = new TemperatureRange(1.0, 10.0);
        assertAll(
                () -> assertEquals("Compound", raceCarTestOne.getTyres().getCompound()),
                () -> assertEquals(95, raceCarTestOne.getTyres().getGripLevel()),
                () -> assertEquals(1.0, raceCarTestOne.getTyres().getWearRate()),
                () -> assertEquals(tempRangeOne.toString(), raceCarTestOne.getTyres().getOptimalTempRange().toString())
        );
    }

    @DisplayName("WB_RCT_03 - Critical: Testing constructor and getter (AerodynamicKit Parameters).")
    @Test
    public void testConstructorAerodynamicKitParameters() {
        assertAll(
                () -> assertEquals("Standard Kit", raceCarTestOne.getAeroKit().getKitName()),
                () -> assertEquals(0.30, raceCarTestOne.getAeroKit().getDragCoefficient()),
                () -> assertEquals(200, raceCarTestOne.getAeroKit().getDownforceValue()),
                () -> assertEquals(250, raceCarTestOne.getAeroKit().getTopSpeed()),
                () -> assertEquals(12, raceCarTestOne.getAeroKit().getFuelEfficiency()),
                () -> assertEquals(6, raceCarTestOne.getAeroKit().getCorneringAbility())
        );
    }

    @DisplayName("WB_RCT_04 - Critical: Testing constructor and getter (Other Parameters).")
    @Test
    public void testConstructorOtherParameters() {
        assertAll(
                () -> assertEquals(900.00, raceCarTestOne.getCarWeight()),
                () -> assertEquals(60.0, raceCarTestOne.getFuelTankCapacity()),
                () -> assertEquals(60.0, raceCarTestOne.getCurrentFuel()),
                () -> assertEquals(0.0, raceCarTestOne.getCurrentTyreWear())
        );
    }

    @DisplayName("WB_RCT_05 - Core: Testing constructor (IllegalArgumentException for weight).")
    @Test
    public void testConstructorInvalidArgumentExceptionCarWeight() {
        assertThrows(IllegalArgumentException.class, () -> {
            RaceCar raceCarTestThree = new RaceCar(testEngine, testTyres, testAerodynamicKit, 899, 60);
        });
    }

    @DisplayName("WB_RCT_06 - Core: Testing constructor (IllegalArgumentException for Fuel Capacity).")
    @Test
    public void testConstructorInvalidArgumentExceptionFuelCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            RaceCar raceCarTestFour = new RaceCar(testEngine, testTyres, testAerodynamicKit, 900, 59);
        });
    }

    @ParameterizedTest(name = "WB_RCT_06 - Critical: Testing setters for currentFuel method.")
    @CsvSource({"80.0", "60.0", "40.0", "20.0", "0.1", "-0.1", "-1.0"})
    public void testSetterCurrentFuel(double testNumbers) {
        raceCarTestTwo.setCurrentFuel(testNumbers);
        if (testNumbers > 0) {
            assertEquals(testNumbers, raceCarTestTwo.getCurrentFuel());
        } else {
            assertEquals(0, raceCarTestTwo.getCurrentFuel());
        }
    }

    @ParameterizedTest(name = "WB_RCT_07 - Critical: Testing setters for currentTyreWear method.")
    @CsvSource({"80.0", "1.1", "0.9", "0.5", "0.1", "-0.1", "-1.0"})
    public void testSetterCurrentTyreWear(double testNumbers) {
        raceCarTestTwo.setCurrentTyreWear(testNumbers);
        if (testNumbers > 0 && testNumbers < 1) {
            assertEquals(testNumbers, raceCarTestTwo.getCurrentTyreWear());
        } else if (testNumbers > 1) {
            assertEquals(1, raceCarTestTwo.getCurrentTyreWear());
        } else {
            assertEquals(0, raceCarTestTwo.getCurrentTyreWear());
        }
    }

    @DisplayName("WB_RCT_07 - Critical: Testing method calculate top speed and subsequent getter method for" +
            "getTopSpeed.")
    @Test
    public void testMethodCalculateTopSpeed() {
        double testCalculation = testAerodynamicKit.getTopSpeed() + (testEngine.getPowerRating()/100.0);
        assertEquals(testCalculation, raceCarTestTwo.getTopSpeed());
    }

    @DisplayName("WB_RCT_08 - Critical: Testing method calculate acceleration time and subsequent getter method for" +
            "getAccelerationTime0To100.")
    @Test
    public void testMethodCalculateAccelerationTime() {
        double testCalculation = (raceCarTestTwo.getCarWeight() / testEngine.getPowerRating()) * 5;
        assertEquals(testCalculation, raceCarTestTwo.getAccelerationTime0To100());
    }

    @DisplayName("WB_RCT_09 - Critical: Testing method for Aggressive calculation of powerToWeightRatio in" +
            "method determineAccelerationProfile.")
    @Test
    public void testAggressiveAccelerationProfile() {
        Engine newTestEngine = new Engine("Max", 500, 1.0, 1.2);
        RaceCar raceCarTestFive = new RaceCar(newTestEngine, testTyres, testAerodynamicKit, 1000, 60);
        assertEquals("Aggressive", raceCarTestFive.getAccelerationProfile());
    }

    @DisplayName("WB_RCT_10 - Critical: Testing method for Balanced calculation of powerToWeightRatio in" +
            "method determineAccelerationProfile.")
    @Test
    public void testBalancedAccelerationProfile() {
        Engine newTestEngine = new Engine("Max", 450, 1.0, 1.2);
        RaceCar raceCarTestFive = new RaceCar(newTestEngine, testTyres, testAerodynamicKit, 1000, 60);
        assertEquals("Balanced", raceCarTestFive.getAccelerationProfile());
    }

    @DisplayName("WB_RCT_11 - Critical: Testing method for Conservative calculation of powerToWeightRatio in" +
            "method determineAccelerationProfile.")
    @Test
    public void testConservativeAccelerationProfile() {
        assertEquals("Conservative", raceCarTestTwo.getAccelerationProfile());
    }

    @DisplayName("WB_RCT_12 - Critical: Testing calculation for handling to be between 1 to 10.")
    @Test
    public void testCalculationHandling() {
        int expectedNumber = raceCarTestTwo.getHandlingRating();
        assertTrue(expectedNumber >= 1 && expectedNumber <= 10);
    }

    @DisplayName("WB_RCT_13 - Critical: Testing calculation for CorneringAbility to be between 1 to 99.")
    @Test
    public void testCalculationCorneringAbility() {
        int expectedNumber = raceCarTestTwo.getCorneringAbilityRating();
        assertTrue(expectedNumber > 1 && expectedNumber < 100);
    }

    @DisplayName("WB_RCT_14 - Critical: Testing calculation for Fuel Consumption per Lap.")
    @Test
    public void testCalculationFuelConsumptionPerLap() {
        double expectedNumber = 30 / ((testEngine.getFuelEfficiency() + testAerodynamicKit.getFuelEfficiency()) / 2);
        assertEquals(expectedNumber, raceCarTestTwo.getBaseFuelConsumptionPerLap());
    }

    @DisplayName("WB_RCT_14 - Critical: Testing to String method")
    @Test
    public void testToString() {
        String expected = "RaceCar configured with:\n" +
                "  " + testEngine + "\n" +
                "  " + testTyres + "\n" +
                "  " + testAerodynamicKit + "\n" +
                "  Car Weight: " + String.format("%.1f", raceCarTestTwo.getCarWeight()) + " kg\n" +
                "  Fuel Tank Capacity: " + String.format("%.1f", raceCarTestTwo.getFuelTankCapacity()) + " L\n" +
                "--- Current Status ---" + "\n" +
                "  Current Fuel: " + String.format("%.2f", raceCarTestTwo.getCurrentFuel()) + " L\n" +
                "  Current Tyre Wear: " + String.format("%.2f%%", raceCarTestTwo.getCurrentTyreWear() * 100) + "\n" +
                "--- Performance Metrics ---" + "\n" +
                "  Top Speed: " + String.format("%.1f", raceCarTestTwo.getTopSpeed()) + " km/h" + "\n" +
                "  0-100 km/h: " + String.format("%.2f", raceCarTestTwo.getAccelerationTime0To100()) + " s" + "\n" +
                "  Acceleration Profile: " + raceCarTestTwo.getAccelerationProfile() + "\n" +
                "  Handling Rating (1-10): " + raceCarTestTwo.getHandlingRating() + "/10" + "\n" +
                "  Cornering Ability (1-100): " + raceCarTestTwo.getCorneringAbilityRating() + "/100" + "\n" +
                "  Base Fuel Consumption (per 'standard' lap): " + String.format("%.2f", raceCarTestTwo.getBaseFuelConsumptionPerLap()) + " L";
        assertEquals(expected, raceCarTestTwo.toString());
    }
}