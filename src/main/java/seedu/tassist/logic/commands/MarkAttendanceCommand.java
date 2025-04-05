package seedu.tassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_NOT_ATTENDED;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_NO_TUTORIAL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_ON_MC;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.tassist.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.tassist.commons.core.LogsCenter;
import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.logic.parser.ParserUtil;
import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.person.Attendance;
import seedu.tassist.model.person.AttendanceList;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.TutGroup;

/**
 * Marks the attendance of a student within an existing week.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "att";

    public static final String MESSAGE_MARK_WHEN_NO_TUTORIAL_FAILURE =
            "%1$s (%2$s) in Tutorial Group %3$s currently has No Tutorial for Tutorial Week %4$d.\n"
            + "You must mark the Tutorial Group %3$s as Attended or Not Attended for Week %4$d,\n"
            + "e.g. using the command: " + COMMAND_WORD + " " + PREFIX_TUT_GROUP + " %3$s "
            + PREFIX_WEEK + " %4$d , which marks tutorial group as attended,\n"
            + "before you can mark %1$s individually as Attended, Not Attended or On MC.";

    public static final String MESSAGE_INVALID_TUT_GROUPS_FAILURE =
            "%1$s are invalid Tutorial Groups.\n"
            + "You cannot mark attendance for these Tutorial Groups.\n"
            + "Please do not include these Tutorial Groups when marking attendance.\n";

    public static final String MESSAGE_MARK_GIVEN_NO_TUT_GROUP_FAILURE =
            "%1$s (%2$s) has No Tutorial Group, so Attendance cannot be marked.\n";

    public static final String MESSAGE_MARK_ATTENDED_SUCCESS =
            "%1$s (%2$s) attended Tutorial Week %3$d.";

    public static final String MESSAGE_MARK_NOT_ATTENDED_SUCCESS =
            "%1$s (%2$s) did not attend Tutorial Week %3$d.";

    public static final String MESSAGE_MARK_MC_SUCCESS =
            "%1$s (%2$s) on MC for Tutorial Week %3$d.";

    public static final String MESSAGE_MARK_NO_TUTORIAL_SUCCESS =
            "%1$s (%2$s) has No Tutorial for Tutorial Week %3$d.";

    public static final String MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS =
            "Everyone in %1$s attended Tutorial Week %2$d.";

    public static final String MESSAGE_MARK_TUT_GROUP_NOT_ATTENDED_SUCCESS =
            "Everyone in %1$s did not attend Tutorial Week %2$d.";

    public static final String MESSAGE_MARK_TUT_GROUP_MC_SUCCESS =
            "Everyone in %1$s on MC Tutorial Week %2$d.";

    public static final String MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS =
            "Everyone in %1$s has No Tutorial for Tutorial Week %2$d.";

    public static final String MESSAGE_USAGE = String.format(
            "%s: %s (%s INDEX | %s TUTORIAL_GROUP) %s WEEK_NUMBER [OPTIONS]\n"
                    + "    Marks the attendance of a student or tutorial group for a specific week.\n"
                    + "    Existing attendance status will be overwritten by the input.\n\n"
                    + "    Conditional Parameters: (Either or BUT not both)\n"
                    + "      %s INDEX           Student index (positive integer, valid index)\n"
                    + "      %s TUTORIAL_GROUP  Tutorial group ('T' or 't', followed by a positive integer)\n\n"
                    + "    Mandatory Parameters:\n"
                    + "      %s WEEK_NUMBER     Week number (positive integer, 1-13 inclusive)\n\n"
                    + "    Options:\n"
                    + "      %s                Mark as not attended\n"
                    + "      %s                Mark as on MC\n"
                    + "      %s                Mark as no tutorial\n\n"
                    + "    Additional Restrictions:\n"
                    + "      - Only one of -u, -mc, or -nt can be provided, or none.\n"
                    + "      - The -nt flag cannot be used with -i (cannot mark a single student as no tutorial).\n"
                    + "      - If a student currently has no tutorial, you cannot mark his attendance using -i flag.\n"
                    + "        (Students can only be marked TO No Tutorial, and FROM No Tutorial to other statuses,\n"
                    + "         through commands that target the whole tutorial group.).\n"
                    + "    Batch Marking of Attendance:\n"
                    + "      - You can provide a comma-separated or dashed range of indexes and tutorial groups.\n"
                    + "        For example, att -i 1,3,5 -w 1 marks attendance for indexes 1,3,5.\n"
                    + "        att -t T01-T03 -w 1 marks attendance for tutorial groups T01, T02 and T03.\n"
                    + "    Examples:\n"
                    + "      %s -i 1 -w 3\n"
                    + "        Marks student at index 1 as attended in tutorial week 3.\n\n"
                    + "      %s -i 3 -w 5 -mc\n"
                    + "        Marks student at index 3 as on MC in tutorial week 5.\n\n"
                    + "      %s -t T01 -w 2 -u\n"
                    + "        Marks all students in tutorial group T01 as not attended in tutorial week 2.\n\n"
                    + "      %s -t T01 -w 13 -nt\n"
                    + "        Marks all students in tutorial group T01 as no tutorial in tutorial week 13.\n\n"
                    + "    Exit Status:\n"
                    + "      Returns success unless an invalid index, tutorial group, week number, or option is given.",
            COMMAND_WORD, COMMAND_WORD, PREFIX_INDEX, PREFIX_TUT_GROUP, PREFIX_WEEK,
            PREFIX_INDEX, PREFIX_TUT_GROUP,
            PREFIX_WEEK, PREFIX_MARK_NOT_ATTENDED, PREFIX_MARK_ON_MC, PREFIX_MARK_NO_TUTORIAL,
            COMMAND_WORD, COMMAND_WORD, COMMAND_WORD, COMMAND_WORD
    );

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final List<Index> indexList;

    private final List<TutGroup> tutGroupList;

    private final int week;

    private final int attendanceStatus;

    /**
     * Instantiates the MarkAttendanceCommand instance, with the provided
     * index, week and attendanceStatus.
     *
     * @param indexList List of Indexes of person to mark attendance for.
     * @param week Week to mark attendance of person for.
     * @param attendanceStatus New Attendance Status to set the person or tutorial group to.
     */
    public MarkAttendanceCommand(List<Index> indexList, int week, int attendanceStatus) {
        requireAllNonNull(indexList, week, attendanceStatus);
        this.indexList = indexList;
        this.week = week;
        this.tutGroupList = null;
        this.attendanceStatus = attendanceStatus;
    }

    /**
     * Instantiates the MarkAttendanceCommand instance, with the provided
     * tutGroupList, week and attendanceStatus.
     *
     * @param tutGroupList List of Tutorial groups to mark attendance for. Should be null if index is not null.
     * @param week Week to mark attendance of person for.
     * @param attendanceStatus New Attendance Status to set the person or tutorial group to.
     */
    public MarkAttendanceCommand(int week, int attendanceStatus, List<TutGroup> tutGroupList) {
        requireAllNonNull(tutGroupList, week, attendanceStatus);
        this.indexList = null;
        this.week = week;
        this.tutGroupList = tutGroupList;
        this.attendanceStatus = attendanceStatus;
    }

    /**
     * Checks if a Command applied on an Index list is valid, and throws a CommandException if not.
     * Here, the method checks for two invalid cases:
     * Firstly, if the command is marking attendance for a person who has no tutorial group,
     * and hence has an empty attendance list.
     * Secondly, if the command is marking attendance for a person who has no tutorial for the given week.
     *
     * This method applies only for commands applied on an Index list.
     * Commands applied on a TutGroup list will not involve people with an Empty AttendanceList
     * and a whole tutorial group can be marked to any attendance status,
     * regardless of the initial attendance status of students within it.
     *
     * @param personToCheck Person to be checked against.
     * @throws CommandException Exception to be thrown if Index Flag is Invalid.
     */
    public void checkIfIndexFlagCommandValid(Person personToCheck) throws CommandException {

        // Assert that the command is being applied on an index list, not a tutGroup list.
        assert this.indexList != null && this.tutGroupList == null;

        if (personToCheck.getAttendanceList() == AttendanceList.EMPTY_ATTENDANCE_LIST) {
            logger.info("Invalid Command: Person has No TutGroup hence Empty AttendanceList.");
            // Cannot mark attendance if AttendanceList is Empty.
            throw new CommandException(String.format(
                    MESSAGE_MARK_GIVEN_NO_TUT_GROUP_FAILURE, personToCheck.getName(), personToCheck.getMatNum()));
        } else if (personToCheck.getAttendanceList().getAttendanceForWeek(week) == Attendance.NO_TUTORIAL) {
            logger.info("Invalid Command: Individual currently has No Tutorial.");
            // Cannot mark attendance if attendance status is currently No Tutorial.
            throw new CommandException(String.format(
                    MESSAGE_MARK_WHEN_NO_TUTORIAL_FAILURE, personToCheck.getName(), personToCheck.getMatNum(),
                    personToCheck.getTutGroup(), week));
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> personsToEdit;
        StringBuilder successMessage = new StringBuilder();

        if (tutGroupList != null) {
            String invalidTutGroupsString = ParserUtil.getInvalidTutGroupsAsString(lastShownList, tutGroupList);
            if (!invalidTutGroupsString.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_INVALID_TUT_GROUPS_FAILURE, invalidTutGroupsString));
            }
            personsToEdit = ParserUtil.getPersonsInTutorialGroups(lastShownList, tutGroupList);
            successMessage.append(generateSuccessMessage(tutGroupList)).append("\n-------------\n");
        } else {
            boolean allIndexesValid = indexList.stream()
                    .map(index -> index.getZeroBased() < lastShownList.size())
                    .reduce(true, (a, b) -> a && b);
            if (!allIndexesValid) {
                throw new CommandException(
                        String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, lastShownList.size()));
            }
            personsToEdit = new ArrayList<>();
            StringBuilder invalidPersonsErrorMessage = new StringBuilder();
            for (Index index : indexList) {
                Person personToEdit = lastShownList.get(index.getZeroBased());
                try {
                    this.checkIfIndexFlagCommandValid(personToEdit);
                    personsToEdit.add(personToEdit);
                } catch (CommandException e) {
                    invalidPersonsErrorMessage.append(e.getMessage());
                }
            }
            if (!invalidPersonsErrorMessage.toString().isEmpty()) {
                throw new CommandException(invalidPersonsErrorMessage.toString());
            }
        }

        for (Person personToEdit : personsToEdit) {
            AttendanceList newAttendanceList =
                    personToEdit.getAttendanceList().setAttendanceForWeek(this.week, this.attendanceStatus);

            Person editedPerson = new Person(
                    personToEdit.getName(), personToEdit.getPhone(), personToEdit.getTeleHandle(),
                    personToEdit.getEmail(), personToEdit.getMatNum(), personToEdit.getTutGroup(),
                    personToEdit.getLabGroup(), personToEdit.getFaculty(), personToEdit.getYear(),
                    personToEdit.getRemark(), newAttendanceList, personToEdit.getLabScoreList(),
                    personToEdit.getTags());

            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            successMessage.append(generateSuccessMessage(editedPerson)).append("\n");
        }

        return new CommandResult(successMessage.toString());

    }

    /**
     * Generates a command execution success message for
     * {@code personToEdit}.
     *
     * @return String with the Success Message once MarkAttendanceCommand
     *         is executed successfully.
     */
    private String generateSuccessMessage(Person personToEdit) {
        requireNonNull(personToEdit);
        String message = "";
        switch (attendanceStatus) {
        case Attendance.ATTENDED:
            message = MESSAGE_MARK_ATTENDED_SUCCESS;
            break;
        case Attendance.NOT_ATTENDED:
            message = MESSAGE_MARK_NOT_ATTENDED_SUCCESS;
            break;
        case Attendance.ON_MC:
            message = MESSAGE_MARK_MC_SUCCESS;
            break;
        case Attendance.NO_TUTORIAL:
            message = MESSAGE_MARK_NO_TUTORIAL_SUCCESS;
            break;
        default:
            message = MESSAGE_USAGE;
        }

        return String.format(message,
                personToEdit.getName(),
                personToEdit.getMatNum(),
                this.week);
    }

    /**
     * Generates a command execution success message for
     * {@code tutGroupsToEdit}.
     *
     * @return String with the Success Message once MarkAttendanceCommand
     *         is executed successfully.
     */
    private String generateSuccessMessage(List<TutGroup> tutGroupsToEdit) {
        requireNonNull(tutGroupsToEdit);
        String message = "";
        switch (attendanceStatus) {
        case Attendance.ATTENDED:
            message = MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS;
            break;
        case Attendance.NOT_ATTENDED:
            message = MESSAGE_MARK_TUT_GROUP_NOT_ATTENDED_SUCCESS;
            break;
        case Attendance.ON_MC:
            message = MESSAGE_MARK_TUT_GROUP_MC_SUCCESS;
            break;
        case Attendance.NO_TUTORIAL:
            message = MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS;
            break;
        default:
            message = MESSAGE_USAGE;
        }

        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) -> acc + tg.toString() + ", ", (x, y) -> x + y);
        return String.format(message, tutGroupString.substring(0, tutGroupString.length() - 2), this.week);
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
        if (this.week != e.week || this.attendanceStatus != e.attendanceStatus) {
            return false;
        }

        if (this.indexList != null) {
            return this.indexList.equals(e.indexList);
        } else {
            return this.tutGroupList.equals(e.tutGroupList);
        }
    }

}
