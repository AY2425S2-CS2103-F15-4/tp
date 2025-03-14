package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "r/ [REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "r/ Likes to swim.";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";

    private final Index index;
    private final String remark;

    /**
     * Constructs a {@code RemarkCommand}.
     *
     * @param index  Index of the person in the filtered person list to edit the remark.
     * @param remark The new remark of the person.
     */
    public RemarkCommand(Index index, String remark) {
        requireAllNonNull(index, remark);
        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), remark));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        RemarkCommand otherRemarkCommand = (RemarkCommand) other;
        return index.equals(otherRemarkCommand.index) && remark.equals(otherRemarkCommand.remark);
    }

    @Override
    public int hashCode() {
        return index.hashCode() ^ remark.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + "{index=" + index.getOneBased() + ", remark='" + remark + "'}";
    }

}
