package seedu.tassist.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GuiSettingsTest {
    @Test
    public void toStringMethod() {
        GuiSettings guiSettings = new GuiSettings();
        String expected = GuiSettings.class.getCanonicalName() + "{windowWidth=" + guiSettings.getWindowWidth()
                + ", windowHeight=" + guiSettings.getWindowHeight() + ", windowCoordinates="
                + guiSettings.getWindowCoordinates() + "}";
        assertEquals(expected, guiSettings.toString());
    }

    @Test
    public void equalsMethod() {
        GuiSettings guiSettings = new GuiSettings(500, 500, 10, 10);
        GuiSettings guiSettingsDuplicate = new GuiSettings(500, 500, 10, 10);

        // Same object -> returns true.
        assertTrue(guiSettings.equals(guiSettings));

        // Same values -> returns true.
        assertTrue(guiSettings.equals(guiSettingsDuplicate));

        // Null -> returns false.
        assertFalse(guiSettings.equals(null));

        // Different type -> returns false.
        assertFalse(guiSettings.equals(0.5f));

        GuiSettings guiSettingsDiffWindowWidth = new GuiSettings(600, 500, 10, 10);
        GuiSettings guiSettingsDiffWindowHeight = new GuiSettings(500, 600, 10, 10);
        GuiSettings guiSettingsDiffXPosition = new GuiSettings(500, 500, 20, 10);
        GuiSettings guiSettingsDiffYPosition = new GuiSettings(500, 500, 10, 20);

        // Different window width -> returns false.
        assertFalse(guiSettings.equals(guiSettingsDiffWindowWidth));

        // Different window height -> returns false.
        assertFalse(guiSettings.equals(guiSettingsDiffWindowHeight));

        // Different X position -> returns false.
        assertFalse(guiSettings.equals(guiSettingsDiffXPosition));

        // Different Y position -> returns false.
        assertFalse(guiSettings.equals(guiSettingsDiffYPosition));

    }
}
