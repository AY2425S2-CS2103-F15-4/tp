package seedu.tassist.storage;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.tassist.commons.core.LogsCenter;
import seedu.tassist.commons.exceptions.DataLoadingException;
import seedu.tassist.commons.util.FileUtil;
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
 * A class to save the AddressBook data as a CSV file on the hard disk.
 */
public class CsvAddressBookStorage implements AddressBookStorage {

    private static final java.util.logging.Logger logger = LogsCenter.getLogger(CsvAddressBookStorage.class);
    private Path filePath;

    public CsvAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path of the data file.
     *
     * @return The file path where the AddressBook is stored.
     */
    @Override
    public Path getAddressBookFilePath() {
        return filePath;
    }

    /**
     * Similar to {@link #readAddressBook(Path)}.
     *
     * @return An {@code Optional} containing the AddressBook data if available.
     * @throws DataLoadingException if loading the data from storage fails.
     */
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(filePath);
    }

    /**
     * Reads the AddressBook data from the specified file path.
     *
     * @param filePath The path of the CSV file to read from.
     * @return An {@code Optional} containing the AddressBook data if available.
     * @throws DataLoadingException if loading the data from storage fails.
     */
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);
        AddressBook addressBook = new AddressBook();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String header = reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] tokens = line.split(",", -1); // preserve empty strings

                    String name = tokens.length > 0 ? tokens[0] : "Unknown";
                    String phone = tokens.length > 1 ? tokens[1] : "";
                    String teleHandle = tokens.length > 2 ? tokens[2] : "";
                    String email = tokens.length > 3 ? tokens[3] : "";
                    String matNum = tokens.length > 4 ? tokens[4] : "";
                    String tutGroup = tokens.length > 5 ? tokens[5] : "";
                    String labGroup = tokens.length > 6 ? tokens[6] : "";
                    String faculty = tokens.length > 7 ? tokens[7] : "";
                    int year = (tokens.length > 8 && !tokens[8].isBlank()) ? Integer.parseInt(tokens[8]) : 0;
                    String remark = tokens.length > 9 ? tokens[9] : "";

                    // AttendanceList — from tokens[10] to tokens[22], should be 13 digits
                    StringBuilder attendanceBuilder = new StringBuilder();
                    for (int i = 10; i <= 22 && i < tokens.length; i++) {
                        String value = tokens[i].strip();
                        attendanceBuilder.append(value.matches("[012]") ? value : "0");
                    }
                    while (attendanceBuilder.length() < 13) {
                        attendanceBuilder.append("0"); // pad with 0 if short
                    }
                    AttendanceList attendanceList = AttendanceList.generateAttendanceList(attendanceBuilder.toString());

                    // LabScoreList — assume 1 lab score in tokens[23]
                    LabScoreList labScoreList = new LabScoreList();
                    if (tokens.length > 23 && !tokens[23].isBlank()) {
                        try {
                            int score = Integer.parseInt(tokens[23].trim());
                            labScoreList.updateLabScore(1, score);
                        } catch (Exception ignored) {
                            // fallback: labScoreList remains default
                        }
                    }

                    // Tags — comma-separated string in tokens[24]
                    Set<Tag> tags = new HashSet<>();
                    if (tokens.length > 24 && !tokens[24].isBlank()) {
                        String[] tagStrings = tokens[24].replace("\"", "").split(",");
                        for (String tag : tagStrings) {
                            if (!tag.strip().isEmpty()) {
                                tags.add(new Tag(tag.strip()));
                            }
                        }
                    }

                    Person person = new Person(
                            new Name(name),
                            new Phone(phone),
                            new TeleHandle(teleHandle),
                            new Email(email),
                            new MatNum(matNum),
                            new TutGroup(tutGroup),
                            new LabGroup(labGroup),
                            new Faculty(faculty),
                            new Year(String.valueOf(year)),
                            new Remark(remark),
                            attendanceList,
                            labScoreList,
                            tags
                    );

                    addressBook.addPerson(person);

                } catch (Exception e) {
                    logger.warning("Skipping invalid line: " + line + "\nReason: " + e.getMessage());
                }
            }

            return Optional.of(addressBook);
        } catch (IOException e) {
            throw new DataLoadingException(new IOException("Could not read CSV file: " + e.getMessage(), e));
        }
    }

    /**
     * Saves the AddressBook data to the default file path.
     *
     * @param addressBook The current AddressBook to be stored.
     * @throws IOException If an I/O error occurs while writing the data.
     */
    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)} but saves to a specified file path.
     *
     * @param addressBook The current AddressBook to be stored.
     * @param filePath    The location to store the CSV data. Cannot be null.
     * @throws IOException If an I/O error occurs while writing the data.
     */
    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        seedu.tassist.commons.util.CsvUtil.serializeObjectToCsvFile(filePath, addressBook.getPersonList());
    }
}
