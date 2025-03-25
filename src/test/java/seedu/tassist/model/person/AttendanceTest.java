package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    public void constructor_invalidWeek_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Attendance(14, Attendance.ATTENDED));
    }

    @Test
    public void constructor_invalidAttendance_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Attendance(10, 4));
    }

    @Test
    public void isValidWeek() {

        // Invalid week -> returns false.
        assertFalse(Attendance.isValidWeek(-10000000));
        assertFalse(Attendance.isValidWeek(-1));
        assertFalse(Attendance.isValidWeek(0));
        assertFalse(Attendance.isValidWeek(14));
        assertFalse(Attendance.isValidWeek(15));
        assertFalse(Attendance.isValidWeek(10000000));

        // Valid week -> returns true.
        for (int i = 1; i <= 13; i++) {
            assertTrue(Attendance.isValidWeek(i));
        }
    }

    @Test
    public void isValidAttendance() {
        // Invalid attendance -> returns false.
        assertFalse(Attendance.isValidAttendance(-5));
        assertFalse(Attendance.isValidAttendance(-1));
        assertFalse(Attendance.isValidAttendance(4));
        assertFalse(Attendance.isValidAttendance(10));

        // Valid attendance -> returns true.
        assertTrue(Attendance.isValidAttendance(0)); // Not attended.
        assertTrue(Attendance.isValidAttendance(1)); // Attended.
        assertTrue(Attendance.isValidAttendance(2)); // On MC.
        assertTrue(Attendance.isValidAttendance(3)); // No tutorial.
    }

    @Test
    public void getWeekAsTagPrefix() {
        Attendance attendance = new Attendance(3, Attendance.ATTENDED);

        // Should be "W<week>:"
        assertTrue(attendance.getWeekAsTagPrefix().equals("W3:"));
    }

    @Test
    public void equals() {
        Attendance attendance = new Attendance(3, Attendance.ATTENDED);

        // Same values -> returns true.
        assertTrue(attendance.equals(new Attendance(3, Attendance.ATTENDED)));

        // Same object -> returns true.
        assertTrue(attendance.equals(attendance));

        // Null -> returns false.
        assertFalse(attendance.equals(null));

        // Different types -> returns false.
        assertFalse(attendance.equals(5.0f));

        // Different week -> returns false.
        assertFalse(attendance.equals(new Attendance(5, Attendance.ATTENDED)));

        // Different attendance -> returns false.
        assertFalse(attendance.equals(new Attendance(3, Attendance.NOT_ATTENDED)));
    }
}
