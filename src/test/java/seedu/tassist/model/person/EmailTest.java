package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // Null email.
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // Blank email.
        assertFalse(Email.isValidEmail("")); // Empty string.
        assertFalse(Email.isValidEmail(" ")); // Spaces only.

        // Missing parts
        assertFalse(Email.isValidEmail("@example.com")); // Missing local part
        assertFalse(Email.isValidEmail("peterjackexample.com")); // Missing '@' symbol
        assertFalse(Email.isValidEmail("peterjack@")); // Missing domain name

        // Invalid parts
        assertFalse(Email.isValidEmail("peterjack@-")); // Invalid domain name
        assertFalse(Email.isValidEmail("peterjack@exam_ple.com")); // Underscore in domain name
        assertFalse(Email.isValidEmail("peter jack@example.com")); // Spaces in local part
        assertFalse(Email.isValidEmail("peterjack@exam ple.com")); // Spaces in domain name
        assertFalse(Email.isValidEmail(" peterjack@example.com")); // Leading space
        assertFalse(Email.isValidEmail("peterjack@example.com ")); // Trailing space
        assertFalse(Email.isValidEmail("peterjack@@example.com")); // Double '@' symbol
        assertFalse(Email.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("-peterjack@example.com")); // Local part starts with a hyphen
        assertFalse(Email.isValidEmail("peterjack-@example.com")); // Local part ends with a hyphen
        assertFalse(Email.isValidEmail("peter..jack@example.com")); // Local part has two consecutive periods
        assertFalse(Email.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Email.isValidEmail("peterjack@.example.com")); // Domain name starts with a period
        assertFalse(Email.isValidEmail("peterjack@example.com.")); // Domain name ends with a period
        assertFalse(Email.isValidEmail("peterjack@-example.com")); // Domain name starts with a hyphen
        assertFalse(Email.isValidEmail("peterjack@example.com-")); // Domain name ends with a hyphen
        assertFalse(Email.isValidEmail("peterjack@example.c")); // Top level domain has less than two chars

        // Valid email
        assertTrue(Email.isValidEmail("PeterJack_1190@example.com")); // Underscore in local part
        assertTrue(Email.isValidEmail("PeterJack.1190@example.com")); // Period in local part
        assertTrue(Email.isValidEmail("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(Email.isValidEmail("PeterJack-1190@example.com")); // Hyphen in local part
        assertTrue(Email.isValidEmail("a@bc")); // Minimal
        assertTrue(Email.isValidEmail("test@localhost")); // Alphabets only
        assertTrue(Email.isValidEmail("123@145")); // Numeric local part and domain name
        assertTrue(Email.isValidEmail("a1+be.d@example1.com")); // Mixture of alphanumeric and special characters
        assertTrue(Email.isValidEmail("peter_jack@very-very-very-long-example.com")); // Long domain name
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // Long local part
        assertTrue(Email.isValidEmail("e1234567@u.nus.edu")); // More than one period in domain
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email");

        // Same values -> returns true.
        assertTrue(email.equals(new Email("valid@email")));

        // Same object -> returns true.
        assertTrue(email.equals(email));

        // Null -> returns false.
        assertFalse(email.equals(null));

        // Different types -> returns false.
        assertFalse(email.equals(5.0f));

        // Different values -> returns false.
        assertFalse(email.equals(new Email("other.valid@email")));
    }
}
