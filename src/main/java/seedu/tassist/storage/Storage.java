package seedu.tassist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.tassist.commons.exceptions.DataLoadingException;
import seedu.tassist.model.ReadOnlyAddressBook;
import seedu.tassist.model.ReadOnlyUserPrefs;
import seedu.tassist.model.UserPrefs;

/**
 * API of the Storage component.
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    Optional<ReadOnlyAddressBook> readAddressBookFromCsv(Path filePath) throws DataLoadingException;

    void saveAddressBookToCsv(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;
}
