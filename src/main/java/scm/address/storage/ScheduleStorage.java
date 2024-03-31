package scm.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import scm.address.commons.exceptions.DataLoadingException;
import scm.address.model.ReadOnlyScheduleList;

/**
 * Represents a storage for {@link scm.address.model.ScheduleList}.
 */
public interface ScheduleStorage {

    /**
     * Returns the filepath of the data file.
     *
     * @return A Path.
     */
    Path getScheduleStorageFilePath();

    /**
     * Returns the schedule list data as a {@link ReadOnlyScheduleList}
     * Returns {@code Optional.empty()} if the storage file is not found.
     *
     * @return An Optional of ReadOnlyScheduleList.
     * @throws DataLoadingException If loading the data from storage failed.
     */
    Optional<ReadOnlyScheduleList> readScheduleList() throws DataLoadingException;

    /**
     * Returns the schedule list as a {@link ReadOnlyScheduleList}
     * Returns {@code Optional.empty()} if the storage file is not found.
     * This overloaded method allows reading from {@code filePath}.
     *
     * @param filePath The filepath for the schedule list.
     * @return An Optional of ReadOnlyScheduleList.
     * @throws DataLoadingException If loading the data from storage failed.
     */
    Optional<ReadOnlyScheduleList> readScheduleList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@code scheduleList}to the storage.
     *
     * @param scheduleList The schedule list, which cannot be null.
     * @throws IOException If there are any problems saving.
     */
    void saveScheduleList(ReadOnlyScheduleList scheduleList) throws IOException;

    /**
     * Saves the given {@code scheduleList} to the storage.
     * This overloaded method allows one to indicate the {@code filePath} for this to be saved to.
     *
     * @param scheduleList The schedule list, which cannot be null.
     * @param filePath The filepath for the schedule list to be saved to.
     * @throws IOException If there are any problems saving.
     */
    void saveScheduleList(ReadOnlyScheduleList scheduleList, Path filePath) throws IOException;
}
