package scm.address.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScheduleListTest {
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
