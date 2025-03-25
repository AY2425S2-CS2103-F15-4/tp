package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LabScoreListTest {
    @Test
    public void testLabWeekValidity() {
        assertTrue(LabScoreList.isValidLabNumber("1"));
        assertTrue(LabScoreList.isValidLabNumber("4"));

        assertFalse(LabScoreList.isValidLabNumber("-1"));
        assertFalse(LabScoreList.isValidLabNumber("0"));
        assertFalse(LabScoreList.isValidLabNumber("8"));
    }
}
