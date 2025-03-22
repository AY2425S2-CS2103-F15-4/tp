package seedu.tassist.testutil;

import static seedu.tassist.logic.commands.CommandTestUtil.VALID_ATTENDANCE_STRING;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FACULTY_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FACULTY_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_GROUP_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_GROUP_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_MAT_NUM_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_MAT_NUM_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TELE_HANDLE_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TELE_HANDLE_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_YEAR_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_YEAR_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.tassist.model.AddressBook;
import seedu.tassist.model.person.AttendanceList;
import seedu.tassist.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com").withPhone("94351253").withTeleHandle("@alice")
            .withMatNum("A0000001X").withTutGroup("T01").withLabGroup("B01")
            .withFaculty("SoC").withYear("1").withRemark("todo")
            .withAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING).withLabScores("")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432").withTeleHandle("@ben_mei")
            .withMatNum("A0000002W").withTutGroup("T01").withLabGroup("B01")
            .withFaculty("SoC").withYear("2").withRemark("todo")
            .withTags("owesMoney", "friends")
            .withAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING)
            .withLabScores("4.10/25|-|-|-")
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withTeleHandle("@carlie")
            .withMatNum("A0000003U").withTutGroup("T01").withLabGroup("B01")
            .withFaculty("SoC").withYear("3").withRemark("todo")
            .withAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING)
            .withLabScores("4.15/25|-|-|-").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withTeleHandle("@meister")
            .withMatNum("A0000004R").withTutGroup("T01").withLabGroup("B01")
            .withFaculty("SoC").withYear("4").withRemark("todo")
            .withAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING)
            .withLabScores("4.25/25|-|-|-")
            .withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withTeleHandle("@ellie")
            .withMatNum("A0000005N").withTutGroup("T01").withLabGroup("B01")
            .withFaculty("SoC").withYear("5").withRemark("todo")
            .withAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING)
            .withLabScores("4.25/25|-|-|-").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withTeleHandle("@kunzzz")
            .withMatNum("A0000006M").withTutGroup("T01").withLabGroup("B01")
            .withFaculty("SoC").withYear("6").withRemark("todo")
            .withAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING)
            .withLabScores("4.25/25|-|-|-").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withTeleHandle("@bestie")
            .withMatNum("A0000007L").withTutGroup("T01").withLabGroup("B01")
            .withFaculty("SoC").withYear("3").withRemark("todo")
            .withAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING)
            .withLabScores("4.25/25|-|-|-").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withTeleHandle("@hoo0n")
            .withMatNum("A0000010X").withTutGroup("T01").withLabGroup("B01")
            .withFaculty("SoC").withYear("3").withRemark("todo")
            .withAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING)
            .withLabScores("4.25/25|-|-|-").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withTeleHandle("@id_muu")
            .withMatNum("A0000011W").withTutGroup("T01").withLabGroup("B01")
            .withFaculty("SoC").withYear("3").withRemark("todo")
            .withAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING)
            .withLabScores("4.25/25|-|-|-").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withTeleHandle(VALID_TELE_HANDLE_AMY)
            .withMatNum(VALID_MAT_NUM_AMY).withTutGroup(VALID_TUT_GROUP_AMY)
            .withLabGroup(VALID_LAB_GROUP_AMY).withFaculty(VALID_FACULTY_AMY)
            .withYear(VALID_YEAR_AMY).withRemark(VALID_REMARK_AMY)
            .withAttendanceList(VALID_ATTENDANCE_STRING).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withTeleHandle(VALID_TELE_HANDLE_BOB)
            .withMatNum(VALID_MAT_NUM_BOB).withTutGroup(VALID_TUT_GROUP_BOB)
            .withLabGroup(VALID_LAB_GROUP_BOB).withFaculty(VALID_FACULTY_BOB)
            .withYear(VALID_YEAR_BOB).withRemark(VALID_REMARK_BOB)
            .withAttendanceList(VALID_ATTENDANCE_STRING).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // Prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
