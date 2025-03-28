package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FacultyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Faculty(null));
    }

    @Test
    public void constructor_invalidFaculty_throwsIllegalArgumentException() {
        String invalidFaculty = "abc^%$\"#cba";
        assertThrows(IllegalArgumentException.class, () -> new Faculty(invalidFaculty));
    }

    @Test
    public void isValidFaculty() {
        // Null Faculty.
        assertThrows(NullPointerException.class, () -> Faculty.isValidFaculty(null));

        // Invalid Faculty.
        assertFalse(Faculty.isValidFaculty(" ")); // Spaces only.
        assertFalse(Faculty.isValidFaculty("\"")); // Double quotations.
        assertFalse(Faculty.isValidFaculty("!#@$%^&%*^")); // Symbols only.
        assertFalse(Faculty.isValidFaculty("12345677")); // Numbers only.

        // Valid Faculty.
        assertTrue(Faculty.isValidFaculty("")); // Empty.
        assertTrue(Faculty.isValidFaculty("abcdef")); // Characters only.
        assertTrue(Faculty.isValidFaculty("ABCdef")); // Uppercase characters.
        assertTrue(Faculty.isValidFaculty("3rd School of Yay")); // Alphanumeric characters.
        assertTrue(Faculty.isValidFaculty("3rd-School-of-Nay")); // Hyphens.
        assertTrue(Faculty.isValidFaculty("3rd School of Yay & Nay")); // Ampersand.
        assertTrue(Faculty.isValidFaculty("3rd-School-of-Yay-&-Nay")); // Hyphens and Ampersand.
    }

    @Test
    public void isEmpty() {
        Faculty faculty = new Faculty("");
        assertTrue(faculty.isEmpty());

        faculty = new Faculty("a");
        assertFalse(faculty.isEmpty());
    }

    @Test
    public void equals() {
        Faculty faculty = new Faculty("b");

        // Same object -> returns true.
        assertTrue(faculty.equals(faculty));

        // Same values -> returns true.
        Faculty facultyCopy = new Faculty(faculty.value);
        assertTrue(faculty.equals(facultyCopy));

        // Different types -> returns false.
        assertFalse(faculty.equals(2.0f));

        // Null -> returns false.
        assertFalse(faculty.equals(null));

        // Different Faculty -> returns false.
        Faculty differentFaculty = new Faculty("x");
        assertFalse(faculty.equals(differentFaculty));
    }
}
