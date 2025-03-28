package seedu.tassist.commons.core.index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IndexTest {

    @Test
    public void createOneBasedIndex() {
        // Invalid index.
        assertThrows(IndexOutOfBoundsException.class, () -> Index.fromOneBased(0));

        // Check equality using the same base.
        assertEquals(1, Index.fromOneBased(1).getOneBased());
        assertEquals(5, Index.fromOneBased(5).getOneBased());

        // Convert from one-based index to zero-based index.
        assertEquals(0, Index.fromOneBased(1).getZeroBased());
        assertEquals(4, Index.fromOneBased(5).getZeroBased());
    }

    @Test
    public void createZeroBasedIndex() {
        // Invalid index.
        assertThrows(IndexOutOfBoundsException.class, () -> Index.fromZeroBased(-1));

        // Check equality using the same base.
        assertEquals(0, Index.fromZeroBased(0).getZeroBased());
        assertEquals(5, Index.fromZeroBased(5).getZeroBased());

        // Convert from zero-based index to one-based index.
        assertEquals(1, Index.fromZeroBased(0).getOneBased());
        assertEquals(6, Index.fromZeroBased(5).getOneBased());
    }

    @Test
    public void equalsMethod() {
        final Index fifthPersonIndex = Index.fromOneBased(5);

        // Same values -> returns true.
        assertTrue(fifthPersonIndex.equals(Index.fromOneBased(5)));
        assertTrue(fifthPersonIndex.equals(Index.fromZeroBased(4)));

        // Same object -> returns true.
        assertTrue(fifthPersonIndex.equals(fifthPersonIndex));

        // Null -> returns false.
        assertFalse(fifthPersonIndex.equals(null));

        // Different types -> returns false.
        assertFalse(fifthPersonIndex.equals(5.0f));

        // Different index -> returns false.
        assertFalse(fifthPersonIndex.equals(Index.fromOneBased(1)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromZeroBased(0);
        String expected = Index.class.getCanonicalName() + "{zeroBasedIndex=" + index.getZeroBased() + "}";
        assertEquals(expected, index.toString());
    }
}
