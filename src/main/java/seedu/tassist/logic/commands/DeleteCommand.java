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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " -i <index> [-tag <tag_name>]: Deletes a student or a specific tag from the student.\n"
            + "Parameters:\n"
            + "  -i <index> [,<index> or <range>...](must be a positive integer)\n"
            + "  -tag <tag_name> (optional, to remove a tag instead of deleting the student)\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " -i 1-3,5,7   (Deletes multiple students)\n"
            + "  " + COMMAND_WORD + " -i 1 -tag friend   (Deletes the 'friend' tag from student 1)";

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

    public DeleteCommand(List<Index> targetIndexes) {
        this(targetIndexes, null);
    }

    /**
     * Executes the deletion command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return the result message of the deletion.
     * @throws CommandException if the target index is invalid.
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
            return new CommandResult(String.format(MESSAGE_DELETE_MULTIPLE_SUCCESS, toModify.size(), deletedStudentsSummary));
        } else {
            // Remove tag from students
            List<String> modifiedPersons = new ArrayList<>();
            for (Person person : toModify) {
                Person updatedPerson = removeTagFromPerson(person, tagToRemove, model);
                if (updatedPerson != null) {
                    modifiedPersons.add(updatedPerson.getName().fullName);
                }
            }
            return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToRemove, String.join(", ", modifiedPersons)));
        }
    }

    /**
     * Generates a short summary of deleted students.
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
     * Removes a tag from a person and updates the model.
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
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteCommand
                && targetIndexes.equals(((DeleteCommand) other).targetIndexes)
                && (tagToRemove == null ? ((DeleteCommand) other).tagToRemove == null
                : tagToRemove.equals(((DeleteCommand) other).tagToRemove)));
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexes", targetIndexes)
                .add("tagToRemove", tagToRemove)
                .toString();
    }
}
