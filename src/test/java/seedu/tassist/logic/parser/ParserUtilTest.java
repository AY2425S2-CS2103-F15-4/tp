package seedu.tassist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.tassist.testutil.Assert.assertThrows;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalPersons.getTypicalPersons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.person.Email;
import seedu.tassist.model.person.Faculty;
import seedu.tassist.model.person.LabGroup;
import seedu.tassist.model.person.MatNum;
import seedu.tassist.model.person.Name;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.Phone;
import seedu.tassist.model.person.Remark;
import seedu.tassist.model.person.TeleHandle;
import seedu.tassist.model.person.TutGroup;
import seedu.tassist.model.person.Year;
import seedu.tassist.model.tag.Tag;

public class ParserUtilTest {
    private static final String WHITESPACE = " \t\r\n";

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "-651234";
    private static final String INVALID_TELE_HANDLE = "@c";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_MAT_NUM = "U123";
    private static final String INVALID_TUT_GROUP = "abbb3";
    private static final String INVALID_LAB_GROUP = "bo001";
    private static final String INVALID_FACULTY = "S@@@F";
    private static final String INVALID_YEAR = "8";
    private static final String INVALID_REMARK = "asdf\"asfdasf";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker Jr.";
    private static final String VALID_WHITESPACE_NAME = WHITESPACE + VALID_NAME + WHITESPACE;
    private static final String VALID_PHONE = "123456";
    private static final String VALID_WHITESPACE_PHONE = "123" + WHITESPACE + "456";
    private static final String VALID_TELE_HANDLE = "@walking_rach";
    private static final String VALID_WHITESPACE_TELE_HANDLE = WHITESPACE + "@walking_" + WHITESPACE
            + "rach" + WHITESPACE;
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_WHITESPACE_EMAIL = WHITESPACE + "rachel@" + WHITESPACE
            + "example.com" + WHITESPACE;
    private static final String VALID_MAT_NUM = "a0123456";
    private static final String VALID_WHITESPACE_MAT_NUM = WHITESPACE + "a01234" + WHITESPACE
            + "56" + WHITESPACE;
    private static final String VALID_TUT_GROUP = "T04";
    private static final String VALID_WHITESPACE_TUT_GROUP = WHITESPACE + "T0" + WHITESPACE
            + "4" + WHITESPACE;
    private static final String VALID_LAB_GROUP = "b05";
    private static final String VALID_WHITESPACE_LAB_GROUP = WHITESPACE + "b" + WHITESPACE
            + "05" + WHITESPACE;
    private static final String VALID_FACULTY = "Faculty of Hehe-haha";
    private static final String VALID_WHITESPACE_FACULTY = WHITESPACE + VALID_FACULTY + WHITESPACE;
    private static final String VALID_YEAR = "3";
    private static final String VALID_WHITESPACE_YEAR = WHITESPACE + VALID_YEAR + WHITESPACE;
    private static final String VALID_REMARK = "Likes to walk";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces.
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces.
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_WHITESPACE_NAME));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_WHITESPACE_PHONE));
    }

    @Test
    public void parseTeleHandle_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTeleHandle((String) null));
    }

    @Test
    public void parseTeleHandle_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTeleHandle(INVALID_TELE_HANDLE));
    }

    @Test
    public void parseTeleHandle_validValueWithoutWhitespace_returnsPhone() throws Exception {
        TeleHandle expectedTeleHandle = new TeleHandle(VALID_TELE_HANDLE);
        assertEquals(expectedTeleHandle, ParserUtil.parseTeleHandle(VALID_TELE_HANDLE));
    }

    @Test
    public void parseTeleHandle_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        TeleHandle expectedTeleHandle = new TeleHandle(VALID_TELE_HANDLE);
        assertEquals(expectedTeleHandle, ParserUtil.parseTeleHandle(VALID_WHITESPACE_TELE_HANDLE));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_WHITESPACE_EMAIL));
    }

    @Test
    public void parseMatNum_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMatNum((String) null));
    }

    @Test
    public void parseMatNum_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMatNum(INVALID_MAT_NUM));
    }

    @Test
    public void parseMatNum_validValueWithoutWhitespace_returnsEmail() throws Exception {
        MatNum expectedMatNum = new MatNum(VALID_MAT_NUM);
        assertEquals(expectedMatNum, ParserUtil.parseMatNum(VALID_MAT_NUM));
    }

    @Test
    public void parseMatNum_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        MatNum expectedMatNum = new MatNum(VALID_MAT_NUM);
        assertEquals(expectedMatNum, ParserUtil.parseMatNum(VALID_WHITESPACE_MAT_NUM));
    }

    @Test
    public void parseTutGroup_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTutGroup((String) null));
    }

    @Test
    public void parseTutGroup_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutGroup(INVALID_TUT_GROUP));
    }

    @Test
    public void parseTutGroup_validValueWithoutWhitespace_returnsEmail() throws Exception {
        TutGroup expectedTutGroup = new TutGroup(VALID_TUT_GROUP);
        assertEquals(expectedTutGroup, ParserUtil.parseTutGroup(VALID_TUT_GROUP));
    }

    @Test
    public void parseTutGroup_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        TutGroup expectedTutGroup = new TutGroup(VALID_TUT_GROUP);
        assertEquals(expectedTutGroup, ParserUtil.parseTutGroup(VALID_WHITESPACE_TUT_GROUP));
    }

    @Test
    public void parseLabGroup_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLabGroup((String) null));
    }

    @Test
    public void parseLabGroup_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLabGroup(INVALID_LAB_GROUP));
    }

    @Test
    public void parseLabGroup_validValueWithoutWhitespace_returnsEmail() throws Exception {
        LabGroup expectedLabGroup = new LabGroup(VALID_LAB_GROUP);
        assertEquals(expectedLabGroup, ParserUtil.parseLabGroup(VALID_LAB_GROUP));
    }

    @Test
    public void parseLabGroup_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        LabGroup expectedLabGroup = new LabGroup(VALID_LAB_GROUP);
        assertEquals(expectedLabGroup, ParserUtil.parseLabGroup(VALID_WHITESPACE_LAB_GROUP));
    }

    @Test
    public void parseFaculty_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFaculty((String) null));
    }

    @Test
    public void parseFaculty_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFaculty(INVALID_FACULTY));
    }

    @Test
    public void parseFaculty_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Faculty expectedFaculty = new Faculty(VALID_FACULTY);
        assertEquals(expectedFaculty, ParserUtil.parseFaculty(VALID_FACULTY));
    }

    @Test
    public void parseFaculty_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        Faculty expectedFaculty = new Faculty(VALID_FACULTY);
        assertEquals(expectedFaculty, ParserUtil.parseFaculty(VALID_WHITESPACE_FACULTY));
    }

    @Test
    public void parseYear_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseYear((String) null));
    }

    @Test
    public void parseYear_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseYear(INVALID_YEAR));
    }

    @Test
    public void parseYear_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Year expectedYear = new Year(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(VALID_YEAR));
    }

    @Test
    public void parseYear_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        Year expectedYear = new Year(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(VALID_WHITESPACE_YEAR));
    }


    @Test
    public void parseRemark_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRemark((String) null));
    }

    @Test
    public void parseRemark_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRemark(INVALID_REMARK));
    }

    @Test
    public void parseRemark_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(VALID_REMARK));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseWeek_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWeek("10 a"));
    }

    @Test
    public void parseWeek_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWeek(Long.toString(0)));
        assertThrows(ParseException.class, () -> ParserUtil.parseWeek(Long.toString(14)));
    }

    @Test
    public void parseWeek_validInput_success() throws Exception {
        // No whitespaces.
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));
    }

    @Test
    public void getPersonsInTutorialGroup_nullPersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                ParserUtil.getPersonsInTutorialGroup(null, new TutGroup("T01")));
    }

    @Test
    public void getPersonsInTutorialGroup_nullTutGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                ParserUtil.getPersonsInTutorialGroup(getTypicalPersons(), null));
    }

    @Test
    public void getPersonsInTutorialGroup_validListAndTutorialGroup_success() {
        // All Persons in provided list are of the provided tut group -> Returns the same list.
        List<Person> expectedListReturned = getTypicalPersons();
        assertEquals(expectedListReturned,
                ParserUtil.getPersonsInTutorialGroup(getTypicalPersons(), new TutGroup("T01")));

        // None of the Persons in provided list are of the provided tut group -> Returns an empty list.
        assertEquals(new ArrayList<Person>(),
                ParserUtil.getPersonsInTutorialGroup(getTypicalPersons(), new TutGroup("T99")));
    }

    @Test
    public void parseMultipleIndexes_validInputs_success() throws Exception {
        // Single index
        assertEquals(List.of(Index.fromOneBased(1)), ParserUtil.parseMultipleIndexes("1"));

        // Comma-separated indexes
        assertEquals(List.of(Index.fromOneBased(1), Index.fromOneBased(3)),
                ParserUtil.parseMultipleIndexes("1,3"));

        // Range
        assertEquals(List.of(Index.fromOneBased(1), Index.fromOneBased(2),
                        Index.fromOneBased(3)),
                ParserUtil.parseMultipleIndexes("1-3"));

        // Mixed input
        assertEquals(List.of(Index.fromOneBased(1), Index.fromOneBased(2),
                        Index.fromOneBased(3), Index.fromOneBased(5)),
                ParserUtil.parseMultipleIndexes("1-3,5"));

        // Input with whitespace
        assertEquals(List.of(Index.fromOneBased(2), Index.fromOneBased(4)),
                ParserUtil.parseMultipleIndexes(" 2 , 4 "));
    }

    @Test
    public void parseMultipleIndexes_invalidRange_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMultipleIndexes("5-3"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMultipleIndexes("-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMultipleIndexes("2-"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMultipleIndexes("a-b"));
    }

    @Test
    public void parseMultipleIndexes_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMultipleIndexes("one,two"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMultipleIndexes("1,,3"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMultipleIndexes("1 2 3"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMultipleIndexes(""));
    }

}
