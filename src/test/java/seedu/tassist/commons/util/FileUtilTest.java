package seedu.tassist.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FileUtilTest {

    @Test
    public void isValidPath() {
        // Valid path.
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // Invalid path.
        assertFalse(FileUtil.isValidPath("a\0"));

        // Null path -> throws NullPointerException.
        assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

}
