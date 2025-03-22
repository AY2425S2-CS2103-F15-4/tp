package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_EXTENSION_CSV;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_EXTENSION_JSON;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_NAME;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tassist.logic.commands.ExportDataCommand.INVALID_ARGUMENT_EXTENSION;
import static seedu.tassist.logic.commands.ExportDataCommand.INVALID_FILENAME_ERROR;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.UserPrefs;

public class ExportDataCommandTest {

    private static final String INVALID_FILE_NAME = "hello@world.csv";
    private static final String INVALID_FILE_EXTENSION = "pdf";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_validFileNameAndExtension_success() throws Exception {
        ExportDataCommand command = new ExportDataCommand(VALID_FILE_NAME, VALID_FILE_EXTENSION_CSV);
        CommandResult commandResult = command.execute(model);
        assertEquals("Exported data to file: " + VALID_FILE_NAME + "." + VALID_FILE_EXTENSION_CSV,
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidFileName_failure() {
        String expectedMessage = String.format(INVALID_FILENAME_ERROR, INVALID_FILE_NAME);

        assertCommandFailure(
                new ExportDataCommand(INVALID_FILE_NAME, VALID_FILE_EXTENSION_CSV),
                model, expectedMessage);
    }

    @Test
    public void execute_invalidFileExtension_failure() {
        String expectedMessage = String.format(INVALID_ARGUMENT_EXTENSION, INVALID_FILE_EXTENSION);

        assertCommandFailure(
                new ExportDataCommand(VALID_FILE_NAME, INVALID_FILE_EXTENSION),
                model, expectedMessage);
    }

    @Test
    public void equalsMethod() {
        //TODO: These checks might not be necessary
        final ExportDataCommand exportDataCommand = new ExportDataCommand(VALID_FILE_NAME, VALID_FILE_EXTENSION_CSV);

        // Same values -> returns true.
        ExportDataCommand exportDataCommandTester = new ExportDataCommand(VALID_FILE_NAME, VALID_FILE_EXTENSION_CSV);
        assertTrue(exportDataCommand.equals(exportDataCommandTester));

        // Same object -> returns true.
        assertTrue(exportDataCommand.equals(exportDataCommand));

        // Null -> returns false.
        assertFalse(exportDataCommand.equals(null));

        // Different types -> returns false.
        assertFalse(exportDataCommand.equals(new ClearCommand()));

        // Different file names -> returns false.
        assertFalse(exportDataCommand.equals(new ExportDataCommand("lifeisgood", VALID_FILE_EXTENSION_CSV)));

        // Different file extensions -> returns false.
        assertFalse(exportDataCommand.equals(new ExportDataCommand(VALID_FILE_NAME, VALID_FILE_EXTENSION_JSON)));
    }
}
