package seedu.tassist.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's year of study in the address book.
 * Optional field.
 * Guarantees: immutable; is valid as declared in {@link #isValidYear(String)}
 */
public class Year {

    public static final String MESSAGE_CONSTRAINTS = "Invalid year!"
            + "\nOnly digits 1 through 6 are allowed.";

    public static final String VALIDATION_REGEX = "[1-6]";

    public final String value;

    /**
     * Constructs a {@code Year}.
     *
     * @param year A valid year of study.
     */
    public Year(String year) {
        requireNonNull(year);
        checkArgument(isValidYear(year), MESSAGE_CONSTRAINTS);
        value = year;
    }

    /**
     * Checks if value is empty.
     */
    public boolean isEmpty() {
        return value.isEmpty();
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidYear(String test) {
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
        if (!(other instanceof Year)) {
            return false;
        }

        Year otherYear = (Year) other;
        return value.equals(otherYear.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
