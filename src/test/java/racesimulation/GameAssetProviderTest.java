package racesimulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @DisplayName("WB_GAPV_01: Testing Lists are not null or empty after setUp. (Criticality: Critical)")
    @Test
    public void testNotNullLists() {
        assertAll(
                () -> assertNotNull(testEngine),
                () -> assertNotNull(testTyres),
                () -> assertNotNull(testAerodynamicKit),
                () -> assertNotNull(testRaceTrack),
                () -> assertNotNull(testRaceConditions)
        );

        assertAll(
                () -> assertFalse(testEngine.isEmpty()),
                () -> assertFalse(testTyres.isEmpty()),
                () -> assertFalse(testAerodynamicKit.isEmpty()),
                () -> assertFalse(testRaceTrack.isEmpty()),
                () -> assertFalse(testRaceConditions.isEmpty())
        );
    }

    @DisplayName("WB_GAPV_02: Testing Lists size after setUp. (Criticality: Critical)")
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

    @DisplayName("WB_GAPV_03: Testing constructor that initialised private method initializeGameAssets. " +
            "(Criticality: Critical)")
    @Test
    public void testConstructorWithInitialisedGameAssets() {
        assertEquals("Standard V6", testEngine.get(0).getName());
    }

    @Test
    void getTyreVariations() {
    }

    @Test
    void getAeroKitVariations() {
    }

    @Test
    void getTrackVariations() {
    }

    @Test
    void getConditionVariations() {
    }
}