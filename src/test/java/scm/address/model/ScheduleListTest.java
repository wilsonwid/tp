package scm.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import scm.address.model.schedule.Schedule;

public class ScheduleListTest {

    @Test
    public void returnIterator() {
        ScheduleList schedules = new ScheduleList();
        Iterator<Schedule> iterator = schedules.iterator();
        assertTrue(iterator.equals(iterator));
    }
    @Test
    public void equals() {
        ScheduleList schedules = new ScheduleList();
        assertTrue(schedules.equals(schedules));

        ScheduleList otherSchedules = new ScheduleList();
        assertTrue(schedules.equals(otherSchedules));

        AddressBook addressBook = new AddressBook();
        assertFalse(schedules.equals(addressBook));
    }

    @Test
    public void hashCodeTest() {
        ScheduleList schedules = new ScheduleList();
        assertTrue(schedules.hashCode() == schedules.hashCode());
    }
}
