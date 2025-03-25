package seedu.tassist.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.commons.util.ToStringBuilder;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " -i <index>: Deletes the student identified by the index number (1-based)\n"
            + "Parameters: -i <index> [,<index> or <range>...](must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " -i  1-3, 5, 7";

    public static final String MESSAGE_DELETE_MULTIPLE_SUCCESS = "Deleted %d persons successfully!"
            + "\nDeleted Student(s):\n%s";
    public static final String MESSAGE_DELETE_PERSON_INVALID_INDEX = "Invalid index!"
            + " You currently have %d records!";

    private final List<Index> targetIndexes;


    /**
     * Constructs a {@code DeleteCommand} to delete the person at the specified {@code targetIndexes}.
     *
     * @param targetIndexes Indexes of the persons in the filtered list to delete.
     */
    public DeleteCommand(List<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
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
        List<Person> toDelete = new ArrayList<>();
        Set<Index> uniqueSortedIndexes = new TreeSet<>(Comparator.comparingInt(Index::getZeroBased));
        uniqueSortedIndexes.addAll(targetIndexes);

        for (Index index : uniqueSortedIndexes) {
            int zeroBased = index.getZeroBased();
            if (zeroBased >= lastShownList.size()) {
                throw new CommandException(String.format(MESSAGE_DELETE_PERSON_INVALID_INDEX, lastShownList.size()));
            }
            toDelete.add(lastShownList.get(zeroBased));
        }

        for (Person person : toDelete) {
            model.deletePerson(person);
        }

        String deletedStudentsSummary = getDeletedStudentsSummary(toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MULTIPLE_SUCCESS,
                toDelete.size(), deletedStudentsSummary));

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

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteCommand
                && targetIndexes.equals(((DeleteCommand) other).targetIndexes));
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexes", targetIndexes)
                .toString();
    }
}
