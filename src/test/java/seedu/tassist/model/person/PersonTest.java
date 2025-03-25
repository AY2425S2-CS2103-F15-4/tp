package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FACULTY_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_GROUP_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_MAT_NUM_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TELE_HANDLE_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_YEAR_BOB;
import static seedu.tassist.testutil.Assert.assertThrows;
import static seedu.tassist.testutil.TypicalPersons.ALICE;
import static seedu.tassist.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.tassist.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // Same object -> returns true.
        assertTrue(ALICE.isSamePerson(ALICE));

        // Null -> returns false.
        assertFalse(ALICE.isSamePerson(null));

        // Same matNum, all other attributes different -> returns true.
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withTeleHandle(VALID_TELE_HANDLE_BOB).withEmail(VALID_EMAIL_BOB)
                .withTutGroup(VALID_TUT_GROUP_BOB)
                .withLabGroup(VALID_LAB_GROUP_BOB).withFaculty(VALID_FACULTY_BOB)
                .withYear(VALID_YEAR_BOB).withRemark(VALID_REMARK_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // Different matNum, all other attributes same -> returns false.
        editedAlice = new PersonBuilder(ALICE).withMatNum(VALID_MAT_NUM_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // Lower case matNum will be changed back upper case -> returns true.
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // Same values -> returns true.
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // Same object -> returns true.
        assertTrue(ALICE.equals(ALICE));

        // Null -> returns false.
        assertFalse(ALICE.equals(null));

        // Different type -> returns false.
        assertFalse(ALICE.equals(5));

        // Different person -> returns false.
        assertFalse(ALICE.equals(BOB));

        // Different name -> returns false.
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // Different phone -> returns false.
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // Different email -> returns false.
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // Different tags -> returns false.
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName()
                + ", phone=" + ALICE.getPhone() + ", teleHandle=" + ALICE.getTeleHandle()
                + ", email=" + ALICE.getEmail() + ", matNum=" + ALICE.getMatNum()
                + ", tutGroup=" + ALICE.getTutGroup() + ", labGroup=" + ALICE.getLabGroup()
                + ", faculty=" + ALICE.getFaculty() + ", year=" + ALICE.getYear()
                + ", remark=" + ALICE.getRemark()
                + ", attendanceList=" + ALICE.getAttendanceList()
                + ", labScoreList=" + ALICE.getLabScoreList() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
