package seedu.tassist.logic.commands;

import static seedu.tassist.logic.commands.CommandTestUtil.DEFAULT_LAB_MAX_SCORE;
import static seedu.tassist.logic.commands.CommandTestUtil.DEFAULT_LAB_SCORE_COUNT;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_NUMBER_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_SCORE_A;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.UserPrefs;
import seedu.tassist.model.person.Person;
import seedu.tassist.testutil.PersonBuilder;

public class UpdateLabScoreCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void successCase() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withLabScores("4.20/25|-|-|-").build();

        UpdateLabScoreCommand command = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, VALID_LAB_SCORE_A, false);
        String expectedMessage = String.format(UpdateLabScoreCommand.MESSAGE_UPDATE_LAB_SCORE_SUCCESS,
                0, VALID_LAB_NUMBER_A);
        System.out.println(expectedMessage);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void indexOutOfBoundFail() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UpdateLabScoreCommand command = new UpdateLabScoreCommand(outOfBoundIndex,
                VALID_LAB_NUMBER_A, VALID_LAB_SCORE_A, false);
        assertCommandFailure(command, model, UpdateLabScoreCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void labNumberOutOfBoundFail() {
        UpdateLabScoreCommand command =
                new UpdateLabScoreCommand(INDEX_FIRST_PERSON, -1, VALID_LAB_SCORE_A, false);
        String validErrorMessage = String.format(UpdateLabScoreCommand.MESSAGE_INVALID_LAB_NUMBER,
                DEFAULT_LAB_SCORE_COUNT);
        assertCommandFailure(command, model, validErrorMessage);
    }

    @Test
    public void testScoreOutOfBoundFail() {
        int invalidScore = DEFAULT_LAB_MAX_SCORE + 10;
        UpdateLabScoreCommand command = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, invalidScore, false);
        String validErrorMessage = String.format(UpdateLabScoreCommand.MESSAGE_INVALID_SCORE, invalidScore,
                DEFAULT_LAB_MAX_SCORE);
        assertCommandFailure(command, model, validErrorMessage);
    }

    @Test
    public void testNegativeScoreOutOfBoundFail() {
        int invalidScore = -1;
        UpdateLabScoreCommand command = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, invalidScore, false);
        String validErrorMessage = String.format(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        assertCommandFailure(command, model, validErrorMessage);
    }

    @Test
    public void testMaxScoreOutOfBoundFail() {
        int invalidMaxScore = VALID_LAB_SCORE_A - 10;
        UpdateLabScoreCommand command = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, VALID_LAB_SCORE_A, invalidMaxScore);
        String validErrorMessage = String.format(UpdateLabScoreCommand.MESSAGE_INVALID_MAX_SCORE, invalidMaxScore,
                VALID_LAB_SCORE_A);
        assertCommandFailure(command, model, validErrorMessage);
    }

    @Test
    public void testNegativeMaxScoreOutOfBoundFail() {
        int invalidScore = -1;
        UpdateLabScoreCommand command = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, invalidScore, true);
        String validErrorMessage = String.format(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        assertCommandFailure(command, model, validErrorMessage);
    }

}
