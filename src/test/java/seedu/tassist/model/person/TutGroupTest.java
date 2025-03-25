package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TutGroupTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutGroup(null));
    }

    @Test
    public void constructor_invalidTutGroup_throwsIllegalArgumentException() {
        String invalidTutGroup = "abc^%$#cba";
        assertThrows(IllegalArgumentException.class, () -> new TutGroup(invalidTutGroup));
    }

    @Test
    public void isValidTutGroup() {
        // Null TutGroup.
        assertThrows(NullPointerException.class, () -> TutGroup.isValidTutGroup(null));

        // Invalid TutGroup.
        assertFalse(TutGroup.isValidTutGroup("T^.")); // Unaccepted special characters.
        assertFalse(TutGroup.isValidTutGroup("T")); // Single character.
        assertFalse(TutGroup.isValidTutGroup("T0T")); // Last two characters are not digits.
        assertFalse(TutGroup.isValidTutGroup(" ")); // Spaces only.
        assertFalse(TutGroup.isValidTutGroup("T0")); // Zeroes only.
        assertFalse(TutGroup.isValidTutGroup("T00")); // Zeroes only.
        assertFalse(TutGroup.isValidTutGroup("T001")); // More than two digits.
        assertFalse(TutGroup.isValidTutGroup("T111")); // More than two digits.
        assertFalse(LabGroup.isValidLabGroup(" T 1 2 ")); // Spaces.

        // Valid TutGroup.
        assertTrue(TutGroup.isValidTutGroup("")); // Empty.
        assertTrue(TutGroup.isValidTutGroup("T01")); // Larger than zero.
        assertTrue(TutGroup.isValidTutGroup("T1")); // Larger than zero.
        assertTrue(TutGroup.isValidTutGroup("T10")); // Larger than zero.
        assertTrue(TutGroup.isValidTutGroup("t05")); // Larger than zero.
        assertTrue(TutGroup.isValidTutGroup("t5")); // Larger than zero.
        assertTrue(TutGroup.isValidTutGroup("t50")); // Larger than zero.
    }

    @Test
    public void isEmpty() {
        TutGroup tutGroup = new TutGroup("");
        assertTrue(tutGroup.isEmpty());

        tutGroup = new TutGroup("t5");
        assertFalse(tutGroup.isEmpty());
    }

    @Test
    public void equals() {
        TutGroup tutGroup = new TutGroup("t3");
        TutGroup sameTutGroupOne = new TutGroup("t03");
        TutGroup sameTutGroupTwo = new TutGroup("T3");
        TutGroup sameTutGroupThree = new TutGroup("T03");

        // Same object -> returns true.
        assertTrue(tutGroup.equals(tutGroup));

        // Same values -> returns true.
        TutGroup tutGroupCopy = new TutGroup(tutGroup.value);
        assertTrue(tutGroup.equals(tutGroupCopy));
        assertTrue(tutGroup.equals(sameTutGroupOne));
        assertTrue(tutGroup.equals(sameTutGroupTwo));
        assertTrue(tutGroup.equals(sameTutGroupThree));

        // Different types -> returns false.
        assertFalse(tutGroup.equals(2.0f));

        // Null -> returns false.
        assertFalse(tutGroup.equals(null));

        // Different TutGroup -> returns false.
        TutGroup differentTutGroup = new TutGroup("t05");
        assertFalse(tutGroup.equals(differentTutGroup));
    }

}
