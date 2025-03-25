package seedu.tassist.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's remark in the address book.
 * Optional field.
 * Guarantees: immutable; is always valid.
 * Adapted from https://se-education.org/guides/tutorials/ab3AddRemark.html.
 */
public class Remark {

    public static final String VALIDATION_REGEX = "^[^\"]*$";

    public static final String MESSAGE_CONSTRAINTS = "Invalid remark!"
            + "\nRemarks with hyphens need to be encased in \"\"";

    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid year of study.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidRemark(remark), MESSAGE_CONSTRAINTS);
        value = remark;
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
    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX) || test.isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
