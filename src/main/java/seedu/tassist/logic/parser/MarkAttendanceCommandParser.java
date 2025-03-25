package seedu.tassist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_NOT_ATTENDED;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_NO_TUTORIAL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_ON_MC;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.commons.exceptions.IllegalValueException;
import seedu.tassist.logic.commands.MarkAttendanceCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.person.Attendance;
import seedu.tassist.model.person.TutGroup;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand object.
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttendanceCommand
     * and returns an MarkAttendanceCommand object for execution.
     *
     * @param args String input to be parsed
     * @return MarkAttendanceCommand corresponding to the String input
     * @throws ParseException If the user input does not conform to the expected format
     */
    public MarkAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_INDEX, PREFIX_TUT_GROUP, PREFIX_WEEK,
                PREFIX_MARK_NOT_ATTENDED, PREFIX_MARK_ON_MC, PREFIX_MARK_NO_TUTORIAL);

        Index index = null;
        TutGroup tutGroup = null;
        int week;
        boolean hasIndex = argMultimap.getValue(PREFIX_INDEX).isPresent();
        boolean hasTutGroup = argMultimap.getValue(PREFIX_TUT_GROUP).isPresent();
        boolean isNotAttended = argMultimap.getValue(PREFIX_MARK_NOT_ATTENDED).isPresent();
        boolean isOnMc = argMultimap.getValue(PREFIX_MARK_ON_MC).isPresent();
        boolean isNoTut = argMultimap.getValue(PREFIX_MARK_NO_TUTORIAL).isPresent();

        boolean hasAtLeastTwoConflictingFlags = (isNotAttended && isOnMc) || (isNotAttended && isNoTut)
                || (isOnMc && isNoTut) || (hasIndex && hasTutGroup)
                || (hasIndex && isNoTut); // Cannot set Attendance as No Tutorial for Index commands.
        boolean hasNeitherIndexNorTutGroup = !hasIndex && !hasTutGroup;

        if (hasAtLeastTwoConflictingFlags || hasNeitherIndexNorTutGroup) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MarkAttendanceCommand.MESSAGE_USAGE)
            );
        }

        try {
            week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).orElse(""));
            if (hasIndex) {
                index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).orElse(""));
            } else {
                tutGroup = ParserUtil.parseTutGroup(argMultimap.getValue(PREFIX_TUT_GROUP).orElse(""));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MarkAttendanceCommand.MESSAGE_USAGE), ive
            );
        }

        if (hasIndex) {
            if (isNotAttended) {
                return new MarkAttendanceCommand(index, week, Attendance.NOT_ATTENDED);
            } else if (isOnMc) {
                return new MarkAttendanceCommand(index, week, Attendance.ON_MC);
            } else {
                return new MarkAttendanceCommand(index, week, Attendance.ATTENDED);
            }
        } else {
            if (isNotAttended) {
                return new MarkAttendanceCommand(tutGroup, week, Attendance.NOT_ATTENDED);
            } else if (isOnMc) {
                return new MarkAttendanceCommand(tutGroup, week, Attendance.ON_MC);
            } else if (isNoTut) {
                return new MarkAttendanceCommand(tutGroup, week, Attendance.NO_TUTORIAL);
            } else {
                return new MarkAttendanceCommand(tutGroup, week, Attendance.ATTENDED);
            }
        }
    }
}
