package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.DESC_BOB;
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

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.tassist.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different telehandle -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withTeleHandle(VALID_TELE_HANDLE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different mat_num -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withMatNum(VALID_MAT_NUM_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tut_group -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withTutGroup(VALID_TUT_GROUP_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different lab_group -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withLabGroup(VALID_LAB_GROUP_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different faculty -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withFaculty(VALID_FACULTY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different year -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withYear(VALID_YEAR_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different remark -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withRemark(VALID_REMARK_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        // todo: Wei En
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        String expected = EditPersonDescriptor.class.getCanonicalName() + "{name="
                + editPersonDescriptor.getName().orElse(null) + ", phone="
                + editPersonDescriptor.getPhone().orElse(null) + ", teleHandle="
                + editPersonDescriptor.getTeleHandle().orElse(null) + ", email="
                + editPersonDescriptor.getEmail().orElse(null) + ", matNum="
                + editPersonDescriptor.getMatNum().orElse(null) + ", tutGroup="
                + editPersonDescriptor.getTutGroup().orElse(null) + ", labGroup="
                + editPersonDescriptor.getLabGroup().orElse(null) + ", faculty="
                + editPersonDescriptor.getFaculty().orElse(null) + ", year="
                + editPersonDescriptor.getYear().orElse(null) + ", remark="
                + editPersonDescriptor.getRemark().orElse(null) + ", tags="
                + editPersonDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editPersonDescriptor.toString());
    }
}
