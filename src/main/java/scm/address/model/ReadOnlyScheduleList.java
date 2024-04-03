package scm.address.model;

import javafx.collections.ObservableList;
import scm.address.model.schedule.Schedule;

/**
 * Unmodifiable view of a schedule list.
 */
public interface ReadOnlyScheduleList {

    /**
     * Returns an unmodifiable view of the Schedules list.
     *
     * @return An ObservableList of Schedules.
     */
    ObservableList<Schedule> getScheduleList();
}
