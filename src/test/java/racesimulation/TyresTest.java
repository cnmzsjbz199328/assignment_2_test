package racesimulation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test for Tyres Class")
class TyresTest {

    @DisplayName("[001 - Critical]: Test constructor and getters with valid values")
    @ParameterizedTest(name="Test: {index}/5")
    @CsvSource({
            "compound, 5, 1.0, 1.0, 10.0",
            "soft, 0, 0.0, 0.5, 1.5",
            "hard, 10, 0.123, 1.123, 10.123",
            "medium, 3, 0.000001, 1.0, 10.0",
            "compound, 3, 0.999999, 1.0, 10.0"
    })
    void constructorAndGettersWithValidValues(String compound, int grip, double wearRate, double minTemp, double maxTemp) {
        Tyres tyres = new Tyres(compound, grip, wearRate, minTemp, maxTemp);

        assertAll(
                () -> assertEquals(compound, tyres.getCompound()),
                () -> assertEquals(grip, tyres.getGripLevel()),
                () -> assertEquals(wearRate, tyres.getWearRate()),
                () -> assertEquals(new TemperatureRange(minTemp, maxTemp).toString(), tyres.getOptimalTempRange().toString())
        );
    }

    @DisplayName("[002 - Core?]: Test constructor with null and empty compound")
    @ParameterizedTest(name="Test: {index}/2, compoundName: {0}")
    @NullAndEmptySource
    void constructorWithNullAndEmptyCompound(String compound) {
        Tyres tyres = new Tyres(compound, 5, 1.0, 1.0, 10.0);
        assertEquals(compound, tyres.getCompound());
    }

    @DisplayName("[003 - additional?]: Test constructor with special character compound name")
    @ParameterizedTest(name="Test: {index}/4, compoundName: {0}")
    @ValueSource(strings = {" ", "12345", "!@#$%^&?", "あいう"})
    void constructorWithSpecialCharacterCompoundName(String compound) {
        Tyres tyres = new Tyres(compound, 5, 1.0, 1.0, 10.0);
        assertEquals(compound, tyres.getCompound());
    }

    @DisplayName("[004 - Core?]: Test constructor with extreme values")
    @ParameterizedTest(name="Test: {index}/8, grip: {0} wearRate: {1}, minTemp: {2}, maxTemp: {3}")
    @MethodSource("extremeValues")
    void constructorWithExtremeValues(int grip, double wearRate, double minTemp, double maxTemp) {
        Tyres tyres = new Tyres("compounds", grip, wearRate, minTemp, maxTemp);

        assertAll(
                () -> assertEquals(grip, tyres.getGripLevel()),
                () -> assertEquals(wearRate, tyres.getWearRate()),
                () -> assertEquals(new TemperatureRange(minTemp, maxTemp).toString(), tyres.getOptimalTempRange().toString())
        );
    }
    private static Stream<Arguments> extremeValues() {
        return Stream.of(
                Arguments.of(Integer.MIN_VALUE, 1.0, 1.0, 10.0),
                Arguments.of(Integer.MAX_VALUE, 1.0, 1.0, 10.0),
                Arguments.of(5, Double.MIN_VALUE, 1.0, 10.0),
                Arguments.of(5, Double.MAX_VALUE, 1.0, 10.0),
                Arguments.of(5, 1.0, Double.MIN_VALUE, 10.0),
                Arguments.of(5, 1.0, Double.MAX_VALUE, 10.0),
                Arguments.of(5, 1.0, 1.0, Double.MIN_VALUE),
                Arguments.of(5, 1.0, 1.0, Double.MAX_VALUE)
        );
    };

    // this should fail?
    @DisplayName("[005 - Core?]: Test constructor with reversed temperature values")
    @Test
    void constructorWithReversedTempValues() {
        Tyres tyres = new Tyres("compounds", 5, 1.0, 10.0, 1.0);

        assertEquals(new TemperatureRange(10.0, 1.0).toString(), tyres.getOptimalTempRange().toString());
    }

    @DisplayName("[006 - additional?]: Test toString with valid values")
    @ParameterizedTest(name="Test: {index}/3")
    @CsvSource({
            "compound, 5, 1.0, 1.0, 10.0",
            "soft, 0, 0.0, 0.5, 1.5",
            "hard, 10, 0.123, 1.123, 10.123"
    })
    void toStringWithValidValues(String compound, int grip, double wearRate, double minTemp, double maxTemp) {
        Tyres tyres = new Tyres(compound, grip, wearRate, minTemp, maxTemp);

        String expected = "Tyres{compound='" + compound + "'" + ", gripLevel=" + grip + ", wearRate=" + String.format("%.2f", wearRate) + ", optimalTempRange=" + new TemperatureRange(minTemp, maxTemp) + "}";

        assertEquals(expected, tyres.toString());
    }

    @DisplayName("[007 - Additional?]: Test toString with null and empty compound")
    @ParameterizedTest(name="Test: {index}/2, compoundName: {0}")
    @NullAndEmptySource
    void toStringWithNullAndEmptyCompound(String compound) {
        Tyres tyres = new Tyres(compound, 5, 1.12345, 1.12345, 10.12345);
        String expected = "Tyres{compound='" + compound + "'" + ", gripLevel=5, wearRate=1.12, optimalTempRange=(1.12345 - 10.12345 °C)}";

        assertEquals(expected, tyres.toString());
    }
}