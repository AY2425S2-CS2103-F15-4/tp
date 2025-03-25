package seedu.tassist.logic.parser;

import java.util.function.Predicate;

/**
 * Validates quote pattern in user input.
 */
public class QuotePattern implements Predicate<String> {

    /**
     * Greedily validates that the arguments string has a correct quote pattern.
     * There should be an even number of quotes, ensuring they are paired.
     * Each argument segment between flags should have at most one pair of quotes.
     *
     * @param args The command arguments to validate
     * @return true if the quote pattern is valid, false otherwise
     */
    @Override
    public boolean test(String args) {
        boolean inQuote = false;
        boolean hasQuoteInSegment = false;
        int quoteCount = 0;

        for (int i = 0; i < args.length(); i++) {
            char c = args.charAt(i);

            if (c == '"') {
                quoteCount++;
                inQuote = !inQuote;

                // End of quote.
                if (!inQuote) {
                    // If a complete quote pair was found in this region, invalid input.
                    if (hasQuoteInSegment) {
                        return false;
                    }
                    hasQuoteInSegment = true;
                }
            } else if (c == ' ' && !inQuote) {
                // Checks for next flag boundary.
                if (i + 1 < args.length() && args.charAt(i + 1) == '-') {
                    hasQuoteInSegment = false;
                }
            }
        }

        // Validate: even number of quotes and no unclosed quotes
        return (quoteCount % 2 == 0) && !inQuote;
    }

}
