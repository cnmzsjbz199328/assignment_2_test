package racesimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[WB_RSO]: Test for RaceStrategyOptimiser class")
class RaceStrategyOptimiserTest {

    RaceCar raceCar;
    RaceTrack raceTrack;
    RaceConditions raceConditions;
    RaceStrategyOptimiser raceStrategyOptimiser;

    @BeforeEach
    void setup() {
        raceCar = new RaceCar(
                new Engine("engine", 500, 2.0, 0.95),
                new Tyres("medium", 85, 0.02, 15.0, 20.0),
                new AerodynamicKit("kitname", 600, 600, 300, 2.0, 8),
                900,
                80
        );
        raceTrack = new RaceTrack("raceTrack", 4, 50, 1.0, 1.0);
        raceConditions = new RaceConditions("sunny", RaceConditions.Weather.DRY, 25, 30.0, 50.0);

        raceStrategyOptimiser = new RaceStrategyOptimiser(
                raceCar, raceTrack, raceConditions
        );
    }

    @DisplayName("[WB_RSO_01 - Critical]: Test Constructor and Getters with valid params")
    void constructorAndGettersWithValidParams() {
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(
                raceCar, raceTrack, raceConditions
        );
        assertAll(
                () -> assertEquals(raceCar, strategy.getRaceCar()),
                () -> assertEquals(raceTrack, strategy.getRaceTrack()),
                () -> assertEquals(raceConditions, strategy.getRaceConditions())
        );
    }

