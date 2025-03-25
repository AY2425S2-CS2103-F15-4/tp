package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidTeleHandle_throwsIllegalArgumentException() {
        String invalidTeleHandle = "asdfag!124";
        assertThrows(IllegalArgumentException.class, () -> new TeleHandle(invalidTeleHandle));
    }

    @Test
    public void isValidPhone() {
        // Null phone number.
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // Invalid phone numbers.
        assertFalse(Phone.isValidPhone(" ")); // Spaces only.
        assertFalse(Phone.isValidPhone("91")); // Less than 3 numbers.
        assertFalse(Phone.isValidPhone("phone")); // Non-numeric.
        assertFalse(Phone.isValidPhone("9011p041")); // Alphabets within digits.
        assertFalse(Phone.isValidPhone("9312 1534")); // Spaces within digits.
        assertFalse(Phone.isValidPhone("12345678901234567890")); // Exceed.

        // Valid phone numbers.
        assertTrue(Phone.isValidPhone("")); // Empty string, optional field.
        assertTrue(Phone.isValidPhone("911")); // Exactly 3 numbers.
        assertTrue(Phone.isValidPhone("+6555")); // Start with '+'.
        assertTrue(Phone.isValidPhone("93121534")); // Default.
        assertTrue(Phone.isValidPhone("+123456789012345")); // Maximum 15 digits.
    }

    @Test
    public void isEmpty() {
        Phone phone = new Phone("");
        assertTrue(phone.isEmpty());

        phone = new Phone("5111");
        assertFalse(phone.isEmpty());
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // Same values -> returns true.
        assertTrue(phone.equals(new Phone("999")));

        // Same object -> returns true.
        assertTrue(phone.equals(phone));

        // Null -> returns false.
        assertFalse(phone.equals(null));

        // Different types -> returns false.
        assertFalse(phone.equals(5.0f));

        // Different values -> returns false.
        assertFalse(phone.equals(new Phone("995")));
    }
}
