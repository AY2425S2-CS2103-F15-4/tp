package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MatNumTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MatNum(null));
    }

    @Test
    public void constructor_invalidMatNum_throwsIllegalArgumentException() {
        String invalidMatNum = "abc";
        assertThrows(IllegalArgumentException.class, () -> new MatNum(invalidMatNum));
    }

    @Test
    public void isValidMatNum() {
        // Null MatNum.
        assertThrows(NullPointerException.class, () -> MatNum.isValidMatNum(null));

        // Invalid MatNum.
        assertFalse(MatNum.isValidMatNum("A012345678")); // Exceed character limit with digits.
        assertFalse(MatNum.isValidMatNum("A0000060MSSSSS")); // Exceed character limit with characters.
        assertFalse(MatNum.isValidMatNum("U0123456")); // Does not start with 'A'.
        assertFalse(MatNum.isValidMatNum("A012345")); // Insufficient digits.
        assertFalse(MatNum.isValidMatNum("A0123456M")); // Wrong checksum.
        assertFalse(MatNum.isValidMatNum("0123456J")); // Missing start character.

        // Valid MatNum.
        assertTrue(MatNum.isValidMatNum("A0123456J")); // Correct checksum.
        assertTrue(MatNum.isValidMatNum("A0123456")); // Missing checksum.
    }

    @Test
    public void equals() {
        MatNum matNum = new MatNum("A0123456J");
        MatNum sameMaNum = new MatNum("A0123456");

        // Same object -> returns true.
        assertTrue(matNum.equals(matNum));

        // Same values -> returns true.
        MatNum matNumCopy = new MatNum(matNum.value);
        assertTrue(matNum.equals(matNumCopy));

        // Same values -> returns true.
        assertTrue(matNum.equals(sameMaNum));

        // Different types -> returns false.
        assertFalse(matNum.equals(2.0f));

        // Null -> returns false.
        assertFalse(matNum.equals(null));

        // Different MatNum -> returns false.
        MatNum differentMatNum = new MatNum("A6543210W");
        assertFalse(matNum.equals(differentMatNum));
    }
}