    @DisplayName("[WB_RSO_02 - Core]: Test Constructor with null param")
    @MethodSource("constructorParamNull")
    @ParameterizedTest(name="Test: {index}/3, nullItem: {3}")
    void constructorWithNull(RaceCar raceCar, RaceTrack raceTrack, RaceConditions raceConditions, String nullItem) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            new RaceStrategyOptimiser(raceCar, raceTrack, raceConditions);
        });
        assertEquals(nullItem + " cannot be null.", error.getMessage());

    }
    private static Stream<Arguments> constructorParamNull() {
        RaceCar raceCar = new RaceCar(
                new Engine("engine", 500, 2.0, 0.95),
                new Tyres("medium", 85, 0.02, 15.0, 20.0),
                new AerodynamicKit("kitname", 600, 600, 300, 2.0, 8),
                900,
                80
        );
        RaceTrack raceTrack = new RaceTrack("raceTrack", 4, 50, 1.0, 1.0);
        RaceConditions raceConditions = new RaceConditions("sunny", RaceConditions.Weather.DRY, 25, 30.0, 50.0);

        return Stream.of(
                Arguments.of(null, raceTrack, raceConditions, "RaceCar"),
                Arguments.of(raceCar, null, raceConditions, "RaceTrack"),
                Arguments.of(raceCar, raceTrack, null, "RaceConditions")
        );
    }

    @DisplayName("[WB_RSO_03 - Critical]: Test simulateLap with different weathers conditions of DRY, DAMP, WET")
    @ParameterizedTest(name="Test: {index}/3, weather: {0}, fuelAfterLap: {1}, tyreWearAfterLap: {2}")
    @CsvSource({
            "DRY, 65.0, 0.02",
            "DAMP, 65.0, 0.018",
            "WET, 65.0, 0.015"
    })
    void simulateLapWithDifferentWeatherConditions(RaceConditions.Weather weather, double fuelAfterLap, double tyreWearAfterLap) {
        RaceConditions conditions = new RaceConditions("weather", weather, 25, 30.0, 50.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, raceTrack, conditions);
        strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());

        assertAll(
                () -> assertEquals(fuelAfterLap, strategy.getRaceCar().getCurrentFuel()),
                () -> assertEquals(tyreWearAfterLap, strategy.getRaceCar().getCurrentTyreWear())
        );
    }

    @DisplayName("[WB_RSO_04 - Critical]: Test simulateLap with different airTemperature")
    @ParameterizedTest(name="Test: {index}/10, airTemperature: {0}°C, fuelAfterLap: {1}, tyreWearAfterLap: {2}")
    @CsvSource({
            "-1.0, 64.25, 0.02",
            "5.0, 64.25, 0.02",
            "9.0, 64.25, 0.02",
            "9.99, 64.25, 0.02",
            "10.0, 65.00, 0.02",
            "10.01, 65.00, 0.02",
            "20.0, 65.00, 0.02",
            "30.0, 65.00, 0.02",
            "30.01, 65.30, 0.02",
            "31.0, 65.30, 0.02"
    })
    void simulateLapWithDifferentAirTemperature(double airTemperature, double fuelAfterLap, double tyreWearAfterLap) {
        RaceConditions conditions = new RaceConditions("weather", RaceConditions.Weather.DRY, airTemperature, 30.0, 50.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, raceTrack, conditions);
        strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());

        assertAll(
                () -> assertEquals(fuelAfterLap, strategy.getRaceCar().getCurrentFuel()),
                () -> assertEquals(tyreWearAfterLap, strategy.getRaceCar().getCurrentTyreWear())
        );
    }

    @DisplayName("[WB_RSO_05 - Additional]: Test simulateLap with extreme temperature values")
    @ParameterizedTest(name="Test: {index}/2, airTemperature: {0}, fuelAfterLap: {1}, tyreWearAfterLap: {2}")
    @MethodSource("extremeAirTemperature")
    void simulateLapWithExtremeAirTemperature(double airTemp, double fuelAfterLap, double tyreWearAfterLap) {
        RaceConditions conditions = new RaceConditions("weather", RaceConditions.Weather.DRY, airTemp, 30.0, 50.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, raceTrack, conditions);
        strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());

        assertAll(
                () -> assertEquals(fuelAfterLap, strategy.getRaceCar().getCurrentFuel()),
                () -> assertEquals(tyreWearAfterLap, strategy.getRaceCar().getCurrentTyreWear())
        );
    }
    private static Stream<Arguments> extremeAirTemperature() {
        return Stream.of(
            Arguments.of(Double.MIN_VALUE, 64.25, 0.02),
            Arguments.of(Double.MAX_VALUE, 65.30, 0.02)
        );
    }

    @DisplayName("[WB_RSO_06 - Critical]: Test simulateLap with different params to constructor params")
    @Test
    void simulateLapWithDifferentParamsToConstructor() {
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, raceTrack, raceConditions);

        RaceCar car = new RaceCar(
                new Engine("newEngine", 700, 1.5, 0.85),
                new Tyres("soft", 70, 0.03, 60, 100),
                new AerodynamicKit("newKit", 0.28, 700, 320, 1.4, 9),
                1100,
                60
        );
        RaceTrack track = new RaceTrack("newTrack", 5.2, 12, 1.2, 1.1);
        RaceConditions conditions = new RaceConditions("damp", RaceConditions.Weather.DAMP, 8.0, 27.0, 80.0);

        strategy.simulateLap(car, track, conditions);
        assertAll(
                () -> assertEquals(36.103448275862064, car.getCurrentFuel()),
                () -> assertEquals(0.0324, car.getCurrentTyreWear()),
                () -> assertEquals(80.0, raceCar.getCurrentFuel()),
                () -> assertEquals(0.0, raceCar.getCurrentTyreWear())
        );
    }

    @DisplayName("[WB_RSO_06 - Core]: Test simulateLap with null param")
    @ParameterizedTest(name="Test: {index}/3, NullParam: {3}")
    @MethodSource("constructorParamNull")
    void simulateLapWithNullParam(RaceCar car, RaceTrack track, RaceConditions conditions, String nullParams) {
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, raceTrack, raceConditions);

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            strategy.simulateLap(car, track, conditions);
        });
        assertEquals(nullParams + " cannot be null.", error.getMessage());
    }

    @DisplayName("[WB_RSO_07 - Critical]: Test simulateLap for multiple laps")
    @ParameterizedTest(name="Test: {index}/8, laps: {0}, fuelAfterLaps: {1}, tyreWearAfterLaps: {2}")
    @CsvSource({
            "2, 50.0, 0.04",
            "3, 35.0, 0.06",
            "4, 20.0, 0.08",
            "5, 5.0, 0.10",
            "6, 0.0, 0.12",
            "49, 0.0, 0.98",
            "50, 0.0, 1.00",
            "51, 0.0, 1.00"
    })
    void simulateLapForMultipleLaps(int lap, double currentFuel, double currentTyreWear) {
        for (int i = 0; i < lap; i++) {
            raceStrategyOptimiser.simulateLap(raceStrategyOptimiser.getRaceCar(), raceStrategyOptimiser.getRaceTrack(), raceStrategyOptimiser.getRaceConditions());
        }

        assertAll(
                () -> assertEquals(currentFuel, raceStrategyOptimiser.getRaceCar().getCurrentFuel(), 0.000001),
                () -> assertEquals(currentTyreWear, raceStrategyOptimiser.getRaceCar().getCurrentTyreWear(), 0.000001)
        );
    }

    @DisplayName("[WB_RSO_08 - Additional]: Test simulateLap with different trackTemperature")
    @ParameterizedTest(name="Test: {index}/3, fuelAfterLap: {1}, tyreWearLap: {2}")
    @CsvSource({
            "-5.0, 65.00, 0.02",
            "10.5, 65.00, 0.02",
            "30.0, 65.00, 0.02"
    })
    void simulateLapWithDifferentTrackTemperature(double trackTemp, double fuelAfterLap, double tyreWearAfterLap) {
        RaceConditions conditions = new RaceConditions("weather", RaceConditions.Weather.DRY, 10.0, trackTemp, 50.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, raceTrack, conditions);
        strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());

        assertAll(
                () -> assertEquals(fuelAfterLap, strategy.getRaceCar().getCurrentFuel()),
                () -> assertEquals(tyreWearAfterLap, strategy.getRaceCar().getCurrentTyreWear())
        );
    }

    @DisplayName("[WB_RSO_09 - Critical]: Test checkPerformPitStop with current lap being final lap")
    @ParameterizedTest(name="Test: {index}/5, currentLap: {0}, totalLaps: {1}")
    @CsvSource({
            "0, 0",
            "5, 5,",
            "10, 10",
            "19, 19",
            "20, 20"
    })
    void checkAndPerformPitStopWithCurrentLapAsFinalLap(int currentLap, int totalLap) {
        RaceTrack track = new RaceTrack("testTrack",  4.5, totalLap, 1.0, 1.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, track, raceConditions);

        assertEquals(null, strategy.checkAndPerformPitStop(currentLap, strategy.getRaceTrack().getNumberOfLaps()));
    }

    @DisplayName("[WB_RSO_10 - Core]: Test checkPerformPitStop with negative laps")
    @ParameterizedTest(name="Test: {index}/2, currentLap: {0}, totalLap: {1}")
    @CsvSource({
            "-2, 1",
            "-2, 2"
    })
    void checkAndPerformPitStopWithLapsLeft(int currentLap, int totalLap) {
        RaceTrack track = new RaceTrack("testTrack",  4.5, totalLap, 1.0, 1.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, track, raceConditions);

        assertThrows(IllegalArgumentException.class, () -> strategy.checkAndPerformPitStop(currentLap, strategy.getRaceTrack().getNumberOfLaps()));
    }

    @DisplayName("[WB_RSO_11 - Core]: Test checkPerformPitStop with current lap over total laps")
    @ParameterizedTest(name="Test: {index}/2, currentLap: {0}, totalLap: {1}")
    @CsvSource({
            "6, 5",
            "10, 5"
    })
    void checkAndPerformPitStopWithCurrentLapOverTotalLap(int currentLap, int totalLap) {
        RaceTrack track = new RaceTrack("testTrack",  4.5, totalLap, 1.0, 1.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, track, raceConditions);

        assertThrows(IllegalArgumentException.class, () -> strategy.checkAndPerformPitStop(currentLap, strategy.getRaceTrack().getNumberOfLaps()));
    }

    @DisplayName("[WB_RSO_12 - Additional]: Test checkPerformPitStop with extreme current lap and total laps")
    @ParameterizedTest(name="Test: {index}/2, currentLap: {0}, totalLap: {1}")
    @MethodSource("extremeLapValues")
    void checkAndPerformPitStopWithExtremeValues(int currentLap, int totalLap) {
        RaceTrack track = new RaceTrack("testTrack",  4.5, totalLap, 1.0, 1.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, track, raceConditions);

        assertEquals(null, strategy.checkAndPerformPitStop(currentLap, strategy.getRaceTrack().getNumberOfLaps()));
    }
    private static Stream<Arguments> extremeLapValues () {
        return Stream.of(
                Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.of(Integer.MIN_VALUE, Integer.MIN_VALUE)
        );
    }

    @DisplayName("[WB_RSO_13 - Critical]: Test checkPerformPitStop with no pitStop needed")
    @ParameterizedTest(name="Test: {index}/3, currentLap: {3}, reason: {0}, fuelAfter: {1}, tyreWearAfter: {2}")
    @MethodSource("noPitStop")
    void checkAndPerformPitStopFuelAndTyreWearAfterNoPitStop(String reason, double fuelAfter, double tyreWearAfter, int lap) {
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, raceTrack, raceConditions);

        for (int i = 0; i < lap; i++) {
            strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());
        }
        String actualReason = strategy.checkAndPerformPitStop(lap, strategy.getRaceTrack().getNumberOfLaps());

        assertAll(
                () -> assertEquals(reason, actualReason),
                () -> assertEquals(fuelAfter, strategy.getRaceCar().getCurrentFuel()),
                () -> assertEquals(tyreWearAfter, strategy.getRaceCar().getCurrentTyreWear())
        );
    }
    private static Stream<Arguments> noPitStop () {
        return Stream.of(
                Arguments.of(null, 50.0, 0.04, 2),
                Arguments.of(null, 35.0, 0.06, 3),
                Arguments.of(null, 20.0, 0.08, 4)
        );
    }

    @DisplayName("[WB_RSO_14 - Critical]: Test checkPerformPitStop with fuel and tyre reset after pitStop")
    @ParameterizedTest(name="Test: {index}/3, currentLap: {3}, reason: {0}, fuelAfter: {1}, tyreWearAfter: {2}")
    @MethodSource("pitStopped")
    void checkAndPerformPitStopFuelAndTyreWearResetAfterPitStop(String reason, double fuelAfter, double tyreWearAfter, int lap) {
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, raceTrack, raceConditions);

        String result = "";
        for (int i = 0; i < lap; i++) {
            strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());
            result = strategy.checkAndPerformPitStop(lap, strategy.getRaceTrack().getNumberOfLaps());
        }

        String actualReason = result;
        assertAll(
                () -> assertEquals(reason, actualReason),
                () -> assertEquals(fuelAfter, strategy.getRaceCar().getCurrentFuel()),
                () -> assertEquals(tyreWearAfter, strategy.getRaceCar().getCurrentTyreWear())

        );
    }
    private static Stream<Arguments> pitStopped () {
        return Stream.of(
                Arguments.of(null, 20.0, 0.08, 4),
                Arguments.of("Fuel", 80.0, 0.0, 5),
                Arguments.of(null, 65.0, 0.02, 6)
        );
    }

    @DisplayName("[WB_RSO_15 - Critical]: Test checkPerformPitStop for fuel")
    @ParameterizedTest(name="Test: {index}/3, currentLap: {0}, totalLap: {1}")
    @CsvSource({
            "5, 10",
            "10, 30",
            "15, 16"
    })
    void checkAndPerformPitStopForFuel(int currentLap, int totalLap) {
        RaceTrack track = new RaceTrack("testTrack",  4.5, totalLap, 1.0, 1.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, track, raceConditions);

        for (int i = 0; i < currentLap; i++) {
            strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());
        }

        assertEquals("Fuel", strategy.checkAndPerformPitStop(currentLap, strategy.getRaceTrack().getNumberOfLaps()));
    }

    @DisplayName("[WB_RSO_16 - Critical]: Test checkPerformPitStop for fuelCapacity after pitStop")
    @ParameterizedTest(name="Test: {index}/3, currentLap: {0}, totalLap: {1}, fuelAfterPitStop: {2}")
    @CsvSource({
            "5, 10, 80.0",
            "10, 30, 80.0",
            "15, 16, 80.0"
    })
    void checkAndPerformPitStopForFuelCapacityAfter(int currentLap, int totalLap, double fuelAfterPitStop) {
        RaceTrack track = new RaceTrack("testTrack",  4.5, totalLap, 1.0, 1.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, track, raceConditions);

        for (int i = 0; i < currentLap; i++) {
            strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());
        };
        strategy.checkAndPerformPitStop(currentLap, strategy.getRaceTrack().getNumberOfLaps());

        assertEquals(fuelAfterPitStop, strategy.getRaceCar().getCurrentFuel());
    }

    @DisplayName("[WB_RSO_17 - Critical]: Test checkPerformPitStop for tyres")
    @ParameterizedTest(name="Test: {index}/3, currentLap: {0}, totalLap: {1}, tyreWear: {2}")
    @CsvSource({
            "8, 10, 0.0",
            "16, 55, 0.0",
            "24, 25, 0.0"
    })
    void checkAndPerformPitStopForTyres(int currentLap, int totalLap, double tyreWear) {
        RaceCar car = new RaceCar(
                new Engine("engine", 500, 20.0, 0.95),
                new Tyres("medium", 85, 0.1, 15.0, 20.0),
                new AerodynamicKit("kitname", 600, 600, 300, 20.0, 8),
                900,
                80
        );
        RaceTrack track = new RaceTrack("raceTrack", 4, totalLap, 1.0, 1.0);

        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, track, raceConditions);

        for (int i = 0; i < currentLap; i++) {
            strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());
        }

        String reason = strategy.checkAndPerformPitStop(currentLap, strategy.getRaceTrack().getNumberOfLaps());
        assertAll(
                () -> assertEquals("Tyres", reason),
                () -> assertEquals(tyreWear, strategy.getRaceCar().getCurrentTyreWear())
        );
    }

    @DisplayName("[WB_RSO_18 - Critical]: Test checkPerformPitStop for tyreWear after pitStop")
    @ParameterizedTest(name="Test: {index}/3, currentLap: {0}, totalLap: {1}, tyreWearAfterPitStop: {2}")
    @CsvSource({
            "8, 10, 0.0",
            "16, 55, 0.0",
            "24, 25, 0.0"
    })
    void checkAndPerformPitStopForTyresWearAfterPitStop(int currentLap, int totalLap, double tyreWearAfterPitStop) {
        RaceCar car = new RaceCar(
                new Engine("engine", 500, 20.0, 0.95),
                new Tyres("medium", 85, 0.1, 15.0, 20.0),
                new AerodynamicKit("kitname", 600, 600, 300, 20.0, 8),
                900,
                80
        );
        RaceTrack track = new RaceTrack("raceTrack", 4, totalLap, 1.0, 1.0);

        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, track, raceConditions);

        for (int i = 0; i < currentLap; i++) {
            strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());
        };
        strategy.checkAndPerformPitStop(currentLap, strategy.getRaceTrack().getNumberOfLaps());

        assertEquals(tyreWearAfterPitStop, strategy.getRaceCar().getCurrentTyreWear());
    }

    @DisplayName("[WB_RSO_19 - Critical]: Test checkPerformPitStop for fuel and tyres")
    @ParameterizedTest(name="Test: {index}/3, currentLap: {0}, totalLap: {1}")
    @CsvSource({
            "3, 5",
            "6, 7",
            "12, 20"
    })
    void checkAndPerformPitStopForFuelAndTyres(int currentLap, int totalLap) {
        RaceCar car = new RaceCar(
                new Engine("engine", 500, 0.5, 0.95),
                new Tyres("medium", 85, 0.3, 15.0, 20.0),
                new AerodynamicKit("kitname", 600, 600, 300, 1.0, 8),
                900,
                80
        );
        RaceTrack track = new RaceTrack("raceTrack", 4, totalLap, 1.0, 1.0);

        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, track, raceConditions);

        for (int i = 0; i < currentLap; i++) {
            strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());
        }

        assertEquals("Fuel & Tyres", strategy.checkAndPerformPitStop(currentLap, strategy.getRaceTrack().getNumberOfLaps()));
    }

    @DisplayName("[WB_RSO_20 - Core]: Test checkAndPerformPitStop with different airTemperature")
    @ParameterizedTest(name="Test: {index}/9, airTemperature: {0}°C, reason: {1}")
    @MethodSource("pitStopDifferentTemp")
    void checkAndPerformPitStopWithDifferentAirTemperature(double airTemperature, String reason) {
        RaceCar car = new RaceCar(
                new Engine("engine", 500, 5.0, 0.95),
                new Tyres("medium", 85, 0.02, 15.0, 20.0),
                new AerodynamicKit("kitname", 600, 600, 300, 5.0, 8),
                900,
                80
        );
        RaceConditions conditions = new RaceConditions("testConditions", RaceConditions.Weather.DRY, airTemperature, 30.0, 50.0);

        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, raceTrack, conditions);

        int lapCompleted = 12;
        for (int i = 0; i < lapCompleted; i++) {
            strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());
        }

        assertEquals(reason, strategy.checkAndPerformPitStop(lapCompleted + 1, strategy.getRaceTrack().getNumberOfLaps()));
    }

    private static Stream<Arguments> pitStopDifferentTemp() {
        return Stream.of(
                Arguments.of(-1.0, "Fuel"),
                Arguments.of(5.0, "Fuel"),
                Arguments.of(9.99, "Fuel"),
                Arguments.of(10.0, null),
                Arguments.of(10.01, null),
                Arguments.of(20.0, null),
                Arguments.of(30.0, null),
                Arguments.of(30.01, null),
                Arguments.of(31.0, null)
        );
    }

    @DisplayName("[WB_RSO_21 - Core]: Test checkAndPerformPitStop with totalLaps different from RaceTrack totalLaps")
    @ParameterizedTest(name="Test: {index}/3, RaceTrackTotalLap: {0}, paramTotalLap: {1}")
    @CsvSource({
            "0, 5",
            "5, 5",
            "10, 5",
    })
    void checkAndPerformPitStopWithDifferentTotalLapWithRaceTrack(int raceTrackTotalLap, int paramTotalLap) {
        RaceTrack track = new RaceTrack("testTrack", 5.0, raceTrackTotalLap, 1.0, 1.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, track, raceConditions);

        assertEquals(null, strategy.checkAndPerformPitStop(2, paramTotalLap));
    }


    @DisplayName("[WB_RSO_22 - Critical]: Test planPitStops with no laps")
    @Test
    void planPitStopsWithNoLaps() {
        RaceTrack track = new RaceTrack("raceTrack", 50, 0, 1.0, 1.0);

        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, track, raceConditions);

        List<Integer> expected = new ArrayList<>();

        assertEquals(expected, strategy.planPitStops());
    }

    @DisplayName("[WB_RSO_23 - Critical]: Test planPitStops with no necessary pit stops")
    @ParameterizedTest(name="Test: {index}/4, numOfLaps: {0}")
    @ValueSource(ints = {1, 2, 3, 4})
    void planPitStopsWithNoStops(int laps) {
        RaceTrack track = new RaceTrack("testTrack", 5.0, laps, 1.0, 1.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, track, raceConditions);

        List<Integer> expected = new ArrayList<>();

        assertEquals(expected, strategy.planPitStops());
    }

    @DisplayName("[WB_RSO_24 - Critical]: Test planPitStops for fuel")
    @ParameterizedTest(name="Test: {index}/2, pitStopLaps: {1}")
    @MethodSource("pitStopsListFuel")
    void planPitStopsForFuel(int laps, List<Integer> pitStops) {
        RaceTrack track = new RaceTrack("raceTrack", 50, laps, 1.0, 1.0);

        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, track, raceConditions);

        List<Integer> expected = new ArrayList<>(pitStops);

        assertEquals(expected, strategy.planPitStops());
    }
    private static Stream<Arguments> pitStopsListFuel() {
        return Stream.of(
                Arguments.of(10, List.of(5)),
                Arguments.of(20, Arrays.asList(5, 10, 15))
        );
    }

    @DisplayName("[WB_RSO_25 - Critical]: Test checkPerformPitStop for tyres")
    @ParameterizedTest(name="Test: {index}/3, pitStopLaps: {1}")
    @MethodSource("pitStopsListTyres")
    void planPitStopsForTyres (int laps, List<Integer> pitStops, double tyreWear) {
        RaceCar car = new RaceCar(
                new Engine("engine", 500, 20.0, 0.95),
                new Tyres("medium", 85, 0.1, 15.0, 20.0),
                new AerodynamicKit("kitname", 600, 600, 300, 20.0, 8),
                900,
                80
        );
        RaceTrack track = new RaceTrack("testTrack", 5.0, laps, 1.0, 1.0);
        RaceConditions conditions = new RaceConditions(
                "sunny", RaceConditions.Weather.DRY, 25.0, 30.0, 50.0
        );

        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, track, conditions);

        List<Integer> expected = new ArrayList<>(pitStops);
        List<Integer> actual = strategy.planPitStops();

        assertAll(
                () -> assertEquals(expected, actual),
                () -> assertEquals(tyreWear, strategy.getRaceCar().getCurrentTyreWear())
        );
    }
    private static Stream<Arguments> pitStopsListTyres() {
        return Stream.of(
                Arguments.of(10, List.of(8), 0.2),
                Arguments.of(20, Arrays.asList(8, 16), 0.4),
                Arguments.of(30, Arrays.asList(8, 16, 24), 0.6)
        );
    }

    @DisplayName("[WB_RSO_26 - Critical]: Test planPitStops with too high fuel consumption to finish first lap")
    @Test
    void planPitStopsWithHighFuelConsumption() {
        RaceCar car = new RaceCar(
                new Engine("engine", 500, 100.0, 1.0),
                new Tyres("medium", 85, 0.1, 15.0, 20.0),
                new AerodynamicKit("kitname", 600, 600, 300, 100.0, 8),
                900,
                80
        );

        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, raceTrack, raceConditions);

        List<Integer> expected = new ArrayList<>();

        assertEquals(expected, strategy.planPitStops());
    }

    @DisplayName("[WB_RSO_27 - Core]: Test getRaceCar")
    @Test
    void getRaceCar() {
        assertEquals(raceCar, raceStrategyOptimiser.getRaceCar());
    }

    @DisplayName("[WB_RSO_28 - Core]: Test getRaceTrack")
    @Test
    void getRaceTrack() {
        assertEquals(raceTrack, raceStrategyOptimiser.getRaceTrack());
    }

    @DisplayName("[WB_RSO_29 - Core]: Test getRaceConditions")
    @Test
    void getRaceConditions() {
        assertEquals(raceConditions, raceStrategyOptimiser.getRaceConditions());
    }
}