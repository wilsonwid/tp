package scm.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static scm.address.testutil.Assert.assertThrows;
import static scm.address.testutil.TypicalSchedules.getTypicalScheduleList;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import scm.address.commons.exceptions.DataLoadingException;

public class JsonScheduleStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonScheduleStorageTest");

    private static final Path NON_EXISTENT_FILE = TEST_DATA_FOLDER.resolve("nonExistentFile.json");

    private static final Path NON_JSON_FILE = TEST_DATA_FOLDER.resolve("notJsonFormatScheduleList.json");

    private static final Path INVALID_JSON_FILE = TEST_DATA_FOLDER.resolve("invalidSchedulesScheduleList.json");

    private static final Path TYPICAL_SCHEDULE_FILE = TEST_DATA_FOLDER.resolve("typicalSchedulesScheduleList.json");

    @TempDir
    public Path testFolder;

    @Test
    public void readScheduleList_nullFilePath_throwsNullPointerException() {
        JsonScheduleStorage storage = new JsonScheduleStorage(null);
        assertThrows(NullPointerException.class, () -> storage.readScheduleList());
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        JsonScheduleStorage storage = new JsonScheduleStorage(NON_EXISTENT_FILE);
        assertFalse(storage.readScheduleList().isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        JsonScheduleStorage storage = new JsonScheduleStorage(NON_JSON_FILE);
        assertThrows(DataLoadingException.class, () -> storage.readScheduleList());
    }

    @Test
    public void read_invalidJsonFormat_exceptionThrown() {
        JsonScheduleStorage storage = new JsonScheduleStorage(INVALID_JSON_FILE);
        assertThrows(DataLoadingException.class, () -> storage.readScheduleList());
    }

    @Test
    public void read_typicalScheduleList_success() throws Exception {
        JsonScheduleStorage storage = new JsonScheduleStorage(TYPICAL_SCHEDULE_FILE);
        assertEquals(storage.readScheduleList().get(), getTypicalScheduleList());
    }

    @Test
    public void save_typicalScheduleList_success() throws Exception {
        JsonScheduleStorage storage = new JsonScheduleStorage(TYPICAL_SCHEDULE_FILE);
        storage.saveScheduleList(getTypicalScheduleList());
        assertEquals(storage, storage);
    }
}
