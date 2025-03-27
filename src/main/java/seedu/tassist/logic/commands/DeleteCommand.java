package seedu.tassist.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.commons.util.ToStringBuilder;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.tag.Tag;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = String.format(
            "Usage: " + COMMAND_WORD + "[OPTIONS]...\n"
                    + "Deletes a person or removes a tag from a person in the address book.\n\n"
                    + "Mandatory arguments:\n"
                    + "-i <index> [,<index> or <range>...](must be a positive integer, 1-based, "
                    + "required to specify which person)\n\n"
                    + "Options:\n"
                    + "  -i   Deletes the specified person(s) using 1-based index\n"
                    + "  -tag  Removes the specified tag from the person at the given index\n\n"
                    + "Examples:\n"
                    + "  del -i 1         (Deletes the person at index 1)\n"
                    + "  del -i 1-3,5,7   (Deletes multiple persons)\n"
                    + "  del -i 2 -tag friend  (Removes the tag 'friend' from the person at index 2)"

    );


    public static final String MESSAGE_DELETE_MULTIPLE_SUCCESS = "Deleted %d persons successfully!"
            + "\nDeleted Student(s):\n%s";
    public static final String MESSAGE_DELETE_PERSON_INVALID_INDEX = "Invalid index!"
            + " You currently have %d records!";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Removed tag '%s' from %s.";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag '%s' not found in %s.";

    private final List<Index> targetIndexes;
    private final String tagToRemove;

    /**
     * Constructs a {@code DeleteCommand} to delete students or remove a tag.
     */
    public DeleteCommand(List<Index> targetIndexes, String tagToRemove) {
        this.targetIndexes = targetIndexes;
        this.tagToRemove = tagToRemove;
    }

    /**
     * Constructs a {@code DeleteCommand} that deletes persons only.
     *
     * @param targetIndexes List of 1-based indexes representing persons to delete.
     */
    public DeleteCommand(List<Index> targetIndexes) {

        this(targetIndexes, null);
    }

    /**
     * Executes the {@code DeleteCommand}, performing deletion or tag removal depending on the provided arguments.
     *
     * @param model {@code Model} containing the current address book and filtered person list.
     * @return {@code CommandResult} indicating the outcome of the command.
     * @throws CommandException If any specified index is invalid or tag is not found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> toModify = new ArrayList<>();
        Set<Index> uniqueSortedIndexes = new TreeSet<>(Comparator.comparingInt(Index::getZeroBased));
        uniqueSortedIndexes.addAll(targetIndexes);

        for (Index index : uniqueSortedIndexes) {
            int zeroBased = index.getZeroBased();
            if (zeroBased >= lastShownList.size()) {
                throw new CommandException(String.format(MESSAGE_DELETE_PERSON_INVALID_INDEX, lastShownList.size()));
            }
            toModify.add(lastShownList.get(zeroBased));
        }

        if (tagToRemove == null) {
            // Delete students
            for (Person person : toModify) {
                model.deletePerson(person);
            }

            String deletedStudentsSummary = getDeletedStudentsSummary(toModify);
            return new CommandResult(String.format(MESSAGE_DELETE_MULTIPLE_SUCCESS,
                    toModify.size(), deletedStudentsSummary));
        } else {
            // Remove tag from students
            List<String> modifiedPersons = new ArrayList<>();
            for (Person person : toModify) {
                Person updatedPerson = removeTagFromPerson(person, tagToRemove, model);
                if (updatedPerson != null) {
                    modifiedPersons.add(updatedPerson.getName().fullName);
                }
            }
            return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS,
                    tagToRemove, String.join(", ", modifiedPersons)));
        }
    }

    /**
     * Returns a short summary of students deleted, showing name, matriculation number, tutorial group, and lab group.
     *
     * @param students List of students that were deleted.
     * @return A formatted string summary of each deleted student.
     */
    public static String getDeletedStudentsSummary(List<Person> students) {
        StringBuilder sb = new StringBuilder();
        for (Person p : students) {
            sb.append(String.format("%s (%s) - %s, %s\n",
                    p.getName().fullName,
                    p.getMatNum().value,
                    p.getTutGroup().value,
                    p.getLabGroup().value));
        }
        return sb.toString().trim();
    }

    /**
     * Removes the specified tag from a {@code Person} and updates the model with the modified person.
     *
     * @param person    The original person.
     * @param tagName   The name of the tag to remove.
     * @param model     The model to update the person in.
     * @return The updated person without the tag.
     * @throws CommandException If the tag is not found in the person.
     */
    private Person removeTagFromPerson(Person person, String tagName, Model model) throws CommandException {
        Set<Tag> updatedTags = new HashSet<>(person.getTags());

        Tag tagToRemove = new Tag(tagName);
        if (!updatedTags.contains(tagToRemove)) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagName, person.getName().fullName));
        }

        updatedTags.remove(tagToRemove);

        // Create a new person with updated tags
        Person updatedPerson = new Person(
                person.getName(),
                person.getPhone(),
                person.getTeleHandle(),
                person.getEmail(),
                person.getMatNum(),
                person.getTutGroup(),
                person.getLabGroup(),
                person.getFaculty(),
                person.getYear(),
                person.getRemark(),
                person.getAttendanceList(),
                person.getLabScoreList(),
                updatedTags
        );

        model.setPerson(person, updatedPerson);
        return updatedPerson;
    }

    /**
     * Compares two DeleteCommand objects for equality.
     *
     * @param other The other object to compare with.
     * @return true if both commands target the same indexes and tag removal state.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteCommand
                && targetIndexes.equals(((DeleteCommand) other).targetIndexes)
                && (tagToRemove == null ? ((DeleteCommand) other).tagToRemove == null
                : tagToRemove.equals(((DeleteCommand) other).tagToRemove)));
    }

    /**
     * Returns a string representation of the {@code DeleteCommand}.
     *
     * @return Formatted string with target indexes and tag (if applicable).
     */
    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this)
                .add("targetIndexes", targetIndexes);

        if (tagToRemove != null) {
            builder.add("tagToRemove", tagToRemove);
        }

        return builder.toString();
    }
}
