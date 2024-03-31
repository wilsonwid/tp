package scm.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static scm.address.testutil.Assert.assertThrows;
import static scm.address.testutil.TypicalSchedules.MEETING;

import org.junit.jupiter.api.Test;

import scm.address.commons.exceptions.IllegalValueException;
import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

public class JsonAdaptedScheduleTest {
    private static final String INVALID_TITLE = "H@llo";
    private static final String INVALID_DESCRIPTION = "This is an _invalid_ description.";
    private static final String INVALID_START_STRING_TIME = "2024-13-01 13:13";
    private static final String INVALID_END_STRING_TIME = "2024-14-01 20:20";
    private static final String INCORRECT_FORMAT_START_TIME = "00:01 2024-01-01";

    private static final String VALID_TITLE = MEETING.getTitle().toString();
    private static final String VALID_DESCRIPTION = MEETING.getDescription().toString();
    private static final String VALID_START_STRING_TIME = MEETING.getStartDateTime()
            .format(Schedule.DATE_TIME_FORMATTER);
    private static final String VALID_END_STRING_TIME = MEETING.getEndDateTime()
            .format(Schedule.DATE_TIME_FORMATTER);

    @Test
    public void toModelType_validScheduleDetails_success() throws Exception {
        JsonAdaptedSchedule schedule = new JsonAdaptedSchedule(VALID_TITLE, VALID_DESCRIPTION,
                VALID_START_STRING_TIME, VALID_END_STRING_TIME);
        assertEquals(MEETING, schedule.toModelType());
    }

    @Test
    public void constructor_fromSchedule_test() throws Exception {
        JsonAdaptedSchedule schedule = new JsonAdaptedSchedule(MEETING);
        assertEquals(MEETING, schedule.toModelType());
    }

    @Test
    public void toModelType_missingTitle_failure() {
        JsonAdaptedSchedule schedule = new JsonAdaptedSchedule(null,
                VALID_DESCRIPTION, VALID_START_STRING_TIME, VALID_END_STRING_TIME);
        String expectedMessage = String.format(JsonAdaptedSchedule
                .MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_invalidTitle_failure() {
        JsonAdaptedSchedule schedule = new JsonAdaptedSchedule(INVALID_TITLE,
                VALID_DESCRIPTION, VALID_START_STRING_TIME, VALID_END_STRING_TIME);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_missingDescription_failure() {
        JsonAdaptedSchedule schedule = new JsonAdaptedSchedule(VALID_TITLE,
                null, VALID_START_STRING_TIME, VALID_END_STRING_TIME);
        String expectedMessage = String.format(JsonAdaptedSchedule
                .MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_failure() {
        JsonAdaptedSchedule schedule = new JsonAdaptedSchedule(VALID_TITLE,
                INVALID_DESCRIPTION, VALID_START_STRING_TIME, VALID_END_STRING_TIME);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_missingStartTime_failure() {
        JsonAdaptedSchedule schedule = new JsonAdaptedSchedule(VALID_TITLE,
                VALID_DESCRIPTION, null, VALID_END_STRING_TIME);
        String expectedMessage = String.format(JsonAdaptedSchedule
                .MISSING_FIELD_MESSAGE_FORMAT, JsonAdaptedSchedule
                .START_TIME_FIELD_NAME);
        assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_failure() {
        JsonAdaptedSchedule schedule = new JsonAdaptedSchedule(VALID_TITLE,
                VALID_DESCRIPTION, INVALID_START_STRING_TIME, VALID_END_STRING_TIME);
        String expectedMessage = Schedule.DATE_TIME_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_missingEndTime_failure() {
        JsonAdaptedSchedule schedule = new JsonAdaptedSchedule(VALID_TITLE,
                VALID_DESCRIPTION, VALID_START_STRING_TIME, null);
        String expectedMessage = String.format(JsonAdaptedSchedule
                .MISSING_FIELD_MESSAGE_FORMAT, JsonAdaptedSchedule
                .END_TIME_FIELD_NAME);
        assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_failure() {
        JsonAdaptedSchedule schedule = new JsonAdaptedSchedule(VALID_TITLE,
                VALID_DESCRIPTION, VALID_START_STRING_TIME, INVALID_END_STRING_TIME);
        String expectedMessage = Schedule.DATE_TIME_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_incorrectFormatStartTime_failure() {
        JsonAdaptedSchedule schedule = new JsonAdaptedSchedule(VALID_TITLE,
                VALID_DESCRIPTION, INCORRECT_FORMAT_START_TIME, VALID_END_STRING_TIME);
        String expectedMessage = Schedule.DATE_TIME_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }
}
