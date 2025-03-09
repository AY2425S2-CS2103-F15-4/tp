package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AttendanceList;
import seedu.address.model.person.Person;

/**
 * Marks the attendance of a student within an existing week.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "att";

    public static final String MESSAGE_MARK_ATTENDED_SUCCESS =
            "Marked Person as Attended Tutorial Week %1$d: %2$s";

    // Note that -u and -mc will NOT be implemented yet.
    // We will settle the mandatory parameters first.

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of a student as identified"
            + " by the index number provided, for a particular week"
            + " as identified by the week number provided. "
            + "Existing attendance status will be overwritten by the input.\n"
            + "Mandatory Parameters: -i INDEX (must be a positive integer "
            + "that is a valid index)"
            + " -w WEEK NUMBER (must be a positive integer from 1 to 13)\n"
            + "Optional Parameters: -u (mark as not attended) "
            + "-mc (mark as on MC)\n"
            + "Note that either -u OR -mc OR neither can be provided."
            + "Example: " + COMMAND_WORD + " -i 1 -w 3\n"
            + "This marks student of index 1 as attended in tutorial week 3.";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Week: %2$d";

    private final Index index;

    private final int week;

    /**
     * Instantiates the MarkAttendanceCommand instance, with the provided
     * index and week.
     *
     * @param index index of person to be marked attendance for.
     * @param week week to mark attendance of person for.
     */
    public MarkAttendanceCommand(Index index, int week) {
        requireAllNonNull(index, week);
        this.index = index;
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        AttendanceList newAttendanceList =
                personToEdit.getAttendanceList().setAttendanceForWeek(week, 1);

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), null ,newAttendanceList, personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message for
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                this.week,
                Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkAttendanceCommand)) {
            return false;
        }

        MarkAttendanceCommand e = (MarkAttendanceCommand) other;
        return this.index.equals(e.index) && this.week == e.week;
    }

}
