package seedu.tassist.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.tassist.model.AddressBook;
import seedu.tassist.model.ReadOnlyAddressBook;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark DEFAULT_REMARK = new Remark("");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new TeleHandle(""),
                    new Email("alexyeoh@example.com"), new MatNum("A0000010X"), new TutGroup("T01"),
                    new LabGroup("B03"), new Faculty("SoC"), new Year("2"), DEFAULT_REMARK,
                    AttendanceList.generateAttendanceList("3300000000000"), new LabScoreList(),
                    getTagSet("friends")),

            new Person(new Name("Bernice Yu"), new Phone("99272758"), new TeleHandle("@ber_nieee"),
                    new Email("berniceyu@example.com"), new MatNum("A0000020W"),
                    new TutGroup("T02"), new LabGroup(""), new Faculty("FoS"),
                    new Year(""), DEFAULT_REMARK,
                    AttendanceList.generateAttendanceList("3300000000000"),
                    new LabScoreList(), getTagSet("colleagues", "friends")),

            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new TeleHandle("@charro"), new Email("charlotte@example.com"),
                    new MatNum("A0000030U"), new TutGroup("T01"),
                    new LabGroup("B05"), new Faculty("College of Design and Engineering"),
                    new Year("4"), DEFAULT_REMARK,
                    AttendanceList.generateAttendanceList("3300000000000"),
                    new LabScoreList(), getTagSet("neighbours")),

            new Person(new Name("David Li"), new Phone("91031282"),
                    new TeleHandle("@Li_David"), new Email("lidavid@example.com"),
                    new MatNum("A0000040R"), new TutGroup("T03"),
                    new LabGroup("B05"), new Faculty(""), new Year("6"), DEFAULT_REMARK,
                    AttendanceList.generateAttendanceList("3300000000000"),
                    new LabScoreList(), getTagSet("family")),

            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new TeleHandle("@fan_fan"),
                    new Email("irfan@example.com"), new MatNum("A0000050N"), new TutGroup("T06"),
                    new LabGroup(""), new Faculty(""), new Year(""), DEFAULT_REMARK,
                    AttendanceList.generateAttendanceList("3300000000000"),
                    new LabScoreList(), getTagSet("classmates")),

            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new TeleHandle("@roynan"),
                    new Email("royb@example.com"), new MatNum("A0000060M"), new TutGroup(""),
                    new LabGroup("B10"), new Faculty(""), new Year(""), DEFAULT_REMARK,
                    AttendanceList.EMPTY_ATTENDANCE_LIST,
                    new LabScoreList(), getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
