package scm.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static scm.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import scm.address.commons.exceptions.IllegalValueException;
import scm.address.commons.util.JsonUtil;
import scm.address.model.ScheduleList;
import scm.address.testutil.TypicalSchedules;

public class JsonSerializableScheduleListTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableScheduleListTest");
    private static final Path TYPICAL_SCHEDULES_FILE = TEST_DATA_FOLDER.resolve("typicalSchedulesScheduleList.json");
    private static final Path INVALID_SCHEDULES_FILE = TEST_DATA_FOLDER.resolve("invalidSchedulesScheduleList.json");

    @Test
    public void toModelType_typicalSchedulesFile_success() throws Exception {
        JsonSerializableScheduleList dataFromFile = JsonUtil.readJsonFile(TYPICAL_SCHEDULES_FILE,
                JsonSerializableScheduleList.class).get();
        ScheduleList scheduleListFromFile = dataFromFile.toModelType();
        ScheduleList typicalScheduleList = TypicalSchedules.getTypicalScheduleList();
        assertEquals(scheduleListFromFile, typicalScheduleList);
    }

    @Test
    public void toModelType_invalidScheduleFile_throwsIllegalValueException() throws Exception {
        JsonSerializableScheduleList dataFromFile = JsonUtil.readJsonFile(INVALID_SCHEDULES_FILE,
                JsonSerializableScheduleList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
