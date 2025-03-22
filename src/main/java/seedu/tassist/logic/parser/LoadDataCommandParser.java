package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EXTENSION;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FILENAME;

import java.util.stream.Stream;

import seedu.tassist.logic.commands.LoadDataCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LoadDataCommand object.
 */
public class LoadDataCommandParser implements Parser<LoadDataCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoadDataCommand
     * and returns a LoadDataCommand object for execution.
     *
     * @param args String input to be parsed
     * @return LoadDataCommand corresponding to the String input
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public LoadDataCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILENAME, PREFIX_EXTENSION);

        if (!arePrefixesPresent(argMultimap, PREFIX_FILENAME, PREFIX_EXTENSION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadDataCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FILENAME, PREFIX_EXTENSION);

        String fileName = argMultimap.getValue(PREFIX_FILENAME).orElse("");
        String extension = argMultimap.getValue(PREFIX_EXTENSION).orElse("").toLowerCase().trim();

        return new LoadDataCommand(fileName, extension);
    }

    /**
     * Returns true if all required prefixes are present.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
