package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_ATTENDANCE_LIST;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TELE_HANDLE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.tassist.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.AddressBook;
import seedu.tassist.model.Model;
import seedu.tassist.model.person.AttendanceList;
import seedu.tassist.model.person.NameContainsKeywordsPredicate;
import seedu.tassist.model.person.Person;
import seedu.tassist.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "12345678";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_TELE_HANDLE_AMY = "@amyBee";
    public static final String VALID_TELE_HANDLE_BOB = "@bob_low";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_MAT_NUM_AMY = "A0123456J";
    public static final String VALID_MAT_NUM_BOB = "A9876543L";
    public static final String VALID_TUT_GROUP_AMY = "T05";
    public static final String VALID_TUT_GROUP_BOB = "T03";
    public static final String VALID_LAB_GROUP_AMY = "B10";
    public static final String VALID_LAB_GROUP_BOB = "B04";
    public static final String VALID_FACULTY_AMY = "SoC";
    public static final String VALID_FACULTY_BOB = "CDE";
    public static final String VALID_YEAR_AMY = "1";
    public static final String VALID_YEAR_BOB = "6";
    public static final String VALID_REMARK_AMY = "Like skiing.";
    public static final String VALID_REMARK_BOB = "Favourite pastime: Eating";
    public static final String VALID_ATTENDANCE_STRING = AttendanceList.DEFAULT_ATTENDANCE_STRING;
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final int VALID_WEEK_A = 3;
    public static final int VALID_WEEK_B = 5;
    public static final int VALID_LAB_NUMBER_A = 1;
    public static final int VALID_LAB_SCORE_A = 20;
    public static final int VALID_LAB_SCORE_B = 30;
    public static final int DEFAULT_LAB_SCORE_COUNT = 4;
    public static final int DEFAULT_LAB_MAX_SCORE = 25;
    public static final String VALID_EXPORT_FILE_PATH_CSV = "./data/tassist_data.csv";
    public static final String VALID_EXPORT_FILE_PATH_JSON = "./data/tassist_data.json";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + " " + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + " " + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + " " + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + " " + VALID_PHONE_BOB;
    public static final String TELE_HANDLE_DESC_AMY = " " + PREFIX_TELE_HANDLE + " " + VALID_TELE_HANDLE_AMY;
    public static final String TELE_HANDLE_DESC_BOB = " " + PREFIX_TELE_HANDLE + " " + VALID_TELE_HANDLE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + " " + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + " " + VALID_EMAIL_BOB;
    public static final String MAT_NUM_DESC_AMY = " " + PREFIX_MAT_NUM + " " + VALID_MAT_NUM_AMY;
    public static final String MAT_NUM_DESC_BOB = " " + PREFIX_MAT_NUM + " " + VALID_MAT_NUM_BOB;
    public static final String TUT_GROUP_DESC_AMY = " " + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_AMY;
    public static final String TUT_GROUP_DESC_BOB = " " + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_BOB;
    public static final String LAB_GROUP_DESC_AMY = " " + PREFIX_LAB_GROUP + " " + VALID_LAB_GROUP_AMY;
    public static final String LAB_GROUP_DESC_BOB = " " + PREFIX_LAB_GROUP + " " + VALID_LAB_GROUP_BOB;
    public static final String FACULTY_DESC_AMY = " " + PREFIX_FACULTY + " " + VALID_FACULTY_AMY;
    public static final String FACULTY_DESC_BOB = " " + PREFIX_FACULTY + " " + VALID_FACULTY_BOB;
    public static final String YEAR_DESC_AMY = " " + PREFIX_YEAR + " " + VALID_YEAR_AMY;
    public static final String YEAR_DESC_BOB = " " + PREFIX_YEAR + " " + VALID_YEAR_BOB;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + " " + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + " " + VALID_REMARK_BOB;
    public static final String ATTENDANCE_LIST = " " + PREFIX_ATTENDANCE_LIST + " " + VALID_ATTENDANCE_STRING;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + " " + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + " " + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " James&"; // '&' not allowed in names.
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + " 911a"; // 'a' not allowed in phones.
    public static final String INVALID_TELE_HANDLE_DESC = " " + PREFIX_TELE_HANDLE + " @ca"; // Longer than 5 chars.
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + " bob!yahoo"; // Missing '@' symbol.
    public static final String INVALID_MAT_NUM_DESC = " " + PREFIX_MAT_NUM + " A0000000A"; // Failed checksum.
    public static final String INVALID_TUT_GROUP_DESC = " " + PREFIX_TUT_GROUP + " C111"; // Start with 'T' or 't'.
    public static final String INVALID_LAB_GROUP_DESC = " " + PREFIX_LAB_GROUP + " D111"; // Start with 'B' or 'b'
    public static final String INVALID_FACULTY_DESC = " " + PREFIX_FACULTY + " @#$S"; // Only '-' and '&' allowed.
    public static final String INVALID_YEAR_DESC = " " + PREFIX_YEAR + " 8"; // Only 1-6 allowed.
    public static final String INVALID_WEEK_DESC = " " + PREFIX_WEEK + " 14"; // Not within 1 to 13.
    public static final String INVALID_INDEX_DESC = " " + PREFIX_INDEX + " -10"; // Negative index is not allowed.
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + " hubby*"; // '*' Not allowed in tags.
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withTeleHandle(VALID_TELE_HANDLE_AMY)
                .withEmail(VALID_EMAIL_AMY).withMatNum(VALID_MAT_NUM_AMY)
                .withTutGroup(VALID_TUT_GROUP_AMY).withLabGroup(VALID_LAB_GROUP_AMY)
                .withFaculty(VALID_FACULTY_AMY).withYear(VALID_YEAR_AMY)
                .withRemark(VALID_REMARK_AMY).withTags(VALID_TAG_FRIEND).build();

        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTeleHandle(VALID_TELE_HANDLE_BOB)
                .withEmail(VALID_EMAIL_BOB).withMatNum(VALID_MAT_NUM_BOB)
                .withTutGroup(VALID_TUT_GROUP_BOB).withLabGroup(VALID_LAB_GROUP_BOB)
                .withFaculty(VALID_FACULTY_BOB).withYear(VALID_YEAR_BOB)
                .withRemark(VALID_REMARK_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // We are unable to defensively copy the model for comparison later,
        // So we can only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
