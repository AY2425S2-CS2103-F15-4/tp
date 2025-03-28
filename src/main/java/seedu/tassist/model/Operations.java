package seedu.tassist.model;

import static seedu.tassist.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.tassist.logic.commands.RedoCommand;
import seedu.tassist.logic.commands.UndoCommand;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.person.Person;





/**
 * Records all the operations for redo and undo.
 */
public class Operations {

    /**
     * Holds all the types of commands involved.
     */
    public enum CommandType { ADD, EDIT, DELETE, CLEAR, ATTENDANCE, LABSCORE, IGNORED, UNDO, REDO }
    public static final String COMMAND_IGNORED = "%1$s was the last command, no changes has occurred";
    private static final String ERROR_MESSAGE = "You have reached the limit of %s";
    private static final String EMPTY_ERROR_MESSAGE = "There is nothing to %s.";
    private static Model model = null;
    private static ArrayList<Snapshot> pastStates = new ArrayList<>();
    private static ArrayList<Snapshot> futureStates = new ArrayList<>();
    private static Snapshot currentState = null;



    private static boolean isRecorded = false;

    /**
     * Records the undo and redo command state.
     *
     * @param commandType The type of current command.
     * @throws ParseException If there is nothing to redo and undo.
     */
    public static void recordCurrentCommand(CommandType commandType) throws ParseException {
        if (currentState == null || pastStates.isEmpty() && futureStates.isEmpty()) {
            throw new ParseException(String.format(EMPTY_ERROR_MESSAGE, commandType.toString().toLowerCase()));
        }

        Snapshot newState = new Snapshot(currentState);
        CommandType oldCommandType = currentState.getCommandType();
        ObservableList<Person> personList = model.getAddressBook().getPersonList();

        if (oldCommandType == CommandType.CLEAR) {
            //Copy the whole address book
            newState.duplicatePeople(currentState.getPeople());
        } else if (oldCommandType == CommandType.ADD) {
            Person person = personList.get(personList.size() - 1);
            newState.setPerson(person);
        } else if (oldCommandType == CommandType.DELETE) {
            newState.duplicatePeople(currentState.getPeople());
            newState.setIndex(personList.size() - 1);
        } else if (oldCommandType == CommandType.EDIT || oldCommandType == CommandType.LABSCORE
                || oldCommandType == CommandType.ATTENDANCE) {
            Person person = personList.get(currentState.getIndex());
            newState.setPerson(person);
        }

        //add to state list
        if (commandType == CommandType.UNDO) {
            futureStates.add(newState);
        } else if (commandType == CommandType.REDO) {
            pastStates.add(newState);
        }
    }

    /**
     * Records the rest of the commands.
     *
     * @param command The command string.
     * @param commandType The type of command.
     */
    public static void recordCurrentCommand(String command, CommandType commandType) {
        if (model == null) {
            return;
        }
        currentState = new Snapshot(command, commandType);

        //get list and command type
        CommandType currentType = currentState.getCommandType();
        ObservableList<Person> personList = model.getAddressBook().getPersonList();

        if (currentState.getIndex() != -1) {
            saveIndexCommand(personList);
        }

        if (currentType == CommandType.CLEAR) {
            //Save person list
            currentState.setPerson(personList.toArray(new Person[0]));
        }

        pastStates.add(currentState);

        //New branch in timeline, clear future states
        if (!futureStates.isEmpty()) {
            futureStates.clear();
        }

        isRecorded = true;
    }

    /**
     * Saves the person involved in the command.
     *
     * @param personList The current list of people in the address book.
     */
    public static void saveIndexCommand(ObservableList<Person> personList) {
        if (personList.isEmpty()) {
            return;
        }

        if (currentState.getIndex() > personList.size() - 1) {
            return;
        }

        Person editedPerson = personList.get(currentState.getIndex());
        currentState.setPerson(editedPerson);
    }

    /**
     * Resets the isRecorded boolean at the start of new command.
     */
    public static void resetRecording() {
        isRecorded = false;
    }

