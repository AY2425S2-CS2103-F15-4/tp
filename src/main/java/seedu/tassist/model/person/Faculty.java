package seedu.tassist.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's faculty in the address book.
 * Optional field.
 * Guarantees: immutable; is valid as declared in {@link #isValidFaculty(String)}
 */
public class Faculty {

    public static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum} \\&\\-]*$";
    private static final String SPECIAL_CHARACTERS = "&-";
    public static final String MESSAGE_CONSTRAINTS = "Invalid faculty!"
            + "\nFaculties should only contain alphanumeric characters, spaces, "
            + "and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + ")."
            + "\nFaculties with hyphens need to be encased in \"\"";

    public final String value;

    /**
     * Constructs a {@code Faculty}.
     *
     * @param faculty A valid faculty.
     */
    public Faculty(String faculty) {
        requireNonNull(faculty);
        checkArgument(isValidFaculty(faculty), MESSAGE_CONSTRAINTS);
        value = faculty;
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
    public static boolean isValidFaculty(String test) {
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
        if (!(other instanceof Faculty)) {
            return false;
        }

        Faculty otherFaculty = (Faculty) other;
        return value.equals(otherFaculty.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
