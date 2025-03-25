package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Adapted from https://se-education.org/guides/tutorials/ab3AddRemark.html
 */
public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        String invalidRemark = "asfd\"asfd";
        assertThrows(IllegalArgumentException.class, () -> new Remark(invalidRemark));
    }

    @Test
    public void isValidRemark() {
        // Null Remark.
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // Invalid Remark.
        assertFalse(Remark.isValidRemark("asd\"asd")); // Semi colons.

        // Valid Remark.
        assertTrue(Remark.isValidRemark("")); // Empty string.
        assertTrue(Remark.isValidRemark(" ")); // Spaces only.
        assertTrue(Remark.isValidRemark("singing and dancing")); // Alphabets only.
        assertTrue(Remark.isValidRemark("eating 500 chocolate bars")); // Numbers only.
        assertTrue(Remark.isValidRemark("diy101 fan")); // Alphanumeric characters.
        assertTrue(Remark.isValidRemark("Jumping jacks")); // With capital letters.
        assertTrue(Remark.isValidRemark("Walking around school")); // Long Remarks.
        assertTrue(Remark.isValidRemark("last-minute-worker")); // Hyphenated Remarks.
        assertTrue(Remark.isValidRemark("special !@#$%^&*( special")); // Remarks with special characters.
    }

    @Test
    public void isEmpty() {
        Remark remark = new Remark("");
        assertTrue(remark.isEmpty());

        remark = new Remark(" ");
        assertFalse(remark.isEmpty());
    }

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // Same object -> returns true.
        assertTrue(remark.equals(remark));

        // Same values -> returns true.
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        // Different types -> returns false.
        assertFalse(remark.equals(1));

        // Null -> returns false.
        assertFalse(remark.equals(null));

        // Different remark -> returns false.
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }
}
