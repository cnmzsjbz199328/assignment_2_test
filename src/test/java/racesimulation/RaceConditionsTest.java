//package racesimulation;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RaceConditionsTest {
//
//    static RaceConditions raceConditionsTestOne;
//    static RaceConditions raceConditionsTestTwo;
//
//    @BeforeEach
//    void setUp () {
//        raceConditionsTestOne = new RaceConditions("Humid", 20.5, 35.6);
//        raceConditionsTestTwo = new RaceConditions("Icy", -10.2, 13.2);
//    }
//
//    @DisplayName("WB_RCDT_01: Testing the Constructor. (Criticality: Critical)")
//    @Test
//    public void testConstructor () {
//        assertAll(
//                () -> assertEquals("Humid", raceConditionsTestOne.getWeather()),
//                () -> assertEquals(20.5, raceConditionsTestOne.getAirTemperature()),
//                () -> assertEquals(35.6,raceConditionsTestOne.getTrackTemperature())
//        );
//    }
//
//    @DisplayName("WB_RCDT_02: Test getter method for Weather. (Criticality: Core)")
//    @Test
//    public void testGetterWeather() {
//        assertEquals("Icy", raceConditionsTestTwo.getWeather());
//    }
//
//    @DisplayName("WB_RCDT_03: Test getter method for Air Temperature. (Criticality: Core)")
//    @Test
//    public void testGetterAirTemperature() {
//        assertEquals(-10.2, raceConditionsTestTwo.getAirTemperature());
//    }
//
//    @DisplayName("WB_RCDT_04: Test getter method for Track Temperature. (Criticality: Core)")
//    @Test
//    public void testGetterTrackTemperature() {
//        assertEquals(13.2, raceConditionsTestTwo.getTrackTemperature());
//    }
//
//    @DisplayName("WB_RCDT_05: Test to string method. (Criticality: Additional")
//    @Test
//    public void testToString() {
//        String expectationTestOne = "RaceConditions{" +
//                "weather='Humid\'" +
//                ", airTemperature=20.5°C" +
//                ", trackTemperature=35.6°C" +
//                '}';
//        String expectationTestTwo = "RaceConditions{" +
//                "weather='Icy\'" +
//                ", airTemperature=-10.2°C" +
//                ", trackTemperature=13.2°C" +
//                '}';
//        assertEquals(expectationTestOne, raceConditionsTestOne.toString());
//        assertEquals(expectationTestTwo, raceConditionsTestTwo.toString());
//    }
//}