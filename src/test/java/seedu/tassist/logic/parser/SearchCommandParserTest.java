package seedu.tassist.logic.parser;

import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.SearchCommand;
import seedu.tassist.model.person.PersonMatchesPredicate;

public class SearchCommandParserTest {

    private final SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_validName_success() {
        PersonMatchesPredicate predicate = new PersonMatchesPredicate(
                List.of("John"), null, null, null, null, null, null, null, null, null);
        assertParseSuccess(parser, " -n John", new SearchCommand(predicate));
    }

    @Test
    public void parse_validPhone_success() {
        PersonMatchesPredicate predicate = new PersonMatchesPredicate(
                null, null, "12345678", null, null, null, null, null, null, null);
        assertParseSuccess(parser, " -p 12345678", new SearchCommand(predicate));
    }

    @Test
    public void parse_validTeleHandle_success() {
        PersonMatchesPredicate predicate = new PersonMatchesPredicate(
                null, null, null, "@john_doe", null, null, null, null, null, null);
        assertParseSuccess(parser, " -tg @john_doe", new SearchCommand(predicate));
    }

    @Test
    public void parse_validEmail_success() {
        PersonMatchesPredicate predicate = new PersonMatchesPredicate(
                null, null, null, null, "john@example.com", null, null, null, null, null);
        assertParseSuccess(parser, " -e john@example.com", new SearchCommand(predicate));
    }

    @Test
    public void parse_validTag_success() {
        PersonMatchesPredicate predicate = new PersonMatchesPredicate(
                null, null, null, null, null, "friend", null, null, null, null);
        assertParseSuccess(parser, " -tag friend", new SearchCommand(predicate));
    }

    @Test
    public void parse_multipleFields_success() {
        PersonMatchesPredicate predicate = new PersonMatchesPredicate(
                List.of("Amy"), "A1234567X", "98765432", "@amyhandle",
                "amy@example.com", "friend", null, null, null, null);
        assertParseSuccess(parser,
                " -n Amy -m A1234567X -p 98765432 -tg @amyhandle -e amy@example.com -tag friend",
                new SearchCommand(predicate));
    }

    @Test
    public void parse_noValidPrefix_failure() {
        PersonMatchesPredicate predicate = new PersonMatchesPredicate(
                null, null, null, null, null, null, null, null, null, null);
        assertParseSuccess(parser, "Amy", new SearchCommand(predicate));
    }
}
