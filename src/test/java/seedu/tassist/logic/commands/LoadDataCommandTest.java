package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_EXTENSION_CSV;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_EXTENSION_JSON;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_NAME;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tassist.logic.commands.LoadDataCommand.INVALID_ARGUMENT_EXTENSION;
import static seedu.tassist.logic.commands.LoadDataCommand.INVALID_FILENAME_ERROR;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.UserPrefs;

public class LoadDataCommandTest {

    private static final String INVALID_FILE_NAME = "hello@world.csv";
    private static final String INVALID_FILE_EXTENSION = "pdf";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_validFileNameAndExtension_success() throws Exception {
        LoadDataCommand command = new LoadDataCommand(VALID_FILE_NAME, VALID_FILE_EXTENSION_CSV);
        CommandResult commandResult = command.execute(model);
        assertEquals("Loaded data from file: " + VALID_FILE_NAME + "." + VALID_FILE_EXTENSION_CSV,
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidFileName_failure() {
        String expectedMessage = String.format(INVALID_FILENAME_ERROR, INVALID_FILE_NAME);

        assertCommandFailure(
                new LoadDataCommand(INVALID_FILE_NAME, VALID_FILE_EXTENSION_CSV),
                model, expectedMessage);
    }

    @Test
    public void execute_invalidFileExtension_failure() {
        String expectedMessage = String.format(INVALID_ARGUMENT_EXTENSION, INVALID_FILE_EXTENSION);

        assertCommandFailure(
                new LoadDataCommand(VALID_FILE_NAME, INVALID_FILE_EXTENSION),
                model, expectedMessage);
    }

    @Test
    public void equals() {
        final LoadDataCommand loadDataCommand = new LoadDataCommand(VALID_FILE_NAME, VALID_FILE_EXTENSION_CSV);

        // same values -> returns true
        LoadDataCommand loadDataCommandTester = new LoadDataCommand(VALID_FILE_NAME, VALID_FILE_EXTENSION_CSV);
        assertTrue(loadDataCommand.equals(loadDataCommandTester));

        // same object -> returns true
        assertTrue(loadDataCommand.equals(loadDataCommand));

        // null -> returns false
        assertFalse(loadDataCommand.equals(null));

        // different types -> returns false
        assertFalse(loadDataCommand.equals(new ClearCommand()));

        // different file names -> false
        assertFalse(loadDataCommand.equals(new LoadDataCommand("lifeisgood", VALID_FILE_EXTENSION_CSV)));

        // different file extensions -> false
        assertFalse(loadDataCommand.equals(new LoadDataCommand(VALID_FILE_NAME, VALID_FILE_EXTENSION_JSON)));
    }
}
