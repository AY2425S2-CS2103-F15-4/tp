package seedu.tassist.logic.parser;


import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.commands.DeleteCommand;

/**
 * Tests the behavior of {@link DeleteCommandParser} for various inputs.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        List<Index> expectedIndexes = Arrays.asList(
                Index.fromOneBased(1),
                Index.fromOneBased(2),
                Index.fromOneBased(3),
                Index.fromOneBased(5),
                Index.fromOneBased(7)
        );
        DeleteCommand expectedCommand = new DeleteCommand(expectedIndexes);
        assertParseSuccess(parser, " -i 1-3, 5, 7", expectedCommand);

        assertParseSuccess(parser, " -i 1", new DeleteCommand(Collections.singletonList(INDEX_FIRST_PERSON)));

        assertParseSuccess(parser, " -i 2", new DeleteCommand(Collections.singletonList(INDEX_SECOND_PERSON)));
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

    @Test
    public void parse_invalidRange_throwsParseException() {
        assertParseFailure(parser, "-i 5-2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidMixedInput_throwsParseException() {
        assertParseFailure(parser, "-i 2, a-b",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonNumericRange_throwsParseException() {
        assertParseFailure(parser, "-i 1-two",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_validIndexAndTag_returnsDeleteCommand() {
        assertParseSuccess(parser, " -i 2 -tag friend",
                new DeleteCommand(List.of(INDEX_SECOND_PERSON), "friend"));
    }

    @Test
    public void parse_missingIndexWithTag_throwsParseException() {
        assertParseFailure(parser, "-tag friend",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTag_throwsParseException() {
        assertParseFailure(parser, " -i 2 -tag ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
