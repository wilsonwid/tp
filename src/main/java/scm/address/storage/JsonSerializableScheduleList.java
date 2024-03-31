package scm.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import scm.address.commons.exceptions.IllegalValueException;
import scm.address.model.ReadOnlyScheduleList;
import scm.address.model.ScheduleList;
import scm.address.model.schedule.Schedule;

/**
 * An immutable ScheduleList that is serializable to JSON format.
 */
@JsonRootName(value = "schedulestorage")
public class JsonSerializableScheduleList {

    private final List<JsonAdaptedSchedule> schedules = new ArrayList<>();

    /**
     * Constructs a new instance of this class from a given list
     * of schedules.
     *
     * @param schedules List of Schedules to be converted.
     */
    @JsonCreator
    public JsonSerializableScheduleList(@JsonProperty("schedules") List<JsonAdaptedSchedule> schedules) {
        this.schedules.addAll(schedules);
    }

    /**
     * Constructs a new JsonSerializableScheduleList from a given
     * {@code source} {@link ReadOnlyScheduleList}.
     *
     * @param source A ReadOnlyScheduleList to be converted.
     */
    public JsonSerializableScheduleList(ReadOnlyScheduleList source) {
        this.schedules.addAll(source
                .getScheduleList()
                .stream()
                .map(JsonAdaptedSchedule::new)
                .collect(Collectors.toList()));
    }

    /**
     * Returns the model type of this instance to be used inside
     * the application.
     *
     * @return A ScheduleList.
     * @throws IllegalValueException If there are any illegal values detected.
     */
    public ScheduleList toModelType() throws IllegalValueException {
        ScheduleList scheduleList = new ScheduleList();
        for (JsonAdaptedSchedule jsonAdaptedSchedule : schedules) {
            Schedule schedule = jsonAdaptedSchedule.toModelType();
            scheduleList.addSchedule(schedule);
        }
        return scheduleList;
    }
}
