package seedu.tassist.logic.parser;

import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TELE_HANDLE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Arrays;
import java.util.List;

import seedu.tassist.logic.commands.SearchCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.person.Email;
import seedu.tassist.model.person.Faculty;
import seedu.tassist.model.person.LabGroup;
import seedu.tassist.model.person.MatNum;
import seedu.tassist.model.person.Name;
import seedu.tassist.model.person.PersonMatchesPredicate;
import seedu.tassist.model.person.Phone;
import seedu.tassist.model.person.TeleHandle;
import seedu.tassist.model.person.TutGroup;
import seedu.tassist.model.person.Year;
import seedu.tassist.model.tag.Tag;

/**
 * Parses input arguments and creates a new SearchCommand object.
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    @Override
    public SearchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_MAT_NUM, PREFIX_PHONE, PREFIX_TELE_HANDLE,
                PREFIX_EMAIL, PREFIX_TAG, PREFIX_TUT_GROUP, PREFIX_LAB_GROUP,
                PREFIX_FACULTY, PREFIX_YEAR);

        if (argMultimap.getValue(PREFIX_NAME).isPresent()
                && argMultimap.getValue(PREFIX_NAME).get().trim().isEmpty()) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }

        if (argMultimap.getValue(PREFIX_MAT_NUM).isPresent()
                && argMultimap.getValue(PREFIX_MAT_NUM).get().trim().isEmpty()) {
            throw new ParseException(MatNum.MESSAGE_CONSTRAINTS);
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()
                && argMultimap.getValue(PREFIX_PHONE).get().trim().isEmpty()) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }

        if (argMultimap.getValue(PREFIX_TELE_HANDLE).isPresent()
                && argMultimap.getValue(PREFIX_TELE_HANDLE).get().trim().isEmpty()) {
            throw new ParseException(TeleHandle.MESSAGE_CONSTRAINTS);
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()
                && argMultimap.getValue(PREFIX_EMAIL).get().trim().isEmpty()) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()
                && argMultimap.getValue(PREFIX_TAG).get().trim().isEmpty()) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }

        if (argMultimap.getValue(PREFIX_TUT_GROUP).isPresent()
                && argMultimap.getValue(PREFIX_TUT_GROUP).get().trim().isEmpty()) {
            throw new ParseException(TutGroup.MESSAGE_CONSTRAINTS);
        }

        if (argMultimap.getValue(PREFIX_LAB_GROUP).isPresent()
                && argMultimap.getValue(PREFIX_LAB_GROUP).get().trim().isEmpty()) {
            throw new ParseException(LabGroup.MESSAGE_CONSTRAINTS);
        }

        if (argMultimap.getValue(PREFIX_FACULTY).isPresent()
                && argMultimap.getValue(PREFIX_FACULTY).get().trim().isEmpty()) {
            throw new ParseException(Faculty.MESSAGE_CONSTRAINTS);
        }

        if (argMultimap.getValue(PREFIX_YEAR).isPresent()
                && argMultimap.getValue(PREFIX_YEAR).get().trim().isEmpty()) {
            throw new ParseException(Year.MESSAGE_CONSTRAINTS);
        }

        List<String> nameKeywords = null;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String trimmedName = argMultimap.getValue(PREFIX_NAME).get().trim();
            String[] keywords = trimmedName.split("\\s+");
            for (String keyword : keywords) {
                if (!Name.isValidName(keyword)) {
                    throw new ParseException(Name.MESSAGE_CONSTRAINTS);
                }
            }
            nameKeywords = Arrays.asList(keywords);
        }

        String matNum = null;
        if (argMultimap.getValue(PREFIX_MAT_NUM).isPresent()) {
            String matNumValue = argMultimap.getValue(PREFIX_MAT_NUM).get();
            ParserUtil.parseMatNum(matNumValue);
            matNum = matNumValue;
        }

        String phone = null;
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String phoneValue = argMultimap.getValue(PREFIX_PHONE).get();
            ParserUtil.parsePhone(phoneValue);
            phone = phoneValue;
        }

        String teleHandle = null;
        if (argMultimap.getValue(PREFIX_TELE_HANDLE).isPresent()) {
            String teleHandleValue = argMultimap.getValue(PREFIX_TELE_HANDLE).get();
            ParserUtil.parseTeleHandle(teleHandleValue);
            teleHandle = teleHandleValue;
        }

        String email = null;
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String emailValue = argMultimap.getValue(PREFIX_EMAIL).get();
            ParserUtil.parseEmail(emailValue);
            email = emailValue;
        }

        String tag = null;
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String tagValue = argMultimap.getValue(PREFIX_TAG).get();
            ParserUtil.parseTag(tagValue);
            tag = tagValue;
        }

        String tutGroup = null;
        if (argMultimap.getValue(PREFIX_TUT_GROUP).isPresent()) {
            String tutGroupValue = argMultimap.getValue(PREFIX_TUT_GROUP).get();
            ParserUtil.parseTutGroup(tutGroupValue);
            tutGroup = tutGroupValue;
        }

        String labGroup = null;
        if (argMultimap.getValue(PREFIX_LAB_GROUP).isPresent()) {
            String labGroupValue = argMultimap.getValue(PREFIX_LAB_GROUP).get();
            ParserUtil.parseLabGroup(labGroupValue);
            labGroup = labGroupValue;
        }

        String faculty = null;
        if (argMultimap.getValue(PREFIX_FACULTY).isPresent()) {
            String facultyValue = argMultimap.getValue(PREFIX_FACULTY).get();
            ParserUtil.parseFaculty(facultyValue);
            faculty = facultyValue;
        }

        String year = null;
        if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            String yearValue = argMultimap.getValue(PREFIX_YEAR).get();
            ParserUtil.parseYear(yearValue);
            year = yearValue;
        }

        if (nameKeywords == null
                && matNum == null
                && phone == null
                && teleHandle == null
                && email == null
                && tag == null
                && tutGroup == null
                && labGroup == null
                && faculty == null
                && year == null) {
            return new SearchCommand(new PersonMatchesPredicate(
                    null, null, null, null, null, null, null, null, null, null));
        }

        PersonMatchesPredicate predicate = new PersonMatchesPredicate(
                nameKeywords, matNum, phone, teleHandle, email, tag,
                tutGroup, labGroup, faculty, year);

        return new SearchCommand(predicate);
    }
}
