package seedu.tassist.model.person;

import seedu.tassist.logic.commands.UpdateLabScoreCommand;
import seedu.tassist.logic.commands.exceptions.CommandException;

/**
 * Holds the score and max score for a lab.
 */
public class LabScore {
    private int labScore;
    private int maxLabScore = 25; //Default max score is 25.

    public LabScore() {
        labScore = -1;
    }

    /**
     * Creates a LabScore object.
     *
     * @param labScore the score to initialize with.
     * @param maxLabScore the max score of the lab to initialize.
     */
    public LabScore(int labScore, int maxLabScore) {
        this.labScore = labScore;
        this.maxLabScore = maxLabScore;
    }

    /**
     * Updates the lab score for this lab.
     *
     * @param labScore The updated lab score.
     * @return The updated LabScore object.
     * @throws CommandException If the lab score is invalid.
     */
    public LabScore updateLabScore(int labScore) throws CommandException {
        if (labScore > maxLabScore) {
            throw new CommandException(
                    String.format(UpdateLabScoreCommand.MESSAGE_INVALID_SCORE,
                            labScore, maxLabScore));
        } else if (labScore < 0) {
            throw new CommandException(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        }
        return new LabScore(labScore, maxLabScore);
    }

    /**
     * Updates the max lab score for this lab.
     *
     * @param maxLabScore The updated max lab score.
     * @return The updated LabScore object.
     * @throws CommandException If the lab score is invalid.
     */
    public LabScore updateMaxLabScore(int maxLabScore) throws CommandException {
        if (labScore > maxLabScore && labScore != -1) {
            throw new CommandException(
                    String.format(UpdateLabScoreCommand.MESSAGE_INVALID_MAX_SCORE,
                            maxLabScore, labScore));
        } else if (maxLabScore < 0) {
            throw new CommandException(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        }
        return new LabScore(labScore, maxLabScore);
    }

    /**
     * Updates the lab score and max lab score for this lab.
     *
     * @param labScore The updated lab score.
     * @param maxLabScore The updated max lab score.
     * @return The updated LabScore object.
     * @throws CommandException If the lab score or max lab score is invalid.
     */
    public LabScore updateBothLabScore(int labScore, int maxLabScore) throws CommandException {
        if (labScore < 0 || maxLabScore < 0) {
            throw new CommandException(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        } else if (labScore > maxLabScore) {
            throw new CommandException(
                    String.format(UpdateLabScoreCommand.MESSAGE_INVALID_MAX_SCORE,
                            maxLabScore, labScore));
        }
        return new LabScore(labScore, maxLabScore);
    }

    @Override
    public String toString() {
        if (labScore == -1) {
            return "-";
        }

        return String.format("%d/%d", labScore, maxLabScore);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LabScore)) {
            return false;
        }

        LabScore o = (LabScore) other;
        return this.labScore == o.labScore
                && this.maxLabScore == o.maxLabScore;
    }
}
