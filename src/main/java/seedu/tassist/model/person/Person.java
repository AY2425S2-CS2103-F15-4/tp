package seedu.tassist.model.person;

import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.tassist.commons.util.ToStringBuilder;
import seedu.tassist.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final TeleHandle teleHandle;
    private final Email email;
    private final MatNum matNum;
    private final TutGroup tutGroup;
    private final LabGroup labGroup;
    private final Faculty faculty;
    private final Year year;
    private final Remark remark;


    // Data fields
    private final AttendanceList attendanceList;
    private final LabScoreList labScoreList;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, TeleHandle teleHandle, Email email,
                  MatNum matNum, TutGroup tutGroup, LabGroup labGroup, Faculty faculty,
                  Year year, Remark remark, AttendanceList attendanceList,
                  LabScoreList labScoreList, Set<Tag> tags) {
        requireAllNonNull(name, phone, teleHandle, email,
                matNum, tutGroup, labGroup, faculty, remark, attendanceList, labScoreList, tags);
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
        this.attendanceList = attendanceList;
        this.labScoreList = labScoreList;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public TeleHandle getTeleHandle() {
        return teleHandle;
    }

    public Email getEmail() {
        return email;
    }

    public MatNum getMatNum() {
        return matNum;
    }

    public TutGroup getTutGroup() {
        return tutGroup;
    }

    public LabGroup getLabGroup() {
        return labGroup;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Year getYear() {
        return year;
    }

    public Remark getRemark() {
        return remark;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public AttendanceList getAttendanceList() {
        return attendanceList;
    }

    public LabScoreList getLabScoreList() {
        return labScoreList;
    }

    /**
     * Returns true if both persons have the same matriculation number
     * (this is a guaranteed unique form of identification).
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getMatNum().equals(getMatNum());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && teleHandle.equals(otherPerson.teleHandle)
                && email.equals(otherPerson.email)
                && matNum.equals(otherPerson.matNum)
                && tutGroup.equals(otherPerson.tutGroup)
                && labGroup.equals(otherPerson.labGroup)
                && faculty.equals(otherPerson.faculty)
                && year.equals(otherPerson.year)
                && remark.equals(otherPerson.remark)
                && tags.equals(otherPerson.tags)
                && attendanceList.equals(otherPerson.attendanceList)
                && labScoreList.equals(otherPerson.labScoreList);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, teleHandle, email,
                matNum, tutGroup, labGroup, faculty, year, remark,
                tags, attendanceList, labScoreList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("teleHandle", teleHandle)
                .add("email", email)
                .add("matNum", matNum)
                .add("tutGroup", tutGroup)
                .add("labGroup", labGroup)
                .add("faculty", faculty)
                .add("year", year)
                .add("remark", remark)
                .add("attendanceList", attendanceList)
                .add("labScoreList", labScoreList)
                .add("tags", tags)
                .toString();
    }

}
