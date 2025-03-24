package seedu.tassist.logic.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;
import seedu.tassist.logic.commands.exceptions.CommandException;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EXTENSION;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FILENAME;
import seedu.tassist.model.Model;
import seedu.tassist.model.ReadOnlyAddressBook;
import seedu.tassist.storage.CsvAddressBookStorage;
import seedu.tassist.storage.JsonAddressBookStorage;

/**
 * Allows a user to load data from a CSV or JSON file into the address book.
 */
public class LoadDataCommand extends Command {
    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads the data from a JSON or CSV file into TAssist.\n"
            + "Parameters: "
            + PREFIX_FILENAME + "FILE_NAME "
            + PREFIX_EXTENSION + "FILE_EXTENSION\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILENAME + "userdata " + PREFIX_EXTENSION + " csv";

    public static final String MESSAGE_SUCCESS = "Loaded data from file: %1$s";
    public static final String INVALID_ARGUMENT_EXTENSION = "Invalid extension: %1$s";
    public static final String INVALID_FILENAME_ERROR = "Invalid filename: %s\n"
            + "File name should only contain alphanumeric characters, dashes, or underscores.";
    private static final Set<String> VALID_EXTENSIONS = Set.of("csv", "json");

    private final String fileName;
    private final String extension;

    public LoadDataCommand(String fileName, String extension) {
        requireAllNonNull(fileName, extension);
        this.fileName = fileName;
        this.extension = extension;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        validateExtension(extension);
        validateFileName(fileName);

        Path filePath = Paths.get("data", fileName + "." + extension);

        try {
            ReadOnlyAddressBook loadedData = loadAddressBook(filePath, extension);
            model.setAddressBook(loadedData);
            return new CommandResult(String.format(MESSAGE_SUCCESS, fileName + "." + extension));
        } catch (IOException e) {
            throw new CommandException("Failed to load file: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new CommandException("Corrupted or invalid file format: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoadDataCommand)) {
            return false;
        }

        LoadDataCommand e = (LoadDataCommand) other;
        return fileName.equals(e.fileName)
                && extension.equals(e.extension);
    }

    private void validateExtension(String extension) throws CommandException {
        if (!VALID_EXTENSIONS.contains(extension)) {
            throw new CommandException(String.format(INVALID_ARGUMENT_EXTENSION, extension));
        }
    }

    private void validateFileName(String fileName) throws CommandException {
        if (!fileName.matches("^[a-zA-Z0-9-_]+$")) {
            throw new CommandException(String.format(INVALID_FILENAME_ERROR, fileName));
        }
    }

    private ReadOnlyAddressBook loadAddressBook(Path filePath, String extension) throws IOException {
        try {
            switch (extension) {
            case "json":
                return new JsonAddressBookStorage(filePath)
                        .readAddressBook()
                        .orElseThrow(() -> new IOException("File not found or unreadable."));
            case "csv":
                return new CsvAddressBookStorage(filePath)
                        .readAddressBook()
                        .orElseThrow(() -> new IOException("File not found or unreadable."));
            default:
                throw new IllegalArgumentException("Unsupported file extension: " + extension);
            }
        } catch (Exception e) {
            throw new IOException("Failed to load data: " + e.getMessage(), e);
        }
    }
    
}
