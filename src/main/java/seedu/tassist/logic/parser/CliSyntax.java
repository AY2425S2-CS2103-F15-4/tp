package seedu.tassist.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_INDEX = new Prefix("-i");
    public static final Prefix PREFIX_WEEK = new Prefix("-w");
    public static final Prefix PREFIX_ATTENDANCE_LIST = new Prefix("-al");
    public static final Prefix PREFIX_FILENAME = new Prefix("-f ");
    public static final Prefix PREFIX_EXTENSION = new Prefix("-e ");
}
