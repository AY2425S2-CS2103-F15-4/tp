package seedu.tassist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.commons.util.StringUtil;
import seedu.tassist.logic.parser.exceptions.ParseException;
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
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_WEEK =
            "Week is not an unsigned integer from 1 to 13.";
    public static final String MESSAGE_INVALID_DASH_ORDER = "When using dash for Bulk TutGroups, "
            + "ensure they are in ascending order. E.g. T01-T05, not T05-T01.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.replaceAll("\\s+", "");
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String teleHandle} into a {@code TeleHandle}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code teleHandle} is invalid.
     */
    public static TeleHandle parseTeleHandle(String teleHandle) throws ParseException {
        requireNonNull(teleHandle);
        String trimmedTeleHandle = teleHandle.replaceAll("\\s+", "");
        if (!TeleHandle.isValidTeleHandle(trimmedTeleHandle)) {
            throw new ParseException(TeleHandle.MESSAGE_CONSTRAINTS);
        }
        return new TeleHandle(trimmedTeleHandle);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.replaceAll("\\s+", "");
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String matNum} into a {@code MatNum}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code } is invalid.
     */
    public static MatNum parseMatNum(String matNum) throws ParseException {
        requireNonNull(matNum);
        String trimmedMatNum = matNum.replaceAll("\\s+", "");
        if (!MatNum.isValidMatNum(trimmedMatNum)) {
            throw new ParseException(MatNum.MESSAGE_CONSTRAINTS);
        }
        return new MatNum(trimmedMatNum);
    }

    /**
     * Parses a {@code String tutGroup} into a {@code TutGroup}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code tutGroup} is invalid.
     */
    public static TutGroup parseTutGroup(String tutGroup) throws ParseException {
        requireNonNull(tutGroup);
        String trimmedTutGroup = tutGroup.replaceAll("\\s+", "");
        if (!TutGroup.isValidTutGroup(trimmedTutGroup)) {
            throw new ParseException(TutGroup.MESSAGE_CONSTRAINTS);
        }
        return new TutGroup(trimmedTutGroup);
    }

    /**
     * Parses a {@code String labGroup} into a {@code LabGroup}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code labGroup} is invalid.
     */
    public static LabGroup parseLabGroup(String labGroup) throws ParseException {
        requireNonNull(labGroup);
        String trimmedLabGroup = labGroup.replaceAll("\\s+", "");
        if (!LabGroup.isValidLabGroup(trimmedLabGroup)) {
            throw new ParseException(LabGroup.MESSAGE_CONSTRAINTS);
        }
        return new LabGroup(trimmedLabGroup);
    }

    /**
     * Parses a {@code String faculty} into a {@code Faculty}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code faculty} is invalid.
     */
    public static Faculty parseFaculty(String faculty) throws ParseException {
        requireNonNull(faculty);
        String trimmedFaculty = faculty.trim();
        if (!Faculty.isValidFaculty(trimmedFaculty)) {
            throw new ParseException(Faculty.MESSAGE_CONSTRAINTS);
        }
        return new Faculty(trimmedFaculty);
    }

    /**
     * Parses a {@code String year} into a {@code Year}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.replaceAll("\\s+", "");
        if (!Year.isValidYear(trimmedYear)) {
            throw new ParseException(Year.MESSAGE_CONSTRAINTS);
        }
        return new Year(trimmedYear);
    }

    /**
     * Parses a {@code String remark} into a {@code Remark}.
     *
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        if (!Remark.isValidRemark(remark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        return new Remark(remark);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static List<Tag> parseTagsList(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final List<Tag> tagSet = new ArrayList<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String week} into an {@code int}.
     *
     * @throws ParseException if the given {@code week} is invalid.
     */
    public static int parseWeek(String week) throws ParseException {
        requireNonNull(week);
        if (!StringUtil.isValidWeek(week)) {
            throw new ParseException(MESSAGE_INVALID_WEEK);
        }
        return Integer.parseInt(week);
    }

    /**
     * Parses {@code String labNumber} into an {@code int}.
     *
     * @throws ParseException if the given {@code labNumber} is invalid.
     */
    public static int parseLabNumber(String labNumber) throws ParseException {
        requireNonNull(labNumber);
        if (!LabScoreList.isValidLabNumber(labNumber)) {
            throw new ParseException(LabScoreList.LAB_NUMBER_CONSTRAINT);
        }
        return Integer.parseInt(labNumber);
    }

    /**
     * Parses {@code String labScore} into an {@code int}.
     *
     * @throws ParseException if the given {@code labScore} is invalid.
     */
    public static int parseLabScore(String labScore) throws ParseException {
        requireNonNull(labScore);
        try {
            return Integer.parseInt(labScore);
        } catch (NumberFormatException e) {
            throw new ParseException(LabScoreList.INVALID_LAB_SCORE);
        }

    }

    /**
     * Returns a filtered list of persons from personList
     * who are in the given tutorial group.
     *
     * @param personList List of persons to filter from.
     * @param tutGroupList List of TutGroups to match persons against.
     * @return List of persons from personList with the matching tutGroup.
     */
    public static List<Person> getPersonsInTutorialGroups(List<Person> personList, List<TutGroup> tutGroupList) {
        requireAllNonNull(personList, tutGroupList);

        return personList.stream()
                .filter(person -> tutGroupList.contains(person.getTutGroup()))
                .toList();
    }

    /**
     * Gets the invalid {@link TutGroup}s in {@code tutGroupList} as a String.
     * A TutGroup is invalid if it is not a tutorial group of any person in {@code personList}.
     *
     * @param personList List of Persons to check Tutorial Groups of.
     * @param tutGroupList List of Tutorial Groups to be checked.
     * @return String containing the Tutorial Groups that are in the provided
     *         {@code tutGroupList}, but not a Tutorial Group of any person in personList.
     */
    public static String getInvalidTutGroupsAsString(List<Person> personList, List<TutGroup> tutGroupList) {
        requireAllNonNull(personList, tutGroupList);
        List<TutGroup> listOfTutGroups = personList.stream().map(person -> person.getTutGroup()).toList();
        List<TutGroup> listOfUniqueTutGroups = new HashSet<>(listOfTutGroups).stream().toList();
        List<TutGroup> listOfInvalidTutGroups =
                tutGroupList.stream().filter(tutGroup -> !listOfUniqueTutGroups.contains(tutGroup)).toList();
        if (listOfInvalidTutGroups.isEmpty()) {
            return "";
        } else {
            String result = listOfInvalidTutGroups.stream().reduce("", (acc, tg) ->
                    acc + tg.toString() + ", ", (x, y) -> x + y);
            return result.substring(0, result.length() - 2);
        }
    }

    /**
     * Parses an input string representing a list of indexes (e.g. "1, 2, 4-6")
     * into a sorted, de-duplicated list of {@link Index} objects.
     *
     * @param input The raw string containing index specifications.
     * @return A list of {@link Index} parsed from the input, in sorted order and without duplicates.
     * @throws ParseException If any part of the input is invalid.
     */
    public static List<Index> parseMultipleIndexes(String input) throws ParseException {
        requireNonNull(input);
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        Set<Integer> indexSet = parseToSortedUniqueIntegers(trimmedInput);
        return convertIntegersToIndexes(indexSet);
    }

    /**
     * Parses an input string representing a list of TutGroups ("T01,T02", "T02-T05")
     * and returns a sorted, de-duplicated list of {@link TutGroup} objects.
     *
     * @param input Input string provided.
     * @return List of TutGroups parsed from the list.
     * @throws ParseException If the input is invalid.
     */
    public static List<TutGroup> parseMultipleTutGroups(String input) throws ParseException {
        requireNonNull(input);
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(TutGroup.MESSAGE_CONSTRAINTS);
        }

        Set<TutGroup> tutGroupSet = new TreeSet<>();
        String[] parts = input.split(",");

        for (String part : parts) {
            part = part.trim();
            if (part.contains("-")) {
                parseTutGroupRange(part, tutGroupSet);
            } else {
                tutGroupSet.add(parseTutGroup(part));
            }
        }

        return tutGroupSet.stream().toList();
    }

    /**
     * Parses the input string into a set of unique, sorted integers.
     * Supports both individual indexes (e.g. "2") and ranges (e.g. "3-5").
     *
     * @param input The trimmed input string.
     * @return A sorted set of unique one-based indexes.
     * @throws ParseException If the input format is invalid.
     */
    private static Set<Integer> parseToSortedUniqueIntegers(String input) throws ParseException {
        Set<Integer> indexSet = new TreeSet<>();
        String[] parts = input.split(",");

        for (String part : parts) {
            part = part.trim();
            if (part.contains("-")) {
                parseRange(part, indexSet);
            } else {
                parseSingleIndex(part, indexSet);
            }
        }

        return indexSet;
    }

    /**
     * Parses a string representing a range of TutGroups (e.g. "T02-T04")
     * and adds the tutGroups to the provided set.
     *
     * @param rangeStr Range String to be parsed.
     * @param tutGroupSet The Set to populate with TutGroups.
     * @throws ParseException If range provided is invalid.
     */
    private static void parseTutGroupRange(String rangeStr, Set<TutGroup> tutGroupSet) throws ParseException {
        requireAllNonNull(rangeStr, tutGroupSet);
        String[] range = rangeStr.split("-");
        if (range.length != 2) {
            throw new ParseException(TutGroup.MESSAGE_CONSTRAINTS);
        }

        // Check if tutGroups are of valid format.
        parseTutGroup(range[0].trim());
        parseTutGroup(range[1].trim());

        try {
            int start = Integer.parseInt(range[0].trim().substring(1));
            int end = Integer.parseInt(range[1].trim().substring(1));
            if (start > end) {
                throw new ParseException(MESSAGE_INVALID_DASH_ORDER);
            }
            for (int i = start; i <= end; i++) {
                tutGroupSet.add(TutGroup.createTutGroupFromGroupNumber(i));
            }
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX, e);
        }
    }

    /**
     * Parses a string representing a range (e.g. "2-4") and adds the integers
     * to the provided set.
     *
     * @param rangeStr The range string (must be in "start-end" format).
     * @param indexSet The set to populate with parsed integers.
     * @throws ParseException If the range is invalid or not properly formatted.
     */
    private static void parseRange(String rangeStr, Set<Integer> indexSet) throws ParseException {
        String[] range = rangeStr.split("-");
        if (range.length != 2) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        try {
            int start = Integer.parseInt(range[0].trim());
            int end = Integer.parseInt(range[1].trim());

            if (start <= 0 || end <= 0 || start > end) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }

            for (int i = start; i <= end; i++) {
                indexSet.add(i);
            }
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX, e);
        }
    }

    /**
     * Parses a single integer token representing an index (e.g. "3") and adds it to the provided set.
     *
     * @param token The string token to parse.
     * @param indexSet The set to populate with the parsed integer.
     * @throws ParseException If the token is not a valid positive integer.
     */
    private static void parseSingleIndex(String token, Set<Integer> indexSet) throws ParseException {
        try {
            int value = Integer.parseInt(token);
            if (value <= 0) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
            indexSet.add(value);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX, e);
        }
    }

    /**
     * Converts a set of integers into a list of {@link Index} objects.
     *
     * @param intIndexes A set of valid one-based integers.
     * @return A list of corresponding {@link Index} instances.
     */
    private static List<Index> convertIntegersToIndexes(Set<Integer> intIndexes) {
        List<Index> indexList = new ArrayList<>();
        for (int i : intIndexes) {
            indexList.add(Index.fromOneBased(i));
        }
        return indexList;
    }
}
