package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
public class RemarkTutorialHY {
    public final String value;

    public RemarkTutorialHY(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkTutorialHY // instanceof handles nulls
                && value.equals(((RemarkTutorialHY) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
