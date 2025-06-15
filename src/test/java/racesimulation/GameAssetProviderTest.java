package racesimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[WB_GAP]: Test for GameAssetProvider class")
class GameAssetProviderTest {

    static GameAssetProvider testGameAssetProvider;
    static List<Engine> testEngine;
    static List<Tyres> testTyres;
    static List<AerodynamicKit> testAerodynamicKit;
    static List<RaceTrack> testRaceTrack;
    static List<RaceConditions> testRaceConditions;

    @BeforeEach
    void setUp() {
        testGameAssetProvider = new GameAssetProvider();
        testEngine = testGameAssetProvider.getEngineVariations();
        testTyres = testGameAssetProvider.getTyreVariations();
        testAerodynamicKit = testGameAssetProvider.getAeroKitVariations();
        testRaceTrack = testGameAssetProvider.getTrackVariations();
        testRaceConditions = testGameAssetProvider.getConditionVariations();
    }

    @DisplayName("[WB_GAP_01 - Critical]: Testing Lists are not null after setUp.")
    @Test
    public void testNotNullLists() {
        assertAll(
                () -> assertNotNull(testEngine),
                () -> assertNotNull(testTyres),
                () -> assertNotNull(testAerodynamicKit),
                () -> assertNotNull(testRaceTrack),
                () -> assertNotNull(testRaceConditions)
        );
    }

    @DisplayName("[WB_GAP_02 - Critical]: Testing Lists are not empty after setUp.")
    @Test
    public void testNotEmptyLists() {
        assertAll(
                () -> assertFalse(testEngine.isEmpty()),
                () -> assertFalse(testTyres.isEmpty()),
                () -> assertFalse(testAerodynamicKit.isEmpty()),
                () -> assertFalse(testRaceTrack.isEmpty()),
                () -> assertFalse(testRaceConditions.isEmpty())
        );
    }

    @DisplayName("[WB_GAP_03 - Critical]: Testing Lists size after setUp.")
    @Test
    public void testListsSize() {
        assertAll(
                () -> assertEquals(3, testEngine.size()),
                () -> assertEquals(3, testTyres.size()),
                () -> assertEquals(9, testAerodynamicKit.size()),
                () -> assertEquals(3, testRaceTrack.size()),
                () -> assertEquals(3, testRaceConditions.size())
        );
    }

    @DisplayName("[WB_GAP_04 - Critical]: Testing constructor that initialised private method initializeGameAssets," +
            "to check if Engine types were added into List.")
    @Test
    public void testConstructorForEngineTypesAddedIntoList() {
        // Testing all variables that are present for first item in <List> testEngine
        assertAll(
                () -> assertEquals("Standard V6", testEngine.get(0).getName()),
                () -> assertEquals(300, testEngine.get(0).getPowerRating()),
                () -> assertEquals(7.5, testEngine.get(0).getFuelEfficiency()),
                () -> assertEquals(0.95, testEngine.get(0).getReliability())
        );

        // Testing all variables that are present for second item in <List> testEngine
        assertAll(
                () -> assertEquals("Turbocharged V8", testEngine.get(1).getName()),
                () -> assertEquals(550, testEngine.get(1).getPowerRating()),
                () -> assertEquals(5.0, testEngine.get(1).getFuelEfficiency()),
                () -> assertEquals(0.88, testEngine.get(1).getReliability())
        );

        // Testing all variables that are present for third item in <List> testEngine
        assertAll(
                () -> assertEquals("EcoBoost I4", testEngine.get(2).getName()),
                () -> assertEquals(180, testEngine.get(2).getPowerRating()),
                () -> assertEquals(12.0, testEngine.get(2).getFuelEfficiency()),
                () -> assertEquals(0.99, testEngine.get(2).getReliability())
        );
    }

    @DisplayName("[WB_GAP_05 - Critical]: Testing constructor that initialised private method initializeGameAssets," +
            "to check if Tyre variations were added into List.")
    @Test
    public void testConstructorForTyreVariationsAddedIntoList() {
        TemperatureRange tempRangeOne = new TemperatureRange(80, 100);
        TemperatureRange tempRangeTwo = new TemperatureRange(90, 110);
        TemperatureRange tempRangeThree = new TemperatureRange(100, 120);

        // Testing all variables that are present for first item in <List> testTyres
        assertAll(
                () -> assertEquals("Soft Compound", testTyres.get(0).getCompound()),
                () -> assertEquals(95, testTyres.get(0).getGripLevel()),
                () -> assertEquals(0.15, testTyres.get(0).getWearRate()),
                () -> assertEquals(tempRangeOne.toString(), testTyres.get(0).getOptimalTempRange().toString())
        );

        // Testing all variables that are present for second item in <List> testTyres
        assertAll(
                () -> assertEquals("Medium Compound", testTyres.get(1).getCompound()),
                () -> assertEquals(80, testTyres.get(1).getGripLevel()),
                () -> assertEquals(0.08, testTyres.get(1).getWearRate()),
                () -> assertEquals(tempRangeTwo.toString(), testTyres.get(1).getOptimalTempRange().toString())
        );

        // Testing all variables that are present for third item in <List> testTyres
        assertAll(
                () -> assertEquals("Hard Compound", testTyres.get(2).getCompound()),
                () -> assertEquals(65, testTyres.get(2).getGripLevel()),
                () -> assertEquals(0.03, testTyres.get(2).getWearRate()),
                () -> assertEquals(tempRangeThree.toString(), testTyres.get(2).getOptimalTempRange().toString())
        );
    }

