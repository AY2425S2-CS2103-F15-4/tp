package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.FACULTY_DESC_AMY;
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
import static seedu.tassist.logic.commands.CommandTestUtil.MAT_NUM_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.MAT_NUM_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.tassist.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.tassist.logic.commands.CommandTestUtil.TELE_HANDLE_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.TUT_GROUP_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FACULTY_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_GROUP_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_MAT_NUM_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_MAT_NUM_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TELE_HANDLE_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_YEAR_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.YEAR_DESC_AMY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.EditCommand;
import seedu.tassist.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.tassist.model.person.Email;
import seedu.tassist.model.person.Faculty;
import seedu.tassist.model.person.LabGroup;
import seedu.tassist.model.person.MatNum;
import seedu.tassist.model.person.Name;
import seedu.tassist.model.person.Phone;
import seedu.tassist.model.person.TeleHandle;
import seedu.tassist.model.person.TutGroup;
import seedu.tassist.model.person.Year;
import seedu.tassist.model.tag.Tag;
import seedu.tassist.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // TODO: Might need to check all this again
        // No index specified.
        assertParseFailure(parser, PREFIX_PHONE + " " + VALID_PHONE_AMY, Index.MESSAGE_CONSTRAINTS);

        // No field specified
        assertParseFailure(parser, " " + PREFIX_INDEX + " 1", EditCommand.MESSAGE_NOT_EDITED);

        // No index and no field specified
        assertParseFailure(parser, " " + PREFIX_INDEX, Index.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidValue_failure() {
        String firstPersonIndex = " " + PREFIX_INDEX + " 1";
        assertParseFailure(parser, " " + PREFIX_INDEX + " -10", Index.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, firstPersonIndex + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // Invalid name
        assertParseFailure(parser, firstPersonIndex + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // Invalid phone
        // Invalid tele handle.
        assertParseFailure(parser,
                firstPersonIndex + INVALID_TELE_HANDLE_DESC, TeleHandle.MESSAGE_CONSTRAINTS
        );
        assertParseFailure(parser, firstPersonIndex + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // Invalid email
        // Invalid mat_num.
        assertParseFailure(parser,
                firstPersonIndex + INVALID_MAT_NUM_DESC, MatNum.MESSAGE_CONSTRAINTS
        );
        // Invalid tut_group.
        assertParseFailure(parser,
                firstPersonIndex + INVALID_TUT_GROUP_DESC, TutGroup.MESSAGE_CONSTRAINTS
        );
        // Invalid lab_group.
        assertParseFailure(parser,
                firstPersonIndex + INVALID_LAB_GROUP_DESC, LabGroup.MESSAGE_CONSTRAINTS
        );
        // Invalid faculty.
        assertParseFailure(parser,
                firstPersonIndex + INVALID_FACULTY_DESC, Faculty.MESSAGE_CONSTRAINTS
        );
        assertParseFailure(parser, firstPersonIndex + INVALID_YEAR_DESC, Year.MESSAGE_CONSTRAINTS); // Invalid year
        assertParseFailure(parser, firstPersonIndex + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // Invalid tag
        // assertParseFailure(parser, "1" + INVALID_REMARK_DESC,
        //      Remark.MESSAGE_CONSTRAINTS); // Invalid remark (TODO: Zhen Jie to update)

        // ================= Dont know if this is correct =================
        // Invalid phone followed by valid email.
        assertParseFailure(parser, firstPersonIndex + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);
        // Invalid name with valid phone.
        assertParseFailure(parser, firstPersonIndex + INVALID_NAME_DESC + PHONE_DESC_AMY, Name.MESSAGE_CONSTRAINTS);
        // Invalid phone with valid email.
        assertParseFailure(parser, firstPersonIndex + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);
        // Invalid tele handle with valid matric number.
        assertParseFailure(parser,
                firstPersonIndex + INVALID_TELE_HANDLE_DESC + MAT_NUM_DESC_AMY, TeleHandle.MESSAGE_CONSTRAINTS
        );
        // Invalid email with valid tutorial group.
        assertParseFailure(parser,
                firstPersonIndex + INVALID_EMAIL_DESC + TUT_GROUP_DESC_AMY, Email.MESSAGE_CONSTRAINTS
        );
        // Invalid matric number with valid lab group.
        assertParseFailure(parser,
                firstPersonIndex + INVALID_MAT_NUM_DESC + LAB_GROUP_DESC_AMY, MatNum.MESSAGE_CONSTRAINTS
        );
        // Invalid tutorial group with valid faculty.
        assertParseFailure(parser,
                firstPersonIndex + INVALID_TUT_GROUP_DESC + FACULTY_DESC_AMY, TutGroup.MESSAGE_CONSTRAINTS
        );
        // Invalid lab group with valid year.
        assertParseFailure(parser,
                firstPersonIndex + INVALID_LAB_GROUP_DESC + YEAR_DESC_AMY, LabGroup.MESSAGE_CONSTRAINTS
        );
        // Invalid faculty with valid remark.
        assertParseFailure(parser,
                firstPersonIndex + INVALID_FACULTY_DESC + REMARK_DESC_AMY, Faculty.MESSAGE_CONSTRAINTS
        );
        // Invalid year with valid tag.
        assertParseFailure(parser, firstPersonIndex + INVALID_YEAR_DESC + TAG_DESC_FRIEND, Year.MESSAGE_CONSTRAINTS);
        // Invalid tag with valid name.
        assertParseFailure(parser, firstPersonIndex + INVALID_TAG_DESC + NAME_DESC_AMY, Tag.MESSAGE_CONSTRAINTS);

        // While parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error.
        assertParseFailure(parser,
                firstPersonIndex + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS
        );
        assertParseFailure(parser,
                firstPersonIndex + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS
        );
        assertParseFailure(parser,
                firstPersonIndex + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS
        );

        // Multiple invalid values, but only the first invalid value is captured.
        assertParseFailure(parser, firstPersonIndex + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = " " + PREFIX_INDEX + " " + targetIndex.getOneBased()
                + TELE_HANDLE_DESC_AMY + YEAR_DESC_AMY + REMARK_DESC_AMY
                + FACULTY_DESC_AMY + LAB_GROUP_DESC_AMY + MAT_NUM_DESC_BOB
                + TUT_GROUP_DESC_AMY + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withFaculty(VALID_FACULTY_AMY)
                .withMatNum(VALID_MAT_NUM_BOB)
                .withTutGroup(VALID_TUT_GROUP_AMY)
                .withRemark(VALID_REMARK_AMY)
                .withTeleHandle(VALID_TELE_HANDLE_AMY)
                .withYear(VALID_YEAR_AMY)
                .withLabGroup(VALID_LAB_GROUP_AMY)
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = " " + PREFIX_INDEX + " " + targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_PERSON;

        assertSingleFieldEdit(targetIndex, NAME_DESC_AMY,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build());
        assertSingleFieldEdit(targetIndex, PHONE_DESC_AMY,
                new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build());
        assertSingleFieldEdit(targetIndex, TELE_HANDLE_DESC_AMY,
                new EditPersonDescriptorBuilder().withTeleHandle(VALID_TELE_HANDLE_AMY).build());
        assertSingleFieldEdit(targetIndex, EMAIL_DESC_AMY,
                new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build());
        assertSingleFieldEdit(targetIndex, MAT_NUM_DESC_AMY,
                new EditPersonDescriptorBuilder().withMatNum(VALID_MAT_NUM_AMY).build());
        assertSingleFieldEdit(targetIndex, TUT_GROUP_DESC_AMY,
                new EditPersonDescriptorBuilder().withTutGroup(VALID_TUT_GROUP_AMY).build());
        assertSingleFieldEdit(targetIndex, LAB_GROUP_DESC_AMY,
                new EditPersonDescriptorBuilder().withLabGroup(VALID_LAB_GROUP_AMY).build());
        assertSingleFieldEdit(targetIndex, FACULTY_DESC_AMY,
                new EditPersonDescriptorBuilder().withFaculty(VALID_FACULTY_AMY).build());
        assertSingleFieldEdit(targetIndex, YEAR_DESC_AMY,
                new EditPersonDescriptorBuilder().withYear(VALID_YEAR_AMY).build());
        assertSingleFieldEdit(targetIndex, REMARK_DESC_AMY,
                new EditPersonDescriptorBuilder().withRemark(VALID_REMARK_AMY).build());
        assertSingleFieldEdit(targetIndex, TAG_DESC_FRIEND,
                new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build());
    }

    /**
     * Helper method to assert parsing success for a single field edit.
     */
    private void assertSingleFieldEdit(Index targetIndex, String userInputSuffix, EditPersonDescriptor descriptor) {
        String userInput = " " + PREFIX_INDEX + " " + targetIndex.getOneBased() + userInputSuffix;
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure().

        // Valid followed by invalid.
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = " " + PREFIX_INDEX + " " + targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // Invalid followed by valid
        userInput = " " + PREFIX_INDEX + " " + targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // Multiple valid fields repeated.
        userInput = " " + PREFIX_INDEX + " " + targetIndex.getOneBased() + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));

        // Multiple invalid values.
        userInput = " " + PREFIX_INDEX + " " + targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = " " + PREFIX_INDEX + " " + targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
