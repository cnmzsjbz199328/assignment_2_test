package racesimulation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test for TemperatureRange Class")
class TemperatureRangeTest {

    @DisplayName("[001 - Critical]: Test constructor and getters with valid values")
    @ParameterizedTest(name="Test: {index}/3, minTemp: {0}, maxTemp: {1}")
    @CsvSource({
            "1.0, 10.0",
            "0.01, 10.5",
            "0.12345, 20.12345"
    })
    void constructorAndGettersWithValidParams(double minTemp, double maxTemp) {
        TemperatureRange tempRange = new TemperatureRange(minTemp, maxTemp);

        assertAll(
                () -> assertEquals(minTemp, tempRange.getMinTemp()),
                () -> assertEquals(maxTemp, tempRange.getMaxTemp())
        );
    }

    @DisplayName("[002 - Additional?]: Test constructor with negative values")
    @ParameterizedTest(name="Test: {index}/2, minTemp: {0}, maxTemp: {1}")
    @CsvSource({"-1.0, 10.0", "-5.0, -1.0"})
    void constructorWithNegativeValues(double minTemp, double maxTemp) {
        TemperatureRange tempRange = new TemperatureRange(minTemp, maxTemp);

        assertAll(
                () -> assertEquals(minTemp, tempRange.getMinTemp()),
                () -> assertEquals(maxTemp, tempRange.getMaxTemp())
        );
    }

    @DisplayName("[003 - Additional?]: Test constructor with extreme values]")
    @ParameterizedTest(name="Test: {index}/4, minTemp: {0}, maxTemp: {1}")
    @MethodSource("extremeValues")
    void constructorWithExtremeValues(double minTemp, double maxTemp) {
        TemperatureRange tempRange = new TemperatureRange(minTemp, maxTemp);

        assertAll(
                () -> assertEquals(minTemp, tempRange.getMinTemp()),
                () -> assertEquals(maxTemp, tempRange.getMaxTemp())
        );
    }
    private static Stream<Arguments> extremeValues () {
        return Stream.of(
                Arguments.of(Double.MIN_VALUE, 10.0),
                Arguments.of(Double.MAX_VALUE, 10.0),
                Arguments.of(1.0, Double.MIN_VALUE),
                Arguments.of(1.0, Double.MAX_VALUE)
        );
    };

    // shouldn't this be an error?
    @DisplayName("[004 - Critical?]: Test constructor with reversed min and max values")
    @Test
    void constructorWithReversedMinMaxValues() {
        TemperatureRange tempRange = new TemperatureRange(10.0, 1.0);

        assertAll(
                () -> assertEquals(10.0, tempRange.getMinTemp()),
                () -> assertEquals(1.0, tempRange.getMaxTemp())
        );
    }

    @DisplayName("[005 - Additional?]: Test toString with valid values")
    @ParameterizedTest(name="Test: {index}/2")
    @CsvSource({
            "1.0, 10.0",
            "0.01, 10.5",
            "0.12345, 20.12345"
    })
    void toStringWithValidValues(double minTemp, double maxTemp) {
        TemperatureRange tempRange = new TemperatureRange(minTemp, maxTemp);

        String expected = "(" + minTemp + " - " + maxTemp + " °C)";
        assertEquals(expected, tempRange.toString());
    }
}