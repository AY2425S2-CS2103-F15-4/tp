package seedu.tassist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tassist.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.tassist.testutil.Assert.assertThrows;
import static seedu.tassist.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.tassist.commons.exceptions.IllegalValueException;
import seedu.tassist.model.person.AttendanceList;
import seedu.tassist.model.person.Email;
import seedu.tassist.model.person.Name;
import seedu.tassist.model.person.Phone;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "-651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ATTENDANCE_STRING = "1010500210112";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_TELE_HANDLE = BENSON.getTeleHandle().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_MAT_NUM = BENSON.getMatNum().toString();
    private static final String VALID_TUT_GROUP = BENSON.getTutGroup().toString();
    private static final String VALID_LAB_GROUP = BENSON.getLabGroup().toString();
    private static final String VALID_FACULTY = BENSON.getFaculty().toString();
    private static final String VALID_YEAR = BENSON.getYear().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final String VALID_ATTENDANCE_STRING = BENSON.getAttendanceList().toString();
    private static final String VALID_LAB_SCORES_STRING = BENSON.getLabScoreList().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_TELE_HANDLE,
                        VALID_EMAIL, VALID_MAT_NUM, VALID_TUT_GROUP, VALID_LAB_GROUP,
                        VALID_FACULTY, VALID_YEAR, VALID_REMARK,
                        VALID_ATTENDANCE_STRING, VALID_LAB_SCORES_STRING , VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_PHONE, VALID_TELE_HANDLE,
                        VALID_EMAIL, VALID_MAT_NUM, VALID_TUT_GROUP, VALID_LAB_GROUP,
                        VALID_FACULTY, VALID_YEAR, VALID_REMARK,
                        VALID_ATTENDANCE_STRING, VALID_LAB_SCORES_STRING , VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_TELE_HANDLE,
                        VALID_EMAIL, VALID_MAT_NUM, VALID_TUT_GROUP, VALID_LAB_GROUP,
                        VALID_FACULTY, VALID_YEAR, VALID_REMARK,
                        VALID_ATTENDANCE_STRING, VALID_LAB_SCORES_STRING , VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, null, VALID_TELE_HANDLE,
                        VALID_EMAIL, VALID_MAT_NUM, VALID_TUT_GROUP, VALID_LAB_GROUP,
                        VALID_FACULTY, VALID_YEAR, VALID_REMARK,
                        VALID_ATTENDANCE_STRING, VALID_LAB_SCORES_STRING , VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_TELE_HANDLE,
                        INVALID_EMAIL, VALID_MAT_NUM, VALID_TUT_GROUP, VALID_LAB_GROUP,
                        VALID_FACULTY, VALID_YEAR, VALID_REMARK,
                        VALID_ATTENDANCE_STRING, VALID_LAB_SCORES_STRING , VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_TELE_HANDLE,
                null, VALID_MAT_NUM, VALID_TUT_GROUP, VALID_LAB_GROUP,
                VALID_FACULTY, VALID_YEAR, VALID_REMARK,
                VALID_ATTENDANCE_STRING, VALID_LAB_SCORES_STRING , VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAttendance_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_TELE_HANDLE,
                        VALID_EMAIL, VALID_MAT_NUM, VALID_TUT_GROUP, VALID_LAB_GROUP,
                        VALID_FACULTY, VALID_YEAR, VALID_REMARK,
                        INVALID_ATTENDANCE_STRING, VALID_LAB_SCORES_STRING , VALID_TAGS);
        String expectedMessage = AttendanceList.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_TELE_HANDLE,
                        VALID_EMAIL, VALID_MAT_NUM, VALID_TUT_GROUP, VALID_LAB_GROUP,
                        VALID_FACULTY, VALID_YEAR, VALID_REMARK,
                        VALID_ATTENDANCE_STRING, VALID_LAB_SCORES_STRING , invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
