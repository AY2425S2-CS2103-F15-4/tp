package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // Null name.
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // Invalid name.
        assertFalse(Name.isValidName("")); // Empty string.
        assertFalse(Name.isValidName(" ")); // Spaces only.
        assertFalse(Name.isValidName("^")); // Only non-alphanumeric characters.
        assertFalse(Name.isValidName("peter*")); // Contains non-alphanumeric characters.

        // Valid name.
        assertTrue(Name.isValidName("peter jack")); // Alphabets only.
        assertTrue(Name.isValidName("12345")); // Numbers only.
        assertTrue(Name.isValidName("peter the 2nd")); // Alphanumeric characters.
        assertTrue(Name.isValidName("Capital Tan")); // With capital letters.
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // Long names.
        assertTrue(Name.isValidName("Robby-Downy")); // Hyphenated names.
        assertTrue(Name.isValidName("Devi D/O Rajaratnam")); // Names with slashes.
        assertTrue(Name.isValidName("Robert Jr.")); // Names with fullstops.
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // Same values -> returns true.
        assertTrue(name.equals(new Name("Valid Name")));

        // Same object -> returns true.
        assertTrue(name.equals(name));

        // Null -> returns false.
        assertFalse(name.equals(null));

        // Different types -> returns false.
        assertFalse(name.equals(5.0f));

        // Different values -> returns false.
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
