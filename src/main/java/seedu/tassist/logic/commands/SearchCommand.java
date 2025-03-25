package seedu.tassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches for students based on given parameters. "
            + "Parameters: "
            + "[" + PREFIX_NAME + " NAME] "
            + "[" + PREFIX_MAT_NUM + " MATRICULATION NUMBER] "
            + "[" + PREFIX_TUT_GROUP + " TUTORIAL GROUP] "
            + "[" + PREFIX_LAB_GROUP + " LAB GROUP] "
            + "[" + PREFIX_FACULTY + " FACULTY] "
            + "[" + PREFIX_YEAR + " YEAR] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_NAME + " John Doe "
            + PREFIX_MAT_NUM + " A0123456J "
            + PREFIX_YEAR + " 3 ";

    private final PersonMatchesPredicate predicate;

    /**
     * Constructs a SearchCommand.
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

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof SearchCommand && predicate.equals(((SearchCommand) other).predicate));
    }
}
