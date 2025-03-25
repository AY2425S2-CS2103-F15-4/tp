package seedu.tassist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.tassist.commons.core.LogsCenter;
import seedu.tassist.commons.exceptions.DataLoadingException;
import seedu.tassist.model.AddressBook;
import seedu.tassist.model.ReadOnlyAddressBook;
import seedu.tassist.model.ReadOnlyUserPrefs;
import seedu.tassist.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage}
     * and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath)
            throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath)
            throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBookFromCsv(Path filePath)
            throws DataLoadingException {
        logger.fine("Attempting to read CSV data from file: " + filePath);
        try {
            CsvAddressBookStorage tempCsvStorage = new CsvAddressBookStorage(filePath);
            Optional<ReadOnlyAddressBook> optionalAb = tempCsvStorage.readAddressBook();
            AddressBook ab = optionalAb.map(
                    readOnlyAb -> new AddressBook(readOnlyAb))
                    .orElse(new AddressBook());
            return Optional.of(ab);
        } catch (Exception e) {
            throw new DataLoadingException(e);
        }
    }

    @Override
    public void saveAddressBookToCsv(ReadOnlyAddressBook addressBook, Path filePath)
            throws IOException {
        logger.fine("Attempting to write CSV data to file: " + filePath);
        CsvAddressBookStorage tempCsvStorage = new CsvAddressBookStorage(filePath);
        tempCsvStorage.saveAddressBook(addressBook);
    }

}
