package seedu.tassist.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.tassist.commons.exceptions.IllegalValueException;
import seedu.tassist.model.person.AttendanceList;
import seedu.tassist.model.person.Email;
import seedu.tassist.model.person.Faculty;
import seedu.tassist.model.person.LabGroup;
import seedu.tassist.model.person.LabScoreList;
import seedu.tassist.model.person.MatNum;
import seedu.tassist.model.person.Name;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.Phone;
import seedu.tassist.model.person.Remark;
import seedu.tassist.model.person.TeleHandle;
import seedu.tassist.model.person.TutGroup;
import seedu.tassist.model.person.Year;
import seedu.tassist.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
public class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String teleHandle;
    private final String email;
    private final String matNum;
    private final String tutGroup;
    private final String labGroup;
    private final String faculty;
    private final String year;
    private final String remark;
    private final String attendances;
    private final String labScores;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                              @JsonProperty("teleHandle") String teleHandle,
                              @JsonProperty("email") String email,
                              @JsonProperty("matNum") String matNum,
                              @JsonProperty("tutGroup") String tutGroup,
                              @JsonProperty("labGroup") String labGroup,
                              @JsonProperty("faculty") String faculty,
                              @JsonProperty("year") String year,
                              @JsonProperty("remark") String remark,
                              @JsonProperty("attendances") String attendances,
                              @JsonProperty("labScores") String labScores,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.teleHandle = teleHandle;
        this.email = email;
        this.matNum = matNum;
        this.tutGroup = tutGroup;
        this.labGroup = labGroup;
        this.faculty = faculty;
        this.year = year;
        this.remark = remark;
        this.attendances = attendances;
        this.labScores = labScores;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        teleHandle = source.getTeleHandle().value;
        email = source.getEmail().value;
        matNum = source.getMatNum().value;
        tutGroup = source.getTutGroup().value;
        labGroup = source.getLabGroup().value;
        faculty = source.getFaculty().value;
        year = source.getYear().value;
        remark = source.getRemark().value;
        attendances = source.getAttendanceList().toString();
        labScores = source.getLabScoreList().toString();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        final Name modelName = validateAndCreate(name, Name.class, Name::isValidName,
                Name.MESSAGE_CONSTRAINTS, Name::new);

        final Phone modelPhone = validateAndCreate(phone, Phone.class, Phone::isValidPhone,
                Phone.MESSAGE_CONSTRAINTS, Phone::new);

        final TeleHandle modelTeleHandle = validateAndCreate(teleHandle, TeleHandle.class,
                TeleHandle::isValidTeleHandle, TeleHandle.MESSAGE_CONSTRAINTS, TeleHandle::new);

        final Email modelEmail = validateAndCreate(email, Email.class, Email::isValidEmail,
                Email.MESSAGE_CONSTRAINTS, Email::new);

        final MatNum modelMatNum = validateAndCreate(matNum, MatNum.class,
                MatNum::isValidMatNum, MatNum.MESSAGE_CONSTRAINTS, MatNum::new);

        final TutGroup modelTutGroup = validateAndCreate(tutGroup, TutGroup.class,
                TutGroup::isValidTutGroup, TutGroup.MESSAGE_CONSTRAINTS, TutGroup::new);

        final LabGroup modelLabGroup = validateAndCreate(labGroup, LabGroup.class,
                LabGroup::isValidLabGroup, LabGroup.MESSAGE_CONSTRAINTS, LabGroup::new);

        final Faculty modelFaculty = validateAndCreate(faculty, Faculty.class,
                Faculty::isValidFaculty, Faculty.MESSAGE_CONSTRAINTS, Faculty::new);

        final Year modelYear = validateAndCreate(year, Year.class,
                Year::isValidYear, Year.MESSAGE_CONSTRAINTS, Year::new);

        final Remark modelRemark = validateAndCreate(remark, Remark.class, Remark::new);

        final AttendanceList modelAttendanceList = (attendances == null || attendances.isEmpty())
                ? AttendanceList.generateAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING)
                : validateAndCreate(attendances, AttendanceList.class,
                        AttendanceList::isValidAttendanceString,
                        AttendanceList.MESSAGE_CONSTRAINTS, AttendanceList::generateAttendanceList);

        final LabScoreList modelLabScoreList = (labScores == null || labScores.isEmpty())
                ? new LabScoreList()
                : validateAndCreate(labScores, LabScoreList.class,
                        LabScoreList::isValidSaveString,
                        LabScoreList.INVALID_LAB_SCORE, LabScoreList::loadLabScores);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelPhone, modelTeleHandle, modelEmail,
                modelMatNum, modelTutGroup, modelLabGroup, modelFaculty, modelYear, modelRemark,
                modelAttendanceList, modelLabScoreList, modelTags);
    }

    private <T> void validateNull(String value, Class<T> objClass) throws IllegalValueException {
        if (value == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, objClass.getSimpleName()));
        }
    }

    private void validateConstraints(String value,
            Predicate<String> validator, String errorMessage) throws IllegalValueException {
        if (!validator.test(value)) {
            throw new IllegalValueException(errorMessage);
        }
    }

    private <T> T validateAndCreate(String value, Class<T> objClass,
            Function<String, T> constructor) throws IllegalValueException {
        validateNull(value, objClass);
        return constructor.apply(value);
    }

    private <T> T validateAndCreate(String value, Class<T> objClass,
            Predicate<String> validator, String errorMessage,
            Function<String, T> constructor) throws IllegalValueException {
        validateNull(value, objClass);
        validateConstraints(value, validator, errorMessage);
        return constructor.apply(value);
    }
}
