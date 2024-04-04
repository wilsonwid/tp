package scm.address.model.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import scm.address.model.schedule.Description;
import scm.address.model.schedule.Schedule;
import scm.address.model.schedule.Title;

public class CalendarViewTest {
    private class CalendarLogic {
        private List<Schedule> schedules;

        public CalendarLogic(List<Schedule> schedules) {
            this.schedules = schedules;
        }

        public List<Schedule> getSchedulesForDate(LocalDate date) {
            return schedules.stream()
                    .filter(schedule -> !schedule.getStartDateTime()
                            .toLocalDate().isBefore(date) && !schedule.getStartDateTime().toLocalDate().isAfter(date))
                    .collect(Collectors.toList());
        }
    }

    @Test
    public void testGetSchedulesForDate() {
        LocalDateTime start1 = LocalDateTime.of(2024, Month.APRIL, 21, 15, 0);
        LocalDateTime end1 = LocalDateTime.of(2024, Month.APRIL, 21, 16, 0);
        Schedule meeting1 = new Schedule(new Title("Meeting 1"), new Description("PD"), start1, end1);

        LocalDateTime start2 = LocalDateTime.of(2024, Month.APRIL, 22, 10, 0);
        LocalDateTime end2 = LocalDateTime.of(2024, Month.APRIL, 22, 11, 0);
        Schedule meeting2 = new Schedule(new Title("Meeting 2"), new Description("PD"), start2, end2);

        List<Schedule> schedules = Arrays.asList(meeting1, meeting2);
        CalendarLogic calendarLogic = new CalendarLogic(schedules);

        List<Schedule> schedulesForApril21 = calendarLogic.getSchedulesForDate(LocalDate.of(2024,
                Month.APRIL, 21));

        assertEquals(1, schedulesForApril21.size());
        assertEquals("Meeting 1", schedulesForApril21.get(0).getTitle().toString());
    }
}
