package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_EXTENSION_CSV;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_EXTENSION_JSON;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EXTENSION;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FILENAME;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.ExportDataCommand;

public class ExportDataCommandParserTest {
    private ExportDataCommandParser parser = new ExportDataCommandParser();

    @Test
    public void parse_validFileNameAndCsvExtention_success() {
        String userInput = " "
                + PREFIX_FILENAME + " " + VALID_FILE_NAME + " "
                + PREFIX_EXTENSION + " " + VALID_FILE_EXTENSION_CSV;
        ExportDataCommand expectedCommand =
                new ExportDataCommand(VALID_FILE_NAME, VALID_FILE_EXTENSION_CSV);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validFileNameAndJsonExtention_success() {
        String userInput = " "
                + PREFIX_FILENAME + " " + VALID_FILE_NAME + " "
                + PREFIX_EXTENSION + " " + VALID_FILE_EXTENSION_JSON;
        ExportDataCommand expectedCommand =
                new ExportDataCommand(VALID_FILE_NAME, VALID_FILE_EXTENSION_JSON);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportDataCommand.MESSAGE_USAGE);
        String noFileName = ExportDataCommand.COMMAND_WORD + " " + PREFIX_EXTENSION + VALID_FILE_EXTENSION_CSV;
        String noFileExtension = ExportDataCommand.COMMAND_WORD + " " + PREFIX_FILENAME + VALID_FILE_NAME;

        // Missing all parameters.
        assertParseFailure(parser, ExportDataCommand.COMMAND_WORD, expectedMessage);

        // Missing filename.
        assertParseFailure(parser, noFileName , expectedMessage);

        // Missing file extension.
        assertParseFailure(parser, noFileExtension , expectedMessage);
    }
}
