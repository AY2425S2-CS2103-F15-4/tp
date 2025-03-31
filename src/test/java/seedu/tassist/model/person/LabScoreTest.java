package seedu.tassist.model.person;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.tassist.logic.commands.CommandTestUtil.DEFAULT_LAB_MAX_SCORE;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.exceptions.CommandException;

public class LabScoreTest {

    @Test
    public void successUpdateLabScore() {
        LabScore newLabScore = new LabScore();
        LabScore correctLabScore = new LabScore(15, 1);
        LabScore updatedLabScore = null;
        try {
            updatedLabScore = newLabScore.updateLabScore(15, 1);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(updatedLabScore, correctLabScore);

    }

    @Test
    public void invalidLabScore() {
        //Negative score.
        assertThrows(CommandException.class, () -> new LabScore().updateLabScore(-10, 1));

        //Lab score exceeds max lab score.
        assertThrows(CommandException.class, () -> new LabScore().updateLabScore(DEFAULT_LAB_MAX_SCORE + 35, 1));

        //Score larger than max score.
        assertThrows(CommandException.class, () -> new LabScore().updateBothLabScore(35, 10, 1));
    }

    @Test
    public void successUpdateLabMaxScore() {

        LabScore newLabScore = new LabScore();

        LabScore correctLabScore = new LabScore(15, 30, 1);

        LabScore updatedLabScore = null;
        try {
            newLabScore = newLabScore.updateLabScore(15, 1);
            updatedLabScore = newLabScore.updateMaxLabScore(30, 1);

        } catch (CommandException e) {
            fail();
        }

        assertEquals(updatedLabScore.toString(), correctLabScore.toString());

    }

    @Test
    public void invalidLabMaxScore() {
        assertThrows(CommandException.class, () -> new LabScore().updateMaxLabScore(-10, 1));
    }

    @Test
    public void successUpdateBothLabScore() {
        LabScore newLabScore = new LabScore();
        LabScore correctLabScore = new LabScore(100, 100, 0);
        LabScore updatedLabScore = null;
        try {
            updatedLabScore = newLabScore.updateBothLabScore(100, 100, 0);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(updatedLabScore, correctLabScore);

        LabScore correctLabScore2 = new LabScore(15, 35, 0);
        try {
            updatedLabScore = newLabScore.updateBothLabScore(15, 35, 0);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(updatedLabScore, correctLabScore2);
    }

}
