package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.FACULTY_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.FACULTY_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_FACULTY_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_LAB_GROUP_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_MAT_NUM_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_TELE_HANDLE_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_TUT_GROUP_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.LAB_GROUP_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.LAB_GROUP_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.MAT_NUM_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.MAT_NUM_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.tassist.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.tassist.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.tassist.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.tassist.logic.commands.CommandTestUtil.TELE_HANDLE_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.TELE_HANDLE_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.TUT_GROUP_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.TUT_GROUP_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FACULTY_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_GROUP_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_MAT_NUM_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TELE_HANDLE_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_YEAR_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.YEAR_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.YEAR_DESC_BOB;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TELE_HANDLE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tassist.testutil.TypicalPersons.AMY;
import static seedu.tassist.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.AddCommand;
import seedu.tassist.model.person.Email;
import seedu.tassist.model.person.Name;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.Phone;
import seedu.tassist.model.tag.Tag;
import seedu.tassist.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // Whitespace only preamble.
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                + TELE_HANDLE_DESC_BOB + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // Trailing whitespace.
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                + TELE_HANDLE_DESC_BOB + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND + PREAMBLE_WHITESPACE, new AddCommand(expectedPerson));

        // Preamble and trailing whitespace.
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                + TELE_HANDLE_DESC_BOB + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND + PREAMBLE_WHITESPACE, new AddCommand(expectedPerson));


        // Multiple tags - all accepted.
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB
                        + TELE_HANDLE_DESC_BOB + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                        + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB
                + TELE_HANDLE_DESC_BOB + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND;

        // Multiple names.
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_NAME));

        // Multiple phones.
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_PHONE));

        // Multiple Telegram handles.
        assertParseFailure(parser, TELE_HANDLE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_TELE_HANDLE));

        // Multiple emails.
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_EMAIL));

        // Multiple matric numbers.
        assertParseFailure(parser, MAT_NUM_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_MAT_NUM));

        // Multiple tutorial groups.
        assertParseFailure(parser, TUT_GROUP_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_TUT_GROUP));

        // Multiple lab groups.
        assertParseFailure(parser, LAB_GROUP_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_LAB_GROUP));

        // Multiple faculties.
        assertParseFailure(parser, FACULTY_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_FACULTY));

        // Multiple years.
        assertParseFailure(parser, YEAR_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_YEAR));

        // Multiple remarks.
        assertParseFailure(parser, REMARK_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_REMARK));

        // Multiple fields repeated.
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_TELE_HANDLE, PREFIX_MAT_NUM,
                        PREFIX_NAME, PREFIX_PHONE, PREFIX_LAB_GROUP, PREFIX_REMARK, PREFIX_TUT_GROUP,
                        PREFIX_EMAIL, PREFIX_YEAR, PREFIX_FACULTY));

        // Invalid value followed by valid value.

        // Invalid name.
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_NAME));

        // Invalid phone.
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_PHONE));

        // Invalid Telegram handle.
        assertParseFailure(parser, INVALID_TELE_HANDLE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_TELE_HANDLE));

        // Invalid email.
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_EMAIL));

        // Invalid matric number.
        assertParseFailure(parser, INVALID_MAT_NUM_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_MAT_NUM));

        // Invalid tutorial group.
        assertParseFailure(parser, INVALID_TUT_GROUP_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_TUT_GROUP));

        // Invalid lab group.
        assertParseFailure(parser, INVALID_LAB_GROUP_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_LAB_GROUP));

        // Invalid faculty.
        assertParseFailure(parser, INVALID_FACULTY_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_FACULTY));

        // Invalid year.
        assertParseFailure(parser, INVALID_YEAR_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_YEAR));

        // Valid value followed by invalid value.

        // Invalid name.
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_NAME));

        // Invalid phone.
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_PHONE));

        // Invalid Telegram handle.
        assertParseFailure(parser, INVALID_TELE_HANDLE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_TELE_HANDLE));

        // Invalid email.
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_EMAIL));

        // Invalid Matric number.
        assertParseFailure(parser, validExpectedPersonString + INVALID_MAT_NUM_DESC,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_MAT_NUM));

        // Invalid tutorial group.
        assertParseFailure(parser, validExpectedPersonString + INVALID_TUT_GROUP_DESC,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_TUT_GROUP));

        // Invalid lab group.
        assertParseFailure(parser, validExpectedPersonString + INVALID_LAB_GROUP_DESC,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_LAB_GROUP));

        // Invalid faculty.
        assertParseFailure(parser, validExpectedPersonString + INVALID_FACULTY_DESC,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_FACULTY));

        // Invalid year.
        assertParseFailure(parser, validExpectedPersonString + INVALID_YEAR_DESC,
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_YEAR));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // Zero tags.
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY
                + TELE_HANDLE_DESC_AMY + EMAIL_DESC_AMY + MAT_NUM_DESC_AMY
                + TUT_GROUP_DESC_AMY + LAB_GROUP_DESC_AMY + FACULTY_DESC_AMY
                + YEAR_DESC_AMY + REMARK_DESC_AMY,
                new AddCommand(expectedPerson));

        // Only mandatory fields: name, email and matriculation number.
        // Phone with no Telegram handle.
        // Tutorial group with no lab group.
        expectedPerson = new PersonBuilder(AMY).withTeleHandle("")
                .withLabGroup("").withFaculty("").withYear("").withRemark("").withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + MAT_NUM_DESC_AMY
                        + TUT_GROUP_DESC_AMY,
                new AddCommand(expectedPerson));

        // Phone with no Telegram handle.
        // Lab group with no tutorial group.
        expectedPerson = new PersonBuilder(AMY).withTeleHandle("")
                .withTutGroup("").withFaculty("").withYear("").withRemark("").withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + MAT_NUM_DESC_AMY
                        + LAB_GROUP_DESC_AMY,
                new AddCommand(expectedPerson));

        // Telegram handle with no phone.
        // Lab group with no tutorial group.
        expectedPerson = new PersonBuilder(AMY).withPhone("")
                .withTutGroup("").withFaculty("").withYear("").withRemark("").withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + TELE_HANDLE_DESC_AMY
                        + EMAIL_DESC_AMY + MAT_NUM_DESC_AMY
                        + LAB_GROUP_DESC_AMY,
                new AddCommand(expectedPerson));

        // Telegram handle with no phone.
        // Tutorial group with no lab group.
        expectedPerson = new PersonBuilder(AMY).withPhone("")
                .withLabGroup("").withFaculty("").withYear("").withRemark("").withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + TELE_HANDLE_DESC_AMY
                        + EMAIL_DESC_AMY + MAT_NUM_DESC_AMY
                        + TUT_GROUP_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // Missing name prefix.
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + TELE_HANDLE_DESC_BOB
                        + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                        + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB
                        + REMARK_DESC_BOB,
                expectedMessage);

        // Missing phone prefix.
        // Exclude Telegram handle so the command does not pass.
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB
                        + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                        + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB
                        + REMARK_DESC_BOB,
                expectedMessage);

        // Missing Telegram handle prefix.
        // Exclude phone number so the command does not pass.
        assertParseFailure(parser, NAME_DESC_BOB + VALID_TELE_HANDLE_BOB
                        + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                        + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB
                        + REMARK_DESC_BOB,
                expectedMessage);

        // Missing email prefix.
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB,
                expectedMessage);

        // Missing matric number prefix.
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + TELE_HANDLE_DESC_BOB
                        + EMAIL_DESC_BOB + VALID_MAT_NUM_BOB + TUT_GROUP_DESC_BOB
                        + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB
                        + REMARK_DESC_BOB,
                expectedMessage);

        // Missing tutorial group prefix.
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + TELE_HANDLE_DESC_BOB
                        + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + VALID_TUT_GROUP_BOB
                        + FACULTY_DESC_BOB + YEAR_DESC_BOB
                        + REMARK_DESC_BOB,
                expectedMessage);

        // Missing lab group prefix.
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + TELE_HANDLE_DESC_BOB
                        + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB
                        + VALID_LAB_GROUP_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB
                        + REMARK_DESC_BOB,
                expectedMessage);

        // All prefixes missing.
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_TELE_HANDLE_BOB
                + VALID_EMAIL_BOB + VALID_MAT_NUM_BOB + VALID_TUT_GROUP_BOB + VALID_LAB_GROUP_BOB
                + VALID_FACULTY_BOB + VALID_YEAR_BOB + VALID_REMARK_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid name.
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + TELE_HANDLE_DESC_BOB
                + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // Invalid phone.
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + TELE_HANDLE_DESC_BOB
                + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // Invalid email.
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + TELE_HANDLE_DESC_BOB
                + INVALID_EMAIL_DESC + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // Invalid tag.
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + TELE_HANDLE_DESC_BOB
                + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // Two invalid values, only first invalid value reported.
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + TELE_HANDLE_DESC_BOB
                + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // Non-empty preamble.
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_quotationSuccess() {
        // Quotation marks are allowed in every single field, but will be removed after.
        Person expectedPerson = new PersonBuilder(BOB).withTags().build();
        assertParseSuccess(parser, " " + PREFIX_NAME + " \"" + VALID_NAME_BOB + "\" "
                + PREFIX_PHONE + " \"" + VALID_PHONE_BOB + "\" "
                + PREFIX_TELE_HANDLE + " \"" + VALID_TELE_HANDLE_BOB + "\" "
                + PREFIX_EMAIL + " \"" + VALID_EMAIL_BOB + "\" "
                + PREFIX_MAT_NUM + " \"" + VALID_MAT_NUM_BOB + "\" "
                + PREFIX_TUT_GROUP + " \"" + VALID_TUT_GROUP_BOB + "\" "
                + PREFIX_LAB_GROUP + " \"" + VALID_LAB_GROUP_BOB + "\" "
                + PREFIX_FACULTY + " \"" + VALID_FACULTY_BOB + "\" "
                + PREFIX_YEAR + " \"" + VALID_YEAR_BOB + "\" "
                + PREFIX_REMARK + " \"" + VALID_REMARK_BOB + "\" ",
                new AddCommand(expectedPerson));

        // Hyphens are allowed in name, email, faculty and remark,
        // and are valid without quotation marks as long as they do not follow prefix patterns.
        // (i.e. whitespace before and after within the user input,
        //  or whitespace before if the prefix is at the end of the user input).
        expectedPerson = new PersonBuilder(BOB).withName("Bob-Choo")
                .withEmail("bob-bob@example.com").withFaculty("hehe-haha")
                .withRemark("before-after").withTags().build();
        assertParseSuccess(parser, " " + PREFIX_NAME + " " + "Bob-Choo" + " "
                + PHONE_DESC_BOB
                + TELE_HANDLE_DESC_BOB
                + " " + PREFIX_EMAIL + " " + "bob-bob@example.com" + " "
                + MAT_NUM_DESC_BOB
                + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB
                + " " + PREFIX_FACULTY + " " + "hehe-haha" + " "
                + YEAR_DESC_BOB
                + " " + PREFIX_REMARK + " " + "before-after" + " ", new AddCommand(expectedPerson));

        // Quotations will block detection of intentional invalid inputs.
        expectedPerson = new PersonBuilder(BOB).withName("Bob -t ./123 Choo")
                .withEmail("bob-bob@example.com").withFaculty("m -p abcdefg")
                .withRemark("-n !@# -p &^% -e !@# -tg !$#! -b %#$ -t").withTags().build();
        assertParseSuccess(parser, " " + PREFIX_NAME + " \"" + "Bob -t ./123 Choo" + "\" "
                + PHONE_DESC_BOB
                + TELE_HANDLE_DESC_BOB
                + " " + PREFIX_EMAIL + " " + "bob-bob@example.com" + " "
                + MAT_NUM_DESC_BOB
                + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB
                + " " + PREFIX_FACULTY + " \"" + "m -p abcdefg" + "\" "
                + YEAR_DESC_BOB
                + " " + PREFIX_REMARK + " \"" + "-n !@# -p &^% -e !@#"
                + " -tg !$#! -b %#$ -t" + "\" ", new AddCommand(expectedPerson));
    }

    @Test
    public void parse_quotationFailure() {
        // Missing quotation in name.
        assertParseFailure(parser, " " + PREFIX_NAME + " " + "Bob -t ./123 Choo" + " "
                + PHONE_DESC_BOB
                + TELE_HANDLE_DESC_BOB
                + " " + PREFIX_EMAIL + " " + "bob-bob@example.com" + " "
                + MAT_NUM_DESC_BOB
                + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB
                + " " + PREFIX_FACULTY + " \"" + "m -p abcdefg" + "\" "
                + YEAR_DESC_BOB
                + " " + PREFIX_REMARK + " \"" + "-n !@# -p &^% -e !@#"
                + " -tg !$#! -b %#$ -t" + "\" ",
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_TUT_GROUP));

        // Missing quotation in faculty.
        assertParseFailure(parser, " " + PREFIX_NAME + " \"" + "Bob -t ./123 Choo" + "\" "
                + PHONE_DESC_BOB
                + TELE_HANDLE_DESC_BOB
                + " " + PREFIX_EMAIL + " " + "bob-bob@example.com" + " "
                + MAT_NUM_DESC_BOB
                + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB
                + " " + PREFIX_FACULTY + " " + "m -p abcdefg" + " "
                + YEAR_DESC_BOB
                + " " + PREFIX_REMARK + " \"" + "-n !@# -p &^% -e !@#"
                + " -tg !$#! -b %#$ -t" + "\" ",
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_PHONE));

        // Missing quotation in remark.
        assertParseFailure(parser, " " + PREFIX_NAME + " \"" + "Bob -t ./123 Choo" + "\" "
                + PHONE_DESC_BOB
                + TELE_HANDLE_DESC_BOB
                + " " + PREFIX_EMAIL + " " + "bob-bob@example.com" + " "
                + MAT_NUM_DESC_BOB
                + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB
                + " " + PREFIX_FACULTY + " \"" + "m -p abcdefg" + "\" "
                + YEAR_DESC_BOB
                + " " + PREFIX_REMARK + " " + "-n !@# -p &^% -e !@#"
                + " -tg !$#! -b %#$ -t" + " ",
                Messages.getErrorMessageForDuplicatePrefixesWithQuotes(PREFIX_TELE_HANDLE, PREFIX_NAME,
                        PREFIX_PHONE, PREFIX_LAB_GROUP, PREFIX_TUT_GROUP, PREFIX_EMAIL));

    }

    @Test
    public void parse_whiteSpaceSuccess() {
        // One of TeleHandle or Phone is passed and one of TutGroup or LabGroup is passed
        Person expectedPerson = new PersonBuilder(BOB).withName("Bob -t ./123 Choo")
                .withEmail("bob-bob@example.com").withPhone("").withFaculty("m -p abcdefg")
                .withLabGroup("").withRemark("-n !@# -p &^% -e !@# -tg !$#! -b %#$ -t")
                .withTags().build();
        assertParseSuccess(parser, " " + PREFIX_NAME + " \"" + "Bob -t ./123 Choo" + "\" "
                + PREFIX_PHONE + " " + PREAMBLE_WHITESPACE
                + TELE_HANDLE_DESC_BOB
                + " " + PREFIX_EMAIL + " " + "bob-bob@example.com" + " "
                + MAT_NUM_DESC_BOB
                + TUT_GROUP_DESC_BOB + " "
                + PREFIX_LAB_GROUP + " " + PREAMBLE_WHITESPACE
                + " " + PREFIX_FACULTY + " \"" + "m -p abcdefg" + "\" "
                + YEAR_DESC_BOB
                + " " + PREFIX_REMARK + " \"" + "-n !@# -p &^% -e !@#"
                + " -tg !$#! -b %#$ -t" + "\" ", new AddCommand(expectedPerson));
    }

    @Test
    public void parse_whiteSpaceFailure() {
        // Both TeleHandle and Phone are empty.
        assertParseFailure(parser, " " + PREFIX_NAME + " \"" + "Bob -t ./123 Choo" + "\" "
                        + " " + PREFIX_PHONE + " " + PREAMBLE_WHITESPACE + " "
                        + " " + PREFIX_TELE_HANDLE + " " + PREAMBLE_WHITESPACE + " "
                        + " " + PREFIX_EMAIL + " " + "bob-bob@example.com" + " "
                        + MAT_NUM_DESC_BOB
                        + TUT_GROUP_DESC_BOB
                        + LAB_GROUP_DESC_BOB,
                Messages.getErrorMessageForRequiredButEmptyField(PREFIX_TELE_HANDLE, PREFIX_PHONE));

        // Both TutGroup and LabGroup are empty.
        assertParseFailure(parser, " " + PREFIX_NAME + " \"" + "Bob -t ./123 Choo" + "\" "
                        + PHONE_DESC_BOB
                        + TELE_HANDLE_DESC_BOB
                        + " " + PREFIX_EMAIL + " " + "bob-bob@example.com" + " "
                        + MAT_NUM_DESC_BOB
                        + " " + PREFIX_TUT_GROUP + " " + PREAMBLE_WHITESPACE + " "
                        + " " + PREFIX_LAB_GROUP + " " + PREAMBLE_WHITESPACE + " ",
                Messages.getErrorMessageForRequiredButEmptyField(PREFIX_TUT_GROUP,
                        PREFIX_LAB_GROUP));

    }

}
