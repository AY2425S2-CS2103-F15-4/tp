package seedu.tassist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QuotePatternTest {

    @Test
    public void isValidAttendanceString() {

        QuotePattern tester = new QuotePattern();

        // Odd number of quotation marks -> returns false.
        assertFalse(tester.test("\"hehehaha\"\""));
        // More than a pair of quotation marks within a flag -> returns false.
        assertFalse(tester.test("-a \"hehehaha\"\"\" -g abc"));

        // No quotation marks -> returns true.
        assertTrue(tester.test("abc"));
        // One pair of quotation marks per flag -> returns true.
        assertTrue(tester.test("-t \"abc\" -g \" abc \""));
    }

}
