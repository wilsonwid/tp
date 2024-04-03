package scm.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import scm.address.commons.exceptions.DataLoadingException;
import scm.address.model.ReadOnlyAddressBook;
import scm.address.model.ReadOnlyScheduleList;
import scm.address.model.ReadOnlyUserPrefs;
import scm.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, ScheduleStorage {

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

    @Override
    Path getScheduleStorageFilePath();

    @Override
    Optional<ReadOnlyScheduleList> readScheduleList() throws DataLoadingException;

    @Override
    void saveScheduleList(ReadOnlyScheduleList scheduleList) throws IOException;
}
