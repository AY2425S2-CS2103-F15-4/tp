package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TeleHandleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TeleHandle(null));
    }

    @Test
    public void constructor_invalidTeleHandle_throwsIllegalArgumentException() {
        String invalidTeleHandle = "asdfag!124";
        assertThrows(IllegalArgumentException.class, () -> new TeleHandle(invalidTeleHandle));
    }

    @Test
    public void isValidTeleHandle() {
        // Null teleHandle.
        assertThrows(NullPointerException.class, () -> TeleHandle.isValidTeleHandle(null));

        // Invalid teleHandle.
        assertFalse(TeleHandle.isValidTeleHandle(" ")); // Spaces only.
        assertFalse(TeleHandle.isValidTeleHandle("ababab")); // Does not start with '@'.
        assertFalse(TeleHandle.isValidTeleHandle("@hehe***haha")); // Contains unaccepted special characters.
        assertFalse(TeleHandle.isValidTeleHandle("@aba bab")); // Space in handle.
        assertFalse(TeleHandle.isValidTeleHandle("@abab")); // Shorter than 5 characters.
        assertFalse(TeleHandle.isValidTeleHandle("@abcdefghabcdefghabcdefghabcdefgha")); // Exceed 32 characters.

        // Valid teleHandle.
        assertTrue(TeleHandle.isValidTeleHandle("")); // Empty string.
        assertTrue(TeleHandle.isValidTeleHandle("@peter")); // Alphabets only.
        assertTrue(TeleHandle.isValidTeleHandle("@12345")); // Numbers only.
        assertTrue(TeleHandle.isValidTeleHandle("@Peter_the_2nd")); // Underscores with alphanumerics.
        assertTrue(TeleHandle.isValidTeleHandle("@CapitalTan")); // With capital letters.
        assertTrue(TeleHandle.isValidTeleHandle("@abcde")); // Minimum size.
        assertTrue(TeleHandle.isValidTeleHandle("@abcdefghabcdefghabcdefghabcdefgh")); // Maximum size.
    }

    @Test
    public void isEmpty() {
        TeleHandle teleHandle = new TeleHandle("");
        assertTrue(teleHandle.isEmpty());

        teleHandle = new TeleHandle("@abcde");
        assertFalse(teleHandle.isEmpty());
    }

    @Test
    public void equals() {
        TeleHandle teleHandle = new TeleHandle("@peter");

        // Same values -> returns true.
        assertTrue(teleHandle.equals(new TeleHandle("@peter")));

        // Same object -> returns true.
        assertTrue(teleHandle.equals(teleHandle));

        // Null -> returns false.
        assertFalse(teleHandle.equals(null));

        // Different types -> returns false.
        assertFalse(teleHandle.equals(5.0f));

        // Different values -> returns false.
        assertFalse(teleHandle.equals(new TeleHandle("@other_peter")));
    }
}
