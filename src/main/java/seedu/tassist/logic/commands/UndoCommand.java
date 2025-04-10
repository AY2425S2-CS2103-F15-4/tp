package seedu.tassist.logic.commands;

import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.Operations;

/**
 * Undoes the previous command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Successfully undo %1$s command.\nCommand was: %2$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String response = Operations.undo(model);

        return new CommandResult(response);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UndoCommand)) {
            return false;
        }

        return true;
    }
}
