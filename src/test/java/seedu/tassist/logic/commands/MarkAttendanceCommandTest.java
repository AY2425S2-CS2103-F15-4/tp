package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_WEEK_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_WEEK_B;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tassist.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_ATTENDED_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_MC_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_NOT_ATTENDED_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_NO_TUTORIAL_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_TUT_GROUP_MC_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_TUT_GROUP_NOT_ATTENDED_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_WHEN_NO_TUTORIAL_FAILURE;
import static seedu.tassist.testutil.Assert.assertThrows;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.UserPrefs;
import seedu.tassist.model.person.Attendance;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.TutGroup;
import seedu.tassist.testutil.PersonBuilder;

public class MarkAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new MarkAttendanceCommand((Index) null, 1, Attendance.ATTENDED));
    }

    @Test
    public void constructor_nullTutGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new MarkAttendanceCommand((TutGroup) null, 1, Attendance.ATTENDED));
    }

    public String getReplacedIndexAndNewString(
            String newStatus, String existingAttendanceString,
            int firstReplacementIndex, int secondReplacementIndex) {

        boolean firstReplacementFound = firstReplacementIndex != -1;
        boolean secondReplacementFound = secondReplacementIndex != -1;
        boolean noReplacementFound = !firstReplacementFound && !secondReplacementFound;

        if (noReplacementFound) {
            return "5" + existingAttendanceString;
        }

        int replacementIndex = 0;
        if (firstReplacementFound) {
            replacementIndex = firstReplacementIndex;
        } else if (secondReplacementFound) {
            replacementIndex = secondReplacementIndex;
        }

        return replacementIndex
                + existingAttendanceString.substring(0, replacementIndex)
                + newStatus
                + existingAttendanceString.substring(replacementIndex + 1);

    }

    @Test
    public void execute_markIndexAttendedUnfilteredList_success() {

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String existingAttendanceString = firstPerson.getAttendanceList().toString();
        String replacedIndexAndNewString = getReplacedIndexAndNewString(
                String.valueOf(Attendance.ATTENDED), existingAttendanceString,
                existingAttendanceString.indexOf(String.valueOf(Attendance.NOT_ATTENDED)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.ON_MC)));

        String newAttendanceString = replacedIndexAndNewString.substring(1);
        int replacedIndex = Integer.parseInt(replacedIndexAndNewString.substring(0, 1)) + 1;
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAttendanceList(newAttendanceString).build();
        MarkAttendanceCommand command =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, replacedIndex, Attendance.ATTENDED);

        String expectedMessage = String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                editedPerson.getName(), editedPerson.getMatNum(), replacedIndex) + "\n";

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_markIndexNotAttendedUnfilteredList_success() {

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String existingAttendanceString = firstPerson.getAttendanceList().toString();
        String replacedIndexAndNewString = getReplacedIndexAndNewString(
                String.valueOf(Attendance.NOT_ATTENDED), existingAttendanceString,
                existingAttendanceString.indexOf(String.valueOf(Attendance.ATTENDED)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.ON_MC)));

        String newAttendanceString = replacedIndexAndNewString.substring(1);
        int replacedIndex = Integer.parseInt(replacedIndexAndNewString.substring(0, 1)) + 1;
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAttendanceList(newAttendanceString).build();
        MarkAttendanceCommand command =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, replacedIndex, Attendance.NOT_ATTENDED);

        String expectedMessage = String.format(MESSAGE_MARK_NOT_ATTENDED_SUCCESS,
                editedPerson.getName(), editedPerson.getMatNum(), replacedIndex) + "\n";

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_markIndexOnMcUnfilteredList_success() {

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String existingAttendanceString = firstPerson.getAttendanceList().toString();
        String replacedIndexAndNewString = getReplacedIndexAndNewString(
                String.valueOf(Attendance.ON_MC), existingAttendanceString,
                existingAttendanceString.indexOf(String.valueOf(Attendance.NOT_ATTENDED)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.ATTENDED)));

        String newAttendanceString = replacedIndexAndNewString.substring(1);
        int replacedIndex = Integer.parseInt(replacedIndexAndNewString.substring(0, 1)) + 1;
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAttendanceList(newAttendanceString).build();
        MarkAttendanceCommand command =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, replacedIndex, Attendance.ON_MC);

        String expectedMessage = String.format(MESSAGE_MARK_MC_SUCCESS,
                editedPerson.getName(), editedPerson.getMatNum(), replacedIndex) + "\n";

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }


    @Test
    public void execute_markIndexGivenNoTutorialUnfilteredList_failure() {

        // First Person uses Default Attendance String, where Tutorial Week 1 and 2 has No Tutorial.
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        int weekToEdit = 1;
        MarkAttendanceCommand commandSetWeek1Attended =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, weekToEdit, Attendance.ATTENDED);
        MarkAttendanceCommand commandSetWeek1NotAttended =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, weekToEdit, Attendance.NOT_ATTENDED);
        MarkAttendanceCommand commandSetWeek1OnMc =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, weekToEdit, Attendance.ON_MC);

        String expectedMessage = String.format(MESSAGE_MARK_WHEN_NO_TUTORIAL_FAILURE,
                firstPerson.getName(), firstPerson.getMatNum(),
                firstPerson.getTutGroup(), weekToEdit);

        assertCommandFailure(commandSetWeek1Attended, model, expectedMessage);
        assertCommandFailure(commandSetWeek1NotAttended, model, expectedMessage);
        assertCommandFailure(commandSetWeek1OnMc, model, expectedMessage);

    }

    @Test
    public void execute_markTutGroupNoTutorialUnfilteredList_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int replacementIndex = 5;
        int weekToEdit = replacementIndex + 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = existingAttendanceString.substring(0, replacementIndex)
                        + Attendance.NO_TUTORIAL
                        + existingAttendanceString.substring(replacementIndex + 1);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NO_TUTORIAL_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command =
                new MarkAttendanceCommand(tutGroupToEdit, replacementIndex + 1, Attendance.NO_TUTORIAL);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markTutGroupNotAttendedUnfilteredList_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int replacementIndex = 5;
        int weekToEdit = replacementIndex + 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NOT_ATTENDED_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = existingAttendanceString.substring(0, replacementIndex)
                        + Attendance.NOT_ATTENDED
                        + existingAttendanceString.substring(replacementIndex + 1);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NOT_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command =
                new MarkAttendanceCommand(tutGroupToEdit, replacementIndex + 1, Attendance.NOT_ATTENDED);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markTutGroupAttendedUnfilteredList_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int replacementIndex = 5;
        int weekToEdit = replacementIndex + 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = existingAttendanceString.substring(0, replacementIndex)
                        + Attendance.ATTENDED
                        + existingAttendanceString.substring(replacementIndex + 1);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command =
                new MarkAttendanceCommand(tutGroupToEdit, replacementIndex + 1, Attendance.ATTENDED);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markTutGroupOnMcUnfilteredList_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int replacementIndex = 5;
        int weekToEdit = replacementIndex + 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_MC_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = existingAttendanceString.substring(0, replacementIndex)
                        + Attendance.ON_MC
                        + existingAttendanceString.substring(replacementIndex + 1);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_MC_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command =
                new MarkAttendanceCommand(tutGroupToEdit, replacementIndex + 1, Attendance.ON_MC);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkAttendanceCommand command = new MarkAttendanceCommand(outOfBoundIndex, 1, Attendance.ATTENDED);
        assertCommandFailure(command, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, model.getFilteredPersonList().size()));
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // Ensures that outOfBoundIndex is still in bounds of address book list.
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MarkAttendanceCommand command = new MarkAttendanceCommand(outOfBoundIndex, 1, Attendance.ATTENDED);
        assertCommandFailure(command, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, model.getFilteredPersonList().size()));
    }

    @Test
    public void checkIfIndexFlagCommandValid_emptyAttendanceList_throwsCommandException() {
        // Build a person with empty attendanceList.
        Person personWithEmptyAttendanceList = new PersonBuilder().withTutGroup("").build();
        assertThrows(CommandException.class, () ->
                MarkAttendanceCommand.checkIfIndexFlagCommandValid(personWithEmptyAttendanceList, 5));
    }

    @Test
    public void checkIfIndexFlagCommandValid_existingNoTutorial_throwsCommandException() {
        // Build a person with No Tutorial on all weeks.
        Person personWithNoTutorialAllWeeks = new PersonBuilder().withAttendanceList("3333333333333").build();
        assertThrows(CommandException.class, () ->
                MarkAttendanceCommand.checkIfIndexFlagCommandValid(personWithNoTutorialAllWeeks, 1));
    }

    @Test
    public void equals() {
        final MarkAttendanceCommand standardCommand =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, VALID_WEEK_A, Attendance.ATTENDED);

        // Same values -> returns true.
        MarkAttendanceCommand commandWithSameValues =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, VALID_WEEK_A, Attendance.ATTENDED);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true.
        assertTrue(standardCommand.equals(standardCommand));

        // Null -> returns false.
        assertFalse(standardCommand.equals(null));

        // Different types -> returns false.
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Different index -> returns false.
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(
                INDEX_SECOND_PERSON, VALID_WEEK_A, Attendance.ATTENDED)));

        // Different week -> returns false.
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(
                INDEX_FIRST_PERSON, VALID_WEEK_B, Attendance.ATTENDED)));

        // Different attendanceStatus -> returns false.
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(
                INDEX_FIRST_PERSON, VALID_WEEK_A, Attendance.NOT_ATTENDED)));

        final MarkAttendanceCommand standardCommandWithTutGroup =
                new MarkAttendanceCommand(new TutGroup(VALID_TUT_GROUP_AMY),
                        VALID_WEEK_B, Attendance.NO_TUTORIAL);

        // Different tut group -> returns false.
        assertFalse(standardCommandWithTutGroup.equals(new MarkAttendanceCommand(
                new TutGroup(VALID_TUT_GROUP_BOB), VALID_WEEK_B, Attendance.NO_TUTORIAL)));

    }

}
