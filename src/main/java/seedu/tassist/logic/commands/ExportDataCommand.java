package seedu.tassist.logic.commands;

import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.io.IOException;
import java.nio.file.Path;

import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.ReadOnlyAddressBook;
import seedu.tassist.storage.CsvAddressBookStorage;
import seedu.tassist.storage.JsonAddressBookStorage;

/**
 * Allows a user to export stored in the address book into CSV or JSON format.
 */
public class ExportDataCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = "Usage: " + COMMAND_WORD + " " + PREFIX_FILE_PATH + " FILE_PATH\n\n"
            + "Export the data in TAssist to a JSON or CSV file.\n\n"
            + "Options:\n"
            + "  " + PREFIX_FILE_PATH + " FILE_PATH   Specify the output file path.\n"
            + "                 Can be either a full file path or a path relative\n"
            + "                 to the current directory.\n"
            + "                 The file must have a .csv or .json extension.\n"
            + "Examples:\n"
            + "  export " + PREFIX_FILE_PATH + " ./data/userdata.csv";

    public static final String MESSAGE_SUCCESS = "Exported data to file: %1$s";
    public static final String INVALID_ARGUMENT_EXTENSION = "Invalid extension: %1$s";

    public static final String FILE_SAVE_ERROR = "Failed to save file: %s";
    private final Path filePath;

    /**
     * Instantiates the ExportDataCommand instance, with the provided
     * filePath
     *
     * @param filePath path of file to be stored.
     */
    public ExportDataCommand(Path filePath) {
        requireAllNonNull(filePath);
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String fileName = filePath.getFileName().toString();
        String extension = fileName.substring(fileName.lastIndexOf(".")).trim();

        try {
            saveAddressBook(model.getAddressBook(), filePath, extension);
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.filePath));
        } catch (IOException | IllegalArgumentException e) {
            throw new CommandException(String.format(FILE_SAVE_ERROR, e.getMessage()), e);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportDataCommand)) {
            return false;
        }

        ExportDataCommand e = (ExportDataCommand) other;
        return filePath.equals(e.filePath);
    }

    /**
     * Saves the AddressBook to the specified file path and format based on the file extension.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath, String extension)
            throws IOException, IllegalArgumentException {
        switch (extension) {
        case ".json":
            new JsonAddressBookStorage(filePath).saveAddressBook(addressBook);
            break;
        case ".csv":
            new CsvAddressBookStorage(filePath).saveAddressBook(addressBook);
            break;
        default:
            throw new IllegalArgumentException(String.format(INVALID_ARGUMENT_EXTENSION, extension));
        }
    }
}