    @DisplayName("[WB_GAP_06 - Critical]: Testing constructor that initialised private method initializeGameAssets," +
            "to check if all AerodynamicKits were added into List.")
    @Test
    public void testConstructorForAerodynamicKitsAddedIntoList() {
        // Testing all variables that are present for first item in <List> testAerodynamicKit
        assertAll(
                () -> assertEquals("Standard Kit", testAerodynamicKit.get(0).getKitName()),
                () -> assertEquals(0.30, testAerodynamicKit.get(0).getDragCoefficient()),
                () -> assertEquals(200, testAerodynamicKit.get(0).getDownforceValue()),
                () -> assertEquals(250, testAerodynamicKit.get(0).getTopSpeed()),
                () -> assertEquals(12, testAerodynamicKit.get(0).getFuelEfficiency()),
                () -> assertEquals(6, testAerodynamicKit.get(0).getCorneringAbility())
        );

        // Testing all variables that are present for second item in <List> testAerodynamicKit
        assertAll(
                () -> assertEquals("Downforce-Focused Kit", testAerodynamicKit.get(1).getKitName()),
                () -> assertEquals(0.35, testAerodynamicKit.get(1).getDragCoefficient()),
                () -> assertEquals(350, testAerodynamicKit.get(1).getDownforceValue()),
                () -> assertEquals(220, testAerodynamicKit.get(1).getTopSpeed()),
                () -> assertEquals(10, testAerodynamicKit.get(1).getFuelEfficiency()),
                () -> assertEquals(9, testAerodynamicKit.get(1).getCorneringAbility())
        );

        // Testing all variables that are present for third item in <List> testAerodynamicKit
        assertAll(
                () -> assertEquals("Low-Drag Kit", testAerodynamicKit.get(2).getKitName()),
                () -> assertEquals(0.25, testAerodynamicKit.get(2).getDragCoefficient()),
                () -> assertEquals(150, testAerodynamicKit.get(2).getDownforceValue()),
                () -> assertEquals(280, testAerodynamicKit.get(2).getTopSpeed()),
                () -> assertEquals(14, testAerodynamicKit.get(2).getFuelEfficiency()),
                () -> assertEquals(5, testAerodynamicKit.get(2).getCorneringAbility())
        );

        // Testing all variables that are present for fourth item in <List> testAerodynamicKit
        assertAll(
                () -> assertEquals("Adjustable Aero Kit", testAerodynamicKit.get(3).getKitName()),
                () -> assertEquals(0.31, testAerodynamicKit.get(3).getDragCoefficient()),
                () -> assertEquals(250, testAerodynamicKit.get(3).getDownforceValue()),
                () -> assertEquals(250, testAerodynamicKit.get(3).getTopSpeed()),
                () -> assertEquals(12, testAerodynamicKit.get(3).getFuelEfficiency()),
                () -> assertEquals(8, testAerodynamicKit.get(3).getCorneringAbility())
        );

        // Testing all variables that are present for fifth item in <List> testAerodynamicKit
        assertAll(
                () -> assertEquals("Ground Effect Kit", testAerodynamicKit.get(4).getKitName()),
                () -> assertEquals(0.27, testAerodynamicKit.get(4).getDragCoefficient()),
                () -> assertEquals(400, testAerodynamicKit.get(4).getDownforceValue()),
                () -> assertEquals(240, testAerodynamicKit.get(4).getTopSpeed()),
                () -> assertEquals(12, testAerodynamicKit.get(4).getFuelEfficiency()),
                () -> assertEquals(8, testAerodynamicKit.get(4).getCorneringAbility())
        );

        // Testing all variables that are present for sixth item in <List> testAerodynamicKit
        assertAll(
                () -> assertEquals("Drag Reduction System", testAerodynamicKit.get(5).getKitName()),
                () -> assertEquals(0.25, testAerodynamicKit.get(5).getDragCoefficient()),
                () -> assertEquals(200, testAerodynamicKit.get(5).getDownforceValue()),
                () -> assertEquals(290, testAerodynamicKit.get(5).getTopSpeed()),
                () -> assertEquals(13, testAerodynamicKit.get(5).getFuelEfficiency()),
                () -> assertEquals(6, testAerodynamicKit.get(5).getCorneringAbility())
        );

        // Testing all variables that are present for seventh item in <List> testAerodynamicKit
        assertAll(
                () -> assertEquals("Wet Weather Kit", testAerodynamicKit.get(6).getKitName()),
                () -> assertEquals(0.32, testAerodynamicKit.get(6).getDragCoefficient()),
                () -> assertEquals(220, testAerodynamicKit.get(6).getDownforceValue()),
                () -> assertEquals(230, testAerodynamicKit.get(6).getTopSpeed()),
                () -> assertEquals(11, testAerodynamicKit.get(6).getFuelEfficiency()),
                () -> assertEquals(7, testAerodynamicKit.get(6).getCorneringAbility())
        );

        // Testing all variables that are present for eighth item in <List> testAerodynamicKit
        assertAll(
                () -> assertEquals("Hybrid Kit", testAerodynamicKit.get(7).getKitName()),
                () -> assertEquals(0.29, testAerodynamicKit.get(7).getDragCoefficient()),
                () -> assertEquals(260, testAerodynamicKit.get(7).getDownforceValue()),
                () -> assertEquals(260, testAerodynamicKit.get(7).getTopSpeed()),
                () -> assertEquals(12, testAerodynamicKit.get(7).getFuelEfficiency()),
                () -> assertEquals(7, testAerodynamicKit.get(7).getCorneringAbility())
        );

        // Testing all variables that are present for ninth item in <List> testAerodynamicKit
        assertAll(
                () -> assertEquals("Extreme Aero Kit", testAerodynamicKit.get(8).getKitName()),
                () -> assertEquals(0.40, testAerodynamicKit.get(8).getDragCoefficient()),
                () -> assertEquals(500, testAerodynamicKit.get(8).getDownforceValue()),
                () -> assertEquals(200, testAerodynamicKit.get(8).getTopSpeed()),
                () -> assertEquals(9, testAerodynamicKit.get(8).getFuelEfficiency()),
                () -> assertEquals(10, testAerodynamicKit.get(8).getCorneringAbility())
        );
    }

