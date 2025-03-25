package seedu.tassist.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's matriculation number in the address book.
 * Compulsory field.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatNum(String)}
 */
public class MatNum {

    public static final String MESSAGE_CONSTRAINTS = "Invalid matriculation number!"
            + "\nMatriculation numbers should start with 'A' and contain 7 digits minimally."
            + "\nThe last character of the matriculation number will"
            + "be automatically generated if not provided.";

    public static final String VALIDATION_REGEX = "^[Aa]\\d{7}[A-Za-z]?$";

    private static final String CHECKSUM_CHARACTERS = "YXWURNMLJHEAB";
    private static final int FULL_LENGTH_MAT_NUM = 9;


    public final String value;

    /**
     * Constructs a {@code MatNum}.
     *
     * @param matNum A valid matriculation number.
     */
    public MatNum(String matNum) {
        requireNonNull(matNum);
        checkArgument(isValidMatNum(matNum), MESSAGE_CONSTRAINTS);
        value = calculateMatNum(matNum.toUpperCase());
    }

    /**
     * Source: https://github.com/nusmodifications/nus-matriculation-number-calculator/blob/gh-pages/matric.js.
     * Validates an NUS matriculation number by checking if it has the correct format
     * and if the checksum digit is valid.
     *
     * @param test The matriculation number to validate
     * @return true if the matriculation number is valid, false otherwise
     */
    public static boolean isValidMatNum(String test) {
        String upperCaseTest = test.toUpperCase();

        boolean validRegex = test.matches(VALIDATION_REGEX);

        if (validRegex) {
            if (upperCaseTest.length() == FULL_LENGTH_MAT_NUM) {
                String calculatedMat = calculateMatNum(upperCaseTest);
                return upperCaseTest.equals(calculatedMat);
            }
            return true;
        }

        return false;
    }

    /**
     * Source: https://github.com/nusmodifications/nus-matriculation-number-calculator/blob/gh-pages/matric.js.
     * Assumes valid input.
     */
    private static String calculateMatNum(String id) {
        String digits = id.substring(2); // remove the first character.

        int sum = 0;
        for (int i = 0; i < 6; i++) {
            sum += Character.getNumericValue(digits.charAt(i));
        }

        char checksumChar = CHECKSUM_CHARACTERS.charAt(sum % 13);

        if (id.length() == FULL_LENGTH_MAT_NUM) {
            String truncatedId = id.substring(0, id.length() - 1);
            return truncatedId + checksumChar;
        }
        return id + checksumChar;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatNum)) {
            return false;
        }

        MatNum otherMatNum = (MatNum) other;
        return value.equals(otherMatNum.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
