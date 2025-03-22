package seedu.tassist.logic.parser;


import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.DeleteCommand;

/**
 * Tests the behavior of {@link DeleteCommandParser} for various inputs.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {

        assertParseSuccess(parser, " -i 1", new DeleteCommand(INDEX_FIRST_PERSON));

        assertParseSuccess(parser, " -i 2", new DeleteCommand(INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        // User typed only "-i".
        assertParseFailure(parser, "-i ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // User typed nothing at all
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noFlag_throwsParseException() {
        // User typed just "1" without the -i prefix.
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonNumeric_throwsParseException() {
        // e.g. user typed "-i abc".
        assertParseFailure(parser, "-i abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        // e.g. user typed "-i 0".
        // The parser or Index will fail if zero or negative is not allowed.
        assertParseFailure(parser, "-i 0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        // e.g. user typed "-i -3".
        assertParseFailure(parser, "-i -3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraTokens_throwsParseException() {
        // e.g. user typed "-i 1 extra".
        assertParseFailure(parser, "-i 1 extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_exceedIntRange_throwsParseException() {
        // e.g. user typed "-i 2147483648" => integer overflow.
        assertParseFailure(parser, "-i 2147483648",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