    /**
     * Remove the last recorded command if it did not run (errors thrown).
     */
    public static void removeRecording() {
        if (!isRecorded) {
            return;
        }
        pastStates.remove(pastStates.size() - 1);
        isRecorded = false;
    }

    /**
     * Updates the current model with the updated model at each execution.
     *
     * @param modelUpdate The new model.
     */
    public static void update(Model modelUpdate) {
        model = modelUpdate;
    }

    /**
     * Undoes the current command.
     *
     * @param model The current model.
     * @return A Feedback String.
     * @throws CommandException If there is no command to undo.
     */
    public static String undo(Model model) throws CommandException {
        if (pastStates.isEmpty()) {
            futureStates.remove(futureStates.size() - 1);
            throw new CommandException(String.format(ERROR_MESSAGE, "undo"));
        }

        CommandType currentCommandType = currentState.getCommandType();
        if (currentCommandType == CommandType.IGNORED) {
            return String.format(COMMAND_IGNORED, currentCommandType);
        }

        //Update timeline
        currentState = pastStates.remove(pastStates.size() - 1);
        runCommand(model, currentState);
        return String.format(UndoCommand.MESSAGE_UNDO_SUCCESS, currentCommandType.toString().toLowerCase());
    }

    /**
     * Redoes the current command.
     *
     * @param model The current model.
     * @return A feedback string.
     * @throws CommandException If there is nothing to redo.
     */
    public static String redo(Model model) throws CommandException {
        if (futureStates.isEmpty()) {
            pastStates.remove(pastStates.size() - 1);
            throw new CommandException(String.format(ERROR_MESSAGE, "redo"));
        }

        CommandType currentCommandType = currentState.getCommandType();
        if (currentCommandType == CommandType.IGNORED) {
            return String.format(COMMAND_IGNORED, currentCommandType);
        }

        //Update timeline
        currentState = futureStates.remove(futureStates.size() - 1);
        swapCommand();
        runCommand(model, currentState);
        swapCommand();

        return String.format(RedoCommand.MESSAGE_REDO_SUCCESS, currentCommandType.toString());
    }

    /**
     * Swaps the placement of two commands.
     */
    private static void swapCommand() {
        CommandType currentCommandType = currentState.getCommandType();
        if (currentCommandType == CommandType.ADD) {
            currentState.setCommandType(CommandType.DELETE);
        } else if (currentCommandType == CommandType.DELETE) {
            currentState.setCommandType(CommandType.ADD);
        }
    }

    /**
     * Sets the states to the desired moment.
     *
     * @param model The current model.
     * @param currentState The current state.
     */
    public static void runCommand(Model model, Snapshot currentState) {
        CommandType currentCommand = currentState.getCommandType();

        if (currentCommand == CommandType.EDIT || currentCommand == CommandType.ATTENDANCE
                || currentCommand == CommandType.LABSCORE) {
            //Set back the previous person state
            List<Person> lastShownList = model.getFilteredPersonList();
            Person personToUpdate = lastShownList.get(currentState.getIndex());
            Person updatedPerson = currentState.getPerson();
            model.setPerson(personToUpdate, updatedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        } else if (currentCommand == CommandType.DELETE) {
            //Add back the deleted person
            ArrayList<Person> addAll = currentState.getPeople();
            for (Person person : addAll) {
                model.addPerson(person);
            }

        } else if (currentCommand == CommandType.ADD) {
            //Delete the newly added person
            Person personToDelete = model.getFilteredPersonList().get(model.getFilteredPersonList().size() - 1);
            model.deletePerson(personToDelete);

        } else if (currentCommand == CommandType.CLEAR) {
            if (model.getFilteredPersonList().isEmpty()) {
                //Add every person back (undo)
                ArrayList<Person> addAll = currentState.getPeople();
                for (Person person : addAll) {
                    model.addPerson(person);
                }
            } else {
                //Reset address book (redo)
                model.setAddressBook(new AddressBook());
            }
        }
    }
}
