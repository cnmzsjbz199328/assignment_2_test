//package racesimulation;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.function.BiConsumer;
//import java.util.function.Consumer;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@DisplayName("Test for RaceSimulationTask Class")
//class RaceSimulationTaskTest {
//
//    RaceCar raceCar = new RaceCar(
//            new Engine("engine", 5, 5.0, 5.0),
//            new Tyres("compund", 5, 5.0, 15.0, 20.0),
//            new AerodynamicKit("kitname", 5.0, 5, 1.0, 1.0),
//            50,
//            50
//    );
//    RaceTrack raceTrack = new RaceTrack("raceTrack", 50, 5, 5, 5);
//    RaceConditions raceConditions = new RaceConditions("sunny", 10, 10);
//    RaceStrategyOptimiser raceStrategyOptimiser = new RaceStrategyOptimiser(raceCar, raceTrack, raceConditions);
//
//    // GUI related??
//    Consumer<String> logMessageConsumer;
//    Consumer<Double> updateFuelConsumer;
//    Consumer<Double> updateTyreWearConsumer;
//    BiConsumer<Integer, String> addPitStopConsumer;
//    Consumer<Double> updateProgressConsumer;
//    Consumer<String> simulationFinishedConsumer;
//
//
//    RaceSimulationTask raceSimulationTask;
//    @DisplayName("Constructor")
//    @BeforeEach
//    void setup() {
//        raceSimulationTask = new RaceSimulationTask(
//                raceCar,
//                raceTrack,
//                raceConditions,
//                raceStrategyOptimiser,
//                logMessageConsumer,
//                updateFuelConsumer,
//                updateTyreWearConsumer,
//                addPitStopConsumer,
//                updateProgressConsumer,
//                simulationFinishedConsumer
//        );
//    }
//
//    @DisplayName("Test call method")
//    @Test
//    void call() throws Exception {
//        raceSimulationTask.call();
//    }
//}