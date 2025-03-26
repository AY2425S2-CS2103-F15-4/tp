package seedu.tassist.logic.parser;


import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.Messages.MESSAGE_MISSING_ARGUMENTS;
import static seedu.tassist.logic.Messages.MESSAGE_MISSING_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Optional;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.commands.DeleteCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a {@link DeleteCommand}.
 * Expected format: del -i [index],[index],[range]...
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses {@code userInput} in the context of the DeleteCommand, returning a {@code DeleteCommand} for execution.
     * <p>
     * If the user input is invalid or missing the {@code -i} flag, a {@link ParseException} is thrown.
     *
     * @param userInput the raw string following "delete", e.g. "-i 3".
     * @return a {@code DeleteCommand} with the parsed {@code Index}.
     * @throws ParseException if the input does not conform to the required format.
     */
    @Override
    public DeleteCommand parse(String userInput) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_INDEX, PREFIX_TAG);

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        String rawIndexes = argMultimap.getValue(PREFIX_INDEX).orElse("").trim();
        if (rawIndexes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_MISSING_ARGUMENTS, DeleteCommand.MESSAGE_USAGE));
        }


        try {
            List<Index> targetIndexes = ParserUtil.parseMultipleIndexes(rawIndexes);
            Optional<String> tagToRemove = argMultimap.getValue(PREFIX_TAG);
            if (tagToRemove.isPresent()) {
                String tagValue = tagToRemove.get().trim();
                if (tagValue.isEmpty()) {
                    throw new ParseException(MESSAGE_MISSING_TAG);
                }
                return new DeleteCommand(targetIndexes, tagValue);
            }

            return new DeleteCommand(targetIndexes);

        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), e);
        }
    }


}
