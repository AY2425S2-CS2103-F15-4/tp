package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tassist.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.commons.util.ToStringBuilder;
import seedu.tassist.model.AddressBook;
import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.UserPrefs;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_PERSON));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MULTIPLE_SUCCESS,
                1, DeleteCommand.getDeletedStudentsSummary(List.of(personToDelete)));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        int currentSize = model.getFilteredPersonList().size();
        Index outOfBoundIndex = Index.fromOneBased(currentSize + 1);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(outOfBoundIndex));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_INVALID_INDEX, currentSize);
        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_PERSON));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MULTIPLE_SUCCESS,
                1, DeleteCommand.getDeletedStudentsSummary(List.of(personToDelete)));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(List.of(outOfBoundIndex));

        int currentSize = model.getFilteredPersonList().size();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_INVALID_INDEX, currentSize);
        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_emptyAddressBook_throwsCommandException() {

        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_PERSON));

        assertCommandFailure(deleteCommand, emptyModel,
                String.format(DeleteCommand.MESSAGE_DELETE_PERSON_INVALID_INDEX, 0));
    }

    @Test
    public void execute_multipleValidIndexes_success() {
        List<Index> indexesToDelete = List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        List<Person> peopleToDelete = indexesToDelete.stream()
                .map(i -> model.getFilteredPersonList().get(i.getZeroBased()))
                .collect(Collectors.toList());

        DeleteCommand deleteCommand = new DeleteCommand(indexesToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (Person p : peopleToDelete) {
            expectedModel.deletePerson(p);
        }

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MULTIPLE_SUCCESS,
                peopleToDelete.size(), DeleteCommand.getDeletedStudentsSummary(peopleToDelete));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeTag_success() {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tag tagToRemove = personToModify.getTags().iterator().next(); // Get first tag

        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_PERSON), tagToRemove.tagName);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Set<Tag> newTags = personToModify.getTags().stream()
                .filter(tag -> !tag.equals(tagToRemove))
                .collect(Collectors.toSet());
        Person updatedPerson = new Person(
                personToModify.getName(), personToModify.getPhone(), personToModify.getTeleHandle(),
                personToModify.getEmail(), personToModify.getMatNum(), personToModify.getTutGroup(),
                personToModify.getLabGroup(), personToModify.getFaculty(), personToModify.getYear(),
                personToModify.getRemark(), personToModify.getAttendanceList(), personToModify.getLabScoreList(),
                newTags
        );

        expectedModel.setPerson(personToModify, updatedPerson);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TAG_SUCCESS,
                tagToRemove.tagName, updatedPerson.getName());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_removeNonExistentTag_failure() {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String nonExistentTag = "nonexistent";

        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_PERSON), nonExistentTag);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_TAG_NOT_FOUND, nonExistentTag, personToModify.getName());

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(List.of(INDEX_FIRST_PERSON));
        DeleteCommand deleteSecondCommand = new DeleteCommand(List.of(INDEX_SECOND_PERSON));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(List.of(INDEX_FIRST_PERSON));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        List<Index> targetIndexes = List.of(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndexes);

        String expected = new ToStringBuilder(deleteCommand)
                .add("targetIndexes", targetIndexes)
                .toString();
        assertEquals(expected, deleteCommand.toString());

        // Test with tag removal
        DeleteCommand deleteTagCommand = new DeleteCommand(targetIndexes, "friend");
        String expectedWithTag = new ToStringBuilder(deleteTagCommand)
                .add("targetIndexes", targetIndexes)
                .add("tagToRemove", "friend")
                .toString();
        assertEquals(expectedWithTag, deleteTagCommand.toString());
    }


}
