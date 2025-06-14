package racesimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test for RaceStrategyOptimiser Class")
class RaceStrategyOptimiserTest {

    static RaceCar raceCar;
    static RaceTrack raceTrack;
    static RaceConditions raceConditions;
    static RaceStrategyOptimiser raceStrategyOptimiser;

    @BeforeEach
    void setup() {
        raceCar = new RaceCar(
                new Engine("engine", 500, 2.0, 0.95),
                new Tyres("medium", 85, 0.02, 15.0, 20.0),
                new AerodynamicKit("kitname", 600, 600, 300, 2.0, 8),
                900,
                80
        );
        raceTrack = new RaceTrack("raceTrack", 4, 10, 1.0, 1.0);
        raceConditions = new RaceConditions("sunny", RaceConditions.Weather.DRY, 25, 30.0, 50.0);

        raceStrategyOptimiser = new RaceStrategyOptimiser(
                raceCar, raceTrack, raceConditions
        );
    }

    @DisplayName("[001 - Critical]: Test Constructor and Getters with valid params")
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

    @DisplayName("[002 - Core?]: Test Constructor with null param")
    @MethodSource("constructorParamNull")
    @ParameterizedTest(name="Test: {index}/3, nullItem: {3}")
    void constructorWithNull(RaceCar raceCar, RaceTrack raceTrack, RaceConditions raceConditions, String nullItem) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            new RaceStrategyOptimiser(raceCar, raceTrack, raceConditions);
        });
        assertEquals(nullItem + " cannot be null.", error.getMessage());

    }
    private static Stream<Arguments> constructorParamNull() {
        return Stream.of(
                Arguments.of(null, raceTrack, raceConditions, "RaceCar"),
                Arguments.of(raceCar, null, raceConditions, "RaceTrack"),
                Arguments.of(raceCar, raceTrack, null, "RaceConditions")
        );
    }

    // RaceCar .getCurrentFuel should return a value that is 0.###? or should that be handled in UI related section?
    @DisplayName("[003 - Critical?]: Test simulateLap with different weathers conditions of DRY, DAMP, WET")
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

    @DisplayName("[004 - Additional?]: Test simulateLap with different temperature")
    @ParameterizedTest(name="Test: {index}/9, temperature: {0}°C, fuelAfterLap: {1}, tyreWearAfterLap: {2}")
    @CsvSource({
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
    void simulateLapWithDifferentTemperature(double temperature, double fuelAfterLap, double tyreWearAfterLap) {
        RaceConditions conditions = new RaceConditions("weather", RaceConditions.Weather.DRY, temperature, 30.0, 50.0);
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, raceTrack, conditions);
        strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());

        assertAll(
                () -> assertEquals(fuelAfterLap, strategy.getRaceCar().getCurrentFuel()),
                () -> assertEquals(tyreWearAfterLap, strategy.getRaceCar().getCurrentTyreWear())
        );
    }

    @DisplayName("Test simulateLap with different params to constructor")
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

    @DisplayName("Test simulateLap with null param")
    @ParameterizedTest(name="Test: {index}/3, NullParam: {3}")
    @MethodSource("constructorParamNull")
    void simulateLapWithNullParam(RaceCar car, RaceTrack track, RaceConditions conditions, String nullParams) {
        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(raceCar, raceTrack, raceConditions);

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            strategy.simulateLap(car, track, conditions);
        });
        assertEquals(nullParams + " cannot be null.", error.getMessage());
    }
























