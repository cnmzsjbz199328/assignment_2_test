package racesimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RaceConditionsTest {

    static RaceConditions raceConditionsTestOne;
    static RaceConditions raceConditionsTestTwo;
    static RaceConditions raceConditionsTestThree;

    @BeforeEach
    void setUp () {
        raceConditionsTestOne = new RaceConditions("Dry", RaceConditions.Weather.DRY, 20.5,
                35.6, 0.1);
        raceConditionsTestTwo = new RaceConditions("Wet", RaceConditions.Weather.WET, -10.2,
                13.2, 0.5);
        raceConditionsTestThree = new RaceConditions("Damp", RaceConditions.Weather.DAMP, 40.2,
                60.6, 1.0);
    }

    @DisplayName("WB_RCDT_01: Testing the Constructor. (Criticality: Critical)")
    @Test
    public void testConstructor () {
        assertAll(
                () -> assertEquals("Dry", raceConditionsTestOne.getName()),
                () -> assertEquals(RaceConditions.Weather.valueOf("DRY"), raceConditionsTestOne.getWeather()),
                () -> assertEquals(20.5, raceConditionsTestOne.getAirTemperature()),
                () -> assertEquals(35.6,raceConditionsTestOne.getTrackTemperature()),
                () -> assertEquals(0.1, raceConditionsTestOne.getHumidity())
        );
    }

    @DisplayName("WB_RCDT_02: Test valid weather conditions. (Criticality: Critical)")
    @Test
    public void testValidWeather() {
        assertEquals(RaceConditions.Weather.valueOf("DRY"), raceConditionsTestOne.getWeather());
        assertEquals(RaceConditions.Weather.valueOf("WET"), raceConditionsTestTwo.getWeather());
        assertEquals(RaceConditions.Weather.valueOf("DAMP"), raceConditionsTestThree.getWeather());
    }

    @DisplayName("WB_RCDT_03: Test values in Enum Weather method. (Criticality: Critical)")
    @Test
    public void testEnumWeatherValues() {
        RaceConditions.Weather[] weather = RaceConditions.Weather.values();
        assertEquals(3, weather.length);
        assertEquals(RaceConditions.Weather.DRY, weather[0]);
        assertEquals(RaceConditions.Weather.WET, weather[1]);
        assertEquals(RaceConditions.Weather.DAMP, weather[2]);
    }

    @DisplayName("WB_RCDT_04: Test invalid weather conditions. (Criticality: Critical)")
    @Test
    public void testInvalidWeather() {
        assertThrows(IllegalArgumentException.class, () -> {
            RaceConditions.Weather.valueOf("RAINY");
            RaceConditions.Weather.valueOf("CLOUDY");
            RaceConditions.Weather.valueOf("SNOW");
            RaceConditions.Weather.valueOf("ICY");
        });
    }

    @DisplayName("WB_RCDT_05: Test getter method for name. (Criticality: Core)")
    @Test
    public void testGetterName() {
        assertEquals("Damp", raceConditionsTestThree.getName());
    }

    @DisplayName("WB_RCDT_06: Test getter method for Weather. (Criticality: Core)")
    @Test
    public void testGetterWeather() {
        assertEquals(RaceConditions.Weather.DAMP, raceConditionsTestThree.getWeather());
    }

    @DisplayName("WB_RCDT_07: Test getter method for Air Temperature. (Criticality: Core)")
    @Test
    public void testGetterAirTemperature() {
        assertEquals(40.2, raceConditionsTestThree.getAirTemperature());
    }

    @DisplayName("WB_RCDT_08: Test getter method for Track Temperature. (Criticality: Core)")
    @Test
    public void testGetterTrackTemperature() {
        assertEquals(60.6, raceConditionsTestThree.getTrackTemperature());
    }

    @DisplayName("WB_RCDT_09: Test getter method for Humidity. (Criticality: Core)")
    @Test
    public void testGetterHumidity() {
        assertEquals(1.0, raceConditionsTestThree.getHumidity());
    }

    @ParameterizedTest(name = "WB_RCDT_10: Test edge cases for humidity. (Criticality: Core)")
    @CsvSource({"-0.1", "1.1"})
    public void testEdgeCasesHumidity(double testHumidity) {
        assertThrows(IllegalArgumentException.class, () ->
                new RaceConditions("Dry", RaceConditions.Weather.DRY, 15.0,
                        20.0, testHumidity));
    }

    @DisplayName("WB_RCDT_011: Test to string method. (Criticality: Additional")
    @Test
    public void testToString() {
        String expectationTestOne = "Dry (Air: 21°C, Track: 36°C)";
        String expectationTestTwo = "Wet (Air: -10°C, Track: 13°C)";
        String expectationTestThree = "Damp (Air: 40°C, Track: 61°C)";
        assertEquals(expectationTestOne, raceConditionsTestOne.toString());
        assertEquals(expectationTestTwo, raceConditionsTestTwo.toString());
        assertEquals(expectationTestThree, raceConditionsTestThree.toString());
    }
}