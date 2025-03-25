package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LabGroupTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LabGroup(null));
    }

    @Test
    public void constructor_invalidLabGroup_throwsIllegalArgumentException() {
        String invalidLabGroup = "abc^%$#cba";
        assertThrows(IllegalArgumentException.class, () -> new LabGroup(invalidLabGroup));
    }

    @Test
    public void isValidLabGroup() {
        // Null LabGroup.
        assertThrows(NullPointerException.class, () -> LabGroup.isValidLabGroup(null));

        // Invalid LabGroup.
        assertFalse(LabGroup.isValidLabGroup("B^.")); // Unaccepted special characters.
        assertFalse(LabGroup.isValidLabGroup("B")); // Single character.
        assertFalse(LabGroup.isValidLabGroup("B0B")); // Last two characters are not digits.
        assertFalse(LabGroup.isValidLabGroup(" ")); // Spaces only.
        assertFalse(LabGroup.isValidLabGroup("B0")); // Zeroes only.
        assertFalse(LabGroup.isValidLabGroup("B00")); // Zeroes only.
        assertFalse(LabGroup.isValidLabGroup("B001")); // More than two digits.
        assertFalse(LabGroup.isValidLabGroup("B111")); // More than two digits.
        assertFalse(LabGroup.isValidLabGroup(" B 1 2 ")); // Spaces.

        // Valid LabGroup.
        assertTrue(LabGroup.isValidLabGroup("")); // Empty.
        assertTrue(LabGroup.isValidLabGroup("B01")); // Larger than zero.
        assertTrue(LabGroup.isValidLabGroup("B1")); // Larger than zero.
        assertTrue(LabGroup.isValidLabGroup("B10")); // Larger than zero.
        assertTrue(LabGroup.isValidLabGroup("b05")); // Larger than zero.
        assertTrue(LabGroup.isValidLabGroup("b5")); // Larger than zero.
        assertTrue(LabGroup.isValidLabGroup("b50")); // Larger than zero.
    }

    @Test
    public void isEmpty() {
        LabGroup labGroup = new LabGroup("");
        assertTrue(labGroup.isEmpty());

        labGroup = new LabGroup("B51");
        assertFalse(labGroup.isEmpty());
    }

    @Test
    public void equals() {
        LabGroup labGroup = new LabGroup("b3");
        LabGroup sameLabGroupOne = new LabGroup("b03");
        LabGroup sameLabGroupTwo = new LabGroup("B3");
        LabGroup sameLabGroupThree = new LabGroup("B03");

        // Same object -> returns true.
        assertTrue(labGroup.equals(labGroup));

        // Same values -> returns true.
        LabGroup labGroupCopy = new LabGroup(labGroup.value);
        assertTrue(labGroup.equals(labGroupCopy));
        assertTrue(labGroup.equals(sameLabGroupOne));
        assertTrue(labGroup.equals(sameLabGroupTwo));
        assertTrue(labGroup.equals(sameLabGroupThree));

        // Different types -> returns false.
        assertFalse(labGroup.equals(2.0f));

        // Null -> returns false.
        assertFalse(labGroup.equals(null));

        // Different LabGroup -> returns false.
        LabGroup differentLabGroup = new LabGroup("b05");
        assertFalse(labGroup.equals(differentLabGroup));
    }
}