//    @DisplayName("[003 - Critical]: Test simulateLap method with a single lap")
//    @ParameterizedTest(name="Test: {index}/9, fuelAfterLap: {5}, tyreWearAfterLap: {6}")
//    @CsvSource({
//            "5.0, 50.0, 1.0, 5.0, 1.0, 20.0, 1.0",
//            "4.328, 50.0, 1.0, 5.0, 1.0, 15.35, 1.0",
//            "3.006, 50.0, 1.0, 5.0, 1.0, 0.1, 1.0",
//            "5.0, 30.0, 5.0, 5.0, 1.0, 0.0, 1.0",
//            "5.0, 1.0, 5.0, 5.0, 1.0, 0.0, 1.0",
//            "5.0, 50.0, 0.18, 5.0, 5.0, 20.0, 0.9",
//            "5.0, 50.0, 0.05, 5.0, 5.0, 20.0, 0.25",
//            "5.0, 50.0, 0.02, 5.0, 5.0, 20.0, 0.1",
//            "5.0, 50.0, 0.0, 5.0, 5.0, 20.0, 0.0"
//    })
//    void simulateLapWithSingleLap(double fuelEfficiency, double fuelTankCapacity, double tyreWearRate, double fuelConsumptionFactor, double tyreWearFactor, double expectedCurrentFuel, double expectedCurrentTyreWear) {
//        RaceCar car = new RaceCar(
//            new Engine("engine", 5, fuelEfficiency, 5.0),
//            new Tyres("compund", 5, tyreWearRate, 15.0, 20.0),
//            new AerodynamicKit("kitname", 5.0, 5, 1.0, 1.0),
//                200,
//                fuelTankCapacity
//        );
//        RaceTrack track = new RaceTrack("raceTrack", 50, 5, tyreWearFactor, fuelConsumptionFactor);
//        RaceConditions conditions = new RaceConditions("sunny", 10, 10);
//
//        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, track, conditions);
//
//        strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());
//
//        assertAll(
//                () -> assertEquals(expectedCurrentFuel, strategy.getRaceCar().getCurrentFuel(), 0.01),
//                () -> assertEquals(expectedCurrentTyreWear, strategy.getRaceCar().getCurrentTyreWear(), 0.01)
//        );
//    }
//
//    @DisplayName("[004 - Critical]: Test simulateLap method for multiple laps")
//    @ParameterizedTest(name="Test: {index}/35 numOfLaps: {0} fuelAfterLaps: {1}, tyreWearAfterLaps: {2}")
//    @CsvSource({
//            "2, 38.0, 0.2",
//            "3, 32.0, 0.3",
//            "8, 2.0, 0.8",
//            "9, 0.0, 0.9",
//            "10, 0.0, 1.0"
//    })
//    void simulateLapWithMultipleLaps(int laps, double expectedFuelAfterLaps, double expectedTyreWearAfterLaps) {
//        RaceCar car = new RaceCar(
//                new Engine("engine", 5, 5.0, 5.0),
//                new Tyres("compund", 5, 0.1, 15.0, 20.0),
//                new AerodynamicKit("kitname", 5.0, 5, 1.0, 1.0),
//                200,
//                50.0
//        );
//        RaceTrack track = new RaceTrack("raceTrack", 50, 5, 1.0, 1.0);
//        RaceConditions conditions = new RaceConditions("sunny", 10, 10);
//
//        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, track, conditions);
//
//        for (int i = 0; i < laps; i++) {
//            strategy.simulateLap(strategy.getRaceCar(), strategy.getRaceTrack(), strategy.getRaceConditions());
//        }
//
//        assertAll(
//                () -> assertEquals(expectedFuelAfterLaps, strategy.getRaceCar().getCurrentFuel(), 0.01),
//                () -> assertEquals(expectedTyreWearAfterLaps, strategy.getRaceCar().getCurrentTyreWear(), 0.01)
//        );
//    }
//
//    @DisplayName("[005 - Critical]: Test planPitStops method with no pit stops")
//    @ParameterizedTest(name="Test: {index}/5, numOfLaps: {0}")
//    @CsvSource({
//            "1, 3.006, 50.0, 0.3, 2.0, 1.0",
//            "2, 5.0, 50.0, 0.05, 2.5, 5.0",
//            "3, 5.5, 45.5, 0.12, 1.0, 1.2",
//            "4, 6.0, 20.0, 0.25, 1.0, 1.0",
//            "5, 7.0, 50.0, 0.15, 1.0, 1.0"
//    })
//    void planPitStopsWithNoStops(int laps, double fuelEfficiency, double fuelTankCapacity, double tyreWearRate, double fuelConsumptionFactor, double tyreWearFactor) {
//        RaceCar car = new RaceCar(
//                new Engine("engine", 5, fuelEfficiency, 5.0),
//                new Tyres("compund", 5, tyreWearRate, 15.0, 20.0),
//                new AerodynamicKit("kitname", 5.0, 5, 1.0, 1.0),
//                200,
//                fuelTankCapacity
//        );
//        RaceTrack track = new RaceTrack("raceTrack", 50, laps, tyreWearFactor, fuelConsumptionFactor);
//        RaceConditions conditions = new RaceConditions("sunny", 10, 10);
//
//        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, track, conditions);
//
//        List<Integer> expected = new ArrayList<>();
//
//        assertEquals(expected, strategy.planPitStops());
//    }
//
//    @DisplayName("[006 - Core?]: Test planPitStops method with no laps")
//    @Test
//    void planPitStopsWithNoLaps() {
//        RaceCar car = new RaceCar(
//                new Engine("engine", 5, 5.0, 5.0),
//                new Tyres("compund", 5, 0.1, 15.0, 20.0),
//                new AerodynamicKit("kitname", 5.0, 5, 1.0, 1.0),
//                200,
//                50.0
//        );
//        RaceTrack track = new RaceTrack("raceTrack", 50, 0, 1.0, 1.0);
//        RaceConditions conditions = new RaceConditions("sunny", 10, 10);
//
//        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, track, conditions);
//
//        List<Integer> expected = new ArrayList<>();
//
//        assertEquals(expected, strategy.planPitStops());
//    }
//
//    @DisplayName("[007 - Core?]: Test planPitStops method for one before last lap")
//    @ParameterizedTest(name="Test: {index}/3, totalLaps: {0}, pitStopLap: {1}")
//    @CsvSource({
//            "3, 2, 5.0, 12.0, 0.5, 1.0, 1.0",
//            "5, 4, 5.0, 24.0, 0.25, 1.0, 1.0",
//            "15, 14, 7.0, 60.0, 0.05, 1.0, 1.0"
//    })
//    void planPitStopsForOneBeforeLastLap(int laps, int pitStop, double fuelEfficiency, double fuelTankCapacity, double tyreWearRate, double fuelConsumptionFactor, double tyreWearFactor) {
//        RaceCar car = new RaceCar(
//                new Engine("engine", 5, fuelEfficiency, 5.0),
//                new Tyres("compund", 5, tyreWearRate, 15.0, 20.0),
//                new AerodynamicKit("kitname", 5.0, 5, 1.0, 1.0),
//                200,
//                fuelTankCapacity
//        );
//        RaceTrack track = new RaceTrack("raceTrack", 50, laps, tyreWearFactor, fuelConsumptionFactor);
//        RaceConditions conditions = new RaceConditions("sunny", 10, 10);
//
//        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, track, conditions);
//
//        List<Integer> expected = new ArrayList<>();
//        expected.add(pitStop);
//
//        assertEquals(expected, strategy.planPitStops());
//    }
//
//    @DisplayName("[007 - Core?]: Test planPitStops method for fuel")
//    @ParameterizedTest(name="Test: {index}/3, pitStopLaps: {1}")
//    @MethodSource("pitStopsListFuel")
//    void planPitStopsForFuel(int laps, List<Integer> pitStops, double fuelEfficiency, double fuelTankCapacity, double tyreWearRate, double fuelConsumptionFactor, double tyreWearFactor) {
//        RaceCar car = new RaceCar(
//                new Engine("engine", 5, fuelEfficiency, 5.0),
//                new Tyres("compund", 5, tyreWearRate, 15.0, 20.0),
//                new AerodynamicKit("kitname", 5.0, 5, 1.0, 1.0),
//                200,
//                fuelTankCapacity
//        );
//        RaceTrack track = new RaceTrack("raceTrack", 50, laps, tyreWearFactor, fuelConsumptionFactor);
//        RaceConditions conditions = new RaceConditions("sunny", 10, 10);
//
//        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, track, conditions);
//
//        List<Integer> expected = new ArrayList<>(pitStops);
//
//        assertEquals(expected, strategy.planPitStops());
//    }
//
//    private static Stream<Arguments> pitStopsListFuel() {
//        return Stream.of(
//                Arguments.of(8, List.of(4), 5.0, 24.0, 0.05, 1.0, 1.0),
//                Arguments.of(10, Arrays.asList(4, 7), 5.0, 24.0, 0.05, 1.0, 1.0)
//        );
//    }
//
//    @DisplayName("[008 - Core?]: Test planPitStops method for tyre")
//    @ParameterizedTest(name="Test: {index}/3, pitStopLaps: {1}")
//    @MethodSource("pitStopsListTyre")
//    void planPitStopsForTyre(int laps, List<Integer> pitStops, double fuelEfficiency, double fuelTankCapacity, double tyreWearRate, double fuelConsumptionFactor, double tyreWearFactor) {
//        RaceCar car = new RaceCar(
//                new Engine("engine", 5, fuelEfficiency, 5.0),
//                new Tyres("compund", 5, tyreWearRate, 15.0, 20.0),
//                new AerodynamicKit("kitname", 5.0, 5, 1.0, 1.0),
//                200,
//                fuelTankCapacity
//        );
//        RaceTrack track = new RaceTrack("raceTrack", 50, laps, tyreWearFactor, fuelConsumptionFactor);
//        RaceConditions conditions = new RaceConditions("sunny", 10, 10);
//
//        RaceStrategyOptimiser strategy = new RaceStrategyOptimiser(car, track, conditions);
//
//        List<Integer> expected = new ArrayList<>(pitStops);
//
//        assertEquals(expected, strategy.planPitStops());
//    }
//
//    private static Stream<Arguments> pitStopsListTyre() {
//        return Stream.of(
//                Arguments.of(4, List.of(2), 15.0, 200.0, 0.6, 1.0, 1.0),
//                Arguments.of(6, Arrays.asList(2, 4), 15.0, 200.0, 0.55, 1.0, 1.0)
//        );
//    }
//
//    @DisplayName("[009 - Additional?]: Test getRaceCar method with RaceCar")
//    @Test
//    void getRaceCar() {
//        assertEquals(raceCar, raceStrategyOptimiser.getRaceCar());
//    }
//
//    @DisplayName("[010 - Additional?]: Test getRaceTrack method with RaceTrack")
//    @Test
//    void getRaceTrack() {
//        assertEquals(raceTrack, raceStrategyOptimiser.getRaceTrack());
//    }
//
//    @DisplayName("[01 - Additional?]: Test getRaceConditions method with valid RaceConditions")
//    @Test
//    void getRaceConditions() {
//        assertEquals(raceConditions, raceStrategyOptimiser.getRaceConditions());
//    }
}