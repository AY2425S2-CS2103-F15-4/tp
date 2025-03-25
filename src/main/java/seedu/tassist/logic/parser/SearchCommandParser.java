package seedu.tassist.logic.parser;

import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.tassist.logic.commands.SearchCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.person.PersonMatchesPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object.
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MAT_NUM, PREFIX_TUT_GROUP,
                        PREFIX_LAB_GROUP, PREFIX_FACULTY, PREFIX_YEAR, PREFIX_LAB_NUMBER);

        String name = argMultimap.getValue(PREFIX_NAME).orElse(null);
        String matNum = argMultimap.getValue(PREFIX_MAT_NUM).orElse(null);
        String tutGroup = argMultimap.getValue(PREFIX_TUT_GROUP).orElse(null);
        String labGroup = argMultimap.getValue(PREFIX_LAB_GROUP).orElse(null);
        String faculty = argMultimap.getValue(PREFIX_FACULTY).orElse(null);
        String year = argMultimap.getValue(PREFIX_YEAR).orElse(null);

        PersonMatchesPredicate predicate = new PersonMatchesPredicate(
                name, matNum, tutGroup, labGroup, faculty, year
        );

        return new SearchCommand(predicate);
    }
}
