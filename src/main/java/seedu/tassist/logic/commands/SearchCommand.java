package seedu.tassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TELE_HANDLE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.person.PersonMatchesPredicate;

/**
 * Searches for persons in the address book based on given criteria.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE =
        String.format(
            "Usage: %s [OPTIONS]...\n\n"
                    + "Searches for students based on given parameters.\n"
                    + "If no arguments are provided, shows all persons (same as 'list' command).\n\n"
                    + "Options:\n"
                    + "  %-7s       Name (supports partial match, but prioritizes exact full name matches)\n"
                    + "  %-7s       Matriculation number\n"
                    + "  %-7s       Phone number\n"
                    + "  %-7s       Telegram handle\n"
                    + "  %-7s       Email address\n"
                    + "  %-7s       Tutorial group\n"
                    + "  %-7s       Lab group\n"
                    + "  %-7s       Faculty\n"
                    + "  %-7s       Academic year\n"
                    + "  %-7s       Tag (can be specified multiple times)\n\n"
                    + "Example: %s %s Alice %s friend",
            COMMAND_WORD,
            PREFIX_NAME, PREFIX_MAT_NUM, PREFIX_PHONE, PREFIX_TELE_HANDLE, PREFIX_EMAIL,
            PREFIX_TUT_GROUP, PREFIX_LAB_GROUP, PREFIX_FACULTY, PREFIX_YEAR, PREFIX_TAG,
            COMMAND_WORD, PREFIX_NAME, PREFIX_TAG);

    private final PersonMatchesPredicate predicate;

    /**
     * Constructs a SearchCommand with the given search predicate.
     *
     * @param predicate The predicate to apply to the person list.
     */
    public SearchCommand(PersonMatchesPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * Checks whether two SearchCommands are equal.
     *
     * @param other The object to compare to.
     * @return True if both are SearchCommands with equal predicates.
     */
    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof SearchCommand
                && predicate.equals(((SearchCommand) other).predicate));
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + "{predicate=" + predicate + "}";
    }
}
