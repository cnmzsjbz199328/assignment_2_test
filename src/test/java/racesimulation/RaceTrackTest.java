package racesimulation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test for RaceTrack Class")
class RaceTrackTest {

    @DisplayName("[001 - Critical]:Test constructor and getters with valid values")
    @ParameterizedTest(name="Test: {index}/3")
    @CsvSource({
            "Adelaide, 5.0, 5, 1.5, 1.5",
            "Suzuka, 7.55, 50, 1,05, 0.95",
            "Monaco, 3.337, 78, 1.0, 1.0"
    })
    void constructorAndGetters(String name, double length, int laps, double tyreWearFactor, double fuelConsumptionFactor) {
        RaceTrack track = new RaceTrack(name, length, laps, tyreWearFactor, fuelConsumptionFactor);

        assertAll(
                () -> assertEquals(name, track.getName()),
                () -> assertEquals(length, track.getLength_km()),
                () -> assertEquals(laps, track.getNumberOfLaps()),
                () -> assertEquals(tyreWearFactor, track.getTyreWearFactor()),
                () -> assertEquals(fuelConsumptionFactor, track.getFuelConsumptionFactor())
        );
    }

    @DisplayName("[002 - Critical?]: Test constructor with null and empty name")
    @ParameterizedTest(name="Test: {index}/2, trackName: {0}")
    @NullAndEmptySource
    void constructorWithNullAndEmptyName(String name) {
        RaceTrack track = new RaceTrack(name, 5.0, 5, 1.5, 1.5);

        assertEquals(name, track.getName());
    }

    @DisplayName("[002 - Critical?]: Test constructor with special character name")
    @ParameterizedTest(name="Test: {index}/4, trackName: {0}")
    @ValueSource(strings = {" ", "12345", "!@#$?%^&", "あいう"})
    void constructorWithSpecialCharacterName(String name) {
        RaceTrack track = new RaceTrack(name, 5.0, 5, 1.5, 1.5);

        assertEquals(name, track.getName());
    }

    @DisplayName("[003 - Core?]: Test constructor with extreme numbers")
    @ParameterizedTest(name="Test: {index}/8, trackLength: {0}, laps: {1}, tyreWearFactor: {2}, fuelConsumptionFactor: {3}")
    @MethodSource("extremeNumbers")
    void constructorWithExtremeValues(double length, int laps, double tyre, double fuel) {
        RaceTrack track = new RaceTrack("Track", length, laps, tyre, fuel);

        assertAll(
                () -> assertEquals(length, track.getLength_km()),
                () -> assertEquals(laps, track.getNumberOfLaps()),
                () -> assertEquals(tyre, track.getTyreWearFactor()),
                () -> assertEquals(fuel, track.getFuelConsumptionFactor())
        );
    }
    private static Stream<Arguments> extremeNumbers() {
        return Stream.of(
                Arguments.of(Double.MAX_VALUE, 2, 1.5, 1.5),
                Arguments.of(Double.MIN_VALUE, 2, 1.5, 1.5),
                Arguments.of(5.0, Integer.MAX_VALUE, 1.5, 1.5),
                Arguments.of(5.0, Integer.MIN_VALUE, 1.5, 1.5),
                Arguments.of(5.0, 2, Double.MAX_VALUE, 1.5),
                Arguments.of(5.0, 2, Double.MIN_VALUE, 1.5),
                Arguments.of(5.0, 2, 1.5, Double.MAX_VALUE),
                Arguments.of(5.0, 2, 1.5, Double.MIN_VALUE)
        );
    }

    @DisplayName("[004 - Core?]: Test constructor with negative numbers")
    @ParameterizedTest(name="Test: {index}/4, trackLength: {0}, laps: {1}, tyreWearFactor: {2}, fuelConsumptionFactor: {3}")
    @CsvSource({
            "-1.0, 2, 1.5, 1.5",
            "5.0, -1, 1.5, 1.5",
            "5.0, 2, -1.0, 1.5",
            "5.0, 2, 1.5, -1.0"
    })
    void constructorWithNegativeValues(double length, int laps, double tyre, double fuel) {
        RaceTrack track = new RaceTrack("Track", length, laps, tyre, fuel);

        assertAll(
                () -> assertEquals(length, track.getLength_km()),
                () -> assertEquals(laps, track.getNumberOfLaps()),
                () -> assertEquals(tyre, track.getTyreWearFactor()),
                () -> assertEquals(fuel, track.getFuelConsumptionFactor())
        );
    }

    @DisplayName("[005 - Additional?]: Test toString with valid values")
    @ParameterizedTest(name="Test: {index}/3")
    @CsvSource({
            "Adelaide, 5.0, 5, 1.0, 1.0",
            "Suzuka, 7.543, 50, 1,05, 0.95",
            "Monaco, 3.3378, 78, 1.54321, 1.54321"
    })
    void toStringWithValidValues(String name, double length, int laps, double tyre, double fuel) {
        RaceTrack track = new RaceTrack(name, length, laps, tyre, fuel);

        String expected = "RaceTrack{name='" + name + "'" + ", length_km=" + String.format("%.3f", length) + " km" + ", numberOfLaps=" + laps + ", tyreWearFactor=" + String.format("%.2f", tyre) + ", fuelConsumptionFactor=" + String.format("%.2f", fuel) + "}";

        assertEquals(expected, track.toString());
    }

    @DisplayName("[006 - Additional?]: Test toString with null and empty name")
    @ParameterizedTest(name="Test: {index}/2, trackName: {0}")
    @NullAndEmptySource
    void toStringWithNullAndEmptyName(String name) {
        RaceTrack track = new RaceTrack(name, 5.0, 5, 1.0, 1.0);

        String expected = "RaceTrack{name='" + name + "'" + ", length_km=5.000 km, numberOfLaps=5, tyreWearFactor=1.00, fuelConsumptionFactor=1.00}";

        assertEquals(expected, track.toString());
    }

}