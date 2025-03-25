package seedu.tassist.commons.util;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import seedu.tassist.commons.core.LogsCenter;
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
 * Converts Java objects to and from CSV.
 */
public class CsvUtil {
    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    // ====================== CSV Write ======================

    /**
     * Serializes a list of objects into a CSV file at the given path.
     *
     * @param csvFile The path to write the CSV file to.
     * @param objects The list of objects to serialize.
     * @param <T>     The type of objects in the list.
     * @throws IOException If writing to the file fails.
     */
    public static <T> void serializeObjectToCsvFile(Path csvFile, List<T> objects) throws IOException {
        FileUtil.writeToFile(csvFile, toCsvString(objects));
    }

    /**
     * Converts a list of objects into a CSV string.
     *
     * @param objects The list of objects to convert.
     * @param <T>     The type of objects.
     * @return A CSV-formatted string representing the list.
     */
    public static <T> String toCsvString(List<T> objects) {
        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException("Object list is empty.");
        }

        Class<?> clazz = objects.get(0).getClass();
        String header = getHeader(clazz);

        List<String> rows = objects.stream()
                .map(CsvUtil::getRow)
                .collect(Collectors.toList());

        return header + "\n" + String.join("\n", rows);
    }

    private static String getHeader(Class<?> clazz) {
        return List.of(clazz.getDeclaredFields())
                .stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));
    }

    private static <T> String getRow(T obj) {
        try {
            return List.of(obj.getClass().getDeclaredFields())
                    .stream()
                    .map(field -> {
                        field.setAccessible(true);
                        try {
                            return escapeCsv(field.get(obj));
                        } catch (IllegalAccessException e) {
                            return "";
                        }
                    })
                    .collect(Collectors.joining(","));
        } catch (Exception e) {
            throw new RuntimeException("Error processing object fields", e);
        }
    }

    private static String escapeCsv(Object value) {
        if (value == null) {
            return "";
        }
        String str = value.toString();
        if (str.contains(",") || str.contains("\"") || str.contains("\n")) {
            str = "\"" + str.replace("\"", "\"\"") + "\"";
        }
        return str;
    }

    // ====================== CSV Read ======================

    /**
     * Reads a CSV file and returns a list of Person objects.
     * Expected header: Name,Phone,TeleHandle,Email,MatNum,TutGroup,LabGroup,Faculty,Year,Remark,Tags
     *
     * @param filePath Path to the CSV file.
     * @return List of valid Person objects.
     * @throws IOException              If file reading fails.
     * @throws CsvValidationException  If CSV parsing fails.
     */
    public static List<Person> deserializeCsvToPersonList(Path filePath)
            throws IOException, CsvValidationException {
        List<Person> personList = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath.toFile()))) {
            String[] header = csvReader.readNext(); // Skip header
            if (header == null || header.length < 11) {
                throw new IOException("Missing or invalid CSV header.");
            }

            String[] row;
            while ((row = csvReader.readNext()) != null) {
                if (row.length < 11) {
                    continue;
                }

                Name name = new Name(row[0].trim());
                Phone phone = new Phone(row[1].trim());
                TeleHandle teleHandle = new TeleHandle(row[2].trim());
                Email email = new Email(row[3].trim());
                MatNum matNum = new MatNum(row[4].trim());
                TutGroup tutGroup = new TutGroup(row[5].trim());
                LabGroup labGroup = new LabGroup(row[6].trim());
                Faculty faculty = new Faculty(row[7].trim());
                Year year = new Year(row[8].trim());
                Remark remark = new Remark(row[9].trim());

                Set<Tag> tags = new HashSet<>();
                if (!row[10].trim().isEmpty()) {
                    String[] tagStrings = row[10].split(",");
                    for (String tagStr : tagStrings) {
                        tags.add(new Tag(tagStr.trim()));
                    }
                }

                AttendanceList attendanceList = AttendanceList.generateAttendanceList(
                        AttendanceList.DEFAULT_ATTENDANCE_STRING);
                LabScoreList labScoreList = new LabScoreList();

                Person person = new Person(name, phone, teleHandle, email, matNum,
                        tutGroup, labGroup, faculty, year, remark,
                        attendanceList, labScoreList, tags);
                personList.add(person);
            }
        }

        return personList;
    }
}
