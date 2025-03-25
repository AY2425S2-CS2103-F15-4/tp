package seedu.tassist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TELE_HANDLE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.commands.EditCommand;
import seedu.tassist.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object.
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_INDEX, PREFIX_NAME, PREFIX_PHONE, PREFIX_TELE_HANDLE, PREFIX_EMAIL,
                        PREFIX_MAT_NUM, PREFIX_TUT_GROUP, PREFIX_LAB_GROUP, PREFIX_FACULTY,
                        PREFIX_YEAR, PREFIX_REMARK, PREFIX_TAG
                );

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).orElse(""));
        } catch (ParseException pe) {
            throw new ParseException(Index.MESSAGE_CONSTRAINTS, pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_INDEX, PREFIX_NAME, PREFIX_PHONE, PREFIX_TELE_HANDLE, PREFIX_EMAIL,
                PREFIX_MAT_NUM, PREFIX_TUT_GROUP, PREFIX_LAB_GROUP, PREFIX_FACULTY,
                PREFIX_YEAR, PREFIX_REMARK
        );

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(
                    argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(
                    argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_TELE_HANDLE).isPresent()) {
            editPersonDescriptor.setTeleHandle(ParserUtil.parseTeleHandle(
                    argMultimap.getValue(PREFIX_TELE_HANDLE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(
                    argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_MAT_NUM).isPresent()) {
            editPersonDescriptor.setMatNum(ParserUtil.parseMatNum(
                    argMultimap.getValue(PREFIX_MAT_NUM).get()));
        }
        if (argMultimap.getValue(PREFIX_TUT_GROUP).isPresent()) {
            editPersonDescriptor.setTutGroup(ParserUtil.parseTutGroup(
                    argMultimap.getValue(PREFIX_TUT_GROUP).get()));
        }
        if (argMultimap.getValue(PREFIX_LAB_GROUP).isPresent()) {
            editPersonDescriptor.setLabGroup(ParserUtil.parseLabGroup(
                    argMultimap.getValue(PREFIX_LAB_GROUP).get()));
        }
        if (argMultimap.getValue(PREFIX_FACULTY).isPresent()) {
            editPersonDescriptor.setFaculty(ParserUtil.parseFaculty(
                    argMultimap.getValue(PREFIX_FACULTY).get()));
        }
        if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            editPersonDescriptor.setYear(ParserUtil.parseYear(
                    argMultimap.getValue(PREFIX_YEAR).get()));
        }
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editPersonDescriptor.setRemark(ParserUtil.parseRemark(
                    argMultimap.getValue(PREFIX_REMARK).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG))
                .ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1
                && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