    @DisplayName("[WB_GAP_07 - Critical]: Testing constructor that initialised private method initializeGameAssets," +
            "to check if Race Tracks were added into List.")
    @Test
    public void testConstructorForRaceTracksAddedIntoList() {
        // Testing all variables that are present for first item in <List> testRaceTrack
        assertAll(
                () -> assertEquals("Monaco", testRaceTrack.get(0).getName()),
                () -> assertEquals(3.337, testRaceTrack.get(0).getLength_km()),
                () -> assertEquals(78, testRaceTrack.get(0).getNumberOfLaps()),
                () -> assertEquals(1.3, testRaceTrack.get(0).getTyreWearFactor()),
                () -> assertEquals(1.1, testRaceTrack.get(0).getFuelConsumptionFactor())
        );

        // Testing all variables that are present for second item in <List> testRaceTrack
        assertAll(
                () -> assertEquals("Monza", testRaceTrack.get(1).getName()),
                () -> assertEquals(5.793, testRaceTrack.get(1).getLength_km()),
                () -> assertEquals(53, testRaceTrack.get(1).getNumberOfLaps()),
                () -> assertEquals(0.8, testRaceTrack.get(1).getTyreWearFactor()),
                () -> assertEquals(0.9, testRaceTrack.get(1).getFuelConsumptionFactor())
        );

        // Testing all variables that are present for third item in <List> testRaceTrack
        assertAll(
                () -> assertEquals("Silverstone", testRaceTrack.get(2).getName()),
                () -> assertEquals(5.891, testRaceTrack.get(2).getLength_km()),
                () -> assertEquals(52, testRaceTrack.get(2).getNumberOfLaps()),
                () -> assertEquals(1.0, testRaceTrack.get(2).getTyreWearFactor()),
                () -> assertEquals(1.0, testRaceTrack.get(2).getFuelConsumptionFactor())
        );
    }

