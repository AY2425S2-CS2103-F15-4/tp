package seedu.tassist.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Telegram handle in the address book.
 * Optional field.
 * Guarantees: immutable; is valid as declared in {@link #isValidTeleHandle(String)}
 */
public class TeleHandle {

    public static final String MESSAGE_CONSTRAINTS = "Invalid Telegram handle!"
            + "\nMust start with @, and can only contain alphanumeric characters and underscores."
            + "\nMinimally 5, maximally 32 characters long.";

    // Regex based on Telegram's handle rules.
    public static final String VALIDATION_REGEX = "^@[a-zA-Z0-9_]{5,32}$";

    public final String value;

    /**
     * Constructs a {@code TeleHandle}.
     *
     * @param teleHandle A valid Telegram handle.
     */
    public TeleHandle(String teleHandle) {
        requireNonNull(teleHandle);
        checkArgument(isValidTeleHandle(teleHandle), MESSAGE_CONSTRAINTS);
        value = teleHandle;
    }

    /**
     * Checks if value is empty.
     */
    public boolean isEmpty() {
        return value.isEmpty();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidTeleHandle(String test) {
        return test.matches(VALIDATION_REGEX) || test.isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TeleHandle)) {
            return false;
        }

        TeleHandle otherTeleHandle = (TeleHandle) other;
        return value.equals(otherTeleHandle.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
