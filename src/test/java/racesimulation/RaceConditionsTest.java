package racesimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[WB_RCD]: Test for RaceConditions class")
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

    @DisplayName("[WB_RCD_01 - Critical]: Testing the Constructor.")
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

    @DisplayName("[WB_RCD_02 - Critical]: Test valid weather conditions.")
    @Test
    public void testValidWeather() {
        assertAll(
                () -> assertEquals(RaceConditions.Weather.valueOf("DRY"), raceConditionsTestOne.getWeather()),
                () -> assertEquals(RaceConditions.Weather.valueOf("WET"), raceConditionsTestTwo.getWeather()),
                () -> assertEquals(RaceConditions.Weather.valueOf("DAMP"), raceConditionsTestThree.getWeather())
        );
    }

    @DisplayName("[WB_RCD_03 - Critical]: Test values in Enum Weather method.")
    @Test
    public void testEnumWeatherValues() {
        RaceConditions.Weather[] weather = RaceConditions.Weather.values();
        assertAll(
                () -> assertEquals(3, weather.length),
                () -> assertEquals(RaceConditions.Weather.DRY, weather[0]),
                () -> assertEquals(RaceConditions.Weather.WET, weather[1]),
                () -> assertEquals(RaceConditions.Weather.DAMP, weather[2])
        );
    }

    @DisplayName("[WB_RCD_04 - Core]: Test invalid weather conditions.")
    @Test
    public void testInvalidWeather() {
        assertThrows(IllegalArgumentException.class, () -> {
            RaceConditions.Weather.valueOf("RAINY");
            RaceConditions.Weather.valueOf("CLOUDY");
            RaceConditions.Weather.valueOf("SNOW");
            RaceConditions.Weather.valueOf("ICY");
        });
    }

    @DisplayName("[WB_RCD_05 - Critical]: Test getter method for name.")
    @Test
    public void testGetterName() {
        assertEquals("Damp", raceConditionsTestThree.getName());
    }

    @DisplayName("[WB_RCD_06 - Critical]: Test getter method for Weather.")
    @Test
    public void testGetterWeather() {
        assertEquals(RaceConditions.Weather.DAMP, raceConditionsTestThree.getWeather());
    }

    @DisplayName("[WB_RCD_07 - Critical]: Test getter method for Air Temperature.")
    @Test
    public void testGetterAirTemperature() {
        assertEquals(40.2, raceConditionsTestThree.getAirTemperature());
    }

    @DisplayName("[WB_RCD_08 - Additional]: Test getter method for Track Temperature.")
    @Test
    public void testGetterTrackTemperature() {
        assertEquals(60.6, raceConditionsTestThree.getTrackTemperature());
    }

    @DisplayName("[WB_RCD_09 - Additional]: Test getter method for Humidity.")
    @Test
    public void testGetterHumidity() {
        assertEquals(1.0, raceConditionsTestThree.getHumidity());
    }

    @ParameterizedTest(name = "[WB_RCD_10 - Core]: Test edge cases for humidity.")
    @CsvSource({"-0.1", "1.1"})
    public void testEdgeCasesHumidity(double testHumidity) {
        assertThrows(IllegalArgumentException.class, () ->
                new RaceConditions("Dry", RaceConditions.Weather.DRY, 15.0,
                        20.0, testHumidity));
    }

    @DisplayName("[WB_RCD_11 - Critical]: Test to string method.")
    @Test
    public void testToString() {
        String expectationTestOne = "Dry (Air: 21°C, Track: 36°C)";
        String expectationTestTwo = "Wet (Air: -10°C, Track: 13°C)";
        String expectationTestThree = "Damp (Air: 40°C, Track: 61°C)";
        assertAll(
                () -> assertEquals(expectationTestOne, raceConditionsTestOne.toString()),
                () -> assertEquals(expectationTestTwo, raceConditionsTestTwo.toString()),
                () -> assertEquals(expectationTestThree, raceConditionsTestThree.toString())
        );
    }
}