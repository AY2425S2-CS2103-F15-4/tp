package seedu.tassist.model.person;

import seedu.tassist.logic.commands.UpdateLabScoreCommand;
import seedu.tassist.logic.commands.exceptions.CommandException;

import java.util.ArrayList;

/**
 * Handles the list of LabScore objects.
 */
public class LabScoreList {
    public static final String INVALID_LAB_SCORE = "Lab score needs to be a number";
    private static int labTotal = 4;
    public static final String LAB_NUMBER_CONSTRAINT = String.format("Lab number must be between 1 and %d", labTotal);

    private ArrayList<LabScore> labScoreList = new ArrayList<>();

    /**
     * Creates a default LabScoreList object.
     */
    public LabScoreList() {
        for (int i = 0; i < labTotal; i++) {
            labScoreList.add(new LabScore());
        }
    }

    /**
     * Creates a LabScoreList object using the save file values.
     * @param labs the array of values to initialize the list with.
     */
    public LabScoreList(String[] labs) {
        for (int i = 0; i < labs.length; i++) {
            if (labs[i].equals("-")) {
                labScoreList.add(new LabScore());
            } else {
                String[] scoreSplit = labs[i].split("/");
                labScoreList.add(new LabScore(Integer.parseInt(scoreSplit[0]), Integer.parseInt(scoreSplit[1])));
            }
        }
    }

    /**
     * Updates the specified lab with the updated score.
     * @param labNumber The LabScore object to update.
     * @param labScore The updated score for the lab.
     * @return
     */
    public LabScoreList updateLabScore(int labNumber, int labScore) throws CommandException {
        if (labNumber < 1 || labNumber > labTotal) {
            throw new CommandException(String.format(UpdateLabScoreCommand.MESSAGE_INVALID_LAB_NUMBER, labTotal));
        }
        labScoreList.get(labNumber - 1).updateLabScore(labScore);
        return this;
    }

    /**
     * Checks if the lab number is valid.
     * @param labNumber The string of the lab number to verify.
     * @return a boolean.
     */
    public static boolean isValidLabNumber(String labNumber) {
        int labNo;
        try {
            labNo = Integer.parseInt(labNumber);
        } catch (NumberFormatException e) {
            return false;
        }

        return labNo > 0 && labNo < labTotal;
    }

    /**
     * Gets the list of LabScore objects.
     * @return The list of LabScore objects.
     */
    public ArrayList<LabScore> getLabScores() {
        return labScoreList;
    }

    /**
     * Checks if the save string is valid.
     * @param saveString The string to validate if it is correct.
     * @return A boolean showing if the string is valid.
     */
    public static boolean isValidSaveString(String saveString) {
        int total;
        int split = saveString.indexOf(".");
        try {
            if (split == -1) {
                return false;
            }
            total = Integer.parseInt(saveString.substring(0, split));
        } catch (NumberFormatException e) {
            return false;
        }
        String[] labs = saveString.substring(split + 1).split("\\Q|\\E");
        if (labs.length != total) {
            return false;
        }

        for (int i = 0; i < total; i++) {
            if (labs[i].equals("-")) {
                continue;
            }
            String[] scoreSplit = labs[i].split("/");
            if (scoreSplit.length != 2) {
                return false;
            }

            try {
                Integer.parseInt(scoreSplit[0]);
                Integer.parseInt(scoreSplit[1]);
            } catch (NumberFormatException e) {
                return false;
            }

        }

        return true;
    }

    /**
     * Loads the lab scores from the save file.
     * @param saveString The string from the save file.
     * @return LabScoreList object.
     */
    public static LabScoreList loadLabScores(String saveString) {

        int split = saveString.indexOf(".");
        String[] labs = saveString.substring(split + 1).split("\\Q|\\E");
        return new LabScoreList(labs);
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        returnString.append(String.format("%d.", labTotal));
        for (int i = 0; i < labScoreList.size(); i++) {
            returnString.append(labScoreList.get(i).toString());
            if (i == labScoreList.size() - 1) {
                break;
            }
            returnString.append("|");
        }
        return returnString.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LabScoreList)) {
            return false;
        }

        LabScoreList o = (LabScoreList) other;
        for (int i = 0; i < o.labScoreList.size(); i++) {
            if(!labScoreList.get(i).equals(o.labScoreList.get(i))) {
                return false;
            }
        }
        return true;
    }
}
