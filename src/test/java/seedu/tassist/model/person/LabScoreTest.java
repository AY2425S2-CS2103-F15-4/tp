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
        LabScore correctLabScore = new LabScore(15, 25);
        LabScore updatedLabScore = null;
        try {
            updatedLabScore = newLabScore.updateLabScore(15);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(updatedLabScore, correctLabScore);

    }

    @Test
    public void invalidLabScore() {
        //Negative score.
        assertThrows(CommandException.class, () -> new LabScore().updateLabScore(-10));

        //Lab score exceeds max lab score.
        assertThrows(CommandException.class, () -> new LabScore().updateLabScore(DEFAULT_LAB_MAX_SCORE + 35));

        //Score larger than max score.
        assertThrows(CommandException.class, () -> new LabScore().updateBothLabScore(35, 10));
    }

    @Test
    public void successUpdateLabMaxScore() {
        LabScore newLabScore = new LabScore();
        LabScore correctLabScore = new LabScore(15, 30);
        LabScore updatedLabScore = null;
        try {
            newLabScore = newLabScore.updateLabScore(15);
            updatedLabScore = newLabScore.updateMaxLabScore(30);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(updatedLabScore, correctLabScore);

    }

    @Test
    public void invalidLabMaxScore() {
        assertThrows(CommandException.class, () -> new LabScore().updateMaxLabScore(-10));
    }

    @Test
    public void successUpdateBothLabScore() {
        LabScore newLabScore = new LabScore();
        LabScore correctLabScore = new LabScore(100, 100);
        LabScore updatedLabScore = null;
        try {
            updatedLabScore = newLabScore.updateBothLabScore(100, 100);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(updatedLabScore, correctLabScore);

        LabScore correctLabScore2 = new LabScore(15, 35);
        try {
            updatedLabScore = newLabScore.updateBothLabScore(15, 35);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(updatedLabScore, correctLabScore2);
    }

}
