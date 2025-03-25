package seedu.tassist.testutil;

import java.util.HashSet;
import java.util.Set;

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
import seedu.tassist.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_TELE_HANDLE = "@beee_mee";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_MAT_NUM = "A0000000Y";
    public static final String DEFAULT_TUT_GROUP = "T01";
    public static final String DEFAULT_LAB_GROUP = "B01";
    public static final String DEFAULT_FACULTY = "SoC";
    public static final String DEFAULT_YEAR = "1";
    public static final String DEFAULT_REMARK = "todo";
    public static final String DEFAULT_ATTENDANCE_STRING = AttendanceList.DEFAULT_ATTENDANCE_STRING;

    public static final String DEFAULT_LAB_SCORES = "-1/25 -1/25 -1/25 -1/25";

    private Name name;
    private Phone phone;
    private TeleHandle teleHandle;
    private Email email;
    private MatNum matNum;
    private TutGroup tutGroup;
    private LabGroup labGroup;
    private Faculty faculty;
    private Year year;
    private Remark remark;
    private AttendanceList attendanceList;
    private LabScoreList labScoreList;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        teleHandle = new TeleHandle(DEFAULT_TELE_HANDLE);
        email = new Email(DEFAULT_EMAIL);
        matNum = new MatNum(DEFAULT_MAT_NUM);
        tutGroup = new TutGroup(DEFAULT_TUT_GROUP);
        labGroup = new LabGroup(DEFAULT_LAB_GROUP);
        faculty = new Faculty(DEFAULT_FACULTY);
        year = new Year(DEFAULT_YEAR);
        remark = new Remark(DEFAULT_REMARK);
        attendanceList = AttendanceList.generateAttendanceList(DEFAULT_ATTENDANCE_STRING);
        labScoreList = new LabScoreList();
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        teleHandle = personToCopy.getTeleHandle();
        email = personToCopy.getEmail();
        matNum = personToCopy.getMatNum();
        tutGroup = personToCopy.getTutGroup();
        labGroup = personToCopy.getLabGroup();
        faculty = personToCopy.getFaculty();
        year = personToCopy.getYear();
        remark = personToCopy.getRemark();
        attendanceList = personToCopy.getAttendanceList();
        labScoreList = personToCopy.getLabScoreList();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code TeleHandle} of the {@code Person} that we are building.
     */
    public PersonBuilder withTeleHandle(String teleHandle) {
        this.teleHandle = new TeleHandle(teleHandle);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code MatNum} of the {@code Person} that we are building.
     */
    public PersonBuilder withMatNum(String matNum) {
        this.matNum = new MatNum(matNum);
        return this;
    }

    /**
     * Sets the {@code TutGroup} of the {@code Person} that we are building.
     */
    public PersonBuilder withTutGroup(String tutGroup) {
        if (!this.attendanceList.isEmpty() && tutGroup.isEmpty()) {
            // If the origin attendanceList is not empty, but the tutGroup is empty,
            // Assign the Empty AttendanceList.
            this.attendanceList = AttendanceList.EMPTY_ATTENDANCE_LIST;
        } else if (this.attendanceList.isEmpty() && !tutGroup.isEmpty()) {
            // Else if the origin attendanceList is empty, but the tutGroup is not empty,
            // Generate an attendanceList with the Default AttendanceString.
            this.attendanceList = AttendanceList.generateAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING);
        }
        this.tutGroup = new TutGroup(tutGroup);
        return this;
    }

    /**
     * Sets the {@code LabGroup} of the {@code Person} that we are building.
     */
    public PersonBuilder withLabGroup(String labGroup) {
        this.labGroup = new LabGroup(labGroup);
        return this;
    }

    /**
     * Sets the {@code Faculty} of the {@code Person} that we are building.
     */
    public PersonBuilder withFaculty(String faculty) {
        this.faculty = new Faculty(faculty);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code Person} that we are building.
     */
    public PersonBuilder withYear(String year) {
        this.year = new Year(year);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code AttendanceList} of the {@code Person} that we are building.
     */
    public PersonBuilder withAttendanceList(String attendanceString) {
        this.attendanceList = AttendanceList.generateAttendanceList(attendanceString);
        return this;
    }

    /**
     * Sets the {@code LabScoreList} of the {@code Person} that we are building.
     */
    public PersonBuilder withLabScores(String newLabString) {
        if (newLabString == "") {
            this.labScoreList = new LabScoreList();
        } else {
            this.labScoreList = LabScoreList.loadLabScores(newLabString);
        }
        return this;
    }

    /**
     * Creates a {@code Person}.
     */
    public Person build() {
        return new Person(name, phone, teleHandle, email, matNum, tutGroup, labGroup,
                faculty, year, remark, attendanceList, labScoreList, tags);
    }

}
