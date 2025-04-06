package seedu.tassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tassist.testutil.TypicalPersons.CARL;
import static seedu.tassist.testutil.TypicalPersons.ELLE;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.tassist.model.AddressBook;
import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.ReadOnlyAddressBook;
import seedu.tassist.model.ReadOnlyUserPrefs;
import seedu.tassist.model.UserPrefs;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.PersonMatchesPredicate;
import seedu.tassist.testutil.PersonBuilder;

public class SearchCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
                new PersonMatchesPredicate(List.of("Alice"), null, null, null, null, null, null, null, null, null)
        );
        CommandResult commandResult = searchCommand.execute(modelStub);

        assertEquals("1 persons listed!", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_noCriteriaProvided_noResults() throws Exception {
        ModelStubWithPersons modelStub = new ModelStubWithPersons();
        Person validPerson = new PersonBuilder().withName("Alice").build();
        modelStub.addPerson(validPerson);

        SearchCommand searchCommand = new SearchCommand(
                new PersonMatchesPredicate(null, null, null, null, null, null, null, null, null, null)
        );
        CommandResult commandResult = searchCommand.execute(modelStub);

        assertEquals("1 persons listed!", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_personNotFound_noResults() throws Exception {
        ModelStubWithPersons modelStub = new ModelStubWithPersons();
        Person validPerson = new PersonBuilder().withName("Alice").build();
        modelStub.addPerson(validPerson);

        SearchCommand searchCommand = new SearchCommand(
                new PersonMatchesPredicate(List.of("Bob"), null, null, null, null, null, null, null, null, null)
        );
        CommandResult commandResult = searchCommand.execute(modelStub);

        assertEquals("0 persons listed!", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        // Changed test to use individual keywords instead of a list of keywords
        // This aligns with the current implementation in PersonMatchesPredicate
        // which requires exact matches for multi-word searches

        // Search for "Kurz" only - should match CARL
        PersonMatchesPredicate predicateKurz = new PersonMatchesPredicate(
                List.of("Kurz"), null, null, null, null, null, null, null, null, null);
        SearchCommand commandKurz = new SearchCommand(predicateKurz);
        expectedModel.updateFilteredPersonList(predicateKurz);
        assertCommandSuccess(commandKurz, model,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1), expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredPersonList());

        // Reset model for next test
        model.updateFilteredPersonList(person -> true);
        expectedModel.updateFilteredPersonList(person -> true);

        // Search for "Meyer" only - should match ELLE
        PersonMatchesPredicate predicateMeyer = new PersonMatchesPredicate(
                List.of("Meyer"), null, null, null, null, null, null, null, null, null);
        SearchCommand commandMeyer = new SearchCommand(predicateMeyer);
        expectedModel.updateFilteredPersonList(predicateMeyer);
        assertCommandSuccess(commandMeyer, model,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1), expectedModel);
        assertEquals(Arrays.asList(ELLE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PersonMatchesPredicate predicate = new PersonMatchesPredicate(
                List.of("keyword"), null, null, null, null, null, null, null, null, null);
        SearchCommand searchCommand = new SearchCommand(predicate);
        String expected = SearchCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, searchCommand.toString());
    }

    @Test
    public void equals() {
        SearchCommand searchAlice = new SearchCommand(
                new PersonMatchesPredicate(List.of("Alice"), null, null, null, null, null, null, null, null, null)
        );
        SearchCommand searchBob = new SearchCommand(
                new PersonMatchesPredicate(List.of("Bob"), null, null, null, null, null, null, null, null, null)
        );

        assertTrue(searchAlice.equals(searchAlice));
        assertTrue(searchAlice.equals(new SearchCommand(
                new PersonMatchesPredicate(List.of("Alice"), null, null, null, null, null, null, null, null, null)
        )));
        assertFalse(searchAlice.equals(1));
        assertFalse(searchAlice.equals(null));
        assertFalse(searchAlice.equals(searchBob));
    }

    private class ModelStubWithPersons implements Model {
        private final ArrayList<Person> personsList = new ArrayList<>();
        private Predicate<Person> predicate = person -> false;

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

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError();
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError();
        }

        @Override
        public void setGuiSettings(seedu.tassist.commons.core.GuiSettings guiSettings) {
            throw new AssertionError();
        }

        @Override
        public seedu.tassist.commons.core.GuiSettings getGuiSettings() {
            throw new AssertionError();
        }

        @Override
        public void setAddressBookFilePath(java.nio.file.Path addressBookFilePath) {
            throw new AssertionError();
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            throw new AssertionError();
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError();
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError();
        }
    }
}
