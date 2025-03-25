package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import seedu.tassist.logic.commands.ExportDataCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportDataCommand object.
 */
public class ExportDataCommandParser implements Parser<ExportDataCommand> {

    public static final String MESSAGE_INVALID_PATH_EMPTY = "Empty or null paths are invalid";
    public static final String MESSAGE_INVALID_PARENT_DIR = "Parent directory does not exist: %s";
    public static final String MESSAGE_MISSING_FILE = "File path does not end with a file!";
    private static final String MESSAGE_INVALID_PATH = "Invalid path provided!";

    /**
     * Parses the given {@code String} of arguments in the context of the ExportDataCommand
     * and returns an ExportDataCommand object for execution.
     *
     * @param args String input to be parsed
     * @return ExportDataCommand corresponding to the String input
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ExportDataCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE_PATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_FILE_PATH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExportDataCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FILE_PATH);

        String filePath = argMultimap.getValue(PREFIX_FILE_PATH).orElse("").trim();
        validateFilePath(filePath);
        return new ExportDataCommand(Paths.get(filePath));
    }

    private static void validateFilePath(String pathStr) throws ParseException {

        if (pathStr == null || pathStr.trim().isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_PATH_EMPTY);
        }

        try {
            Path path = Paths.get(pathStr);

            // Ensure the path is valid (catch InvalidPathException)
            if (!(path.getParent() == null || Files.exists(path.getParent()))) {
                throw new ParseException(String.format(MESSAGE_INVALID_PARENT_DIR, path.getParent()));
            }

            // If the file exists, check if it’s readable & writable
            if (Files.exists(path) && (!Files.isReadable(path) || !Files.isWritable(path))) {
                throw new ParseException("File is not accessible: " + pathStr);
            }

            Path p = path.getParent();
            if (p == null) {
                p = Paths.get("./");
            }
            String prefix = path.getFileName().toString().substring(0, path.getFileName().toString().lastIndexOf("."));
            String suffix = path.getFileName().toString().substring(path.getFileName().toString().lastIndexOf(".") + 1);
            Path tempFilePath = Files.createTempFile(p, prefix, suffix);
            Files.delete(tempFilePath);

        } catch (InvalidPathException e) {
            throw new ParseException("Path contains illegal characters");
        } catch (StringIndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_MISSING_FILE);
        } catch (IOException e) {
            throw new ParseException("File path has an error!" + e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(
                prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
