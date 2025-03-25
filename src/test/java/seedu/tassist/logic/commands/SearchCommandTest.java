package seedu.tassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.tassist.model.AddressBook;
import seedu.tassist.model.Model;
import seedu.tassist.model.ReadOnlyAddressBook;
import seedu.tassist.model.ReadOnlyUserPrefs;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.PersonMatchesPredicate;
import seedu.tassist.testutil.PersonBuilder;

public class SearchCommandTest {

    @Test
    public void constructor_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SearchCommand(null));
    }

    @Test
    public void execute_personFound_success() throws Exception {
        ModelStubWithPersons modelStub = new ModelStubWithPersons();
        Person validPerson = new PersonBuilder().withName("Alice").build();
        modelStub.addPerson(validPerson);

        SearchCommand searchCommand = new SearchCommand(
                new PersonMatchesPredicate("Alice", null, null, null, null, null)
        );
        CommandResult commandResult = searchCommand.execute(modelStub);

        assertEquals("1 persons listed!", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_noCriteriaProvided_noResults() throws Exception {
        ModelStubWithPersons modelStub = new ModelStubWithPersons();
        Person validPerson = new PersonBuilder().withName("Alice").build();
        modelStub.addPerson(validPerson);

        // All parameters null, should return false in predicate
        SearchCommand searchCommand = new SearchCommand(
                new PersonMatchesPredicate(null, null, null, null, null, null)
        );
        CommandResult commandResult = searchCommand.execute(modelStub);

        assertEquals("0 persons listed!", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_personNotFound_noResults() throws Exception {
        ModelStubWithPersons modelStub = new ModelStubWithPersons();
        Person validPerson = new PersonBuilder().withName("Alice").build();
        modelStub.addPerson(validPerson);

        SearchCommand searchCommand = new SearchCommand(
                new PersonMatchesPredicate("Bob", null, null, null, null, null)
        );
        CommandResult commandResult = searchCommand.execute(modelStub);

        assertEquals("0 persons listed!", commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        SearchCommand searchAlice = new SearchCommand(
                new PersonMatchesPredicate("Alice", null, null, null, null, null)
        );
        SearchCommand searchBob = new SearchCommand(
                new PersonMatchesPredicate("Bob", null, null, null, null, null)
        );

        // Same object -> returns true.
        assertTrue(searchAlice.equals(searchAlice));

        // Same values -> returns true.
        SearchCommand searchAliceCopy = new SearchCommand(
                new PersonMatchesPredicate("Alice", null, null, null, null, null)
        );
        assertTrue(searchAlice.equals(searchAliceCopy));

        // Different types -> returns false.
        assertFalse(searchAlice.equals(1));

        // Null -> returns false.
        assertFalse(searchAlice.equals(null));

        // Different values -> returns false.
        assertFalse(searchAlice.equals(searchBob));
    }

    /**
     * A Model stub that contains multiple persons and allows filtering.
     */
    private class ModelStubWithPersons implements Model {
        private final ArrayList<Person> personsList = new ArrayList<>();
        private Predicate<Person> predicate = person -> false; // Default: No match

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsList.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsList.add(person);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            requireNonNull(predicate);
            this.predicate = predicate;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableArrayList(
                    personsList.stream().filter(predicate).toList()
            );
        }

        // Methods not used in tests throw AssertionError.
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(seedu.tassist.commons.core.GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public seedu.tassist.commons.core.GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(java.nio.file.Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
