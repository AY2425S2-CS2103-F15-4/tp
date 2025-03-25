package seedu.tassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TELE_HANDLE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.tassist.commons.util.ToStringBuilder;
import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE =
            String.format(
            "Usage: %s [OPTIONS]...\n\n"
                    + "Adds a person to the address book.\n"
                    + "Mandatory arguments:\n"
                    + "  %-7s       Name\n"
                    + "  %-7s       Email\n"
                    + "  %-7s       Matriculation number\n\n"
                    + "At least one of these must be provided:\n"
                    + "  %-7s       Phone number\n"
                    + "  %-7s       Telegram handle\n\n"
                    + "At least one of these must be provided:\n"
                    + "  %-7s       Tutorial group\n"
                    + "  %-7s       Lab group\n"
                    + "Optional arguments:\n"
                    + "  %-7s       Faculty\n"
                    + "  %-7s       Academic year\n"
                    + "  %-7s       Remarks\n"
                    + "  %-7s       Tag (can be specified multiple times) \n\n"
                    + "Example: %s %s John Doe %s johnd@example.com %s A0123456J %s 98765432"
                    + "%s @johnDoe %s T01 %s B02 %s School of Computing %s 3 %s TA candidate"
                    + "%s friends %s owesMoney",
                    COMMAND_WORD, PREFIX_NAME, PREFIX_EMAIL, PREFIX_MAT_NUM, PREFIX_PHONE,
                    PREFIX_TELE_HANDLE, PREFIX_TUT_GROUP, PREFIX_LAB_GROUP, PREFIX_FACULTY,
                    PREFIX_YEAR, PREFIX_REMARK, PREFIX_TAG, COMMAND_WORD, PREFIX_NAME, PREFIX_EMAIL,
                    PREFIX_MAT_NUM, PREFIX_PHONE, PREFIX_TELE_HANDLE, PREFIX_TUT_GROUP,
                    PREFIX_LAB_GROUP, PREFIX_FACULTY, PREFIX_YEAR, PREFIX_REMARK, PREFIX_TAG,
                    PREFIX_TAG);


    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "This person already exists in the address book (same matriculation number)";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}.
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.getFormattedPersonAttributesForDisplay(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
