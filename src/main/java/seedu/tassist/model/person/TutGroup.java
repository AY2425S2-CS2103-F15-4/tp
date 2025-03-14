package seedu.tassist.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's tutorial group in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTutGroup(String)}
 */
public class TutGroup {

    public static final String MESSAGE_CONSTRAINTS = "Invalid tutorial group!"
            + "\nTutorial group should either start with a 'T' and/or contain only numbers.";

    public static final String VALIDATION_REGEX = "^[Tt]\\d+$";

    public final String value;

    /**
     * Constructs a {@code TutGroup}.
     *
     * @param tutGroup A valid tutorial group.
     */
    public TutGroup(String tutGroup) {
        requireNonNull(tutGroup);
        checkArgument(isValidTutGroup(tutGroup), MESSAGE_CONSTRAINTS);
        value = tutGroup;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidTutGroup(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof TutGroup)) {
            return false;
        }

        Name otherTutGroup = (Name) other;
        return value.equals(otherTutGroup.fullName);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
