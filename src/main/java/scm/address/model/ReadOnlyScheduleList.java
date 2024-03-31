package scm.address.model;

import javafx.collections.ObservableList;
import scm.address.model.schedule.Schedule;

public interface ReadOnlyScheduleList {

    /**
     * Returns an unmodifiable view of the Schedules list.
     *
     * @return An ObservableList of Schedules.
     */
    ObservableList<Schedule> getScheduleList();
}
