package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class YearTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Year(null));
    }

    @Test
    public void constructor_invalidYear_throwsIllegalArgumentException() {
        String invalidYear = "abc";
        assertThrows(IllegalArgumentException.class, () -> new Year(invalidYear));
    }

    @Test
    public void isValidYear() {
        // Null Year.
        assertThrows(NullPointerException.class, () -> Year.isValidYear(null));

        // Invalid Year.
        assertFalse(Year.isValidYear("0")); // Out of range.
        assertFalse(Year.isValidYear("8")); // Out of range.
        assertFalse(Year.isValidYear(" ")); // Spaces only.
        assertFalse(Year.isValidYear("s")); // Characters only.
        assertFalse(Year.isValidYear("ab3")); // Alphanumeric.
        assertFalse(Year.isValidYear("1.04")); // Floats.
        assertFalse(Year.isValidYear("3-12")); // Numbers with hyphens.

        // Valid Year.
        assertTrue(Year.isValidYear("1"));
        assertTrue(Year.isValidYear("2"));
        assertTrue(Year.isValidYear("3"));
        assertTrue(Year.isValidYear("4"));
        assertTrue(Year.isValidYear("5"));
        assertTrue(Year.isValidYear("6"));

    }

    @Test
    public void isEmpty() {
        Year year = new Year("");
        assertTrue(year.isEmpty());

        year = new Year("5");
        assertFalse(year.isEmpty());
    }

    @Test
    public void equals() {
        Year year = new Year("3");

        // Same object -> returns true.
        assertTrue(year.equals(year));

        // Same values -> returns true.
        Year yearCopy = new Year(year.value);
        assertTrue(year.equals(yearCopy));

        // Different types -> returns false.
        assertFalse(year.equals(2.0f));

        // Null -> returns false.
        assertFalse(year.equals(null));

        // Different Year -> returns false.
        Year differentYear = new Year("5");
        assertFalse(year.equals(differentYear));
    }
}
