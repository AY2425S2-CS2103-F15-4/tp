package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // Same values -> returns true.
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // Same object -> returns true.
        assertTrue(commandResult.equals(commandResult));

        // Null -> returns false.
        assertFalse(commandResult.equals(null));

        // Different types -> returns false.
        assertFalse(commandResult.equals(0.5f));

        // Different feedbackToUser value -> returns false.
        assertFalse(commandResult.equals(new CommandResult("different")));

        // Different showHelp value -> returns false.
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // Different exit value -> returns false.
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));
    }

    @Test
    public void hashCodeMethod() {
        CommandResult commandResult = new CommandResult("feedback");

        // Same values -> returns same hashcode.
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // Different feedbackToUser value -> returns different hashcode.
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // Different showHelp value -> returns different hashcode.
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // Different exit value -> returns different hashcode.
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + "}";
        assertEquals(expected, commandResult.toString());
    }
}
