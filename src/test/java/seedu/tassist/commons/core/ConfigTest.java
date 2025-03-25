package seedu.tassist.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void toStringMethod() {
        Config config = new Config();
        String expected = Config.class.getCanonicalName() + "{logLevel=" + config.getLogLevel()
                + ", userPrefsFilePath=" + config.getUserPrefsFilePath() + "}";
        assertEquals(expected, config.toString());
    }

    @Test
    public void equals() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);

        // Same object -> returns true.
        assertTrue(defaultConfig.equals(defaultConfig));

        // Same values -> returns true.
        assertTrue(defaultConfig.equals(new Config()));

        // Null -> returns false.
        assertFalse(defaultConfig.equals((Config) null));

        // Different type -> returns false.
        assertFalse(defaultConfig.equals(0.5f));

        Config diffLogLevelConfig = new Config();
        diffLogLevelConfig.setLogLevel(Level.SEVERE);

        // Different LogLevel -> returns false.
        assertFalse(defaultConfig.equals(diffLogLevelConfig));

        Config diffPathConfig = new Config();
        diffPathConfig.setUserPrefsFilePath(Paths.get("../../../../../data/ConfigTest/preferences_other.json"));

        // Different Path -> returns false.
        assertFalse(defaultConfig.equals(diffPathConfig));
    }

    @Test
    public void hashCodeMethod() {
        Config config = new Config();
        assertEquals(config.hashCode(), Objects.hash(config.getLogLevel(), config.getUserPrefsFilePath()));
    }


}
