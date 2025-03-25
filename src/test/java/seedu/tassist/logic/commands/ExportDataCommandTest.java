package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_EXPORT_FILE_PATH_CSV;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_EXPORT_FILE_PATH_JSON;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tassist.logic.commands.ExportDataCommand.FILE_SAVE_ERROR;
import static seedu.tassist.logic.commands.ExportDataCommand.INVALID_ARGUMENT_EXTENSION;
import static seedu.tassist.logic.commands.ExportDataCommand.MESSAGE_SUCCESS;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.UserPrefs;

public class ExportDataCommandTest {

    private static final String INVALID_FILE_NAME = "hello@world";
    private static final String INVALID_FILE_EXTENSION = ".txt";
    private static final String VALID_EXPORT_FILE = "./data/tassist_data";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_validFileLocation_success() throws Exception {
        Path path = Paths.get(VALID_EXPORT_FILE_PATH_CSV);
        ExportDataCommand command = new ExportDataCommand(path);
        CommandResult commandResult = command.execute(model);
        assertEquals(String.format(MESSAGE_SUCCESS, path),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidFileExtension_failure() {
        String expectedMessage = String.format(FILE_SAVE_ERROR,
                String.format(INVALID_ARGUMENT_EXTENSION, INVALID_FILE_EXTENSION));

        assertCommandFailure(
                new ExportDataCommand(Paths.get(VALID_EXPORT_FILE + INVALID_FILE_EXTENSION)),
                model, expectedMessage);
    }

    @Test
    public void equals() {
        final ExportDataCommand exportDataCommand = new ExportDataCommand(Paths.get(VALID_EXPORT_FILE_PATH_CSV));

        // Same values -> returns true.
        ExportDataCommand exportDataCommandTester = new ExportDataCommand(Paths.get(VALID_EXPORT_FILE_PATH_CSV));
        assertTrue(exportDataCommand.equals(exportDataCommandTester));

        // Same object -> returns true.
        assertTrue(exportDataCommand.equals(exportDataCommand));

        // Null -> returns false.
        assertFalse(exportDataCommand.equals(null));

        // Different types -> returns false.
        assertFalse(exportDataCommand.equals(new ClearCommand()));

        // Different file names -> returns false.
        assertFalse(exportDataCommand.equals(new ExportDataCommand(Paths.get("./data/anotherName.csv"))));

        // Different file extensions -> returns false.
        assertFalse(exportDataCommand.equals(new ExportDataCommand(Paths.get(VALID_EXPORT_FILE_PATH_JSON))));
    }
}
