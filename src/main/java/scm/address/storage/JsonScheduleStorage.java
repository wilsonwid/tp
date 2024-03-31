package scm.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import scm.address.commons.core.LogsCenter;
import scm.address.commons.exceptions.DataLoadingException;
import scm.address.commons.exceptions.IllegalValueException;
import scm.address.commons.util.FileUtil;
import scm.address.commons.util.JsonUtil;
import scm.address.model.ReadOnlyScheduleList;

/**
 * A class to access ScheduleStorage data stored as a JSON file on the hard disk.
 */
public class JsonScheduleStorage implements ScheduleStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonScheduleStorage.class);

    private Path filePath;

    public JsonScheduleStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getScheduleStorageFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<ReadOnlyScheduleList> readScheduleList() throws DataLoadingException {
        return readScheduleList(this.filePath);
    }

    @Override
    public Optional<ReadOnlyScheduleList> readScheduleList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableScheduleList> jsonScheduleList = JsonUtil
                .readJsonFile(this.filePath, JsonSerializableScheduleList.class);

        if (!jsonScheduleList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonScheduleList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + this.filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveScheduleList(ReadOnlyScheduleList scheduleList) throws IOException {
        saveScheduleList(scheduleList, this.filePath);
    }

    @Override
    public void saveScheduleList(ReadOnlyScheduleList scheduleList, Path filePath) throws IOException {
        requireNonNull(scheduleList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableScheduleList(scheduleList), filePath);
    }
}