    @DisplayName("[WB_GAP_08 - Critical]: Testing constructor that initialised private method initializeGameAssets," +
            "to check if Race Conditions were added into List.")
    @Test
    public void testConstructorForRaceConditionsAddedIntoList() {
        // Testing all variables that are present for first item in <List> testRaceConditions
        assertAll(
                () -> assertEquals("Dry", testRaceConditions.get(0).getName()),
                () -> assertEquals(RaceConditions.Weather.DRY, testRaceConditions.get(0).getWeather()),
                () -> assertEquals(25.0, testRaceConditions.get(0).getAirTemperature()),
                () -> assertEquals(35.0, testRaceConditions.get(0).getTrackTemperature()),
                () -> assertEquals(0.3, testRaceConditions.get(0).getHumidity())
        );

        // Testing all variables that are present for second item in <List> testRaceConditions
        assertAll(
                () -> assertEquals("Wet", testRaceConditions.get(1).getName()),
                () -> assertEquals(RaceConditions.Weather.WET, testRaceConditions.get(1).getWeather()),
                () -> assertEquals(15.0, testRaceConditions.get(1).getAirTemperature()),
                () -> assertEquals(18.0, testRaceConditions.get(1).getTrackTemperature()),
                () -> assertEquals(0.9, testRaceConditions.get(1).getHumidity())
        );

        // Testing all variables that are present for third item in <List> testRaceConditions
        assertAll(
                () -> assertEquals("Damp", testRaceConditions.get(2).getName()),
                () -> assertEquals(RaceConditions.Weather.DAMP, testRaceConditions.get(2).getWeather()),
                () -> assertEquals(20.0, testRaceConditions.get(2).getAirTemperature()),
                () -> assertEquals(25.0, testRaceConditions.get(2).getTrackTemperature()),
                () -> assertEquals(0.6, testRaceConditions.get(2).getHumidity())
        );
    }

    @DisplayName("[WB_GAP_09 - Critical]: Testing getter method for List<Engine> engineVariations.")
    @Test
    public void testGetterEngineVariations() {
        String expected = "[Engine{name='Standard V6', powerRating=300 HP, fuelEfficiency=7.5 km/l, reliability=" +
                "95%}" + ", Engine{name='Turbocharged V8', powerRating=550 HP, fuelEfficiency=5.0 km/l, reliability=" +
                "88%}" + ", Engine{name='EcoBoost I4', powerRating=180 HP, fuelEfficiency=12.0 km/l, reliability=" +
                "99%}]";
        assertEquals(expected, testGameAssetProvider.getEngineVariations().toString());
    }

    @DisplayName("[WB_GAP_10 - Critical]: Testing getter method for List<Tyre> tyreVariations.")
    @Test
    public void testGetterTyreVariations() {
        String expected = "[Tyres{compound='Soft Compound', gripLevel=95, wearRate=0.15, optimalTempRange=(80.0 - 100.0 °C)}" +
                ", Tyres{compound='Medium Compound', gripLevel=80, wearRate=0.08, optimalTempRange=(90.0 - 110.0 °C)}" +
                ", Tyres{compound='Hard Compound', gripLevel=65, wearRate=0.03, optimalTempRange=(100.0 - 120.0 °C)}]";
        assertEquals(expected, testGameAssetProvider.getTyreVariations().toString());
    }

    @DisplayName("[WB_GAP_11 - Critical]: Testing getter method for List<AerodynamicKit> aeroKitVariations.")
    @Test
    public void testGetterAeroKitVariations() {
        String expected = "[Standard Kit, Downforce-Focused Kit, Low-Drag Kit, Adjustable Aero Kit, " +
                "Ground Effect Kit, Drag Reduction System, Wet Weather Kit, Hybrid Kit, Extreme Aero Kit]";
        assertEquals(expected, testGameAssetProvider.getAeroKitVariations().toString());
    }

    @DisplayName("[WB_GAP_12 - Critical]: Testing getter method for List<RaceTrack> trackVariations.")
    @Test
    public void testGetterTrackVariations() {
        String expected = "[RaceTrack{name='Monaco', length_km=3.337 km, numberOfLaps=78, tyreWearFactor=1.30, " +
                "fuelConsumptionFactor=1.10}, RaceTrack{name='Monza', length_km=5.793 km, numberOfLaps=53, " +
                "tyreWearFactor=0.80, fuelConsumptionFactor=0.90}, RaceTrack{name='Silverstone', length_km=5.891 km, " +
                "numberOfLaps=52, tyreWearFactor=1.00, fuelConsumptionFactor=1.00}]";
        assertEquals(expected, testGameAssetProvider.getTrackVariations().toString());
    }

    @DisplayName("[WB_GAP_13 - Critical]: Testing getter method for List<RaceConditions> conditionVariations.")
    @Test
    public void testGetterConditionVariations() {
        String expected = "[Dry (Air: 25°C, Track: 35°C), Wet (Air: 15°C, Track: 18°C), Damp (Air: 20°C, Track: 25°C)]";
        assertEquals(expected, testGameAssetProvider.getConditionVariations().toString());
    }
}