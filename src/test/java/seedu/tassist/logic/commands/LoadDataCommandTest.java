package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_NAME;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tassist.logic.commands.LoadDataCommand.INVALID_ARGUMENT_EXTENSION;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.UserPrefs;

public class LoadDataCommandTest {

    private static final String INVALID_FILE_NAME = "hello@world.csv";
    private static final String INVALID_FILE_EXTENSION = "pdf";

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_validCsvFile_success() throws Exception {
        String fileName = "addressbook";
        String extension = "csv";
        Path filePath = Paths.get("data", "addressbook.csv");

        LoadDataCommand command = new LoadDataCommand(fileName, extension);
        CommandResult result = command.execute(model);
        assertEquals("Loaded data from file: " + fileName + "." + extension, result.getFeedbackToUser());
    }

    @Test
    void execute_validJsonFile_success() throws Exception {
        String fileName = "addressbook";
        String extension = "json";
        Path filePath = Paths.get("data", fileName + "." + extension);

        LoadDataCommand command = new LoadDataCommand(fileName, extension);
        CommandResult result = command.execute(model);
        assertEquals("Loaded data from file: " + fileName + "." + extension, result.getFeedbackToUser());
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
        final LoadDataCommand loadDataCommand = new LoadDataCommand("manualcsv", "csv");

        // same values -> returns true
        LoadDataCommand loadDataCommandTester = new LoadDataCommand("manualcsv", "csv");
        assertTrue(loadDataCommand.equals(loadDataCommandTester));

        // same object -> returns true
        assertTrue(loadDataCommand.equals(loadDataCommand));

        // null -> returns false
        assertFalse(loadDataCommand.equals(null));

        // different types -> returns false
        assertFalse(loadDataCommand.equals(new ClearCommand()));

        // different file names -> false
        assertFalse(loadDataCommand.equals(new LoadDataCommand("different", "csv")));

        // different file extensions -> false
        assertFalse(loadDataCommand.equals(new LoadDataCommand("manualcsv", "json")));
    }
}
