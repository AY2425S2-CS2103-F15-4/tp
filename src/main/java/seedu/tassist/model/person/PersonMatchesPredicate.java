package seedu.tassist.model.person;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.tassist.commons.util.StringUtil;

/**
 * Tests if a {@code Person} matches the given search criteria.
 */
public class PersonMatchesPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final String matNum;
    private final String phone;
    private final String teleHandle;
    private final String email;
    private final String tag;
    private final String tutGroup;
    private final String labGroup;
    private final String faculty;
    private final String year;

    /**
     * Constructs a predicate with search parameters for a Person.
     */
    public PersonMatchesPredicate(List<String> nameKeywords, String matNum, String phone, String teleHandle,
                                  String email, String tag, String tutGroup, String labGroup,
                                  String faculty, String year) {
        this.nameKeywords = nameKeywords;
        this.matNum = matNum;
        this.phone = phone;
        this.teleHandle = teleHandle;
        this.email = email;
        this.tag = tag;
        this.tutGroup = tutGroup;
        this.labGroup = labGroup;
        this.faculty = faculty;
        this.year = year;
    }

    /**
     * Returns true if the given person matches all non-null and non-empty criteria.
     */
    @Override
    public boolean test(Person person) {
        // If all search parameters are null or empty, return true (show all persons)
        if ((nameKeywords == null || nameKeywords.isEmpty())
                && matNum == null && phone == null && teleHandle == null
                && email == null && tag == null && tutGroup == null && labGroup == null
                && faculty == null && year == null) {
            return true;
        }

        // Special case for name search with multiple keywords (full name search)
        if (nameKeywords != null && nameKeywords.size() > 1) {
            // First check for exact full name match
            String searchFullName = String.join(" ", nameKeywords);

            // If we have an exact match for the full name (case insensitive)
            boolean isExactFullNameMatch = person.getName().fullName.equalsIgnoreCase(searchFullName);

            // When performing a multi-word (full name) search, we ONLY want exact matches
            // If other criteria match too, include it in results, otherwise exclude
            if (isExactFullNameMatch) {
                return checkAllOtherCriteria(person);
            } else {
                // If it's not an exact match for a multi-word search, don't include
                return false;
            }
        }

        // Regular matching for single keyword (not full name) searches
        return (nameKeywords == null || (
                nameKeywords.isEmpty() ? person.getName().fullName.isEmpty()
                        : nameKeywords.stream().anyMatch(
                                keyword -> StringUtil.containsIgnoreCase(person.getName().fullName, keyword))))
                && checkAllOtherCriteria(person);
    }

    /**
     * Helper method to check all criteria except name
     */
    private boolean checkAllOtherCriteria(Person person) {
        return (matNum == null || (
                matNum.isEmpty() ? person.getMatNum().value.isEmpty()
                        : StringUtil.containsIgnoreCase(person.getMatNum().value, matNum)))
                && (phone == null || (
                        phone.isEmpty() ? person.getPhone().value.isEmpty()
                                : StringUtil.containsIgnoreCase(person.getPhone().value, phone)))
                && (teleHandle == null || (
                        teleHandle.isEmpty() ? person.getTeleHandle().value.isEmpty()
                                : StringUtil.containsIgnoreCase(person.getTeleHandle().value, teleHandle)))
                && (email == null || (
                        email.isEmpty() ? person.getEmail().value.isEmpty()
                                : StringUtil.containsIgnoreCase(person.getEmail().value, email)))
                && (tag == null || (
                        tag.isEmpty() ? person.getTags().isEmpty()
                                : person.getTags().stream()
                                        .anyMatch(t -> StringUtil.containsIgnoreCase(t.tagName, tag))))
                && (tutGroup == null || (
                        tutGroup.isEmpty() ? person.getTutGroup().value.isEmpty()
                                : matchesTutorialGroup(person.getTutGroup().value, tutGroup)))
                && (labGroup == null || (
                        labGroup.isEmpty() ? person.getLabGroup().value.isEmpty()
                                : StringUtil.containsIgnoreCase(person.getLabGroup().value, labGroup)))
                && (faculty == null || (
                        faculty.isEmpty() ? person.getFaculty().value.isEmpty()
                                : StringUtil.containsIgnoreCase(person.getFaculty().value, faculty)))
                && (year == null || (
                        year.isEmpty() ? person.getYear().value.isEmpty()
                                : StringUtil.containsIgnoreCase(person.getYear().value, year)));
    }

    /**
     * Checks if a tutorial group value matches the search term, handling different formats.
     * For example, "T01" will match "t1", "T1", "t01", etc.
     */
    private boolean matchesTutorialGroup(String value, String searchTerm) {
        // Remove any 'T' or 't' prefix and leading zeros from both strings
        String normalizedValue = value.replaceAll("^[Tt]0*", "");
        String normalizedSearch = searchTerm.replaceAll("^[Tt]0*", "");

        // Compare the normalized strings
        return normalizedValue.equalsIgnoreCase(normalizedSearch);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonMatchesPredicate)) {
            return false;
        }
        PersonMatchesPredicate o = (PersonMatchesPredicate) other;
        return Objects.equals(nameKeywords, o.nameKeywords)
                && Objects.equals(matNum, o.matNum)
                && Objects.equals(phone, o.phone)
                && Objects.equals(teleHandle, o.teleHandle)
                && Objects.equals(email, o.email)
                && Objects.equals(tag, o.tag)
                && Objects.equals(tutGroup, o.tutGroup)
                && Objects.equals(labGroup, o.labGroup)
                && Objects.equals(faculty, o.faculty)
                && Objects.equals(year, o.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameKeywords, matNum, phone, teleHandle, email, tag, tutGroup, labGroup, faculty, year);
    }
}
